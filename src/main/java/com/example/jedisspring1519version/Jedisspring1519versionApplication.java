package com.example.jedisspring1519version;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//        (exclude={
//        RedisAutoConfiguration.class,
//        RedisRepositoriesAutoConfiguration.class
//})
@EnableEurekaClient
@EnableSwagger2
public class Jedisspring1519versionApplication {

    public static void main(String[] args) {
        SpringApplication.run(Jedisspring1519versionApplication.class, args);
    }

}
