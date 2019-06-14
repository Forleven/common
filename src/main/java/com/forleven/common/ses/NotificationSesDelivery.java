package com.forleven.common.ses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationSesDelivery implements Serializable {
    private Date timestamp;
    private Integer processingTimeMillis;
    private List<String> recipients;
    private String smtpResponse;
    private String reportingMta;
    private String remoteMtaIp;
}
