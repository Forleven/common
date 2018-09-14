package com.forleven.common.web;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceErrors {
    @JsonProperty("count")
    private int count;

    @JsonProperty("errors")
    private List<ResponseError> data;

    public ResourceErrors(List<ResponseError> data) {
        this.count = data.size();
        this.data = data;
    }
}
