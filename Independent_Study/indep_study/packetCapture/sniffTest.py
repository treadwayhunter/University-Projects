# scapy can be used to sniff packets
# scapy can also write pcap files
# I am really only interested in these

from scapy.all import *
from scapy.all import VXLAN
from scapy.all import TCP
from scapy.all import IP
from pprint import pprint
import pandas as pd
#seq_arr = []
seq_num = []
payloads = []
next_seq = []
time_inspected = []

count = 1

def packet_callback(packet):
    global seq_num, payloads, next_seq, time_inspected, count
    if VXLAN in packet: # only show packets with a VXLAN tunnel
        inner_eth = packet[VXLAN].payload
        #inner_ethernet.show()
        if TCP in inner_eth:
            if inner_eth[IP].src == '172.16.0.1': # or inner_eth[IP].src == '172.16.0.2':
                if len(inner_eth[TCP].payload) != 0: # if the payload is 0, I'm likely not interested in that flow

                    if count % 1000 == 0:
                        print('Packet number: ' + str(count))

                    seq_num.append(inner_eth[TCP].seq)
                    #payloads.append(len(inner_eth[TCP].payload))
                    next_seq_num = inner_eth[TCP].seq + len(inner_eth[TCP].payload)
                    next_seq.append(next_seq_num)
                    time_inspected.append(packet.time)
                    count += 1

# interface enp0s31f6 is the correct interface to use
def sniffTest():
    print('Starting to sniff for packets')
    sniff(iface="enp0s31f6", prn=packet_callback, count=10000)
    print('Finished sniffing packets')
    print('Attempting to store data in csv file')
    global seq_num, payloads, next_seq, time_inspected
    data = {
        "Sequence Num": seq_num,
        #"Payload Size": payloads,
        "Next Seq Num": next_seq,
        "Time Inspected": time_inspected
    }
    df = pd.DataFrame(data)
    df.to_csv('data/csv/output.csv', index=False)
    print('Finished')