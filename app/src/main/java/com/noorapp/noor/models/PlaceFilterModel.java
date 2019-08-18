package com.noorapp.noor.models;

public class PlaceFilterModel {
    public PlaceFilterModel(boolean exist, String name) {
        this.exist = exist;
        this.name = name;
    }

    boolean exist;

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
