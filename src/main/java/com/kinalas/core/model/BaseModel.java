package com.kinalas.core.model;

public class BaseModel {
    protected String id;

    protected BaseModel(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
