package com.increff.pos.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;

public class InventoryServiceTest extends AbstractUnitTest {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	@Test
	public void testAdd() throws ApiException {
		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setMrp(10.1);
		product.setName("product");

		BrandPojo brand = new BrandPojo();
		brand.setBrand("brand");
		brand.setCategory("category");

		brandService.add(brand);

		product.setBrand(brand);

		productService.add(product);

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(product);
		inv.setQuantity(10);

		inventoryService.add(inv);

	}

	@Test
	public void testGet() throws ApiException {
		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setMrp(10.1);
		product.setName("product");

		BrandPojo brand = new BrandPojo();
		brand.setBrand("brand");
		brand.setCategory("category");

		brandService.add(brand);

		product.setBrand(brand);

		productService.add(product);

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(product);
		inv.setQuantity(10);

		inventoryService.add(inv);
		InventoryPojo actual = inventoryService.get(inv.getId());

		assertEquals(product, actual.getProduct());
		assertEquals((Integer) 10, actual.getQuantity());

	}

	@Test
	public void testGetByProduct() throws ApiException {
		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setMrp(10.1);
		product.setName("product");

		BrandPojo brand = new BrandPojo();
		brand.setBrand("brand");
		brand.setCategory("category");

		brandService.add(brand);

		product.setBrand(brand);

		productService.add(product);

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(product);
		inv.setQuantity(10);

		inventoryService.add(inv);

		InventoryPojo actual = inventoryService.getByProductId(product.getId());

		assertEquals(product, actual.getProduct());
		assertEquals((Integer) 10, actual.getQuantity());

	}

	@Test
	public void testGetAll() throws ApiException {
		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setMrp(10.1);
		product.setName("product");

		BrandPojo brand = new BrandPojo();
		brand.setBrand("brand");
		brand.setCategory("category");

		brandService.add(brand);

		product.setBrand(brand);

		productService.add(product);

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(product);
		inv.setQuantity(10);

		inventoryService.add(inv);

		ProductPojo product1 = new ProductPojo();
		product1.setBarcode("barcode1");
		product1.setMrp(10.2);
		product1.setName("product1");

		BrandPojo brand1 = new BrandPojo();
		brand1.setBrand("brand1");
		brand1.setCategory("category1");

		brandService.add(brand1);

		product1.setBrand(brand1);
		productService.add(product1);

		InventoryPojo inv1 = new InventoryPojo();
		inv1.setProduct(product1);
		inv1.setQuantity(11);

		inventoryService.add(inv1);

		List<InventoryPojo> list = inventoryService.getAll();

		assertEquals(product, list.get(0).getProduct());
		assertEquals((Integer) 10, list.get(0).getQuantity());
		assertEquals((Integer) 11, list.get(1).getQuantity());
		assertEquals(product1, list.get(1).getProduct());

	}

	@Test
	public void testUpdate() throws ApiException {
		ProductPojo product = new ProductPojo();
		product.setBarcode("barcode");
		product.setMrp(10.1);
		product.setName("product");

		BrandPojo brand = new BrandPojo();
		brand.setBrand("brand");
		brand.setCategory("category");

		brandService.add(brand);

		product.setBrand(brand);

		productService.add(product);

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(product);
		inv.setQuantity(10);

		inventoryService.add(inv);

		inventoryService.update(inv.getId(), 13);

		InventoryPojo actual = inventoryService.get(inv.getId());

		assertEquals((Integer) 13, actual.getQuantity());

	}

}
