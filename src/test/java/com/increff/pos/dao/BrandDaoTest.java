package com.increff.pos.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.AbstractUnitTest;

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
	public void testSelectAll() {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		dao.insert(b);

		BrandPojo b1 = new BrandPojo();
		b1.setBrand("brand1");
		b1.setCategory("category1");

		dao.insert(b1);
		List<BrandPojo> list = dao.selectAll();
//		list size
		assertEquals(b.getBrand(), list.get(0).getBrand());
		assertEquals(b.getCategory(), list.get(0).getCategory());
		assertEquals(b1.getBrand(), list.get(1).getBrand());
		assertEquals(b1.getCategory(), list.get(1).getCategory());
	}

	@Test
	public void testSelectDistinctCategories() {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		dao.insert(b);

		BrandPojo b1 = new BrandPojo();
		b1.setBrand("brand1");
		b1.setCategory("category1");

		dao.insert(b1);
		
		BrandPojo b2 = new BrandPojo();
		b2.setBrand("brand1");
		b2.setCategory("category");

		dao.insert(b2);
		
		
		List<String> list = dao.selectDistinctCategories();
		assertEquals(2, list.size());
		assertEquals(b.getCategory(), list.get(0));
		assertEquals(b1.getCategory(), list.get(1));

	}

	@Test
	public void testSelectDistinctbrands() {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		dao.insert(b);

		BrandPojo b1 = new BrandPojo();
		b1.setBrand("brand1");
		b1.setCategory("category1");
//		add
		dao.insert(b1);
		List<String> list = dao.selectDistinctBrands();

		assertEquals(b.getBrand(), list.get(0));
		assertEquals(b1.getBrand(), list.get(1));
	}

	@Test
	public void testSelectByBrand() {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		dao.insert(b);

		BrandPojo b1 = new BrandPojo();
		b1.setBrand("brand");
		b1.setCategory("category1");
		
		dao.insert(b1);
		
		//add brandpojo b2
		List<BrandPojo> list = dao.selectByBrand("brand");

		assertEquals(b.getBrand(), list.get(0).getBrand());
		assertEquals(b1.getBrand(), list.get(1).getBrand());
		assertEquals(b.getCategory(), list.get(0).getCategory());
		assertEquals(b1.getCategory(), list.get(1).getCategory());

	}

	@Test
	public void testSelectByCategory() {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		dao.insert(b);

		BrandPojo b1 = new BrandPojo();
		b1.setBrand("brand1");
		b1.setCategory("category");

		dao.insert(b1);
		//asdd
		List<BrandPojo> list = dao.selectByCategory("category");

		assertEquals(b.getBrand(), list.get(0).getBrand());
		assertEquals(b1.getBrand(), list.get(1).getBrand());
		assertEquals(b.getCategory(), list.get(0).getCategory());
		assertEquals(b1.getCategory(), list.get(1).getCategory());
	}

	@Test
	public void testSelectId() {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		int id = dao.insert(b).getId();
		BrandPojo p = dao.select(id);
		assertEquals(b.getBrand(), p.getBrand());
		assertEquals(b.getCategory(), p.getCategory());

	}

	@Test
	public void testSelectBrandCategory() {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		dao.insert(b);
		//add
		BrandPojo p = dao.selectByBrandAndCategory("brand", "category");
		assertEquals(b.getBrand(), p.getBrand());
		assertEquals(b.getCategory(), p.getCategory());
	}
	

}
