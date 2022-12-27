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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
        @Id
        @Column(name = "product_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String img;
        @Type(type = "org.hibernate.type.StringNVarcharType")
        private String author;
        @Type(type = "org.hibernate.type.StringNVarcharType")
        private String name;
        @Type(type = "org.hibernate.type.StringNVarcharType")
        private String brand;
        @Type(type = "org.hibernate.type.StringNVarcharType")
        private String category;
        @Type(type = "org.hibernate.type.StringNVarcharType")
        private String suplier;
        @Type(type = "org.hibernate.type.StringNVarcharType")
        private String original;

        private BigDecimal price;
        private int quantity;

        @JsonIgnore
        @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
        // Quan hệ n-n với đối tượng ở dưới (cart) (1 product o trong nhieu cart)
        @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
        @ToString.Exclude // Khoonhg sử dụng trong toString()
        @JoinTable(name = "product_order", // Tạo ra một join Table tên là "product_cart"
                        joinColumns = @JoinColumn(name = "product_id"), // TRong đó, khóa ngoại chính là product_id trỏ
                                                                        // tới class
                                                                        // hiện tại (Address)
                        inverseJoinColumns = @JoinColumn(name = "order_id") // Khóa ngoại thứ 2 trỏ tới thuộc tính ở
                                                                            // dưới (Cart)
        )
        private Set<MyOrder> order_products;

        @JsonIgnore
        @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
        // Quan hệ n-n với đối tượng ở dưới (cart) (1 product o trong nhieu cart)
        @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
        @ToString.Exclude // Khoonhg sử dụng trong toString()
        @JoinTable(name = "product_cart", // Tạo ra một join Table tên là "product_cart"
                        joinColumns = @JoinColumn(name = "product_id"), // TRong đó, khóa ngoại chính là product_id trỏ
                                                                        // tới class
                                                                        // hiện tại (Address)
                        inverseJoinColumns = @JoinColumn(name = "cart_id") // Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới
                                                                           // (Cart)
        )
        private Set<MyCart> carts;

        // @Override
        // public String toString() {
        // return "{" +
        // " id='" + getId() + "'" +
        // ", img='" + getImg() + "'" +
        // ", author='" + getAuthor() + "'" +
        // ", name='" + getName() + "'" +
        // ", brand='" + getBrand() + "'" +
        // ", category='" + getCategory() + "'" +
        // ", suplier='" + getSuplier() + "'" +
        // ", original='" + getOriginal() + "'" +
        // ", price='" + getPrice() + "'" +
        // ", quantity='" + getQuantity() + "'" +
        // ", order_products='" + getOrder_products() + "'" +
        // ", carts='" + getCarts() + "'" +
        // "}";
        // }

}
