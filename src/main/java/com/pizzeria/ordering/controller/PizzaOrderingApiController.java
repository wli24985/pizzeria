package com.pizzeria.ordering.controller;

import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pizzeria.ordering.DTO.OrderingDTO;
import com.pizzeria.ordering.service.PizzaOrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Pizza Ordering Controller")
@RequestMapping(path = "/pizzaria")
public class PizzaOrderingApiController {
    @Autowired
    PizzaOrderServiceImpl pizzaOrderService;

    private static final Logger logIt = LoggerFactory.getLogger(PizzaOrderingApiController.class);
    /**
     * - creates a new pizza order
     * <p>
        - expects a valid pizza order object as its body payload, except that it does not have an id property; you can assume that the given object is always valid
        <p>
        - adds the given object to the collection and assigns a unique integer id to it
        <p>
        - the response code is 201 and the response body is the created record, including its unique id
     * @param OrderingDTO
     * @return ResponseEntity with created pizza order
     * @throws ParseException
     */
    @Operation(summary = "Create a Pizza Order")
    @PostMapping("/")
    public ResponseEntity<?> createPizzaOrderEntity(@RequestBody OrderingDTO orderingDTO) throws ParseException {
        logIt.info("Logging: begin Posting 1 new pizza orger.");
        OrderingDTO w =  pizzaOrderService.createNewOrder(orderingDTO);
        if(w == null){
            return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST); 
        }
        return new ResponseEntity<>(w, HttpStatus.CREATED);
    }

    /**
     * - returns list of all pizza order records in the db
     * - the response code is 200
     * <p>
     * @return 200 with weather data
     */
    @Operation(summary = "Get the full list of Pizza Orders")
    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        logIt.info("Logging: begin listing all pizza orgers.");
        List<OrderingDTO> ret_ordering_dto_list = pizzaOrderService.getAllOrderedById();
        return ResponseEntity.ok().body(ret_ordering_dto_list);
    }

    /**
     * - returns a record with the given id
     * <p>
     - if the matching record exists, the response code is 200 and the response body is the matching object
     * <p>
     - if there is no record in the collection with the given id, the response code is 404
     * @param id
     * @return 404 if not found or 200 with weather data if found
     */
    @Operation(summary = "Get a Pizza Order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderingById(@PathVariable Integer id) {
        logIt.info("Logging: begin getting 1 pizza by ID.");
        logIt.debug("the id passed in is: " + id);
        OrderingDTO ret_ordering_dto = pizzaOrderService.getOrderUsingId(id);
        if(ret_ordering_dto == null){
            // ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            // return responseEntity;
           return new ResponseEntity<Error>(HttpStatus.NOT_FOUND); 
        }else{
            return ResponseEntity.ok().body(ret_ordering_dto);
        }
    }

    /**
     * - deletes a record with the given id
     * <p>
     - if the matching record exists, the response code is 204 and the response body is empty
     * <p>
     - if there is no record in the collection with the given id, the response code is 404
     * @param id
     * @return 404 if not found or 204 with order data if found
     */
    @Operation(summary = "Delete a Pizza Order by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderingById(@PathVariable Integer id) {
        logIt.info("Logging: begin delete 1 pizza order.");
        String s = pizzaOrderService.deleteOrderWithId(id);
        if(s == null){

           return new ResponseEntity<Error>(HttpStatus.NOT_FOUND); 
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * - updates a record with the given id to new status
     * <p>
     - if the matching record exists, the response code is 200 and the response body is the updated PizzaOrder
     * <p>
     - if there is no record in the collection with the given id, the response code is 404
     * @param id
     * @return 404 if not found or 200 with order data if found
    */
    @Operation(summary = "Update a Pizza Order by ID to new status")
    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<?> updateOrderStatusById(@PathVariable Integer id, @PathVariable String status) {
        logIt.info("Logging: begin update 1 pizza order.");
        List<String> validStatusList = new ArrayList<>(){{
            add("PREPARING");
            add("BAKED");
            add("DELIVERING");
            add("DELIVERED");
        }};
        if(!validStatusList.contains(status)){
            return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST); 
        }
        OrderingDTO ret_ordering_dto = pizzaOrderService.updateOrderStatusWithId(id, status);
        if(ret_ordering_dto == null){
            return new ResponseEntity<Error>(HttpStatus.NOT_FOUND); 
        }else{
            return ResponseEntity.ok().body(ret_ordering_dto);
        }
    }
    
}
