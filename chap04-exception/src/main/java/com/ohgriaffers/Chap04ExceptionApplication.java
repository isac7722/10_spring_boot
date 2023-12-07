package com.ohgriaffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ohgriaffers.exception")

public class Chap04ExceptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap04ExceptionApplication.class, args);
    }

}
