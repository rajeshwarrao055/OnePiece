package com.personal.OnePiece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableAutoConfiguration
@EnableNeo4jRepositories
@EnableRedisRepositories
@SpringBootApplication
public class OnePieceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnePieceApplication.class, args);
	}

}
