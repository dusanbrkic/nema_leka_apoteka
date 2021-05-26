package com.team_08.ISAproj.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RestController
@EnableJpaRepositories("com.team_08.ISAproj.repository")
@EntityScan("com.team_08.ISAproj.model")
@SpringBootApplication(scanBasePackages={"com.team_08.ISAproj"})
public class ISAprojApplication {

    public static void main(String[] args) {
        SpringApplication.run(ISAprojApplication.class, args);
    }

		
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

}
