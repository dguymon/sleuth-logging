package com.home.dguymon.domain.capability.domain.dao;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides data access via DynamoDB queries into capability table.
 * 
 * @author Danazn
 *
 */
@Slf4j
@Component
public class CapabilityDao {
  
  public static final String TABLE = "capability";
  public static final String PRIMARY_KEY = "name";
  public static final String DESCRIPTION_KEY = "description";
  
  /**
   * Retrieves all capability items from capability table in DynamoDB.
   * 
   * @return ArrayList of CapabilityDtos from capability table.
   */
  public List<CapabilityDto> getAllCapabilities() {
    
    List<CapabilityDto> capabilities = new ArrayList<>();
    
    try (DynamoDbClient ddb = DynamoDbClient.create()) {
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
    }
    
    return capabilities;
  }
  
  /**
   * Retreives a capability item by name from the capability table in DynamoDB.
   * 
   * @param name Primary key to search for.
   * @return CapabilityDto matching the capability with the same primary key.
   */
  public CapabilityDto getCapabilityByName(String name) {
    
    HashMap<String, AttributeValue> keyToGet = 
        new HashMap<>();
    
    CapabilityDto capabilityDto = new CapabilityDto();
    
    keyToGet.put(PRIMARY_KEY, AttributeValue.builder()
        .s(name).build());
    
    GetItemRequest request = null;
    request = GetItemRequest.builder()
        .key(keyToGet)
        .tableName(TABLE)
        .build();
    
    
    try (DynamoDbClient ddb = DynamoDbClient.create()) {  
      try {
        Map<String, AttributeValue> returnedItem = 
            ddb.getItem(request).item();
        
        if (returnedItem != null) {
          Set<String> keys = returnedItem.keySet();
          for (String key : keys) {
            if (key.equals(PRIMARY_KEY)) {
              capabilityDto.setName(returnedItem.get(key).s());
            } else if (key.equals(DESCRIPTION_KEY)) {
              capabilityDto.setDescription(returnedItem.get(key).s());
            } else {
              log.warn("Unanticipated column");
            }
          } 
        } else {
          log.warn("No item found with the provided primary name key");
          return null;
        }
      } catch (DynamoDbException e) {
        log.error(e.getMessage());
        return null;
      }
    } 
    
    return capabilityDto;
  }
  
  /**
   * Updates an existing capability in the capability table in DynamoDB.
   * 
   * @param capabilityDto Updated capability info.
   * @return MessageDto indicating status of update operation.
   */
  public MessageDto updateCapability(CapabilityDto capabilityDto) {
    
    HashMap<String, AttributeValue> itemToUpdate = 
        new HashMap<>();
    
    MessageDto messageDto = new MessageDto();
    
    itemToUpdate.put(PRIMARY_KEY, AttributeValue.builder()
        .s(capabilityDto.getName())
        .build());
    
    HashMap<String, AttributeValueUpdate> attributesToUpdate = 
        new HashMap<>();
    
    attributesToUpdate.put(DESCRIPTION_KEY, AttributeValueUpdate.builder()
        .value(AttributeValue.builder().s(capabilityDto.getDescription()).build())
        .action(AttributeAction.PUT)
        .build());
    
    UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
        .tableName(TABLE)
        .key(itemToUpdate)
        .attributeUpdates(attributesToUpdate)
        .build();
    
    try (DynamoDbClient ddb = DynamoDbClient.create()) {
      try {
        ddb.updateItem(updateItemRequest);
      } catch (ResourceNotFoundException e) {
        log.error("Item not found in table \"%s\"", TABLE);
        messageDto.setMessage("Failed to find item to update in table " + TABLE);
        messageDto.setError(true);
        return messageDto;
      } catch (DynamoDbException e) {
        log.error(e.getMessage());
        messageDto.setMessage("Failed to update item in table " + TABLE);
        messageDto.setError(true);
        return messageDto;
      }
    }
    
    messageDto.setMessage("Item successfully updated in table " + TABLE + ".");
    messageDto.setError(false);
    return messageDto;
  }
  
  /**
   * Creates a new capability item in the capability table of DynamoDB.
   * 
   * @param capabilityDto The capability to create.
   * @return MessageDto indicating status of creation attempt.
   */
  public MessageDto createCapability(CapabilityDto capabilityDto) {
    
    HashMap<String, AttributeValue> itemToInsert = 
        new HashMap<>();
    
    MessageDto messageDto = new MessageDto();
    
    itemToInsert.put(PRIMARY_KEY, AttributeValue.builder()
        .s(capabilityDto.getName())
        .build());
    
    itemToInsert.put(DESCRIPTION_KEY, AttributeValue.builder()
        .s(capabilityDto.getDescription())
        .build());
    
    try (DynamoDbClient ddb = DynamoDbClient.create()) {
      PutItemRequest request = PutItemRequest.builder()
          .tableName(TABLE)
          .item(itemToInsert)
          .build();
      
      try {
        ddb.putItem(request);
      } catch (ResourceNotFoundException e) {
        log.error("Table \"%s\" was not found", TABLE);
        messageDto.setMessage("Failed to find table " + TABLE + " in DynamoDB");
        messageDto.setError(true);
        return messageDto;
      } catch (DynamoDbException e) {
        log.error(e.getMessage());
        messageDto.setMessage("Failed to insert capability into DynamoDB");
        messageDto.setError(true);
        return messageDto;
      }
    }
    
    messageDto.setMessage("Item successfully inserted into table " + TABLE + ".");
    messageDto.setError(false);
    return messageDto;
  }
  
  /**
   * Deletes an existing capability item in the capability table in DynamoDB.
   * 
   * @param name Primary key of the item to delete in capability table.
   * @return MessageDto indicating deletion attempt status.
   */
  public MessageDto deleteCapability(String name) {
    
    HashMap<String, AttributeValue> keyToDelete = 
        new HashMap<>();
    
    MessageDto messageDto = new MessageDto();
    
    keyToDelete.put(PRIMARY_KEY, AttributeValue.builder()
        .s(name)
        .build());
    
    DeleteItemRequest deleteRequest = DeleteItemRequest.builder()
        .tableName(TABLE)
        .key(keyToDelete)
        .build();
    
    try (DynamoDbAsyncClient ddb = DynamoDbAsyncClient.create()) {
      try {
        ddb.deleteItem(deleteRequest);
      } catch (DynamoDbException e) {
        log.error(e.getMessage());
        messageDto.setMessage("Unable to delete item from table " + TABLE);
        messageDto.setError(true);
        return messageDto;
      }
    }
    
    messageDto.setMessage("Item successfully deleted from table " + TABLE + ".");
    messageDto.setError(false);
    return messageDto;
  }
  
}