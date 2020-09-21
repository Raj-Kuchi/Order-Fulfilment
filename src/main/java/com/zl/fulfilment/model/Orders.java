package com.zl.fulfilment.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Orders {

	@Id
	@Column(name="order_id")
	private Long orderId;
	
	/*
	@Column(name="requested")
	@OneToMany(mappedBy="orders")
	
	private List<OrderDetails> orderDetails = new ArrayList();
*/
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/*
	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	} */
	
	
	
	
	
}
