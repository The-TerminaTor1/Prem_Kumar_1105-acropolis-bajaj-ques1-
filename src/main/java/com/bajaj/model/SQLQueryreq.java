package com.bajaj.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SQLQueryreq {

    @JsonProperty("query")
    private String finalQuery;

    public SQLQueryreq(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public String getFinalQuery() {
        return finalQuery;
    }

    public void setFinalQuery(String finalQuery) {
        this.finalQuery = finalQuery;
    }
}