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
public class NotificationSesComplaint implements Serializable {
    private List<ComplainedRecipient> complainedRecipients;
    private Date timestamp;
    private String feedbackId;
    private String userAgent;
    private String complaintFeedbackType;
    private String arrivalDate;
}
