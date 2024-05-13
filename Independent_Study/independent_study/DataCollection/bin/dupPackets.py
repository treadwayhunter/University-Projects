# Test for duplicate packets
# number of dup packets, vs number of dup packets over a period of time.

# measure start time, and measure finish time
# first packet vs end packet... how do I know what the end one is?

import pandas as pd
from datetime import datetime

file = 'cleanDump1.csv'
columns_to_load = ['Source Port', 'Sequence #', 'Time', 'Dst Port']
df = pd.read_csv(f'/home/hunter/Desktop/project/DataCollection/data/csv/{file}', usecols=columns_to_load)


# store sequence numbers of packets
# if seq num does not exist, store it and set it to 0
# if seq num does exist, increment value
packets = {}
sent_packet = 0
src_arr = df[columns_to_load[0]].tolist()
seq_arr = df[columns_to_load[1]].tolist()
time_arr = df[columns_to_load[2]].tolist()
dst_port_arr = df[columns_to_load[3]].tolist()

start_time = time_arr[0]
end_time = time_arr[len(time_arr) - 1]

for i in range(len(time_arr)): # any array could be used
    if src_arr[i] == 8000:
        seq = seq_arr[i]
        sent_packet += 1
        if seq in packets:
            id = str(seq) + str(dst_port_arr[i])
            packets[seq] += 1
        else:
            packets[seq] = 0 # occurred once, if 1 then it duplicated once

dup_counter = 0
for value in packets.values():
    #print(value)
    if value >= 1:
        dup_counter += value # not += 1, because 1 packet could be duplicated multiple times

p_counter = 0
for packet in packets:
    p_counter += 1

print(f'Unique packets: {p_counter}')
print(f'Start Time: {datetime.fromtimestamp(start_time)}')
print(f'End Time: {datetime.fromtimestamp(end_time)}')

time_dif = end_time - start_time #time dif is in seconds, and it came out to 62.03 seconds initially
dups_per_minute = int((dup_counter/time_dif)*60)
print(f'Dups per minute: {dups_per_minute}')
print(f'TCP Sent by Server: {sent_packet} :: {int(sent_packet/time_dif)*60}')

