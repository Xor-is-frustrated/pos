package com.increff.employee.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.AbstractUnitTest;

public class BrandDaoTest extends AbstractUnitTest {

	@Autowired
	private BrandDao dao;

	@Test
	public void testInsert() {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

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
	public void testSelectCategories() {
		dao.selectCategories();
	}

	@Test
	public void testSelectbrands() {
		dao.selectBrands();
	}

	@Test
	public void testSelectDistinctbrands() {
		dao.selectDistinctBrands("brand");
	}

	@Test
	public void testSelectDistinctCategories() {
		dao.selectDistinctCategories("category");
	}

	@Test
	public void testSelectId() {
		dao.select(1);
	}

	@Test
	public void testSelectBrandCategory() {
		dao.select("brand", "category");
	}
	
	@Test
	public void testSelectBrand() {
		dao.select("brand");
	}

}
