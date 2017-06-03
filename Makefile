.PHONY: install
install:
			# Download Kafka from the offocial page
			wget http://www-eu.apache.org/dist/kafka/0.10.2.1/kafka_2.12-0.10.2.1.tgz
			# Uplack Kafka
			tar zxvf kafka_2.12-0.10.2.1.tgz
			# Build the project
			mvn package

.PHONY: createTopic
createTopic:
			kafka_2.12-0.10.2.1/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic 101companies-cut
			kafka_2.12-0.10.2.1/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic 101companies


run: createTopic



