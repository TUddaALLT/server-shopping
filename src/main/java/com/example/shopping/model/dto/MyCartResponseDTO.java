package com.example.shopping.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
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
public class MyCartResponseDTO {
    private long id;
    private BigDecimal totalPrice;
    private List<ProductResponseDTO> products;
    private Set<CartDetailsResponseDTO> cartDetails;
}
