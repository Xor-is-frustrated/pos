package com.increff.employee.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.AbstractUnitTest;

public class ProductDaoTest  extends AbstractUnitTest {

	@Autowired
	private ProductDao dao;
	
	@Test
	public void testInsert() {
		ProductPojo b = new ProductPojo();
		b.setBarcode("barcode");
		b.setBrand(new BrandPojo());
		b.setMrp(10);
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
	
	@Test
	public void testSelectBarcode() {
		dao.select("barcode");
	}
}
