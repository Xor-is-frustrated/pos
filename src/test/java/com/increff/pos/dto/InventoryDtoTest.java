package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;

public class InventoryDtoTest extends AbstractUnitTest {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private InventoryDto dto;

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private BrandDao brandDao;

	@Test
	public void testAdd() throws ApiException {
		ProductPojo p = new ProductPojo();
		p.setBarcode("barcode");
		p.setMrp(10.1);
		p.setName("product");

		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		int id = brandService.add(b).getId();

		b.setId(id);
		p.setBrand(b);
		id = productService.add(p).getId();

		InventoryForm form = new InventoryForm();
		form.setProduct(id);
		form.setQuantity(100);

		dto.add(form);
	}

	@Test
	public void testGet() throws ApiException {

		ProductPojo p = new ProductPojo();
		p.setBarcode("barcode");
		p.setMrp(10.1);
		p.setName("product");

		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		int id = brandService.add(b).getId();

		b.setId(id);
		p.setBrand(b);
		ProductPojo pojo = productService.add(p);

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(p);
		inv.setQuantity(10);

		id = inventoryService.add(inv).getId();

		InventoryData actual = dto.get(id);

		assertEquals((Integer) pojo.getId(), (Integer) actual.getProduct());
		assertEquals((Integer) 10, (Integer) actual.getQuantity());

	}

	@Test
	public void testGetAll() throws ApiException {
		ProductPojo p = new ProductPojo();
		p.setBarcode("barcode");
		p.setMrp(10.1);
		p.setName("product");

		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		int id = brandService.add(b).getId();

		b.setId(id);
		p.setBrand(b);
		ProductPojo pojo1 = productService.add(p);

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(p);
		inv.setQuantity(10);

		inventoryService.add(inv);

		ProductPojo p1 = new ProductPojo();
		p1.setBarcode("barcode1");
		p1.setMrp(10.2);
		p1.setName("product1");

		BrandPojo b1 = new BrandPojo();
		b1.setBrand("brand1");
		b1.setCategory("category1");

		int id1 = brandService.add(b1).getId();

		b1.setId(id1);
		p1.setBrand(b1);
		ProductPojo pojo = productService.add(p1);

		InventoryPojo inv1 = new InventoryPojo();
		inv1.setProduct(p1);
		inv1.setQuantity(11);

		inventoryService.add(inv1);

		List<InventoryData> list = dto.getAll();

		assertEquals((Integer) pojo1.getId(), (Integer) list.get(0).getProduct());
		assertEquals((Integer) 10, (Integer) list.get(0).getQuantity());
		assertEquals((Integer) 11, (Integer) list.get(1).getQuantity());
		assertEquals((Integer) pojo.getId(), (Integer) list.get(1).getProduct());
	}

	@Test
	public void testUpdate() throws ApiException {
		ProductPojo p = new ProductPojo();
		p.setBarcode("barcode");
		p.setMrp(10.1);
		p.setName("product");

		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		BrandPojo brandPojo = brandService.add(b);

		p.setBrand(brandPojo);
		ProductPojo productPojo = productService.add(p);

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(productPojo);
		inv.setQuantity(10);

		int invId = inventoryService.add(inv).getId();

		InventoryForm inv1 = new InventoryForm();
		inv1.setProduct(productPojo.getId());
		inv1.setQuantity(13);

		dto.update(invId, inv1);

		InventoryPojo actual = inventoryService.get(invId);

		assertEquals((Integer) 13, actual.getQuantity());

	}

	@Test
	public void testGetReport() {
		BrandPojo p = new BrandPojo();
		p.setBrand("brad");
		p.setCategory("cat");

		p = brandDao.insert(p);

		ProductPojo b = new ProductPojo();
		b.setBarcode("barcode");
		b.setBrand(p);
		b.setMrp((double) 10);
		b.setName("name");

		ProductPojo pojo = productDao.insert(b);

		ProductPojo b1 = new ProductPojo();
		b1.setBarcode("barcode1");
		b1.setBrand(p);
		b1.setMrp((double) 11);
		b1.setName("name");

		ProductPojo pojo1 = productDao.insert(b1);

		InventoryPojo inv = new InventoryPojo();
		inv.setQuantity(10);
		inv.setProduct(pojo);
		inventoryDao.insert(inv);

		InventoryPojo inv1 = new InventoryPojo();
		inv1.setQuantity(11);
		inv1.setProduct(pojo1);
		inventoryDao.insert(inv1);
		List<InventoryData> list = dto.getInventoryReport();

		assertEquals(21, list.get(0).getQuantity());
	}

}
