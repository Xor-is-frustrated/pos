package com.increff.employee.dao;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.AbstractUnitTest;

public class OrderDaoTest extends AbstractUnitTest {

	@Autowired
	private OrderDao dao;

	@Test
	public void testInsert() {
		OrderPojo b = new OrderPojo();
		b.setDatetime(LocalDateTime.now());
		dao.insert(b);
	}

	@Test
	public void testDelete() {
		dao.delete(1);
	}

	@Test
	public void testSelectAll() {
		dao.selectAll();
	}
	@Test
	public void testSelectCurrentOrder() {
		dao.selectCurrentOrder();
	}
	
	@Test
	public void testSelectId() {
		dao.select(1);
	}
	
}
