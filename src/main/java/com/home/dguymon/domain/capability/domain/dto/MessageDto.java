package com.home.dguymon.domain.capability.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HAL-compatible message object
 * 
 * @author Danazn
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
  
  String message;
  Boolean error;
}
