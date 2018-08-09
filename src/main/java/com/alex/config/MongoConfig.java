package com.alex.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mongo 连接配置
 *
 * @author yanhui
 * @date 2018年7月3日18:46:42
 */
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    private Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Value("${my.mongo.host1}")
    private String mongodbHost1;

    @Value("${my.mongo.host2}")
    private String mongodbHost2;

    @Value("${my.mongo.port}")
    private int mongodbPort;

    @Value("${my.mongo.database}")
    private String database;

    @Value("${my.mongo.dbname}")
    private String mongodbName;

    @Value("${my.mongo.user}")
    private String mongodbUser;

    @Value("${my.mongo.password}")
    private String mongodbpwd;

    @Value("${my.mongo.test}")
    private Boolean test;

    @Value("${my.mongo.connectionsPerHost}")
    private Integer connectionsPerHost;
    @Value("${my.mongo.connectTimeout}")
    private Integer connectTimeout;

    @Value("${my.mongo.replSetName}")
    private String replSetName;

    @Value("${my.mongo.authentification}")
    private Boolean authentification;

    @Override
    public MongoClient mongoClient() {
        MongoClient mongoClient;
        if (test) {
            MongoCredential credential = MongoCredential.createCredential(mongodbUser, database, mongodbpwd.toCharArray());
            MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(5).build();
            ServerAddress serverAddress = new ServerAddress(mongodbHost1, mongodbPort);
            if (authentification) {
                mongoClient = new MongoClient(serverAddress, Arrays.asList(credential), options);
            } else {
                mongoClient = new MongoClient(serverAddress, options);
            }
        } else {
            ServerAddress address1 = new ServerAddress(mongodbHost1, mongodbPort);
           // ServerAddress address2 = new ServerAddress(mongodbHost1, mongodbPort);
            // 构建Seed列表
            List<ServerAddress> seedList = new ArrayList<ServerAddress>();
            seedList.add(address1);
            //seedList.add(address2);
            // 构建鉴权信息
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(MongoCredential.createCredential(mongodbUser, database, mongodbpwd.toCharArray()));
            // 构建操作选项，requiredReplicaSetName属性外的选项根据自己的实际需求配置，默认参数满足大多数场景
           // MongoClientOptions options = MongoClientOptions.builder().requiredReplicaSetName(replSetName).socketTimeout(connectTimeout).connectionsPerHost(connectionsPerHost).build();
            MongoClientOptions options = MongoClientOptions.builder().socketTimeout(connectTimeout).connectionsPerHost(connectionsPerHost).build();
            mongoClient = new MongoClient(seedList, options);
        }
        return mongoClient;
    }

    @Bean
    @Override
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), mongodbName);
        logger.info("*******" + mongoTemplate.getDb().getName() + "基础库");
        return mongoTemplate;

    }

    @Override
    protected String getDatabaseName() {
        return mongodbName;
    }


}
