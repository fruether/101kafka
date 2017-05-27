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

 
