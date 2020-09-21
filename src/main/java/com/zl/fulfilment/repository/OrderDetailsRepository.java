package com.zl.fulfilment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zl.fulfilment.model.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository <OrderDetails, Long>{

}
