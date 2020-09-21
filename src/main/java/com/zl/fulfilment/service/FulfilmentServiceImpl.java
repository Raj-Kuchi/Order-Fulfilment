package com.zl.fulfilment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zl.fulfilment.model.Catalog;
import com.zl.fulfilment.model.OrderDetails;
import com.zl.fulfilment.model.Orders;
import com.zl.fulfilment.model.Product;
import com.zl.fulfilment.model.Stock;
import com.zl.fulfilment.repository.OrderDetailsRepository;
import com.zl.fulfilment.repository.OrderRepository;
import com.zl.fulfilment.repository.ProductRepository;
import com.zl.fulfilment.repository.StockRepository;
import com.zl.fulfilment.service.interfaces.FulfilmentService;

@Service
public class FulfilmentServiceImpl implements FulfilmentService {

	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Override
	public void initCatalog(List<Product> products) {
		for(Product product: products) {
			productRepo.save(product);
		}
	}
	

	@Override
	public Catalog getCatalog() {
		// return the only catalog in the system
		Catalog catalog = new Catalog();
		catalog.setProducts(productRepo.findAll());
		return catalog;
		
	}
	
	@Override
	public void processRestock(List<Stock> restock){
		
		for(Stock stockItem: restock) {
			Stock s = stockRepo.findById(stockItem.getProductId()).orElse(null);
			if(s == null) {
				stockRepo.save(stockItem);
			} else {
				s.setQuantity(s.getQuantity()+stockItem.getQuantity());
				stockRepo.save(s);
			}
		}
	}
	
	@Override
	public List<OrderDetails> processOrder(Map<String,?> order){
		
		Long orderID = new Long((Integer)order.get("order_id"));
		Orders newOrder = new Orders();
		newOrder.setOrderId(orderID);
		
		List<Map> orderDetails = (List)order.get("requested");
		List<OrderDetails> newOrderDetails = new ArrayList();
		for(Map od : orderDetails) {
			OrderDetails newOd = new OrderDetails();
			newOd.setProductId(new Long((Integer)od.get("product_id")));
			newOd.setQuantity((Integer)od.get("quantity"));				
			newOrderDetails.add(newOd);
			newOd.setOrders(newOrder);
			orderRepo.save(newOrder);
			orderDetailsRepo.save(newOd);
		}
		return newOrderDetails;
	}
	
	public List<OrderDetails> fulfilOrder(List<OrderDetails> orderDetails) {
		
		int orderWeight = 0;
		int canShip = 0;
		int limit = 1800;
		
		/*Shipping data to be returned*/
		Orders shippingOrder = new Orders();
		List<OrderDetails> shippingOrderDetails = new ArrayList();
		
		
		/*Iterate over the order details and process each order for shipping*/
		for(OrderDetails od: orderDetails) {
			
			//Get the product and stock details
			long productID = od.getProductId();
			int perUnitWeight = 0;
			Product p = productRepo.findById(productID).orElse(null);
			Stock s = stockRepo.findById(productID).orElse(null);
			if(p == null || s == null) {
				continue;
			} else {
				perUnitWeight = p.getMass_g();
			}
			
			int required = od.getQuantity();
			int available = s.getQuantity();
			
			/*Iterate over the required items and verify if adding each one to shipping
			 * confirms to the limit requirements
			*/
			for(int i=1; i<=required; i++) {
				if(i>available) {
					break;
				}
				if(orderWeight + (i*perUnitWeight) < limit) {
					canShip++;
				}
			}
			
			//If there is any item available to ship then add to the shipping orders
			//and update the total orderWeight
			if(canShip > 0) {
				
				orderWeight = orderWeight + (canShip*perUnitWeight);
				
				OrderDetails shipOrder = new OrderDetails();
				shipOrder.setProductId(productID);
				shipOrder.setQuantity(canShip);
				
				shippingOrder.setOrderId(od.getOrders().getOrderId());
				shipOrder.setOrders(shippingOrder);
				
				shippingOrderDetails.add(shipOrder);
				
				
				//Update the stock based on the shipped items
				int availableStock = available - canShip;
				if(availableStock > 0) {
					s.setQuantity(availableStock);
					stockRepo.save(s);
				} else {
					stockRepo.delete(s);
				}
				
				//update the order and delete it if entire order is fulfilled
				int remaining = canShip - required;
				if(remaining > 0) {
					od.setQuantity(remaining);
					orderRepo.save(od.getOrders());
					orderDetailsRepo.save(od);
				} else {
					orderDetailsRepo.delete(od);
				}
				
			}
						
		}
		
		return shippingOrderDetails;
	}

}
