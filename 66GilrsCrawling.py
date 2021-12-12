from bs4 import BeautifulSoup 
import requests
import openpyxl

wb = openpyxl.Workbook()
sheet = wb.active
sheet.append(["상품코드", "카테고리1", "카테고리2", "색상코드", "상품명", "가격", "이미지url", "사이트url"])

headers = {'User-Agent': 'Mozilla/5.0'}

url = 'https://66girls.co.kr/'

result = requests.get(url, headers=headers)
result_text = result.text

soup = BeautifulSoup(result_text, 'html.parser')

#큰 카테고리 가져오기
category = soup.select('ul.nav.d1_wrap > li.d1.xans-record- > a')

for category1, category1_name in zip(category, category):
    category1 = category1['href']
    category1_name = category1_name.text

    url = 'https://66girls.co.kr/' + category1

    result = requests.get(url, headers=headers)
    result_text = result.text

    soup = BeautifulSoup(result_text, 'html.parser')

    #작은 카테고리 가져오기
    small_category = soup.select('ul.menuCategory > li > a')

    for category2, category2_name in zip(small_category, small_category):
        category2 = category2['href']
        category2_name = category2_name.text.replace(' ()', '')
        url = 'https://66girls.co.kr/' + category2

        result = requests.get(url, headers=headers)
        result_text = result.text

        soup = BeautifulSoup(result_text, 'html.parser')

        #생상 카테고리 가져오기
        color_list = soup.select('td.colorChip > div.cell > ul.xans-element-.xans-product.xans-product-filterform.content > li.xans-record- > button')

        for each_colorChip in color_list :
            each_colorChip =  each_colorChip['svalue']
            each_colorChip = each_colorChip[7:13]

            index = category2.find('cate')
            url = 'https://66girls.co.kr/' + category2[:index] + 'keyword=&search_form%5Boption_data%5D%5B%5D=color%253D%2523' + each_colorChip + '&' + category2[index:]

            result = requests.get(url, headers=headers)
            result_text = result.text

            soup = BeautifulSoup(result_text, 'html.parser')

            name = soup.select('ul.prdList.grid4 > li.xans-record- > div.box > div.description >  strong.name > a')
            price = soup.select('ul.prdList.grid4 > li.xans-record- > div.box > div.description > ul.xans-element-.xans-product.xans-product-listitem.spec.info_control')
            img = soup.select('ul.prdList.grid4 > li.xans-record- > div.box > div.thumbnail > div.prdImg > a > img')
            page = soup.select('ul.prdList.grid4 > li.xans-record- > div.box > div.thumbnail > div.prdImg > a')

            #상품명, 상품가격, 상품이미지 URL, 상품 URL 가져오기
            for each_name,each_price,each_img,each_page in zip(name,price,img,page):
                each_name = each_name('span')[2]
                each_name = each_name.text

                each_price = each_price('span')[1] 
                each_price = each_price.text.replace(',','')
                each_price = each_price.replace('원','')

                each_img = each_img['src']
                each_page =  each_page['href']
                
                url = 'https://66girls.co.kr' + each_page

                result = requests.get(url, headers=headers)
                result_text = result.text

                soup = BeautifulSoup(result_text, 'html.parser')
                
                code = soup.select('div.infoArea > div.xans-element-.xans-product.xans-product-detaildesign > table.prd_info > tbody')
                
                #상품 코드 가져오기
                for each_code in code:
                    ex = each_code('span')[4]
                    ex = ex.text
                    if ex == '할인판매가':
                        each_code = each_code('span')[13]
                    else:
                        each_code = each_code('span')[8]

                    each_code = each_code.text
                
                    h = each_code
                    a = category1_name
                    b = category2_name
                    c = each_colorChip
                    d = each_name
                    e = each_price
                    f = each_img
                    g = each_page

                    sheet.append([h, a, b, c, d, e, f, g])

                    print(each_code, category1_name, category2_name, each_colorChip, each_name, each_price, each_img, each_page)

# 5. 작업 마친 후 파일 저장 (파일명 저장할 때 확장자 - .xlsx 필수 !) 
wb.save("66Girls.xlsx")