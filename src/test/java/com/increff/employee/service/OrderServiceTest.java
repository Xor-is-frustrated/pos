package com.increff.employee.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;

public class OrderServiceTest extends AbstractUnitTest{
	
	@Autowired
	private OrderService service;
	

	@Test
	public void testAdd() throws ApiException {
		OrderPojo p= new OrderPojo();
		p.setDatetime(LocalDateTime.now());
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
	


}
