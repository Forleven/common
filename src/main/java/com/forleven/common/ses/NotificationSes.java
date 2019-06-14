package com.forleven.common.ses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.vavr.control.Try;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationSes implements Serializable {

    @JsonProperty("notificationType")
    private String notificationType;

    @JsonProperty("mail")
    private NotificationSesMail mail;

    @JsonProperty("bounce")
    private NotificationSesBounce bounce;

    @JsonProperty("complaint")
    private NotificationSesComplaint complaint;

    @JsonProperty("delivery")
    private NotificationSesDelivery delivery;

    public NotificationSes(String a) {
        Try.run(() -> {
            NotificationSes message = new ObjectMapper().readValue(a, NotificationSes.class);
            this.notificationType = message.getNotificationType();
            this.mail = message.getMail();
            this.bounce = message.getBounce();
            this.complaint = message.getComplaint();
            this.delivery = message.getDelivery();
        });
    }
}