package com.example.shopping.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

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
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "quantity")
    private int quantity;

    // private int rate;
    private int ID_product;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    private String feedback;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private MyOrder myOrder;

    // @Override
    // public String toString() {
    // return "{" +
    // " id='" + getId() + "'" +
    // ", quantity='" + getQuantity() + "'" +
    // ", ID_product='" + getID_product() + "'" +
    // ", feedback='" + getFeedback() + "'" +
    // ", price='" + getPrice() + "'" +
    // ", myOrder='" + getMyOrder() + "'" +
    // "}";
    // }

}
