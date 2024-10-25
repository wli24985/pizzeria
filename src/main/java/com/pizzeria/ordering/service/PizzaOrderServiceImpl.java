package com.pizzeria.ordering.service;

import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizzeria.ordering.DTO.OrderingDTO;
import com.pizzeria.ordering.model.PizzaOrder;
import com.pizzeria.ordering.model.Pizza;
import com.pizzeria.ordering.repository.PizzaOrderRepository;

@Service
public class PizzaOrderServiceImpl {
    @Autowired
    PizzaOrderRepository pizzaOrderRepository;

    private static ModelMapper modelMapper = new ModelMapper();
    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public OrderingDTO createNewOrder(OrderingDTO orderingDTO) throws ParseException{
        // for(Pizza p: orderingDTO.getPizzaList()){
        //     if(p.getSize() != "SMALL" && p.getSize() != "MEDIUM" && p.getSize() != "LARGE"){
        //         return null;
        //     }
        // }
        orderingDTO.setId(null);
        PizzaOrder pizzaOrder_entity = modelMapper.map(orderingDTO, PizzaOrder.class);
        pizzaOrder_entity.setDateTime(LocalDateTime.now());
        pizzaOrder_entity = pizzaOrderRepository.save(pizzaOrder_entity);
        return getOrderingDto(pizzaOrder_entity);
    };

    /**
     * get all pizza ordering data stored in database ordered by Id in ascending order
     * @param void
     * @return formatted List Of OrderingDTO ready to be returned
     */
    public List<OrderingDTO> getAllOrderedById(){
        return getListOrderingDTO(
            pizzaOrderRepository.findAllByOrderByIdAsc()
            );
    }

    /**
     * get pizza ordering data stored in database using Id
     * @param id
     * @return formatted OrderingDTO ready to be returned
     */
    public OrderingDTO getOrderUsingId(Integer id) {

        if (pizzaOrderRepository.existsById(id)) {
            PizzaOrder pizzaOrder_Entity = pizzaOrderRepository.findById(id).get();
            if(pizzaOrder_Entity.getPizzaList().size()==0) System.out.println("emplyt PizzaList");
            pizzaOrder_Entity.setPizzaList(pizzaOrder_Entity.getPizzaList());
            return getOrderingDto(pizzaOrder_Entity);
        }else{
            return null;
        }
    }

    /**
     * delete/cancel pizza ordering data stored in database using Id
     * @param id
     * @return no return, it may throw exception, and 
     */
    /*
    public String deleteOrderWithId(Integer id) {

        if (pizzaOrderRepository.existsById(id)) {
            pizzaOrderRepository.deleteById(id);
            return "Delete Successful.";
        }else{
            return null;
        }
    }
    */
    /**
     * update pizza order status stored in database using Id
     * @param id
     * @return the new OrderingDTO 
     */
    // public OrderingDTO updateOrderStatusWithId(Integer id, String newStatus) {

    //     if (pizzaOrderRepository.existsById(id)) {
    //         int i = pizzaOrderRepository.setStatusForPizzaOrder(newStatus, id);
    //         System.out.println("update returned value: " + i);
    //         PizzaOrder pizzaOrder_Entity = pizzaOrderRepository.findById(id).get();
    //         return getOrderingDto(pizzaOrder_Entity);
    //     }else{
    //         return null;
    //     }
    // }

    /**
     * 
     * @param Orders_entity_list
     * @return Formmated lis of OrderingDTO
     */
    private List<OrderingDTO> getListOrderingDTO(List<PizzaOrder> Orders_entity_list){
        //formatter.setLenient(false);
        List<OrderingDTO> orderingDTO_list = new ArrayList<OrderingDTO>();
        for (PizzaOrder pizzaOrder_entity : Orders_entity_list) {
            orderingDTO_list.add(getOrderingDto(pizzaOrder_entity));
        }
        return orderingDTO_list;
    }
    /**
     * 
     * @param PizzaOrder Entity
     * @return formmated OrderingDto
     */
    private OrderingDTO getOrderingDto(PizzaOrder pizzaOrder_entity){
        String formated_datetime = formatter.format(pizzaOrder_entity.getDateTime());
        OrderingDTO o = modelMapper.map(pizzaOrder_entity, OrderingDTO.class);
        o.setDateTime(formated_datetime);
        return o;    
    }

    
}
