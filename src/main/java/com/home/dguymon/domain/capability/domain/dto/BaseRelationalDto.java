package com.home.dguymon.domain.capability.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import java.io.Serializable;

import java.util.Date;

/**
 * Base POJO for relational-backed domain objects
 * 
 * @author Danazn
 *
 */
@Data
@JsonIgnoreProperties(
    value = { "createDate", "updateDate" },
    allowGetters = false
)
public abstract class BaseRelationalDto implements Serializable {
  
  private static final long serialVersionUID = 3843916140698380911L;

  private Date createDate;
  private Date updateDate;
}