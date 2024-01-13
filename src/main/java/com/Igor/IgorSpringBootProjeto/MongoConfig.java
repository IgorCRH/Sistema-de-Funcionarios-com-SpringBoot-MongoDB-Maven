package com.Igor.IgorSpringBootProjeto;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        String connectionString = "mongodb://igorcrod:senhadoseuusuariodobancoaqui@ac-he0tx4l-shard-00-00.zjgy3mq.mongodb.net:27017,ac-he0tx4l-shard-00-01.zjgy3mq.mongodb.net:27017,ac-he0tx4l-shard-00-02.zjgy3mq.mongodb.net:27017/?ssl=true&replicaSet=atlas-9t60tn-shard-0&authSource=admin&retryWrites=true&w=majority";
        
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build());
    }
}

