from sklearn.svm import SVC
import pandas as pd
# from sklearn.model_selection import train_test_split
# from sklearn.metrics import accuracy_score

#데이터 불러오기
df = pd.read_csv("./personal color2.csv")
label = df["season"]
data = df[["red", "green", "blue"]]
#train_data, valid_data, train_label, valid_label = train_test_split(data, label)

#학습시키기
model = SVC() # Support Vector Machine Classifier
model.fit(data, label) #train_data, train_label

# 예측시켜보기
  #(valid_data)
result = model.predict([[62, 63, 103], [16 ,81, 41]])

#정확도 확인하기
# score = accuracy_score(result, valid_label)

print(result)