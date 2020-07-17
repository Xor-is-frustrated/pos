package com.increff.pos.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;

public class BrandServiceTest extends AbstractUnitTest {

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
	public void testGet() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		service.add(p);
		int id = p.getId();

		BrandPojo pojo = service.get(id);
		assertEquals("nestle", pojo.getBrand());
		assertEquals("health", pojo.getCategory());
	}

	@Test
	public void testGetByBrandAndCategory() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		service.add(p);
		//add

		BrandPojo pojo = service.getByBrandAndCategory("nestle", "health");
		assertEquals("nestle", pojo.getBrand());
		assertEquals("health", pojo.getCategory());

	}

	@Test
	public void TestGetByBrand() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		service.add(p);

		BrandPojo p1 = new BrandPojo();
		p1.setBrand("Nestle");
		p1.setCategory("sports");
		service.add(p1);
//add
		List<BrandPojo> list = service.getByBrand("nestle");
		assertEquals("health", list.get(0).getCategory());
		assertEquals("sports", list.get(1).getCategory());
	}

	@Test
	public void TestGetByCategory() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		service.add(p);

		BrandPojo p1 = new BrandPojo();
		p1.setBrand("dabur");
		p1.setCategory("Health");
		service.add(p1);

		List<BrandPojo> list = service.getByCategory("health");
		assertEquals("nestle", list.get(0).getBrand());
		assertEquals("dabur", list.get(1).getBrand());
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
		List<BrandPojo> list = service.getAll();
		assertEquals("health", list.get(0).getCategory());
		assertEquals("drinks", list.get(1).getCategory());
		assertEquals("nestle", list.get(0).getBrand());
		assertEquals("dabur", list.get(1).getBrand());

	}

	@Test
	public void testGetDisitnctCategories() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory("Health");
		service.add(p);

		BrandPojo p1 = new BrandPojo();
		p1.setBrand("Nestle");
		p1.setCategory("sports");
		service.add(p1);

		List<String> list = service.getDistinctCategories();
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
		List<String> list = service.getDistinctBrands();
		assertEquals("nestle", list.get(1));
		assertEquals("dabur", list.get(0));

	}

	@Test
	public void testNormalize() {
		BrandPojo p = new BrandPojo();
		p.setBrand("Nestle");
		p.setCategory(" Health");
		service.normalizeBrand(p);
		assertEquals("nestle", p.getBrand());
		assertEquals("health", p.getCategory());
	}

}
