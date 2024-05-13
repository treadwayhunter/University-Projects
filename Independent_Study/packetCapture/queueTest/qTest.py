from queue import Queue
import threading
import random
import time

#print(str(random.randint(0, 999)))

q = Queue()

def worker():
    while True:
        num = q.get() # get() is a blocking function, it will wait if nothing is available in the queue
        print(f'----- Got Num: {num}')
        q.task_done() # signal that I'm done blocking
        time.sleep(0.001) # purposefully make get slower than put

worker_thread = threading.Thread(target=worker, daemon=True)
worker_thread.start()

# every half second, put a number in the queue
for i in range(100000):
    num = random.randint(0, 999)
    q.put(num)
    print(f'{i}: Put Num: {num}')
    #time.sleep(0.05)

print('Done putting numbers in queue')
q.join() # blocks until all items in the queue have been gotten and processed
print('\nEnd Program')