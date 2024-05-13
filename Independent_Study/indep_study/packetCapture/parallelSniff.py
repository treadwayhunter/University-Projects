from scapy.all import *
from scapy.all import VXLAN
from scapy.all import TCP
from scapy.all import IP
from pprint import pprint

import threading
import queue

packet_queue = []
packets_info = []
thread_flag = True
counter = 0

def packet_callback(packet):
    packet_queue.put(packet) # might be able to optimize, only put packets with VXLAN in the queue

def packet_processor():
    global packets_info
    #global counter
    global thread_flag
    while thread_flag:
        #counter += 1
        if len(packet_queue) != 0:
            # pop packet?
            print('ouch')
            # do stuff
    #print('Exiting while loop')

def parallelSniff():
    global thread_flag
    print('Starting parallel sniff')
    processor_thread = threading.Thread(target=packet_processor)
    processor_thread.daemon = True
    processor_thread.start()

    sniff(iface="enp0s31f6", prn=packet_callback, count=1000)
    print('Finished sniffing')
    thread_flag = False
    processor_thread.join()
    print('Did the thread join?')
    pprint(packets_info)