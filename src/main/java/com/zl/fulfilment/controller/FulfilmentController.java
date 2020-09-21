package com.zl.fulfilment.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zl.fulfilment.exceptions.CatalogInitializedException;
import com.zl.fulfilment.exceptions.OrderAlreadyInProcessException;
import com.zl.fulfilment.model.Catalog;
import com.zl.fulfilment.model.OrderDetails;
import com.zl.fulfilment.model.Product;
import com.zl.fulfilment.model.Stock;
import com.zl.fulfilment.repository.OrderDetailsRepository;
import com.zl.fulfilment.repository.OrderRepository;
import com.zl.fulfilment.repository.ProductRepository;
import com.zl.fulfilment.service.interfaces.FulfilmentService;

@RestController
public class FulfilmentController {
	
	@Autowired
	private FulfilmentService service;
	
	@Autowired
	private ProductRepository productRepo;
		
	@Autowired
	private OrderRepository orderRepo;
	
	
	@PutMapping(path = "/init_catalog", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> initCatalog(@RequestBody List<Product> products){
		if(productRepo.count() != 0) {
			CatalogInitializedException error = 
				      new CatalogInitializedException(HttpStatus.BAD_REQUEST, "Catalog is already initialized.");
			return new ResponseEntity<Object>(
					error, new HttpHeaders(), error.getStatus());
		}
		service.initCatalog(products);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(path="/get_catalog")
	public Catalog getCatalog() {
		return service.getCatalog();
	}
	
	
	@PostMapping(path = "/process_restock", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> processRestock(@RequestBody List<Stock> restock){
		
		service.processRestock(restock);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	@PostMapping(path = "/process_order", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> processOrder(@RequestBody Map<String,?> order){
		
		if(order != null) {
			Long orderID = new Long((Integer)order.get("order_id"));
			if(orderRepo.existsById(orderID)) {
				OrderAlreadyInProcessException error = 
					      new OrderAlreadyInProcessException(HttpStatus.BAD_REQUEST, "An order with this ID has already been received and currently in process.");
				return new ResponseEntity<Object>(
						error, new HttpHeaders(), error.getStatus());
			}
			
			List<OrderDetails> orderDetails = service.processOrder(order);
			List<OrderDetails> shippingDetails = service.fulfilOrder(orderDetails);
			if(shippingDetails.size() > 0) {
				Long orderId = shippingDetails.get(0).getOrders().getOrderId();
				Map<String, Object> shippingOrders = new HashMap();
				shippingOrders.put("order_id", orderId);
				shippingOrders.put("shipped", shippingDetails);
			
				return new ResponseEntity<Object>(shippingOrders, HttpStatus.OK);
			}
			
			
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
