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

@Entity
@Table(name = "capability")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationalCapabilityDto extends BaseRelationalDto {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank
  private String name;
  
  @NotBlank
  private String description;
}