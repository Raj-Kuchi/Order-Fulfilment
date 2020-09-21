package com.zl.fulfilment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zl.fulfilment.model.Product;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long>{

}
