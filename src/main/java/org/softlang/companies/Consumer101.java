package org.softlang.companies;

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

/**
 * Created by freddy on 27.05.17.
 */
public class Consumer101 {
	
	private KafkaConsumer<String, String> consumer;
	
	public Consumer101() throws IOException {
		
		InputStream props = Resources.getResource("consumer.props").openStream();
		Properties properties = new Properties();
		properties.load(props);
		if (properties.getProperty("group.id") == null) {
			properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
		}
		consumer = new KafkaConsumer<>(properties);
	}
	
	public void start_consumption() {
		consumer.subscribe(Arrays.asList("101companies"));
		double total = 0;
		try {
			while(true) {
				ConsumerRecords<String, String> records = consumer.poll(1000);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(record.value());
					JsonReader jsonReader = Json.createReader(new StringReader(record.value()));
					JsonObject object = jsonReader.readObject();
					System.out.println(object.toString());
					String salaryString = object.get("salary").toString();
					double salary = Double.parseDouble(salaryString);
					total+=salary;
					System.out.println("The total salary right now is " + total);
				}
			}
		}
		finally {
			consumer.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Consumer101 consumer = new Consumer101();
		consumer.start_consumption();
	}
}
	

	

