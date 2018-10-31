package com.forleven.common.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Link {
    private String href;
    private int number;
}
