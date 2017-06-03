package org.softlang.companies.kafka;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.io.IOException;
import org.apache.kafka.common.serialization.Serdes;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by freddy on 03.06.17.
 */
public class Stream101 implements ValueMapper<Object, String>{
	KStreamBuilder builder = new KStreamBuilder();
	String inputTopic  ="101companies";
	String outputTopic = "101companies-cut";
	
	public Stream101() throws IOException {
		
		Map<String, Object> props = new HashMap<>();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "101companies-cut");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		System.out.println("Inside Stream101");
		StreamsConfig streamsConfig = new StreamsConfig(props);
		
		builder = new KStreamBuilder();
		builder.stream(inputTopic).mapValues(this).to(outputTopic);
		KafkaStreams streams = new KafkaStreams(builder, streamsConfig);
		streams.start();
		System.out.println("started");
	}
	
	public String apply(Object input) {
		System.out.print("Inside apply");
		String employee = input.toString();
		JsonReader jsonReader = Json.createReader(new StringReader(employee));
		JsonObject object = jsonReader.readObject();
		String salaryString = object.get("salary").toString();
		
		double salary = Double.parseDouble(salaryString);
		salary /= 2;
		
		String result = jsonObjectToBuilder(object).add("salary", salary).build().toString();
		
		return result;
	}
	
	private JsonObjectBuilder jsonObjectToBuilder(JsonObject jo) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		for (Map.Entry<String, JsonValue> entry : jo.entrySet()) {
			if(!entry.getKey().equals("salary")) {
				job.add(entry.getKey(), entry.getValue());
			}
		}
		
		return job;
	}
	
	public static void main(String[] args) throws IOException {
		new Stream101();
		
	}
}
