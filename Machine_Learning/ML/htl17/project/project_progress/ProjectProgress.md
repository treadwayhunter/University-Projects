# Project Progress
## DDoS Detection based on Network Flow Statistics
Hunter Treadway\
Muhammad Qassim\
Toby Okoye\
April 18, 2024

## Project Summary
The goal of this project is to examine a dataset containing legitimate network traffic and malicious network traffic to train a model to be able to detect DDoS network flows. By analyzing this dataset and extracting the most relavent features, we aim to have a model that can accurately determine whether a traffic flow should be allowed into a network or whether it should be blocked. 

## Problem Statement
DDoS attacks are amongst the most prevelant and dangerous threats across the internet. By creating effective Machine Learning models capable of rapidly detecting DDoS traffic, the decision process for denying DDoS traffic can be automated at a faster pace that many current technologies and professionals are capable of doing.

Amongst many of the different types of network attacks, Distributed Denial of Service (DDoS) Attacks remain one of the most difficult to detect and mitigate. Recently, DDoS attacks have become more sophisticated where packets are carefully crafted to appear as legitimate traffic flows, with one research paper by Yu Mi et al. positing that DDoS attack detection rates can be under 50% if crafted well enough. By closely observing the subtle differences in DDoS traffic to legitimate traffic, we believe a Machine Learning model can be trained to rapidly detect DDoS traffic with much greater accuracy than 50% - much faster than anyone could hope to achieve by manual review of packet captures.

The CIC-DDoS2019 dataset comes from the University of New Brunswick. The data was generated in a secure testbed (performing DDoS attacks across the internet is frowned upon, therefore a simulation of attacks is easier to produce) using "the most up-to-date common DDoS attacks". Packet information was captured into .pcap files, where packets were organized into network flows and analyzed for various statistics such as average packet inter-arrival time, average packet size, flow duration, the number of packets in the flow, etc. Initially, we plan to measure informal success based on the accuracy of our models versus the low detection rate of DDoS traffic flows.

By analyzing this dataset, we look to detect DDoS Attacks based on the careful consideration of each feature of network flows. 

## Dataset 
The CIC-DDoS2019 Evaluation Dataset contains 225000+ rows and 85 columns of data that shows features of network flows between two endpoints. Each row is labeled as either BENIGN or DDoS, where DDoS is the label for malicious traffic flows.

### Motivation
The dataset was created by the University of New Brunswick to analyze the complex features of network traffic flows in order to differentiate DDoS traffic flows from benign flows.

### Composition
With 85 columns, the dataset is rich with information about network traffic flows. Many of the features are self explanatory, especially the first few such as "Source IP" and "Source Port" that are the Source IP Address and Source Port Number respectively. Other features are grouped together by type, such as IAT (inter-arrival time) where statistics on the inter-arrival time's are shown: IAT Mean, IAT Std (Standard Deviation). Any feature labeled "Fwd" is forward traffic (traffic from the source device to the destination device) and traffic labeled "Bwd" goes from the destination device to the source device. 

```List of all Features```\
Flow ID, Source IP, Source Port, Destination IP, Destination Port, Protocol, Timestamp, Flow Duration, Total Fwd Packets, Total Backward Packets,Total Length of Fwd Packets, Total Length of Bwd Packets, Fwd Packet Length Max, Fwd Packet Length Min, Fwd Packet Length Mean, Fwd Packet Length Std,Bwd Packet Length Max, Bwd Packet Length Min, Bwd Packet Length Mean, Bwd Packet Length Std,Flow Bytes/s, Flow Packets/s, Flow IAT Mean, Flow IAT Std, Flow IAT Max, Flow IAT Min,Fwd IAT Total, Fwd IAT Mean, Fwd IAT Std, Fwd IAT Max, Fwd IAT Min,Bwd IAT Total, Bwd IAT Mean, Bwd IAT Std, Bwd IAT Max, Bwd IAT Min,Fwd PSH Flags, Bwd PSH Flags, Fwd URG Flags, Bwd URG Flags, Fwd Header Length, Bwd Header Length,Fwd Packets/s, Bwd Packets/s, Min Packet Length, Max Packet Length, Packet Length Mean, Packet Length Std, Packet Length Variance,FIN Flag Count, SYN Flag Count, RST Flag Count, PSH Flag Count, ACK Flag Count, URG Flag Count, CWE Flag Count, ECE Flag Count, Down/Up Ratio, Average Packet Size, Avg Fwd Segment Size, Avg Bwd Segment Size, Fwd Header Length,Fwd Avg Bytes/Bulk, Fwd Avg Packets/Bulk, Fwd Avg Bulk Rate, Bwd Avg Bytes/Bulk, Bwd Avg Packets/Bulk,Bwd Avg Bulk Rate,Subflow Fwd Packets, Subflow Fwd Bytes, Subflow Bwd Packets, Subflow Bwd Bytes,Init_Win_bytes_forward, Init_Win_bytes_backward, act_data_pkt_fwd, min_seg_size_forward, Active Mean, Active Std, Active Max, Active Min,Idle Mean, Idle Std, Idle Max, Idle Min, Label.

### Collection Process
Data was generated in a test environment using information on current DDoS techniques.

## Exploratory Data Analysis
Initially, we are planning on using heatmaps, confusion matrices, and pairplots.
As part of our work so far, we looked for ways to reduce 85 columns into something more human-readable. By using heatmaps, we were able to generate what we believe are the 30 most important features of the dataset. After this preliminary work, we could further generate confusion matrices with results from preliminary models, and additional pairplots against those 30 columns.

## Data Preprocessing
As part of data preprocessing, we ran into some initial issues. We were able to determine from our preliminary models that out of many features, only a couple of them are used to determine the label of the data. We want each feature to have greater weight, so we dropped the features that blatantly give away the label, and had to use better models to give the features better weight.
We have considered utilizing PCA and TSNE, but so far have chosen not to use them. Our best model so far, a Random Forest Classifier, was able to grant each feature a more even amount of weight and achieve a better accuracy. We have yet to test if the Forest is overfit or not.

## Machine Learning Approaches
Our baseline evaluation setup sought to run some simple ML models early on (such as KNN and Logistic Regression), even before we cleaned up our features very much. This was to contrast a lot of what we have done in class - the homework assignments helped us understand the ML pipeline, but we had not seen what a ML model would look like if we had not done much EDA. Surprisingly, the accuracy of our models were already well above 90% for data we had hardly cleaned up. This led us to make important EDA decisions which helped us make some discoveries about some of the features in our model: some features would give away the label, and the model would almost ignore all other features. For example, even after dropping some of the more used features:

```Feature Importances of a Decision Tree Classifier```
* Fwd Packet Length Max          0.574886
* Total Length of Fwd Packets    0.422229
* Bwd IAT Mean                   0.001242

With only 30 out of 85 features, after removing other features that gave away the label, the top 2 features still gave away the label. By running our models early, we were able to detect problems with our dataset. Another example includes the Protocol column/feature. Every flow is either labeled UDP or TCP, but every row labeled is DDoS is also only labeled TCP. No row containing UDP flows contains the DDoS label.

After using the Random Forest Classifier, we seek to continue using the Random Forest Classifier. So far, it has given us the best results while also giving the best weight to each feature. We may continue to allow more features than just 30 to see if we can still get a high accuracy. 


## Experiments
Currently, our work is divided between project_1.ipynb and project_2.ipynb. We seek to fix this by the actual Project Submission, but has been useful for our work so far.
As described above, in project_1.ipynb, we created a heatmap of the 30 top features. From this, we scaled our data using a StandardScaler, then created a Logistic Regression model and a KNN model, and created Confusion Matrices for both. After noticing our accuracies were already a little high, we became concerned about overfitting, or if only a few features were giving away the whole dataset. In project_2.ipynb, we ran some EDA to determine problems with the dataset. We than ran a MinMaxScaler on the cleaned data, and ran the data through multiple Decision Trees to determine the importance of each feature. After using a Random Forest, our feature importance improved significantly:

```Feature Importance of Random Forest Classifier```
* Total Length of Fwd Packets    0.129024
* Fwd Packet Length Max          0.118659
* Avg Fwd Segment Size           0.115659
* Fwd Packet Length Mean         0.095945
* Subflow Fwd Bytes              0.095391
* Bwd Packet Length Min          0.067320
* Average Packet Size            0.063156
* Bwd Packet Length Mean         0.062533
* Avg Bwd Segment Size           0.050677
* Bwd Packet Length Max          0.044076