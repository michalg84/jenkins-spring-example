package com.galka.jenkinsspringexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication(scanBasePackages = "com.galka.jenkinsspringexample")
@Controller
public class JenkinsSpringExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JenkinsSpringExampleApplication.class, args);
    }
}
