package com.increff.employee.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;

public class OrderItemServiceTest extends AbstractUnitTest {
	
	@Autowired
	private OrderItemService service;

	@Test
	public void testAdd() throws ApiException {
		OrderItemPojo p=new OrderItemPojo();
		p.setSellingPrice(100);
		p.setQuantity(1);
		p.setProduct(new ProductPojo());
		service.add(p);
	}

	@Test
	public void testDelete() throws ApiException {
		service.delete(1);
	}
	
	@Test
	public void testGetAll() throws ApiException {
		service.getAll();
	}
	
	@Test
	public void testGetCurrentItems() throws ApiException {
		service.getCurrentItems();
	}
	

	
}
