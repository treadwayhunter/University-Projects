import subprocess

def capture_packets(interface='enp0s3', count=10):
    cmd = ['sudo', 'tcpdump', '-i', interface, '-c', str(count), '-n']

    with subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True) as proc:
        output_list = []
        for line in iter(proc.stdout.readline, ''):
            output_list.append(line.strip())
    
    return output_list

print('Starting capture...')
packets = capture_packets(count=1000000)
print('Done capturing')
for packet in packets:
    print(packet)

print('\nEnd of Program')