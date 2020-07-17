package com.increff.pos.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;

public class ProductDaoTest  extends AbstractUnitTest {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private BrandDao brandDao;
	
	
	@Test
	public void testInsert() {
		BrandPojo p=new BrandPojo();
		p.setBrand("brand");
		p.setCategory("category");
		
		brandDao.insert(p);
		
		ProductPojo b = new ProductPojo();
		b.setBarcode("barcode");
		b.setBrand(p);
		b.setMrp(10.1);
		b.setName("name");
		
		productDao.insert(b);
		
		
	}


	@Test
	public void testSelectAll() {
		BrandPojo p=new BrandPojo();
		p.setBrand("brand");
		p.setCategory("category");
		
		brandDao.insert(p);
		
		ProductPojo b = new ProductPojo();
		b.setBarcode("barcode");
		b.setBrand(p);
		b.setMrp(10.1);
		b.setName("name");
		
		productDao.insert(b);
		List<ProductPojo> pojo=productDao.selectAll();
		assertEquals(b.getBarcode(), pojo.get(0).getBarcode());
		assertEquals(b.getBrand(),pojo.get(0).getBrand());
		assertEquals(b.getMrp(),pojo.get(0).getMrp());
		assertEquals(b.getName(),pojo.get(0).getName());
		
	}
	
	@Test
	public void testSelectId() {
		BrandPojo p=new BrandPojo();
		p.setBrand("brand");
		p.setCategory("category");
		
		brandDao.insert(p);
		
		ProductPojo b = new ProductPojo();
		b.setBarcode("barcode");
		b.setBrand(p);
		b.setMrp(10.1);
		b.setName("name");
		
		int id=productDao.insert(b).getId();
		ProductPojo pojo=productDao.select(id);
		assertEquals(b.getBarcode(), pojo.getBarcode());
		assertEquals(b.getBrand(),pojo.getBrand());
		assertEquals(b.getMrp(),pojo.getMrp());
		assertEquals(b.getName(),pojo.getName());
		
	}
	
	@Test
	public void testSelectBarcode() {
		BrandPojo p=new BrandPojo();
		p.setBrand("brad");
		p.setCategory("cat");
		
		p=brandDao.insert(p);
		
		ProductPojo b = new ProductPojo();
		b.setBarcode("barcode");
		b.setBrand(p);
		b.setMrp(10.1);
		b.setName("name");
		
		productDao.insert(b).getId();
		ProductPojo pojo=productDao.selectByBarcode("barcode");
		assertEquals(b.getBarcode(), pojo.getBarcode());
		assertEquals(b.getBrand(),pojo.getBrand());
		assertEquals(b.getMrp(),pojo.getMrp());
		assertEquals(b.getName(),pojo.getName());
		
	}
	
}
