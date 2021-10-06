# -*- coding: utf-8 -*-
import pandas as pd
import numpy as np
import os
import urllib.request
from PIL import Image
import math
import netbios
import cv2
import sys
import glob
import re


###################################
### Utilify functions
###################################
def get_hue(a, b):
    if a == 0 or b == 0:
        h = 0
    else:
        h = math.atan2(b, a)
        h = (h / math.pi) * 180
    return h


def CIE2000_distance(lab1, lab2):
    lab1 = [lab1[0] / 255 * 100.0, lab1[1] - 128, lab1[2] - 128]
    lab2 = [lab2[0] / 255 * 100.0, lab2[1] - 128, lab2[2] - 128]

    c1 = math.sqrt(lab1[1] ** 2 + lab1[2] ** 2)
    c2 = math.sqrt(lab2[1] ** 2 + lab2[2] ** 2)

    c_mean = (c1 + c2) / 2.0

    G = 0.5 * (1 - math.sqrt(c_mean ** 7 / float(c_mean ** 7 + 25 ** 7)))

    a_1 = (1 + G) * lab1[1]
    a_2 = (1 + G) * lab2[1]

    C_prime_1 = math.sqrt(a_1 ** 2 + lab1[2] ** 2)
    C_prime_2 = math.sqrt(a_2 ** 2 + lab2[2] ** 2)

    h_1 = get_hue(a_1, lab1[2])
    h_2 = get_hue(a_2, lab2[2])

    delta_L = lab2[0] - lab1[0]
    delta_C = C_prime_2 - C_prime_1

    if C_prime_1 * C_prime_2 == 0:
        delta_h = 0
    else:
        if abs(h_2 - h_1) <= 180:
            delta_h = h_2 - h_1
        elif h_2 - h_1 > 180:
            delta_h = h_2 - h_1 - 360
        else:
            delta_h = h_2 - h_1 + 360

    delta_H = 2 * math.sqrt(c1 * c2) * math.sin(delta_h * math.pi / 2.0 * 180)

    l_mean = (lab1[0] + lab2[0]) / 2.0
    c_prime_mean = (C_prime_1 + C_prime_2) / 2.0

    if C_prime_1 * C_prime_2 == 0:
        h_mean = h_1 + h_2
    else:
        if abs(h_1 - h_2) <= 180:
            h_mean = (h_1 + h_2) / 2.0
        else:
            if h_1 + h_2 < 360:
                h_mean = (h_1 + h_2 + 360) / 2.0
            else:
                h_mean = (h_1 + h_2 - 360) / 2.0

    T = 1 - 0.17 * math.cos((h_mean - 30) * math.pi / 180.0) \
        + 0.24 * math.cos(2 * h_mean * math.pi / 180.0) \
        + 0.32 * math.cos((3 * h_mean + 6) * math.pi / 180.0) \
        - 0.2 * math.cos((4 * h_mean - 63) * math.pi / 180.0)

    delta_Phi = 30 * math.exp(-((h_mean - 275) / 25.0) ** 2)
    R_c = 2 * math.sqrt(c_prime_mean ** 7 / float(c_prime_mean ** 7 + 25 ** 7))
    S_l = 1 + (0.015 * (l_mean - 50) ** 2) / math.sqrt(20 + (l_mean - 50) ** 2)
    S_c = 1 + 0.045 * c_prime_mean
    S_h = 1 + 0.015 * c_prime_mean * T
    R_t = -math.sin(2 * delta_Phi * math.pi / 180.0) * R_c

    distance = math.sqrt((delta_L / S_l) ** 2
                         + (delta_C / S_c) ** 2
                         + (delta_H / S_h) ** 2
                         + R_t * (delta_C / S_c) * (delta_H / S_h))
    return distance

def LAB_shadow(LAB_color_1, LAB_color_2):
    # when a color is darker the values in A and B remains almost the same
    # but the value in Lightness changes more

    threshold_L = 70
    threshold_A = 15
    threshold_B = 20

    distance_in_L = math.fabs(LAB_color_1[0] - LAB_color_2[0])
    distance_in_A = math.fabs(LAB_color_1[1] - LAB_color_2[1])
    distance_in_B = math.fabs(LAB_color_1[2] - LAB_color_2[2])

    if distance_in_L < threshold_L \
            and distance_in_A < threshold_A \
            and distance_in_B < threshold_B:
        return True

    return False


# 세호의견 크롤링을 함과 동시에 이미지 처리.

# 이미지 읽기
def cloth_color(image_path, expected_size=40, in_clusters=7, out_clusters=3, final_size=500):
    initial_image = cv2.imread(image_path)

    if initial_image is not None:
        height, width = initial_image.shape[:2]

        factor = math.sqrt(width * height / (expected_size * expected_size))
        # print("\nfactor:", factor)

        # image downsample
        image = cv2.resize(initial_image,
                           (int(width / factor), int(height / factor)),
                           interpolation=cv2.INTER_LINEAR)
        # image = cv2.resize(initial_image,
        #                    (int(width / factor), int(height / factor)),
        #                    interpolation=cv2.INTER_LINEAR)
        # BGR에서 Lab로 변환
        LAB_image = cv2.cvtColor(image, cv2.COLOR_BGR2Lab)
        # 프레임 폭 정의
        frame_width = int(expected_size / 10 + 9)
        # print("\nframe_width:", frame_width)
        in_samples = []
        border_samples = []
        limit_Y = LAB_image.shape[0] - frame_width
        limit_X = LAB_image.shape[1] - frame_width
        # print("\nlimit_Y: {}, limit_X: {}:".format(limit_Y, limit_X))
        # 테두리 샘플 및 내부 이미지 샘플 추출
        for y in range(LAB_image.shape[0] - 1):
            for x in range(LAB_image.shape[1] - 1):
                pt = LAB_image[y][x]
                if x < frame_width or y < frame_width or y >= limit_Y or x >= limit_X:

                    border_samples.append(pt)
                else:
                    in_samples.append(pt)

        in_samples = np.array(in_samples, dtype=float)
        # print("\nin_samples:", in_samples)
        border_samples = np.array(border_samples, dtype=float)
        # print("\nborder_samples:", border_samples)
        # em알고리즘을 사용하여 클러스터링 수행
        em_in = cv2.ml.EM_create()
        em_in.setClustersNumber(in_clusters)

        in_etval, in_likelihoods, in_labels, in_probs = em_in.trainEM(in_samples)
        # print("\nin_likelihoods: {}, in_labels: {}, in_probs: {}:".format(in_likelihoods[:20], in_labels[:20],
        #                                                                  in_probs[:20]))

        in_means = em_in.getMeans()
        in_covs = em_in.getCovs()
        # print("\nin_means: {}, in_covs: {}".format(in_means, in_covs))
        em_border = cv2.ml.EM_create()
        em_border.setClustersNumber(out_clusters)
        border_etval, border_likelihoods, border_labels, border_probs = em_border.trainEM(border_samples)
        # print("\nborder_likelihoods: {}, border_labels: {}, border_probs: {}:".format(border_likelihoods[:20],
        #                                                                              border_labels[:20],
        #                                                                              border_probs[:20]))

        border_means = em_border.getMeans()
        # print("\nborder_means: {}".format(in_means))
        # 라벨 및 라벨 빈도 식별(카운트)
        unique_border, counts_border = np.unique(border_labels, return_counts=True)
        count_border_labels = dict(zip(unique_border, counts_border))
        # print("\nunique_border_labels: counts_border: {}".format(count_border_labels))

        unique, counts = np.unique(in_labels, return_counts=True)

        count_in_labels = dict(zip(unique, counts))
        # print("\nunique_in_labels: counts_in: {}".format(count_in_labels))
        in_len = len(in_covs)
        # print("in_len: {}".format(in_len))
        # 값 배열 설정 여기서 길이는 레이블 수
        valid = [True] * in_len
        # print("\nvalid: {}".format(valid))

        # 색상 대 배경
        # 각 in_label에 대해 비율을 계산합니다. 비율이 너무 작으면 제거합니다. 유사한 색상을 제거합니다.
        # 픽셀이 그림자 또는 이와 유사한 것에 속하는 경우, 다른 픽셀(<-> 테두리)로 간주한다.
        for i in range(in_len):
            if not valid[i]:
                continue

            prop_in = float(count_in_labels.get(i,0)) / len(in_labels)
            # print("\n[{}] prop_in: {}".format(i, prop_in))

            # 비율이 너무 작은 경우 삭제
            if prop_in < 0.15:
                valid[i] = False
                continue

            # 유사한색상 제거
            for key in count_border_labels:
                prop_border = float(count_border_labels[key]) / len(border_labels)
                # print("\n[i: {}, key: {}] prop_border: {}".format(i, key, prop_border))

                # if the color appears more in the center, it belongs to the cloth
                # else is background
                if prop_in > prop_border:
                    continue

                cie_dst = CIE2000_distance(in_means[i], border_means[key])
                # print("\n[i: {}, key: {}] cie_dst: {}".format(i, key, cie_dst))
                # 이 값을 줄여주면 색을 더 민감하게 뽑을 수 있다.
                if cie_dst < 5:
                    valid[i] = False

        # colors vs colors
        for i in range(in_len):
            if not valid[i]:
                continue

            for j in range(i + 1, in_len):
                if not valid[j]:
                    continue

                # 그림자와 비슷한 색상 제거
                cie_dst = CIE2000_distance(in_means[i], in_means[j])
                # print("\n[i: {}, j: {}] cie_dst: {}".format(i, j, cie_dst))

                is_shadow = LAB_shadow(in_means[i], in_means[j])

                if is_shadow or cie_dst < 20:
                    if count_in_labels[j] > count_in_labels[i]:
                        valid[i] = False

                        count_in_labels[j] += count_in_labels[i]
                        # print("\n[i: {}, j: {}] IF (count_in_labels[j] > count_in_labels[i]) => count_in_labels[j]: {}".format(i, j, count_in_labels[j]))

                        break
                    else:
                        valid[j] = False
                        count_in_labels[i] += count_in_labels[j]
                        # print("\n[i: {}, j: {}] ELSE => count_in_labels[i]: {}".format(i, j, count_in_labels[i]))

        num_valid = sum(True == x for x in valid)
        # print("\nnum_valid: {}".format(num_valid))

        colors = []
        proportions = []
        total_color = 0

        # 옷이 배경과 같은 색이면 더 일반적인 색을 취합니다.
        if num_valid == 0:
            pos = max(count_in_labels, key=count_in_labels.get)
            colors = [in_means[pos]]
            proportions = [count_in_labels[pos]]
            total_color = count_in_labels[pos]
            # print("\npos: {}, colors: {}, propertions: {}, total_color: {}".format(pos, colors, proportions, total_color))

        # 색상, 비율, 총 색을 계산한다.
        for i in range(in_len):

            if not valid[i]:
                continue
            color = in_means[i]
            quantity = count_in_labels[i]
            colors.append(color)
            proportions.append(quantity)
            total_color += quantity
            # print("\n[i: {}] color: {}, quantity: {}, colors: {}, \nproportions: {}, total_color: {}".format(i, color,
            #                                                                                                 quantity,
            #                                                                                                 colors,
            #                                                                                                 proportions,
            #                                                                                                 total_color))

            # 크기를 조정하여 이미지를 업샘플링한다.
        factor = max(1, math.sqrt(width * height / (final_size * final_size)))
        # print("\nfactor: {}".format(factor))
        # final_heigth, final_width, colors_witdth를 계산한다.
        final_image = cv2.resize(initial_image,
                                 (int(width / factor),
                                  int(height / factor)),
                                 interpolation=cv2.INTER_LINEAR)
        final_height, final_width = final_image.shape[:2]
        # print("\nfinal_height: {}, final_width: {}".format(final_height, final_width))
        colors_width = int(final_width / 1.0)
        print("\ncolors_width: {}".format(colors_width))
        #테두리 프레임을 가져온다.
        # image_with_border = cv2.copyMakeBorder(final_image,
        #                                       top=0,
        #                                       bottom=0,
        #                                       left=0,
        #                                       right=colors_width,
        #                                       borderType=cv2.BORDER_CONSTANT,
        #                                       value=[0.0, 0.0, 0.0])
        # y_color = 0
        for i, color_LAB in enumerate(colors):
            color_LAB = np.array([[[color_LAB[0], color_LAB[1], color_LAB[2]]]])
            color_LAB = color_LAB.astype(np.uint8)
            color = cv2.cvtColor(color_LAB, cv2.COLOR_Lab2BGR)[0][0]
            color2 = np.uint8([[color]])
            color = color.tolist()
            hsv = cv2.cvtColor(color2, cv2.COLOR_BGR2HSV)
            height_color = int(math.ceil(proportions[i] * final_height / float(total_color)))
            # print("\n[i: {}, color_LAB: {}] color: {} hsv: {} height_color: {}".format(i, color_LAB, color, hsv, height_color))
            # print("hsv:", hsv)
            # a = (str(hsv))
            # print("a = {}, a[7:10] = {}, a[11:14] = {}".format(a, a[7:10], a[11:14]))
            # 17개
            # s = int(a[7:10])
            # v = int(a[11:14])
            s = np.squeeze(hsv)[1]
            v = np.squeeze(hsv)[2]
            print(s, v)
            # 0~127 저
            # 128~255 고
            # 봄 (고 고)
            if (s >= 90):
                if (v >= 127):
                    # print("봄")
                    ret_val = '봄'
                else:
                    # print("겨울")
                    ret_val = '겨울'
            else:
                if (v >= 127):
                    # print("여름")
                    ret_val = '여름'
                else:
                    # print("가을")
                    ret_val = '가을'

            # cv2.rectangle(image_with_border,
            #              (final_width, y_color),
            #              (final_width + colors_width, y_color + height_color),
            #              color,
            #              -1)
            #
            # rec = np.zeros((500, 500, 3), np.uint8)
            # rec = cv2.rectangle(rec, (0, 0), (final_width + colors_width, y_color + height_color),color, -1)
            # y_color += height_color
            # print("\n[i: {}, color_LAB: {}] y_color: {}".format(i, color_LAB, y_color))
            # cv2.imshow("colors", image_with_border)
            # cv2.imshow("o", rec)
            # cv2.waitKey(0)
            # cv2.destroyAllWindows()
    return ret_val


# base = r'xxxr\첨부파일'
# path = os.path.join(base, '66Girls.xlsx')

base = r'C:/Users/FAMILY/PycharmProjects/pythonProject9/color_cloth-master'
path = os.path.join(base, '원피스.xlsx')
# 엑셀 파일 읽기
df = pd.read_excel(path, engine='openpyxl')
image_url = df['이미지url']

# 각 이미지 처리
for i in range(len(image_url)):
    print(image_url.iloc[i])  # for debugging

    # 프로토콜 정보 추가
    image_path = 'http:' + image_url
    print(image_path[i])

    # 이미지 가져오기
    imgURL = image_path[i]
    urllib.request.urlretrieve(imgURL, "./curr_image.gif")

    # 키 프레임 추출
    num_key_frames = 1
    im = Image.open('./curr_image.gif')
    im.seek(0)
    im.save('frame0.png')

    # personal color 가져오기
    rvalue = cloth_color('./frame0.png')
    print("rvalue of cloth_color: ", rvalue)
    df.loc[i, 'season'] = rvalue

#기존 크롤링 엑셀파일에 저장
df.to_excel('./원피스.xlsx')
