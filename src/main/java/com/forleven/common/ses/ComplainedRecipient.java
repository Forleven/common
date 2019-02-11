package com.forleven.common.ses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * class representing recipient of a notificationSesComplaint
 */
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComplainedRecipient implements Serializable {
    private String emailAddress;
}
