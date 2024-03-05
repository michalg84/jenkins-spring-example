package com.galka.jenkinsspringexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

@SpringBootApplication(scanBasePackages = "com.galka.jenkinsspringexample")
@Controller
@EnableJpaRepositories
public class JenkinsSpringExampleApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JenkinsSpringExampleApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(JenkinsSpringExampleApplication.class, args);
    }
}
