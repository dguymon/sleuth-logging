package com.home.dguymon.domain.capability.domain.dao;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;

import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CapabilityDao {
  
  public final String TABLE = "capability";
  public final String PRIMARY_KEY = "name";
  public final String DESCRIPTION_KEY = "description";
  
  public ArrayList<CapabilityDto> getAllCapabilities() {
    
    ArrayList<CapabilityDto> capabilities = new ArrayList<CapabilityDto>();
    
    DynamoDbClient ddb = DynamoDbClient.create();
    ScanRequest scanRequest = ScanRequest.builder()
        .tableName(TABLE)
        .build();
    
    ScanResponse response = ddb.scan(scanRequest);
    for (Map<String, AttributeValue> item : response.items()) {
      CapabilityDto capability = new CapabilityDto();
      capability.setName(item.get(PRIMARY_KEY).s());
      capability.setDescription(item.get(DESCRIPTION_KEY).s());
      
      capabilities.add(capability);
    }
    
    return capabilities;
  }
  
  public CapabilityDto getCapabilityByName(String name) {
    
    HashMap<String, AttributeValue> keyToGet = 
        new HashMap<String, AttributeValue>();
    
    keyToGet.put(PRIMARY_KEY, AttributeValue.builder()
        .s(name).build());
    
    GetItemRequest request = null;
    request = GetItemRequest.builder()
        .key(keyToGet)
        .tableName(TABLE)
        .build();
    
    DynamoDbClient ddb = DynamoDbClient.create();
    CapabilityDto capabilityDto = new CapabilityDto();

    try {
      Map<String, AttributeValue> returnedItem = 
          ddb.getItem(request).item();
      
      if (returnedItem != null) {
        Set<String> keys = returnedItem.keySet();
        for (String key : keys) {
          if (key.equals("name")) {
            capabilityDto.setName(returnedItem.get(key).s());
          } else if (key.equals("description")) {
            capabilityDto.setDescription(returnedItem.get(key).s());
          } else {
            log.warn("Unanticipated column");
          }
        } 
      } else {
        log.warn("No item found with the provided primary name key");
      }
    } catch (DynamoDbException e) {
      log.error(e.getMessage());
      return null;
    }
    
    return capabilityDto;
  }
  
  public String updateCapability(CapabilityDto capabilityDto) {
    
    return "success";
  }
  
  public String createCapability(CapabilityDto capabilityDto) {
    
    HashMap<String, AttributeValue> itemToInsert = 
        new HashMap<String, AttributeValue>();
    
    itemToInsert.put(PRIMARY_KEY, AttributeValue.builder()
        .s(capabilityDto.getName())
        .build());
    
    itemToInsert.put(DESCRIPTION_KEY, AttributeValue.builder()
        .s(capabilityDto.getDescription())
        .build());
    
    DynamoDbClient ddb = DynamoDbClient.create();
    PutItemRequest request = PutItemRequest.builder()
        .tableName(TABLE)
        .item(itemToInsert)
        .build();
    
    try {
      ddb.putItem(request);
    } catch (ResourceNotFoundException e) {
      log.error("Table \"%s\" was not found", TABLE);
      return "Failed to find table " + TABLE + " in DynamoDB";
    } catch (DynamoDbException e) {
      log.error(e.getMessage());
      return "Failed to insert capability into DynamoDB";
    }
    
    return "Item successfully inserted into table " + TABLE + ".";
  }
  
  public String deleteCapability(CapabilityDto capabilityDto) {
    
    return "success";
  }
  
}