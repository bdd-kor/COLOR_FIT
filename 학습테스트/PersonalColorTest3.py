from sklearn.svm import SVC
from tensorflow import keras
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

#데이터 불러오기
df = pd.read_csv("./personal color2.csv")
label = df["season"]
data = df[["red", "green", "blue"]]
train_data, valid_data, train_label, valid_label = train_test_split(data, label)

# mm = keras.models.Sequential()
# mm.add(keras.layers.Dense(1, input_shape=(1,)))
# mm.compile('SGD', 'mse')
# mm.fit(x, y, epochs=1000)

#학습시키기
#model = SVC() # Support Vector Machine Classifier
model = keras.models.Sequential()
model.add(keras.layers.Dense(1, input_shape=(1, 3, )))
model.compile('SGD', 'mse')
model.fit(train_data, train_label, epochs=1000)

# 예측시켜보기
  #(valid_data)
result = model.predict(valid_data)

#정확도 확인하기
score = accuracy_score(result, valid_label)

print(score)