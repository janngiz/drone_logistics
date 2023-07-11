package com.musala.drones.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "medications")
public class Medication {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private double weight;

    @Column(name = "code")
    private String code;

    @Column(name = "image")
    private String image;

    public Medication() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Medication(String id, String name, double weight, String code, String image) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }


}
