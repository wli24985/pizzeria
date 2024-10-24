package com.pizzeria.ordering.DTO;

import java.util.ArrayList;
import java.util.List;

import com.pizzeria.ordering.model.Pizza;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class OrderingDTO {
    @Getter @Setter private Integer id;
    @Getter @Setter private String dateTime;
    @Getter @Setter private String customerName;
    @Getter @Setter private String customerAddress;
    @Getter @Setter private String customerPhoneNumber;
    @Getter @Setter private String status;
    @Getter @Setter private boolean isTestData;
    @Getter @Setter private List<Pizza> pizzaList = new ArrayList<>();
}
