package com.increff.employee.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.BrandPojo;

public class BrandServiceTest extends AbstractUnitTest{

	@Autowired
	private BrandService service;

	@Test
	public void testAdd() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		service.add(p);
	}
	
	@Test
	public void TestDistinctCategories() {
		service.getDistinctCategories("category");
	}

	@Test
	public void getDistinctBrands() {
		service.getDistinctCategories("brand");
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
	public void testGetCategories() {
		service.getCategories();
	}

	@Test
	public void testGetBrands() {
		service.getBrands();
	}

	

	
	@Test
	public void testNormalize() {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory(" Health");
		service.normalize(p);
		assertEquals("nestle", p.getBrand());
		assertEquals("health", p.getCategory());
	}
	
}
