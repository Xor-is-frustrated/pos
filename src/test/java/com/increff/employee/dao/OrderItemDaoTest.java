package com.increff.employee.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.AbstractUnitTest;

public class OrderItemDaoTest  extends AbstractUnitTest {

	@Autowired
	private OrderItemDao dao;
	
	@Test
	public void testInsert() {
		OrderItemPojo b = new OrderItemPojo();
		b.setProduct(new ProductPojo());
		b.setQuantity(10);
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
	public void testSelectCurrentItems() {
		dao.selectCurrentItems();
	}
	
	@Test
	public void testSelectId() {
		dao.select(1);
	}
	
	

}
