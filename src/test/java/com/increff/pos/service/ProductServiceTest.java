package com.increff.pos.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;

public class ProductServiceTest extends AbstractUnitTest {

	private static final double DELTA = 1e-15;

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

		int id = productService.add(product).getId();

		ProductPojo pojo = productService.get(id);

		assertEquals("barcode", pojo.getBarcode());
		assertEquals("product", pojo.getName());
		assertEquals(brand, pojo.getBrand());
		assertEquals(10.1, pojo.getMrp().doubleValue(), DELTA);

	}

	@Test
	public void testGetByBarcode() throws ApiException {
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
		ProductPojo pojo = productService.getByBarcode("barcode");

		assertEquals("barcode", pojo.getBarcode());
		assertEquals("product", pojo.getName());
		assertEquals(brand, pojo.getBrand());
		assertEquals((double) 10.1, (double) pojo.getMrp(), DELTA);
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

		 productService.add(product).getId();

		ProductPojo product1 = new ProductPojo();
		product1.setBarcode("barcode1");
		product1.setMrp(10.2);
		product1.setName("product1");

		BrandPojo brand1 = new BrandPojo();
		brand1.setBrand("brand1");
		brand1.setCategory("category");

		 brandService.add(brand1);
		product1.setBrand(brand1);
		productService.add(product1);

		List<ProductPojo> list = productService.getAll();

		assertEquals("barcode", list.get(0).getBarcode());
		assertEquals("product", list.get(0).getName());
		assertEquals(brand, list.get(0).getBrand());
		assertEquals((double) 10.1, (double) list.get(0).getMrp(), DELTA);

		assertEquals("barcode1", list.get(1).getBarcode());
		assertEquals("product1", list.get(1).getName());
		assertEquals(brand1, list.get(1).getBrand());
		assertEquals((double) 10.2, (double) list.get(1).getMrp(), DELTA);

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

		int id = productService.add(product).getId();

		ProductPojo pojo = productService.get(id);
		ProductPojo updatablePojo = new ProductPojo();
		updatablePojo.setBarcode("barcode1");
		updatablePojo.setMrp(10.2);
		updatablePojo.setName("product1");
		updatablePojo.setBrand(brand);
		updatablePojo.setId(id);

		productService.update(id, updatablePojo);

		pojo = productService.get(id);

		assertEquals("barcode1", pojo.getBarcode());
		assertEquals("product1", pojo.getName());
		assertEquals(brand, pojo.getBrand());
		assertEquals((double) 10.2, (double) pojo.getMrp(), DELTA);

	}

	@Test
	public void testNormalize() {
		ProductPojo p = new ProductPojo();
		p.setName(" Product ");
		productService.normalizeProduct(p);
		assertEquals("product", p.getName());
	}

}
