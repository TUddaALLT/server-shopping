package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.MyOrder;

@Repository
public interface OrderDetailsRepository extends JpaRepository<MyOrder, Integer> {

}
