package com.school.schoolgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SchoolGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolGatewayApplication.class, args);
    }

}
