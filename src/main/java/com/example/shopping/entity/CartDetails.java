package com.example.shopping.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CartDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    private int productID;

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private MyCart cart;

    // @Override
    // public String toString() {
    // return "{" +
    // " id='" + getId() + "'" +
    // ", quantity='" + getQuantity() + "'" +
    // ", productID='" + getProductID() + "'" +
    // ", cart='" + getCart() + "'" +
    // "}";
    // }

}
