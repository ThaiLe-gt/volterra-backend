package com.dt.legacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dt")
public class LegacyDotnetServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LegacyDotnetServiceApplication.class, args);
    }
}
