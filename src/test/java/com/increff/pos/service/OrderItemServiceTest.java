package com.increff.pos.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;

public class OrderItemServiceTest extends AbstractUnitTest {

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private OrderService orderService;

	@Test
	public void testAdd() throws ApiException {

		ProductPojo p = new ProductPojo();
		p.setBarcode("barcode");
		p.setMrp(10.1);
		p.setName("product");

		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");

		 brandService.add(b);

		
		p.setBrand(b);
		productService.add(p);

		OrderPojo order = orderService.add();

		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setQuantity(1);
		item.setOrderpojo(order);
		item.setProduct(p);

		orderItemService.add(item);
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
		productService.add(p);

		OrderPojo order = orderService.add();

		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setQuantity(1);
		item.setOrderpojo(order);
		item.setProduct(p);

		OrderItemPojo expected = orderItemService.add(item);
		OrderItemPojo actual = orderItemService.get(expected.getId());

		assertEquals(expected.getOrderpojo(), actual.getOrderpojo());
		assertEquals(expected.getProduct(), actual.getProduct());
		assertEquals(expected.getQuantity(), actual.getQuantity());
		assertEquals(expected.getSellingPrice(), actual.getSellingPrice());

	}

	@Test
	public void testGetByOrderId() throws ApiException {
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
		productService.add(p);

		OrderPojo order = orderService.add();

		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setQuantity(1);
		item.setOrderpojo(order);
		item.setProduct(p);

		OrderItemPojo expected = orderItemService.add(item);

		List<OrderItemPojo> actual = orderItemService.getByOrderId(order.getId());

		assertEquals(expected.getOrderpojo(), actual.get(0).getOrderpojo());
		assertEquals(expected.getProduct(), actual.get(0).getProduct());
		assertEquals(expected.getQuantity(), actual.get(0).getQuantity());
		assertEquals(expected.getSellingPrice(), actual.get(0).getSellingPrice());

	}

	@Test
	public void testGetByProductId() throws ApiException {
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
		ProductPojo productPojo = productService.add(p);

		OrderPojo order = orderService.add();

		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setQuantity(1);
		item.setOrderpojo(order);
		item.setProduct(p);

		OrderItemPojo expected = orderItemService.add(item);
		List<OrderItemPojo> actual = orderItemService.getByProductId(productPojo.getId());

		assertEquals(expected.getOrderpojo(), actual.get(0).getOrderpojo());
		assertEquals(expected.getProduct(), actual.get(0).getProduct());
		assertEquals(expected.getQuantity(), actual.get(0).getQuantity());
		assertEquals(expected.getSellingPrice(), actual.get(0).getSellingPrice());

	}

	@Test
	public void testGetByProductAndOrder() throws ApiException {

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
		ProductPojo productPojo = productService.add(p);

		OrderPojo order = orderService.add();

		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setQuantity(1);
		item.setOrderpojo(order);
		item.setProduct(p);

		OrderItemPojo expected = orderItemService.add(item);
		OrderItemPojo actual = orderItemService.getByProductAndOrder(productPojo, order);

		assertEquals(expected.getOrderpojo(), actual.getOrderpojo());
		assertEquals(expected.getProduct(), actual.getProduct());
		assertEquals(expected.getQuantity(), actual.getQuantity());
		assertEquals(expected.getSellingPrice(), actual.getSellingPrice());
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

		int id = brandService.add(b).getId();

		
		p.setBrand(b);
		ProductPojo productPojo = productService.add(p);

		OrderPojo order = orderService.add();

		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setQuantity(1);
		item.setOrderpojo(order);
		item.setProduct(p);

		OrderItemPojo expected = orderItemService.add(item);
		OrderItemPojo item1 = new OrderItemPojo();
		item1.setSellingPrice(100.3);
		item1.setQuantity(12);
		item1.setOrderpojo(order);
		item1.setProduct(p);

		orderItemService.update(expected.getId(), item1.getQuantity(), item1.getSellingPrice());

		OrderItemPojo actual = orderItemService.get(expected.getId());

		assertEquals(item1.getOrderpojo(), actual.getOrderpojo());
		assertEquals(item1.getProduct(), actual.getProduct());
		assertEquals(item1.getQuantity(), actual.getQuantity());
		assertEquals(item1.getSellingPrice(), actual.getSellingPrice());

	}

	@Test
	public void testDelete() throws ApiException {
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
		productService.add(p);

		OrderPojo order = orderService.add();

		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setQuantity(1);
		item.setOrderpojo(order);
		item.setProduct(p);

		OrderItemPojo expected = orderItemService.add(item);

		orderItemService.delete(expected.getId());
		//get by id

		
	}

}
