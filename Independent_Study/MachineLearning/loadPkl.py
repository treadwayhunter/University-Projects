import pandas as pd
from sklearn.preprocessing import MinMaxScaler
from sklearn.cluster import KMeans # KMeans is a common clustering algorithm, but assumes all clusters are same size
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from joblib import dump, load # joblib can save the trained model

kmeans = load('kmeans_model.pkl')
scaler = load('scaler.pkl')

critic_score = 0.2
audience_score = 0.90
total_views = 3.5e6 #1.5 million

#new_data = pd.DataFrame(data=[[audience_score, critic_score, total_views]], 
#                        columns=['audience_score', 'critic_score', 'total_views'])
#data_scaled = scaler.transform(new_data)


data_scaled = scaler.transform([[audience_score, critic_score, total_views]])

cluster_label = kmeans.predict(data_scaled)[0]
print('Cluster label: ', cluster_label)