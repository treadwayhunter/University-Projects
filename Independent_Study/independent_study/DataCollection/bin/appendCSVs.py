import pandas as pd

csv_files = ['/home/hunter/Desktop/project/DataCollection/data/csv/cleanDump1.csv',
             '/home/hunter/Desktop/project/DataCollection/data/csv/cleanDump2.csv',
             '/home/hunter/Desktop/project/DataCollection/data/csv/attackDump1.csv',
             '/home/hunter/Desktop/project/DataCollection/data/csv/attackDump2.csv',
             '/home/hunter/Desktop/project/DataCollection/data/csv/cleanDump3.csv']
dataframes = [pd.read_csv(file) for file in csv_files]

combined_df = pd.concat(dataframes, ignore_index=False)
combined_df.to_csv('/home/hunter/Desktop/project/DataCollection/data/csv/combinedFinal.csv', index=False)