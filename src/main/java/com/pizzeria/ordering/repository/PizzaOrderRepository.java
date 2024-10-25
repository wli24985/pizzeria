package com.pizzeria.ordering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pizzeria.ordering.model.PizzaOrder;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Integer> {
    //public List<PizzaOrder> findAllByOrderByCustomerPhoneNumberAsc(String phoneNumber);
    public List<PizzaOrder> findAllByOrderByIdAsc();
    public List<PizzaOrder> findAllByIsTestData(boolean b);

    @Modifying
    @Query("update PizzaOrder set status = :status where id = :id")
    int setStatusForPizzaOrder(@Param("status") String status, @Param("id") Integer id);   

}
