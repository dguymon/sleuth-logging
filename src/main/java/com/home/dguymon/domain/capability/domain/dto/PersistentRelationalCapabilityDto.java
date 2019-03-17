package com.home.dguymon.domain.capability.domain.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A persistence-exposing version of RelationalCapabilityDto 
 * POJO for SQL injection defense.
 * 
 * @author Danazn
 *
 */
@Entity
@Table(name = "capability")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersistentRelationalCapabilityDto extends PersistentBaseRelationalDto {
  
  private static final long serialVersionUID = 7024586836469704100L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank
  private String name;
  
  @NotBlank
  private String description;
}