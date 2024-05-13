from scapy.all import *
from scapy.all import VXLAN
from scapy.all import IP
from scapy.all import TCP

import ipaddress
import pandas as pd

# src, dst, proto, seq, ack, flags, len
file = 'cleanDump3'
src_arr = [] 
dst_arr = []
src_port_arr = []
dst_port_arr = []
protocol_arr = []
seq_arr = [] # current sequence number
next_seq_arr = [] # calculated next sequence number
ack_arr = []
flag_arr = []
len_arr = []
time_arr = []
label_arr = [] # for supervised learning
time_prev_packet_arr = []
time_prev_seq_arr = []
sequence_dict = {} # next_seq_number : time packet arrived

previous_packet_time = 0
previous_tcp_time = 0

def ipToInt(ip_string):
    ip_int = int(ipaddress.ip_address(ip_string))
    return ip_int

# This is nice for printing out, but ultimately the numbers themselves might be more useful
def protocolMap(proto):
    protoDict = {
        6: 'tcp',
        17: 'udp',
        1: 'icmp',
        2: 'igmp'
    }
    return protoDict[proto]

def tcpFlagMap(flag):
    flagDict = {
        'F': 1, # FIN
        'S': 2, # SYN
        'R': 3, # RST
        'P': 4, # PSH
        'A': 5, # ACK
        'U': 6, # URG
        'E': 7, # ECE
        'C': 8, # CWR
        'PA': 9 , # PSH/ACK
        'SA': 10, # SYN/ACK
        'FA': 11, # FIN/ACK
    }
    return flagDict[str(flag)]

print('Starting program...')
pcap_reader = PcapReader(f'/home/hunter/Desktop/project/DataCollection/data/pcap/{file}.pcap')
counter = 0
for packet in pcap_reader:
    #print(packet.show())
    if VXLAN in packet: # I am only interested in the packets inside the VXLAN payload
        inner_eth = packet[VXLAN].payload
        if IP in inner_eth:
            #inner_eth.show()
            #print(f'SRC: {inner_eth[IP].src} : {ipToInt(inner_eth[IP].src)}')
            #print(f'DST: {inner_eth[IP].dst} : {ipToInt(inner_eth[IP].dst)}')
            #print(f'PROTO: {protocolMap(inner_eth[IP].proto)}')
            src_arr.append(ipToInt(inner_eth[IP].src))
            dst_arr.append(ipToInt(inner_eth[IP].dst))

            protocol_arr.append(inner_eth[IP].proto)
            if TCP in inner_eth:
                #print(f'SEQ: {inner_eth[IP].seq} : {int(inner_eth[IP].seq) + len(inner_eth[TCP].payload)}')
                #print(f'ACK: {inner_eth[IP].ack}')
                #print(f'FLAGS: {tcpFlagMap(inner_eth[TCP].flags)}')
                #print(f'LEN: {len(inner_eth[TCP].payload)}')
                seq_arr.append(inner_eth[IP].seq)
                next_seq_arr.append(int(inner_eth[IP].seq) + len(inner_eth[TCP].payload))
                ack_arr.append(inner_eth[IP].ack)
                src_port_arr.append(inner_eth[TCP].sport)
                dst_port_arr.append(inner_eth[TCP].dport)
                flag_arr.append(tcpFlagMap(inner_eth[TCP].flags))
                len_arr.append(len(inner_eth[TCP].payload))
                label_arr.append('Legitimate')

                if inner_eth[IP].seq in sequence_dict: # Then the previous sequence had a next_seq of this seq
                    if inner_eth[TCP].sport == 8000:
                        time_since_last_seq = packet.time - sequence_dict[inner_eth[IP].seq]
                        time_prev_seq_arr.append(time_since_last_seq)
                    else:
                        #time_prev_seq_arr.append(0) # this can be tightened up
                        time_prev_seq_arr.append(packet.time - previous_tcp_time) # current time minus the previous time

                else:
                    #sequence_dict[int(inner_eth[IP].seq) + len(inner_eth[TCP].payload)] = packet.time # this line needs to occur each TCP packet
                    time_prev_seq_arr.append(0)
                sequence_dict[int(inner_eth[IP].seq) + len(inner_eth[TCP].payload)] = packet.time
                previous_tcp_time = packet.time # if there is a tcp, then the previous tcp packet time gets stored here
            else:
                seq_arr.append(0)
                next_seq_arr.append(0)
                ack_arr.append(0)
                flag_arr.append(0)
                len_arr.append(0)
                label_arr.append('Malicious')
                src_port_arr.append(0)
                dst_port_arr.append(0)
                time_prev_seq_arr.append(0)
            time_arr.append(packet.time)
            if previous_packet_time == 0:
                time_prev_packet_arr.append(0) # this generates the very first time in the list
            else:
                time_prev_packet_arr.append(packet.time - previous_packet_time) # this is bad for the very first value
            previous_packet_time = packet.time # store this time for the next value

    counter += 1
    if counter % 1000 == 0:
        print(str(counter) + ' ' + file)
            

pcap_reader.close()

print(f'src_arr size: {len(src_arr)}')
print(f'dst_arr size: {len(dst_arr)}')
print(f'src_port_arr size: {len(src_port_arr)}')
print(f'dst_port_arr size: {len(dst_port_arr)}')
print(f'protocol_arr size: {len(protocol_arr)}')
print(f'seq_arr size: {len(seq_arr)}')
print(f'next_seq_arr: {len(next_seq_arr)}')
print(f'ack_arr: {len(ack_arr)}')
print(f'flags_arr: {len(flag_arr)}')
print(f'len_arr: {len(len_arr)}')
print(f'time_arr: {len(time_arr)}')
print(f'time_prev_seq_arr: {len(time_prev_seq_arr)}')
print(f'time_prev_packet_arr: {len(time_prev_packet_arr)}')
print(f'label_arr: {len(label_arr)}')

data = {
    'Source IP': src_arr,
    'Dest IP': dst_arr,
    'Source Port': src_port_arr,
    'Dst Port': dst_port_arr,
    'Protocol': protocol_arr,
    'Sequence #': seq_arr,
    'Next Seq #': next_seq_arr,
    'Ack #':ack_arr,
    'Flags': flag_arr,
    'Length': len_arr,
    'Time': time_arr,
    'Time Since Last Seq': time_prev_seq_arr,
    'Time Since Last Pack': time_prev_packet_arr,
    'Label': label_arr
}
print('Done parsing pcap. Creating csv file...')
df = pd.DataFrame(data)
df.to_csv(f'/home/hunter/Desktop/project/DataCollection/data/csv/{file}.csv', index=False) # creates a csv, without the index