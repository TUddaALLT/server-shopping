package com.example.shopping.model.dto;

import java.math.BigDecimal;

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
public class OrderDetailsResponseDTO {

    private int id;

    private int quantity;

    private int ID_product;

    private String feedback;

    private BigDecimal price;
}
