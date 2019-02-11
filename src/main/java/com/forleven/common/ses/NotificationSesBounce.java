package com.forleven.common.ses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.joda.time.DateTime;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationSesBounce implements Serializable {
    private String bounceType;
    private String bounceSubType;
    private List<BounceRecipient> bouncedRecipients;
    private Date timestamp;
    private String feedbackId;
    private String remoteMtaIp;
    private String reportingMta;
}
