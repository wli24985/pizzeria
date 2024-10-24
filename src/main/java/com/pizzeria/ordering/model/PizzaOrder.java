package com.pizzeria.ordering.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor //(access = AccessLevel.PROTECTED)
public class PizzaOrder {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter private Integer id;

    @Getter @Setter private LocalDateTime dateTime;
    @Getter @Setter private String customerName;
    @Getter @Setter private String customerAddress;
    @Getter @Setter private String customerPhoneNumber;
    @Getter @Setter private String status;
    @Getter @Setter private boolean isTestData;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) //mappedBy = "pizzaorder",
    @JoinColumn(name = "pizzaorder_id")
    @Getter @Setter private List<Pizza> pizzaList = new ArrayList<>();

    public boolean compare(PizzaOrder actual){
        System.out.println("\n begin to compare with actual");
        boolean r = true;
        r = (this.customerName.compareTo(actual.customerName) == 0 &&
            this.customerAddress.compareTo(actual.customerAddress) == 0 &&
            this.customerPhoneNumber.compareTo(actual.customerPhoneNumber) == 0 &&
            !(this.isTestData ^ actual.isTestData) &&
            this.status.compareTo(actual.status) == 0
        );
        if(!r){
            System.out.println("\n Main Order Not Matching!");
            if(this.customerName.compareTo(actual.customerName) != 0) System.out.println("custmerName not match.");
            if(this.customerAddress.compareTo(actual.customerAddress) != 0) System.out.println("custmerAddress not match.");
            if(this.customerPhoneNumber.compareTo(actual.customerPhoneNumber) != 0) System.out.println("custmerPhoneNumber not match.");
            if((this.isTestData ^ actual.isTestData)) System.out.println("isTestData not match.");
            if(this.status.compareTo(actual.status) != 0) System.out.println("status not match.");

            return r;
        } 
        if(pizzaList.size() <= 0) System.out.println("\n No pizza in order!");
        else System.out.println("\n Number of pizzas in order = " + pizzaList.size());
        if(pizzaList.size() != actual.getPizzaList().size()) {
            System.out.println("\n acutual have less pizza! it has " + actual.getPizzaList().size());
            System.out.println("\n expected have: " + pizzaList.size());
            return false;
        }
        for(int i = 0; i < pizzaList.size(); i++){
            System.out.println("\n working on pizza #" + i);
            r = (
                pizzaList.get(i).getType().compareTo(actual.getPizzaList().get(i).getType()) == 0 &&
                pizzaList.get(i).getTopping().compareTo(actual.getPizzaList().get(i).getTopping()) == 0 &&
                pizzaList.get(i).getSize().compareTo(actual.getPizzaList().get(i).getSize()) == 0 &&
                pizzaList.get(i).getPrice() == actual.getPizzaList().get(i).getPrice() &&
                pizzaList.get(i).getQuantity() == actual.getPizzaList().get(i).getQuantity()
            );
            if(!r){
                System.out.println("\n Pizza Not Matching! #" + i);
                if(pizzaList.get(i).getType() != actual.getPizzaList().get(i).getType())  System.out.println("Pizza type not match.");
                if(pizzaList.get(i).getTopping() != actual.getPizzaList().get(i).getTopping() )  System.out.println("Pizza topping not match.");
                if(pizzaList.get(i).getSize() != actual.getPizzaList().get(i).getSize() )  System.out.println("Pizza size not match.");
                if(pizzaList.get(i).getPrice() != actual.getPizzaList().get(i).getPrice() )  System.out.println("Pizza price not match.");
                if(pizzaList.get(i).getQuantity() != actual.getPizzaList().get(i).getQuantity() )  System.out.println("Pizza Quantitiy not match.");
                return r;
            } 
        }
        System.out.println("\n good match!");
        return true;
    }
}