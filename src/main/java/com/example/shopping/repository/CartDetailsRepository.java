package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.CartDetails;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Integer> {

}
