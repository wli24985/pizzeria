package com.pizzeria.ordering.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pizzeria.ordering.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    
}

