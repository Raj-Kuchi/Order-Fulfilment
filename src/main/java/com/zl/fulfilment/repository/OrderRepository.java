package com.zl.fulfilment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zl.fulfilment.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository <Orders, Long>{

}
