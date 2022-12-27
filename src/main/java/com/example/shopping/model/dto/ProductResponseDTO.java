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
public class ProductResponseDTO {

    private int id;

    private String img;

    private String author;

    private String name;

    private String brand;

    private String category;

    private String suplier;

    private String original;

    private BigDecimal price;

    private int quantity;

}
