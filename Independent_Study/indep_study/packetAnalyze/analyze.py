from scapy.all import *
from scapy.all import VXLAN
from scapy.all import TCP
from scapy.all import IP

from scapy.all import rdpcap

from pprint import pprint

def analyze():
    packets = rdpcap('data/pcap/test.pcap')

    for packet in packets:
        print(packet.summary())