package com.home.dguymon.domain.capability.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A persistence-exposing representation of the 
 * BaseRelationalDto POJO for SQL injection 
 * defense.
 * 
 * @author Danazn
 *
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = { "createDate", "updateDate" },
    allowGetters = false
)
public abstract class PersistentBaseRelationalDto implements Serializable {
  
  private static final long serialVersionUID = 3843916140698380911L;

  @Temporal(TemporalType.DATE)
  @Column(name = "create_date", nullable = false, updatable = false)
  @CreatedDate
  private Date createDate;
  
  @Temporal(TemporalType.DATE)
  @Column(name = "update_date", nullable = false)
  @LastModifiedDate
  private Date updateDate;
}