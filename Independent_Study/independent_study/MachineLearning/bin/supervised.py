# Creates a supervised learning model and saves it as a pkl file

import pandas as pd
import pickle
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import classification_report, confusion_matrix, accuracy_score
print('Starting supervised.py...')
print('Opening csv file may take awhile')
file = 'combinedFinal'
data = pd.read_csv(f'/home/hunter/Desktop/project/DataCollection/data/csv/{file}.csv') # FIX THIS
print('CSV file opened!')
print('Dropping unnecessary data')
#X = data.drop(['Label', 'Source IP', 'Dest IP'], axis=1)
X = data.drop(['Source IP', 'Dest IP', 'Source Port','Dst Port', 'Sequence #', 'Next Seq #', 'Ack #', 'Time', 'Label'], axis=1)
y = data['Label']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

scaler = StandardScaler()
print('Transforming training and test...')
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

model = RandomForestClassifier() # Effective for classification tasks
print('Beginning training...')
model.fit(X_train, y_train) # This trains the model

with open(f'/home/hunter/Desktop/project/DataCollection/data/pkl/{file}.pkl', 'wb') as file:
    pickle.dump(model, file)
print('Testing model...')
predictions = model.predict(X_test)
accuracy = accuracy_score(y_test, predictions)
print(predictions)
print(f'Model Accuracy: {accuracy}')
print('Complete!')