package com.hans.boot.demo.mongodata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FeatureDaoService implements FeatureDaoDAL {

    private List<FeatureDao> result = new ArrayList<>();

    @Autowired
    private ObjectMapper objectMapper;

/*    private final MongoDbFactory mongoDbFactory;

    @Autowired
    public FeatureDaoService(MongoDbFactory mongo) {
        mongoDbFactory = mongo;
    }*/

    private final MongoTemplate template;

    @Autowired
    public FeatureDaoService(MongoTemplate template) {
        this.template = template;
    }

 /*   public Block<Document> getBlock() {
        Block<Document> output = new Block<Document>() {
            @Override
            public void apply(Document document) {
                String json = document.toJson();
                result.add(json);
            }
        };
        return output;
    }

    public List<FeatureDao> query() {
        result = new ArrayList<>();
        //mongoDbFactory.getDb().getCollection("FeatureDao").find().forEach(getBlock());
        return result;
    }*/

    @Override
    public List<FeatureDao> getAllFeatureDao() {
        return template.findAll(FeatureDao.class);
    }
}
