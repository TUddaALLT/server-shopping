package com.example.shopping.model.dto;

import com.example.shopping.entity.OrderDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderCreateDTO {

    private double totalPrice;

    private String address;

    private String phoneNumber;

    private String name;

    private OrderDetails products[];

}
