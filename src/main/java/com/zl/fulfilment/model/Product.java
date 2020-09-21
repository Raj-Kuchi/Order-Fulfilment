package com.zl.fulfilment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PRODUCTS")
public class Product {
	
	@Id
	@Column(name="product_id")
	private Long productId;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="mass_g")
	private Integer massG;

	public Long getProduct_id() {
		return productId;
	}

	public void setProduct_id(Long productId) {
		this.productId = productId;
	}

	public String getProduct_name() {
		return productName;
	}

	public void setProduct_name(String productName) {
		this.productName = productName;
	}

	public Integer getMass_g() {
		return massG;
	}

	public void setMass_g(Integer massG) {
		this.massG = massG;
	}

	
}
