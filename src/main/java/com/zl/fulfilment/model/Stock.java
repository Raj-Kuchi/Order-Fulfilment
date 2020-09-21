package com.zl.fulfilment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Stock {
	
	@Id
	@JoinColumn(name = "product_id")
	private Long productId;
	
	private Integer quantity;

	public Long getProductId() {
		return productId;
	}

	public void setProduct_id(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
