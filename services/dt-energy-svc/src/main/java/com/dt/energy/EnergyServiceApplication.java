package com.dt.energy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dt")
public class EnergyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnergyServiceApplication.class, args);
    }
}
