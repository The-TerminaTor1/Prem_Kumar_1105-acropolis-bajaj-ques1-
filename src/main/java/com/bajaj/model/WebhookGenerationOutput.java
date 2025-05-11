package com.bajaj.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookGenerationOutput {

    @JsonProperty("name")
    private String name;

    @JsonProperty("registrationNumber")
    private String regNo;

    @JsonProperty("email")
    private String email;

    public WebhookGenerationOutput(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}