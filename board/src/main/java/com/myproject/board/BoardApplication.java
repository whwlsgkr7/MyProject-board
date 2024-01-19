package com.myproject.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
public class BoardApplication {

    public static void main(String[] args) {

        SpringApplication.run(BoardApplication.class, args);
    }

}
