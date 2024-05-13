# DDoS Detection based on Network Flow Statistics

This project aims to train a machine learning model to detect Distributed Denial of Service (DDoS) attacks based on network flow statistics. The goal is to identify features within the dataset that can reveal a DDoS attack, even when the attack is carefully crafted to evade detection.

## Dataset

The project uses the CIC-DDoS2019 dataset from the University of New Brunswick. The dataset contains over 225,000 rows and 85 columns of data representing features of network flows between two endpoints. Each row is labeled as either BENIGN or DDoS, indicating whether the traffic flow is malicious or not.

## Installation

1. Clone the repository:

```
git clone https://git.txstate.edu/ned44/ML/tree/main/project
```

2. Install the required dependencies:

```
pip install -r requirements.txt
```
3. 
```
Download the dataset from https://www.kaggle.com/datasets/aymenabb/ddos-evaluation-dataset-cic-ddos2019?rvi=1
```

## Usage

1. Open the Jupyter Notebook `project.ipynb`.

2. Follow the instructions in the notebook to preprocess the data, train the machine learning models, and evaluate their performance.

3. The notebook provides a detailed explanation of the project, including the problem statement, data preprocessing steps, machine learning approaches, experiments, and results.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## Acknowledgments

- The CIC-DDoS2019 dataset was created by the University of New Brunswick.
- The project was inspired by the research paper "AutoDefense: RL Based Autoreactive Defense Against Network Attacks" by Yu Mi et al. [Link](https://ieeexplore.ieee.org/document/9947232)
