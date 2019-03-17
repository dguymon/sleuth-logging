package com.home.dguymon.domain.capability.domain.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A relational-backed capability POJO
 * 
 * @author Danazn
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationalCapabilityDto extends BaseRelationalDto {
  
  private static final long serialVersionUID = 5195718506022633902L;

  private Long id;
  
  @NotBlank
  private String name;
  
  @NotBlank
  private String description;
}