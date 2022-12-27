package com.example.shopping.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class MyOrder {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    private String orderDate;

    private BigDecimal totalPrice;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    private String address;

    private String phoneNumber;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "order_products", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = "account")
    @ToString.Exclude
    private Account account;

    @OneToMany(mappedBy = "myOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderDetails> orderDetails;

}
