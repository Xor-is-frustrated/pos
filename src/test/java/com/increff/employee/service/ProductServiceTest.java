package com.increff.employee.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;

public class ProductServiceTest extends AbstractUnitTest{

	@Autowired
	private ProductService service;
	
	@Test
	public void testAdd() throws ApiException {
		ProductPojo p = new ProductPojo();
		BrandPojo b  = new BrandPojo();
		b.setBrand("Nestle");
		b.setCategory("Health");
		p.setBrand(b);
		p.setProduct("product");
		p.setBarcode("a1b1");
		p.setMrp(10.1);
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
	public void testNormalize() {
		ProductPojo p = new ProductPojo();
		p.setProduct(" Product ");
		service.normalize(p);
		assertEquals("product", p.getProduct());
	}
	
}
