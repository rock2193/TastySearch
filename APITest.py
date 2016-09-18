from tqdm import tqdm
from multiprocessing.dummy import Pool
import json, sys
import urllib2
import urllib
import time

def loadQuries(): 
    quries = []
    with open(sys.argv[1]) as f:
        for line in f:
            quries.append(line.strip())
    return quries;

def doHttpRequest(query):
    urllib2.urlopen("http://ec2-52-39-180-192.us-west-2.compute.amazonaws.com:8080/TastySearch/search?k=1&q=" + urllib.quote_plus(query)).read()

queries = loadQuries()    
pool = Pool(10)
print("Start : " + str(time.time()))
for items in tqdm(pool.imap(doHttpRequest, queries), ascii=True, total=len(queries)):
    i = 1
print("End : " + str(time.time()))





