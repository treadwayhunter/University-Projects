# main.py will be the main file for testing against the ML model
import pandas as pd
from joblib import load
# Source IP,Dest IP,Source Port,Dst Port,Protocol,Sequence #,Next Seq #,Ack #,Flags,Length,Time,Time Since Last Seq,Time Since Last Pack,Label
# 2623878229,2886729729,0,0,1,0,0,0,0,0,1700582062.068808,0,0.000001,Malicious
model = load('/home/hunter/Desktop/project/DataCollection/data/pkl/combinedFinal.pkl')

data = {
    #'Source IP': [2623878229],
    #'Dest IP': [2886729729],
    #'Source Port': [0],
    #'Dst Port': [0],
    'Protocol': [1],
    #'Sequence #': [0],
    #'Next Seq #': [0],
    #'Ack #': [0],
    'Flags': [0],
    'Length': [0],
    #'Time': [0],
    'Time Since Last Seq': [0],
    'Time Since Last Pack': [0.00001],
}

input_data = pd.DataFrame(data)
prediction = model.predict(input_data)
#print(input_data)
#prediction = model.predict([[0.15, 0.12, 0.18531254, 0.17, 0.1, 0.16951956, 0.09, 0.0, 0.00936295, 0.00580494, 0.00]])
print(prediction)
print('Predicted Label: ', prediction[0])
print()

importances = model.feature_importances_
print(importances)