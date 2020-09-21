package com.zl.fulfilment.service.interfaces;


import java.util.List;
import java.util.Map;

import com.zl.fulfilment.model.Catalog;
import com.zl.fulfilment.model.OrderDetails;
import com.zl.fulfilment.model.Product;
import com.zl.fulfilment.model.Stock;


public interface FulfilmentService {
	
	void initCatalog(List<Product> products);
	
	Catalog getCatalog();

	void processRestock(List<Stock> restock);
	
	List<OrderDetails> processOrder(Map<String,?> order);
	
	List<OrderDetails> fulfilOrder(List<OrderDetails> orderDetails);
}
