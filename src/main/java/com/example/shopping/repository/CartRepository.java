package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.MyCart;

@Repository
public interface CartRepository extends JpaRepository<MyCart, Long> {

}
