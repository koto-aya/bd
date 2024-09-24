package com.bd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bd.mapper")
public class BuildingDreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildingDreamApplication.class, args);
    }

}
