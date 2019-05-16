package com.hans.boot.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hans.boot.demo.mongodata.FeatureDao;
import com.hans.boot.demo.mode.Sample;
import com.hans.boot.demo.mongodata.FeatureDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@EnableAutoConfiguration
public class MainResource {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    FeatureDaoService bean;

    @RequestMapping("/sample")
    public Sample main(@RequestParam("text") String text) {
        try {
            String result = Util.load("sample.json");
            Sample sample = objectMapper.readValue(result, Sample.class);
            return sample;
        }catch(Exception e) {
            throw new RuntimeException("Failed to handle request /sample", e);
        }
    }

    @RequestMapping("/mongo")
    public List<FeatureDao> mongo() {
        return bean.getAllFeatureDao();
    }
}

