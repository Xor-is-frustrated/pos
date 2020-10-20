package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;

public class BrandDtoTest  extends AbstractUnitTest {
	
	@Autowired
	private BrandDto brandDto;
	
	@Autowired
	private BrandService service;
	
	@Test
	public void testAdd() throws ApiException {
		BrandForm form= new BrandForm();
		form.setBrand("brand");
		form.setCategory("category");
		
		brandDto.add(form);
		
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		BrandPojo pojo = service.add(p);
		
		BrandData expected= new BrandData();
		expected.setBrand("nestle");
		expected.setCategory("health");
		
		BrandData actual = brandDto.getIt(pojo.getId());
		
		assertEquals(expected.getBrand(), actual.getBrand());
		assertEquals(expected.getCategory(), actual.getCategory());
		
	}
	
	@Test
	public void testGetDistinctCategories() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		service.add(p);
		
		BrandPojo p1 = new BrandPojo();
		p1.setBrand("Nestle");
		p1.setCategory("sports");
		service.add(p1);
		
		
		List<String>list=brandDto.getDistinctCategories();
		assertEquals("health", list.get(0));
		assertEquals("sports", list.get(1));
	}
	
	@Test
	public void testGetDistinctBrands() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		service.add(p);
		
		BrandPojo p1 = new BrandPojo();
		p1.setBrand("dabur");
		p1.setCategory("sports");
		service.add(p1);
		List<String>list=brandDto.getDistinctBrands();
		assertEquals("nestle", list.get(1));
		assertEquals("dabur", list.get(0));
	}
	
	@Test
	public void testGetAll() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		service.add(p);
		
		BrandPojo p1 = new BrandPojo();
		p1.setBrand("dabur");
		p1.setCategory("drinks");
		service.add(p1);
		List<BrandData>list=brandDto.getItAll();
		assertEquals("health", list.get(0).getCategory());
		assertEquals("drinks", list.get(1).getCategory());
		assertEquals("nestle", list.get(0).getBrand());
		assertEquals("dabur", list.get(1).getBrand());
	}

	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		BrandPojo pojo= service.add(p);
		
		BrandForm p1 = new BrandForm();
		p1.setBrand("dabur");
		p1.setCategory("drinks");
		
		brandDto.update(pojo.getId(),p1);
		BrandPojo actual = service.get(pojo.getId());
		
		assertEquals(p1.getBrand(), actual.getBrand());
		assertEquals(p1.getCategory(), actual.getCategory());
		
	}

	





}
