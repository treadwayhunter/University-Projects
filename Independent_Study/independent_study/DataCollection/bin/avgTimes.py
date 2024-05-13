import pandas as pd

columns_to_load = ['Time Since Last Seq', 'Time Since Last Pack']
file = 'attackDump1'
df = pd.read_csv(f'/home/hunter/Desktop/project/DataCollection/data/csv/{file}.csv', usecols=columns_to_load)

last_seq = df[columns_to_load[0]].tolist()
last_pack = df[columns_to_load[1]].tolist()

avg_seq_time = 0
for time in last_seq:
    avg_seq_time += float(time)
avg_seq_time = avg_seq_time/len(last_seq)

avg_pack_time = 0
for time in last_pack:
    #print(time)
    avg_pack_time += float(time)
    #print(f'Avg: {avg_pack_time}')
avg_pack_time = avg_pack_time/len(last_pack)

print(avg_seq_time)
print(avg_pack_time)

