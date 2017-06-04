# Sample 101 company example implemented in Kafka


## Pre-requisites
To start, you need to get Kafka up and running and create some topics.

### Step 1: Download Kafka
Download the 0.10.2.1 release and un-tar it.
```
$ tar -xzf kafka_2.12-0.10.2.1.tgz
$ cd kafka_2.12-0.10.2.1
```
### Step 2: Start the server
Start a ZooKeeper server. Kafka has a single node Zookeeper configuration built-in.
```
$ bin/zookeeper-server-start.sh config/zookeeper.properties &
...
```
Now start Kafka itself:
```
$ bin/kafka-server-start.sh config/server.properties &
...
```
.

### Step 3: Create the topics for company data exchange
We need the topic for the exachnge of the company data
```
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic 101companies
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic 101companies-cut


```
These can be listed
```
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
fast-messages
summary-markers
```
Note that you will see log messages from the Kafka process when you

### Step 4: Compile and package
```
$ cd ..
$ mvn package
...
```


### Step 5: Execute the total and produce
This will create a Kafka consumer that is adding up all the salary of the received employees and prints out the result. Hence implementing the total feature.
 ```
		$ java -jar target/kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar total	
		$ java -jar target/kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar company
  ```
  
### Step 6: Execute the cut and produce
This will create a Kafka stream that is reading all the employee data. Is cutting the salary by 2 and then sending publishes it to another stream.
 ```
   $ kafka_2.12-0.10.2.1bin/kafka-console-consumer --zookeeper zk01.example.com:2181 --topic 101companies-cut

		$ java -jar target/kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar total	
		$ java -jar target/kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar cut
  ```

