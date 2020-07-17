package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;

public class ProductDtoTest extends AbstractUnitTest {

	private static final double DELTA = 1e-15;

	@Autowired
	private ProductDto productDto;

	@Autowired
	private ProductService service;

	@Autowired
	private BrandService brandService;

	@Test
	public void testAdd() throws ApiException {

		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		int id = brandService.add(b).getId();

		ProductForm form = new ProductForm();
		form.setBarcode("barcode");
		form.setBrandId(id);
		form.setMrp(10.1);
		form.setProduct("product");

		productDto.add(form);

	}

	@Test
	public void testGet() throws ApiException {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		int id = brandService.add(b).getId();

		ProductForm form = new ProductForm();
		form.setBarcode("barcode");
		form.setBrandId(id);
		form.setMrp(10.1);
		form.setProduct("product");

		ProductData expected = productDto.add(form);
		ProductData actual = productDto.get(expected.getId());

		assertEquals("barcode", actual.getBarcode());
		assertEquals("product", actual.getProduct());
		assertEquals((Integer) id, (Integer) actual.getBrandId());
		assertEquals((double) 10.1, (double) actual.getMrp(), DELTA);

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

		BrandPojo brandPojo = brandService.add(b);
		int id = brandPojo.getId();
		b.setId(id);
		p.setBrand(b);
		service.add(p);

		ProductPojo p1 = new ProductPojo();
		p1.setBarcode("barcode1");
		p1.setMrp(10.2);
		p1.setName("product1");

		BrandPojo b1 = new BrandPojo();
		b1.setBrand("brand1");
		b1.setCategory("category");

		BrandPojo brandPojo1 = brandService.add(b1);
		int id1 = brandPojo1.getId();
		b1.setId(id1);
		p1.setBrand(b1);
		service.add(p1);

		List<ProductData> list = productDto.getAll();

		assertEquals("barcode", list.get(0).getBarcode());
		assertEquals("product", list.get(0).getProduct());
		assertEquals(id, list.get(0).getBrandId());
		assertEquals((double) 10.1, (double) list.get(0).getMrp(), DELTA);

		assertEquals("barcode1", list.get(1).getBarcode());
		assertEquals("product1", list.get(1).getProduct());
		assertEquals(id1, list.get(1).getBrandId());
		assertEquals((double) 10.2, (double) list.get(1).getMrp(), DELTA);

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
		int id = brandPojo.getId();
		b.setId(id);
		p.setBrand(b);

		id = service.add(p).getId();

		ProductPojo pojo = service.get(id);
		ProductForm updatablePojo = new ProductForm();
		updatablePojo.setBarcode("barcode1");
		updatablePojo.setMrp(10.2);
		updatablePojo.setProduct("product1");
		updatablePojo.setBrandId(brandPojo.getId());

		productDto.update(id, updatablePojo);

		pojo = service.get(id);

		assertEquals("barcode1", pojo.getBarcode());
		assertEquals("product1", pojo.getName());
		assertEquals(b, pojo.getBrand());
		assertEquals((double) 10.2, (double) pojo.getMrp(), DELTA);

	}

}
