package com.forleven.common.ses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * NotificationSesMailHeader entry in SES
 */
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationSesMailHeader implements Serializable {
    private String name;
    private String value;
}
