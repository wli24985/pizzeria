package com.pizzeria.ordering.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

enum Size {
    SMALL,
    MEDIUM,
    LARGE
}
enum Type {
    REGULAR,
    THIN_CRUST,
    DEEP_DISH
}
enum topping{
    PEPERONI,
    MEAT,
    MUSHROOM,
    CHEESE
}
enum status{
    PREPARING,
    BAKED, 
    DELIVERING,
    DELIVERED
}
@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Pizza {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter private Integer id; 

    @ManyToOne   //(fetch = FetchType.EAGER) //@JoinColumn(name = "pizzaorder_id")
    @Getter @Setter private PizzaOrder pizzaorder;

    @Getter @Setter private String type;
    @Getter @Setter private String size;
    @Getter @Setter private String topping;
    @Getter @Setter private double price;
    @Getter @Setter private int quantity;


}
