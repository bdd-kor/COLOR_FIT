from collections import OrderedDict
import numpy as np
import cv2
import sys
#sys.path.append("/usr/local/python3.7/dist-packages")
import dlib
import imutils
from PIL import Image
import face_recognition
import tensorflow.keras
from PIL import ImageOps

import warnings
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
warnings.filterwarnings(action='ignore')

def crop(image):
    image = face_recognition.load_image_file(image)
    face_locations = face_recognition.face_locations(image)
    for face_location in face_locations:
        top, right, bottom, left = face_location
        face_image = image[top:bottom, left:right]
        pil_image = Image.fromarray(face_image)
        pil_image.save('C:/Apache24/htdocs/www/uploads/crop.jpg')

    
def cheek(image):
    CHEEK_IDXS = OrderedDict([("right_cheek", (12,14,47,35))])
    detector = dlib.get_frontal_face_detector()
    predictor = dlib.shape_predictor("C:/Apache24/htdocs/www/shape_predictor_68_face_landmarks.dat")
    img = cv2.imread(image)
    img = imutils.resize(img, width=500)
    overlay = img.copy()
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    detections = detector(gray, 0)
    
    for k,d in enumerate(detections):
        shape = predictor(gray, d)
        pts = np.zeros((len(CHEEK_IDXS['right_cheek']), 2), np.int32)
        
        for i,j in enumerate(CHEEK_IDXS['right_cheek']):
            pts[i] = [shape.part(j).x, shape.part(j).y] 
            
        aa = np.array(pts)
        pts = pts.reshape((-1, 1, 2))
        rect = cv2.boundingRect(aa)
        x, y, w, h = rect
        croped = img[y:y + h, x:x + w].copy()
        aa = aa - aa.min(axis=0)
        mask = np.zeros(croped.shape[:2], np.uint8)
        cv2.drawContours(mask, [aa], -1, (255, 255, 255), -1, cv2.LINE_AA)
        dst = cv2.bitwise_and(croped, croped, mask=mask)
        dst = imutils.resize(dst, width=600)
        cv2.imwrite("C:/Apache24/htdocs/www/uploads/dst.jpg", dst)


def center(image):
    image = Image.open(image)
    width = 500
    height = 500
    left = (width - 100)/2
    top = (height - 100)/2
    right = (width + 100)/2
    bottom = (height + 100)/2
    image = image.crop((left, top, right, bottom))
    image.save('C:/Apache24/htdocs/www/uploads/center.jpg')

        
def avg(image):
    avg = cv2.mean(image)
    #print(avg)
    rec = np.zeros((500, 500,3), np.uint8)
    rec = cv2.rectangle(rec, (0, 0), (500, 500), avg, -1)
    cv2.imwrite("C:/Apache24/htdocs/www/uploads/skin_color.jpg", rec)

crop('C:/Apache24/htdocs/www/uploads/pcimage.jpg')
cheek('C:/Apache24/htdocs/www/uploads/crop.jpg')
center('C:/Apache24/htdocs/www/uploads/dst.jpg')

face_color = cv2.imread('C:/Apache24/htdocs/www/uploads/center.jpg')
avg(face_color)




model_filename = 'C:/Apache24/htdocs/www/keras_model.h5'
model = tensorflow.keras.models.load_model(model_filename, compile=False)
data = np.ndarray(shape=(1, 224, 224, 3), dtype=np.float)
image = Image.open('C:/Apache24/htdocs/www/uploads/skin_color.jpg')
size = (224, 224)
image = ImageOps.fit(image, size, Image.ANTIALIAS)
image_array = np.asarray(image)
normalized_image_array = (image_array.astype(np.float32) / 127.0) -1
data[0] = normalized_image_array
prediction = model.predict(data)
print(prediction) # 각 확률
"""
if prediction[0,0] == max(prediction[0]):
    print('spr') #봄
elif prediction[0,1] == max(prediction[0]):
    print('sum') #여름
elif prediction[0,2] == max(prediction[0]):
    print('fal') #가을
elif prediction[0,3] == max(prediction[0]):
    print('win') #겨울
"""
i=0
if prediction[0,0] == max(prediction[0]):
    i=0
elif prediction[0,1] == max(prediction[0]):
    i=1
elif prediction[0,2] == max(prediction[0]):
    i=2
elif prediction[0,3] == max(prediction[0]):
    i=3

sys.stdout = open('uploads/pc.txt', 'w')

print(i)

sys.stdout.close()
