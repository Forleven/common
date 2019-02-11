package com.forleven.common.ses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BounceRecipient implements Serializable {
    private String emailAddress;
    private String action;
    private String status;
    private String diagnosticCode;
}
