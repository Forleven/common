package com.forleven.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourcesLinks {
    private ResourcesLink last;
    private ResourcesLink self;
    private ResourcesLink first;
    private ResourcesLink previous;
    private ResourcesLink next;
}
