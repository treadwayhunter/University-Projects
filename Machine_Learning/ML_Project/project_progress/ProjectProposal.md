# Project Proposal
Hunter Treadway\
Muhammad Qassim\
Toby Okoye\
March 29, 2024

## Project Summary
The goal of this project is to examine a dataset containing legitimate network traffic and malicious network traffic to train a model to be able to detect DDoS network flows. By analyzing this dataset and extracting the most relavent features, we aim to have a model that can accurately determine whether a traffic flow should be allowed into a network or whether it should be blocked. 

## Problem Statement 
DDoS attacks are amongst the most prevelant and dangerous threats across the internet. By creating effective Machine Learning models capable of rapidly detecting DDoS traffic, the decision process for denying DDoS traffic can be automated at a faster pace that many current technologies and professionals are capable of doing.

## Dataset 
The CIC-DDoS2019 Evaluation Dataset contains 225000+ rows and 85 columns of data that shows features of network flows between two endpoints. Each row is labeled as either BENIGN or DDoS, where DDoS is the label for malicious traffic flows.

---
---
---
## Additional Information
#### Dataset Information
* https://www.kaggle.com/datasets/aymenabb/ddos-evaluation-dataset-cic-ddos2019?rvi=1
* https://www.unb.ca/cic/datasets/ddos-2019.html

#### Related Research
* https://ieeexplore.ieee.org/document/9947232 "AutoDefense: Reinforcement Based Autoreactive Defense Against Network Attacks"

#### Other Important Information
* https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml