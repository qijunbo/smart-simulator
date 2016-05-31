package org.simulator.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoDBConfiguration extends AbstractMongoConfiguration {

	@Value("${databaseName}")
	private String databaseName;

	@Value("${serverAddress}")
	private String serverAddress;

	@Value("${port}")
	private int port;

	@Value("${user}")
	private String user;

	@Value("${password}")
	private String password;

	@Override
	protected String getDatabaseName() {

		return databaseName;
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {

		//return new MongoClient();

		final List<MongoCredential> credential = new ArrayList<MongoCredential>();
		credential.add(MongoCredential.createCredential(user, databaseName, password.toCharArray()));
		return new MongoClient(new ServerAddress(serverAddress, port), credential);
	}
}
