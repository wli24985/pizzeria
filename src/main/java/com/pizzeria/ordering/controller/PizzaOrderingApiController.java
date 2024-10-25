package com.pizzeria.ordering.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pizzeria.ordering.DTO.OrderingDTO;
import com.pizzeria.ordering.service.PizzaOrderServiceImpl;

@RestController
@RequestMapping(path = "/pizzaria")
public class PizzaOrderingApiController {
    @Autowired
    PizzaOrderServiceImpl pizzaOrderService;

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
    @PostMapping
    public ResponseEntity<?> createPizzaOrderEntity(@RequestBody OrderingDTO orderingDTO) throws ParseException {
        OrderingDTO w =  pizzaOrderService.createNewOrder(orderingDTO);
        // if(w == null){
        //     return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST); 
        // }
        return new ResponseEntity<>(w, HttpStatus.CREATED);
    }

    /**
     * - returns list of all pizza order records in the db
     * - the response code is 200
     * <p>
     * @return 200 with weather data
     */
    @GetMapping("/")
    public ResponseEntity<?> getAll() {
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
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderingById(@PathVariable Integer id) {

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
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderingById(@PathVariable Integer id) {

        String s = pizzaOrderService.deleteOrderWithId(id);
        if(s == null){

           return new ResponseEntity<Error>(HttpStatus.NOT_FOUND); 
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * - deletes a record with the given id
     * <p>
     - if the matching record exists, the response code is 204 and the response body is empty
     * <p>
     - if there is no record in the collection with the given id, the response code is 404
     * @param id
     * @return 404 if not found or 200 with order data if found
     
    @GetMapping("/status/{id}/{status}")
    public ResponseEntity<?> updateOrderStatusById(@PathVariable Integer id, @PathVariable String status) {
        if(status != "PREPARING" && status != "BAKED" && status != "DELIVERING" && status != "DELIVERED"){
            return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST); 
        }
        OrderingDTO ret_ordering_dto = pizzaOrderService.updateOrderStatusWithId(id, status);
        if(ret_ordering_dto == null){
            // ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            // return responseEntity;
            return new ResponseEntity<Error>(HttpStatus.NOT_FOUND); 
        }else{
            return ResponseEntity.ok().body(ret_ordering_dto);
        }
    }
    */
}
