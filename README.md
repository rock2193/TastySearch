# TastySearch
Search Engine to search gourmet food reviews data
GitHub Link : https://github.com/rock2193/TastySearch
API Link : http://52.39.180.192:8080/TastySearch/search?q=buying&k=5&str=HS
All files can be downloaded from
http://52.39.180.192/

It contains
1.  QueriesDataGenerator.jar	2016-09-18 17:15	736K
2.  ReviewsSampleDataGenerator.jar	2016-09-18 15:30	734K
3.  StopWords.txt	2016-09-18 17:37	379
4.  foods.txt	2013-03-05 04:48	354M
5.  foods_sample_data.txt	2016-09-18 15:34	57M
6.  index_temp.html	2016-09-18 17:19	11K
7.  random_queries.txt	2016-09-18 17:16	3.0M

Detail

"foods.txt" is the RAW file downloaded from server.

"ReviewsSampleDataGenerator.jar" is jar file which is used for generating Sample data of size 100K reviews.
source_code : https://github.com/rock2193/TastySearch/blob/master/Search/src/main/java/com/kredx/DataGenerator/ReviewsSampleDataGenerator.java
Command :   "java -jar ReviewsSampleDataGenerator.jar"
Requirement :    foods.txt should be in same folder, in which jar is present
Output : it will create "foods_sample_data.txt", in same folder.

"QueriesDataGenerator.jar" is jar file which is used for generating Sample quries of size 100K.
source_code : https://github.com/rock2193/TastySearch/blob/master/Search/src/main/java/com/kredx/DataGenerator/QueriesSampleDataGenerator.java
Command :   "java -jar QueriesDataGenerator.jar"
Requirement :    foods_sample_data.txt should be in same folder, in which jar is present
Output : it will create "random_queries.txt", in same folder.

StopWords.txt is generated file based on top 100 tokens based on document frequency, and then manually verified.
source_code : https://github.com/rock2193/TastySearch/blob/master/Search/src/main/java/com/kredx/DataGenerator/StopWordFileGenerator.java

API : http://52.39.180.192:8080/TastySearch/search?q=buying&k=5&str=HS
It accepts 3 params
1. query ("q") , by default it's empty string
2. k , no of max documents in result, by default it's 20
3. str, Strategy (Most Recent (MR), Low Score (LS), High Score (HS)), by default it's HS
if you don't provide any param, it's default value will be taken

Explanation (logic) :
Providing a unique id to each review.
Keeping a sorted list of review IDs (in which that token occurred), with each token

when a query comes, i first extract all tokens from that query
remove stop words from query tokens
extract sorted list of review IDs for each query token
we can use algo of "merging k sorted arrays" by maintaining a min heap
we also keep an other min heap of size K (no of result we want), which first sort on frequency(how many tokens
occur in a same document), and then our strategy type (Most Recent, High Score etc..)

Load Test
My App is running on a single core ec-2 server, with 512 MB RAM.

Apache BenchMark Testing
ab -n 100 -c 10 "http://ec2-52-39-180-192.us-west-2.compute.amazonaws.com:8080/TastySearch/search?q=becomes%20hard%20after%20sitting%20in%20the%20fridge%20overnight"
This is ApacheBench, Version 2.3 <$Revision: 1528965 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking ec2-52-39-180-192.us-west-2.compute.amazonaws.com (be patient).....done


Server Software:        Apache-Coyote/1.1
Server Hostname:        ec2-52-39-180-192.us-west-2.compute.amazonaws.com
Server Port:            8080

Document Path:          /TastySearch/search?q=becomes%20hard%20after%20sitting%20in%20the%20fridge%20overnight
Document Length:        25422 bytes

Concurrency Level:      10
Time taken for tests:   11.094 seconds
Complete requests:      100
Failed requests:        0
Total transferred:      2557500 bytes
HTML transferred:       2542200 bytes
Requests per second:    9.01 [#/sec] (mean)
Time per request:       1109.400 [ms] (mean)
Time per request:       110.940 [ms] (mean, across all concurrent requests)
Transfer rate:          225.13 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:      267  327 169.2    292    1280
Processing:   545  635 213.4    579    2162
Waiting:      267  294  18.8    288     364
Total:        817  962 267.7    879    2432

Percentage of the requests served within a certain time (ms)
  50%    879
  66%    905
  75%    927
  80%    936
  90%   1174
  95%   1840
  98%   2112
  99%   2432
 100%   2432 (longest request)


Other BenchMarks :
I generated random 1000 queries from given set
Used APITest.py ("https://github.com/rock2193/TastySearch/blob/master/Search/APITest.py")for other load testing
by creating a poll thread of 10.

first i used default value of k (20)
Start : 1474227622.51
100%|####################################################################| 1000/1000 [08:34<00:00,  1.94it/s]
End : 1474228137.39

Overall Test ran for 8:34 minutes

Second i used value of k (1)
Start : 1474228173.98
100%|####################################################################| 1000/1000 [02:25<00:00,  6.87it/s]
End : 1474228319.51

Overall Test ran for : 2:25 minutes



