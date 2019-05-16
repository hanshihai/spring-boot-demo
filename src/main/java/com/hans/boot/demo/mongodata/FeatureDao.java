package com.hans.boot.demo.mongodata;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FeatureDao")
public class FeatureDao {

    @Id
    private String id;

    private String className;
    private boolean state;
    private int version;

    public FeatureDao() {}

    public FeatureDao(String id, String className, boolean state, int version) {
        this.id = id;
        this.className = className;
        this.state = state;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
