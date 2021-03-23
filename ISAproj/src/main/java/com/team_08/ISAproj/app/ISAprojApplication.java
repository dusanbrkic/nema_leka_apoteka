package com.team_08.ISAproj.app;

import com.team_08.ISAproj.controller.ApotekaController;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.repository.IMApotekaRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
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
