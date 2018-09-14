package com.forleven.common.sqs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SqsQueue<T> {

    @JsonProperty("event")
    private String event;

    @JsonProperty("data")
    private T data;
}

