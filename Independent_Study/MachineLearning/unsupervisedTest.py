import pandas as pd
from sklearn.preprocessing import MinMaxScaler
from sklearn.cluster import KMeans # KMeans is a common clustering algorithm, but assumes all clusters are same size
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from joblib import dump, load # joblib can save the trained model

df = pd.read_csv('sample.csv')
df = df.dropna()
scaler = MinMaxScaler()
print(df[['audience_score', 'critic_score', 'total_views']])

df[['audience_score', 'critic_score', 'total_views']] = scaler.fit_transform(df[['audience_score', 'critic_score', 'total_views']])
print('Altered Data, with MinMaxScaler')
print(df[['audience_score', 'critic_score', 'total_views']])

kmeans = KMeans(n_clusters=3, random_state=42)
kmeans.fit(df[['audience_score', 'critic_score', 'total_views']])  # Fit the KMeans model
dump(kmeans, 'kmeans_model.pkl')
dump(scaler, 'scaler.pkl')
#df['cluster'] = kmeans.fit_predict(df[['audience_score', 'critic_score', 'total_views']])

#print('\n')
#for cluster in sorted(df['cluster'].unique()):
#    avg_values = df[df['cluster'] == cluster][['audience_score', 'critic_score', 'total_views']].mean()
#    print(f"Cluster {cluster} averages:\n{avg_values}\n")


# This section creates a 2D plot
#plt.figure(figsize=(10, 7))
#for cluster in sorted(df['cluster'].unique()):
#    cluster_data = df[df['cluster'] == cluster]
#    plt.scatter(cluster_data['audience_score'], cluster_data['critic_score'], label=f'Cluster {cluster}')
#plt.xlabel('Audience Score')
#plt.ylabel('Critic Score')
#plt.legend()
#plt.show()

# This section generates a 3D plot
# WARNING: PLOTTING 1 million points caused the plot to freeze and crash
#fig = plt.figure(figsize=(10, 7))
#ax = fig.add_subplot(111, projection='3d') # 111 means 1x1 grid
#
#for cluster in sorted(df['cluster'].unique()):
#    cluster_data = df[df['cluster'] == cluster]
#    ax.scatter(cluster_data['audience_score'],
#               cluster_data['critic_score'],
#               cluster_data['total_views'],
#               label=f'Cluster {cluster}',
#               s=60)
#ax.set_xlabel('Audience Score')
#ax.set_ylabel('Critic Score')
#ax.set_zlabel('Total Views')
#ax.legend()
#plt.show()

