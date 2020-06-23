package com.increff.employee.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.AbstractUnitTest;

public class InventoryDaoTest extends AbstractUnitTest {

	@Autowired
	private InventoryDao dao;
	
	@Test
	public void testInsert() {
		InventoryPojo b=new InventoryPojo();
		b.setQuantity(10);
		b.setProduct(new ProductPojo());
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
	public void testSelectId() {
		dao.select(1);
	}
}
