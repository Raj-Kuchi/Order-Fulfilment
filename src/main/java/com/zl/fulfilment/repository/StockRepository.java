package com.zl.fulfilment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zl.fulfilment.model.Stock;

@Repository
public interface StockRepository extends JpaRepository <Stock, Long>{

}
