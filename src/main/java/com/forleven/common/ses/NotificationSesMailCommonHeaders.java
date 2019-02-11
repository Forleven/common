package com.forleven.common.ses;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Common notificationSesMailHeaders in an ses message
 */
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationSesMailCommonHeaders implements Serializable {
    private List<String> from;
    private List<String> cc;
    private List<String> bcc;
    private List<String> to;
    private String returnPath;
    private String messageId;
    private String date;
    private String subject;
}
