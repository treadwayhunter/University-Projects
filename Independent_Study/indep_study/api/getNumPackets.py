import requests

url = 'http://localhost:8181/rests/data/opendaylight-inventory:nodes'
node = 'openflow:231233677027398'
username = 'admin'
password = 'admin'
key = 'opendaylight-flow-statistics:packet-count'


def getNumPackets():
    response = requests.get(
        url+'/node='+node+'/flow-node-inventory:table=0/flow=100/opendaylight-flow-statistics:flow-statistics/packet-count',
        auth=(username, password))
    if response.status_code >= 200 or response.status_code <= 299:
        data = response.json() # returns a python dictionary
        return int(data[key])
