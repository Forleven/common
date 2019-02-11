package com.forleven.common.ses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * represents an SES notificationSesMail object
 * @see https://docs.aws.amazon.com/pt_br/ses/latest/DeveloperGuide/notification-contents.html
 */
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationSesMail implements Serializable {
    private Date timestamp;
    private String messageId;
    private String source;
    private String sourceArn;
    private String sourceIp;
    private String sendingAccountId;
    private List<String> destination;
    private Boolean headersTruncated;
    private List<NotificationSesMailHeader> headers;
    private NotificationSesMailCommonHeaders commonHeaders;
}
