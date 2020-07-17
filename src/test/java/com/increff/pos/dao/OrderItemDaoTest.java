package com.increff.pos.dao;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.model.SalesReport;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.OrderStatus;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderService;

public class OrderItemDaoTest extends AbstractUnitTest {

	private static final double DELTA = 1e-15;
	
	@Autowired
	private OrderItemDao orderItemDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private BrandDao brandDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderService orderService;

	@Test
	public void testAdd() {
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

		OrderPojo order = new OrderPojo();
		order.setOrderDate(ZonedDateTime.now());
		order.setStatus(OrderStatus.OPEN);
		orderDao.insert(order);

		OrderItemPojo item = new OrderItemPojo();
		item.setOrderpojo(order);
		item.setProduct(product);
		item.setSellingPrice(11.1);
		item.setQuantity(10);

		orderItemDao.insert(item);

	}

	@Test
	public void testDelete() {
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

		OrderPojo order = new OrderPojo();
		order.setOrderDate(ZonedDateTime.now());
		order.setStatus(OrderStatus.OPEN);
		orderDao.insert(order);

		OrderItemPojo item = new OrderItemPojo();
		item.setOrderpojo(order);
		item.setProduct(product);
		item.setSellingPrice(11.1);
		item.setQuantity(10);

		int id = orderItemDao.insert(item).getId();
		orderItemDao.delete(id);

		OrderItemPojo inv = orderItemDao.select(id);
		assertEquals(null, inv);

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

		OrderPojo order = new OrderPojo();
		order.setOrderDate(ZonedDateTime.now());
		order.setStatus(OrderStatus.OPEN);
		orderDao.insert(order);

		OrderItemPojo item = new OrderItemPojo();
		item.setOrderpojo(order);
		item.setProduct(product);
		item.setSellingPrice(11.1);
		item.setQuantity(10);

		int id = orderItemDao.insert(item).getId();
		OrderItemPojo actual = orderItemDao.select(id);

		assertEquals(item.getOrderpojo(), actual.getOrderpojo());
		assertEquals(item.getProduct(), actual.getProduct());
		assertEquals(item.getQuantity(), actual.getQuantity());
		assertEquals(item.getSellingPrice(), actual.getSellingPrice());

	}

	@Test
	public void testSelectByOrder() {
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

		OrderPojo order = new OrderPojo();
		order.setOrderDate(ZonedDateTime.now());
		order.setStatus(OrderStatus.OPEN);
		orderDao.insert(order);

		OrderItemPojo item = new OrderItemPojo();
		item.setOrderpojo(order);
		item.setProduct(product);
		item.setSellingPrice(11.1);
		item.setQuantity(10);
		orderItemDao.insert(item);
		List<OrderItemPojo> actual = orderItemDao.selectByOrder(order);

		assertEquals(item.getOrderpojo(), actual.get(0).getOrderpojo());
		assertEquals(item.getProduct(), actual.get(0).getProduct());
		assertEquals(item.getQuantity(), actual.get(0).getQuantity());
		assertEquals(item.getSellingPrice(), actual.get(0).getSellingPrice());

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

		OrderPojo order = new OrderPojo();
		order.setOrderDate(ZonedDateTime.now());
		order.setStatus(OrderStatus.OPEN);
		orderDao.insert(order);

		OrderItemPojo item = new OrderItemPojo();
		item.setOrderpojo(order);
		item.setProduct(product);
		item.setSellingPrice(11.1);
		item.setQuantity(10);

		orderItemDao.insert(item);
		List<OrderItemPojo> actual = orderItemDao.selectByProduct(product);

		assertEquals(item.getOrderpojo(), actual.get(0).getOrderpojo());
		assertEquals(item.getProduct(), actual.get(0).getProduct());
		assertEquals(item.getQuantity(), actual.get(0).getQuantity());
		assertEquals(item.getSellingPrice(), actual.get(0).getSellingPrice());

	}  

	@Test
	public void testSelectByParams() throws ApiException {
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

		OrderPojo order = new OrderPojo();
		order.setOrderDate(ZonedDateTime.now());
		order.setStatus(OrderStatus.OPEN);
		orderDao.insert(order);

		OrderItemPojo item = new OrderItemPojo();
		item.setOrderpojo(order);
		item.setProduct(product);
		item.setSellingPrice(11.1);
		item.setQuantity(10);

		orderItemDao.insert(item);

		List<String> brands = new ArrayList<String>();
		brands.add("brad");
		List<String> categories = new ArrayList<String>();
		categories.add("cat");
		ZonedDateTime d1 = ZonedDateTime.now();
		orderService.update(order.getId());
		ZonedDateTime d2 = ZonedDateTime.now();
		List<SalesReport> list = orderItemDao.selectByParams(brands, categories, d1, d2);

		assertEquals("cat", list.get(0).getCategory());
		assertEquals(111.0, list.get(0).getRevenue(),DELTA);

	}

	@Test
	public void testSelectByProductAndOrder() {
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

		OrderPojo order = new OrderPojo();
		order.setOrderDate(ZonedDateTime.now());
		order.setStatus(OrderStatus.OPEN);
		orderDao.insert(order);

		OrderItemPojo item = new OrderItemPojo();
		item.setOrderpojo(order);
		item.setProduct(product);
		item.setSellingPrice(11.1);
		item.setQuantity(10);

		orderItemDao.insert(item);
		OrderItemPojo actual = orderItemDao.selectByProductAndOrder(product, order);

		assertEquals(item.getOrderpojo(), actual.getOrderpojo());
		assertEquals(item.getProduct(), actual.getProduct());
		assertEquals(item.getQuantity(), actual.getQuantity());
		assertEquals(item.getSellingPrice(), actual.getSellingPrice());

	}

}
