package com.increff.pos.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;

public class InventoryDaoTest extends AbstractUnitTest {

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private BrandDao brandDao;

	@Test
	public void testInsert() {
		BrandPojo brand = new BrandPojo();
		brand.setBrand("brad");
		brand.setCategory("cat");

		brandDao.insert(brand);

		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setBrand(brand);
		product.setMrp((double) 10);
		product.setName("name");

		productDao.insert(product);

		InventoryPojo inv = new InventoryPojo();
		inv.setQuantity(10);
		inv.setProduct(product);
		inv = inventoryDao.insert(inv);

		assertEquals((Integer) 10, inv.getQuantity());
		assertEquals(product, inv.getProduct());
	}

	@Test
	public void testSelectByProduct() {
		BrandPojo brand = new BrandPojo();
		brand.setBrand("brad");
		brand.setCategory("cat");

		brandDao.insert(brand);

		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setBrand(brand);
		product.setMrp((double) 10);
		product.setName("name");

		productDao.insert(product);

		InventoryPojo inv = new InventoryPojo();
		inv.setQuantity(10);
		inv.setProduct(product);
		inventoryDao.insert(inv);

		inv = inventoryDao.selectByProduct(product);

		assertEquals((Integer) 10, inv.getQuantity());
		assertEquals(product, inv.getProduct());

	}

	@Test
	public void testSelectAll() {
		BrandPojo brand = new BrandPojo();
		brand.setBrand("brad");
		brand.setCategory("cat");

		brandDao.insert(brand);

		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setBrand(brand);
		product.setMrp((double) 10);
		product.setName("name");

		productDao.insert(product);

		ProductPojo product1 = new ProductPojo();
		product1.setBarcode("barcode1");
		product1.setBrand(brand);
		product1.setMrp((double) 11);
		product1.setName("name");

		productDao.insert(product1);

		InventoryPojo inv = new InventoryPojo();
		inv.setQuantity(10);
		inv.setProduct(product);
		inventoryDao.insert(inv);

		InventoryPojo inv1 = new InventoryPojo();
		inv1.setQuantity(11);
		inv1.setProduct(product1);
		inventoryDao.insert(inv1);
		List<InventoryPojo> list = inventoryDao.selectAll();

		assertEquals((Integer) 10, list.get(0).getQuantity());

	}

	@Test
	public void testSelectParams() {
		BrandPojo brand = new BrandPojo();
		brand.setBrand("brad");
		brand.setCategory("cat");

		brandDao.insert(brand);

		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setBrand(brand);
		product.setMrp((double) 10);
		product.setName("name");

		productDao.insert(product);

		ProductPojo product1 = new ProductPojo();
		product1.setBarcode("barcode1");
		product1.setBrand(brand);
		product1.setMrp((double) 11);
		product1.setName("name");

		productDao.insert(product1);

		InventoryPojo inv = new InventoryPojo();
		inv.setQuantity(10);
		inv.setProduct(product);
		inventoryDao.insert(inv);

		InventoryPojo inv1 = new InventoryPojo();
		inv1.setQuantity(11);
		inv1.setProduct(product1);
		inventoryDao.insert(inv1);
		List<Object[]> list = inventoryDao.selectSum();

		Long value = (Long) list.get(0)[2];
		assertEquals(21, value.intValue());

	}

	@Test
	public void testSelectId() {
		BrandPojo brand = new BrandPojo();
		brand.setBrand("brad");
		brand.setCategory("cat");

		brandDao.insert(brand);

		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setBrand(brand);
		product.setMrp((double) 10);
		product.setName("name");

		productDao.insert(product);
		InventoryPojo inv = new InventoryPojo();
		inv.setQuantity(10);
		inv.setProduct(product);
		int id = inventoryDao.insert(inv).getId();

		inv = inventoryDao.select(id);

		assertEquals((Integer) 10, inv.getQuantity());
		assertEquals(product, inv.getProduct());

	}

}
