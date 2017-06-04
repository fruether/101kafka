.PHONY: install
install:
			# Download Kafka from the offocial page
			wget http://www-eu.apache.org/dist/kafka/0.10.2.1/kafka_2.12-0.10.2.1.tgz
			# Uplack Kafka
			tar zxvf kafka_2.12-0.10.2.1.tgz
			# Build the project
			mvn package

.PHONY: run
run:		
			# Create the necessary topics
			kafka_2.12-0.10.2.1/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic 101companies-cut
			kafka_2.12-0.10.2.1/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic 101companies

.PHONY: start
start: 	
			# Start kafka and zookeeper
			kafka_2.12-0.10.2.1/bin/zookeeper-server-start.sh kafka_2.12-0.10.2.1/config/zookeeper.properties &
			kafka_2.12-0.10.2.1/bin/kafka-server-start.sh kafka_2.12-0.10.2.1/config/server.properties &

.PHONY: total
total:	
		# Create the company producer and total calculation
		java -jar target/kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar total
		# Make sure total is up and running
		sleep 5
		java -jar target/kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar company

.PHONY: cut
cut:	
		# Start kafka and zookeeper
		java -jar target/kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar cut
		sleep 5
		java -jar target/kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar company


.PHONY: all
all: install run start


