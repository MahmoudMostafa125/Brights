package com.noorapp.noor.models;

public class CategoryFilterModel {

    public CategoryFilterModel(boolean exist, String name, String id) {
        this.exist = exist;
        this.name = name;
        this.id = id;
    }

    boolean exist;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

}
