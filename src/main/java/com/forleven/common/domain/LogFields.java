package com.forleven.common.domain;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class LogFields implements Serializable {

    @ApiModelProperty(hidden = true)
    @CreatedDate
    @Column(name="create_date", updatable = false)
    @JsonProperty("create_date")
    private Date createDate = new Date();

    @ApiModelProperty(hidden = true)
    @LastModifiedDate
    @Column(name="update_date", insertable = false)
    @JsonProperty("update_date")
    private Date updateDate = new Date();

    @ApiModelProperty(hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Boolean status = true;
}
