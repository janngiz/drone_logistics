package com.musala.drones.domain;

public class MedicationResponse {

    private String id;
    private String name;
    private double weight;
    private String code;
    private String imageUrl;

    public MedicationResponse(String id, String name, double weight, String code, String imageUrl) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.imageUrl = imageUrl;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
