package com.pizzeria.ordering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pizzeria.ordering.model.PizzaOrder;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Integer> {
    //public List<PizzaOrder> findAllByOrderByCustomerPhoneNumberAsc(String phoneNumber);
    public List<PizzaOrder> findAllByOrderByIdAsc();

}
