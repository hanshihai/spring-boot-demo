package com.hans.boot.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class MainResource {

    @RequestMapping("/main")
    public String main() {
        return "Hello Spring Boot Web!";
    }

}
