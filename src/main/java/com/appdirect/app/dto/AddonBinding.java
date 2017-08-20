package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddonBinding {

    private String id;

    public AddonBinding() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AddonBinding{" +
                "id='" + id + '\'' +
                '}';
    }
}
