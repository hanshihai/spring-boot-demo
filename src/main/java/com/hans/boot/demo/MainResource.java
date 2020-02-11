package com.hans.boot.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@EnableAutoConfiguration
public class MainResource {


    @RequestMapping("/sample")
    public String main(@RequestParam("text") String text) {
        try {
            System.out.println("Here is the input parameter :" + text);
            String result = Util.load("sample.json");
            return result;
        }catch(Exception e) {
            throw new RuntimeException("Failed to handle request /sample", e);
        }
    }

}

