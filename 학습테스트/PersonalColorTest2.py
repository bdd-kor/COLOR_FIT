#구체적 데이터 넣어서 확인
from sklearn.svm import SVC
import pandas as pd

#데이터 불러오기
df = pd.read_csv("./personal color2.csv")
label = df["season"]
data = df[["red", "green", "blue"]]

#학습시키기
model = SVC() # Support Vector Machine Classifier
model.fit(data, label)

# 예측시켜보기
result = model.predict([[45, 245, 34], [45, 78, 100]])

print(result)