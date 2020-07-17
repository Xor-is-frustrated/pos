package com.increff.pos.util;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;

public class ConverterUtilTest extends AbstractUnitTest {

	private static final double DELTA = 1e-15;

	@Test
	public void testConvertBrandPojoToBrandData() {

		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");
		b.setId(1);

		BrandData data = ConvertorUtil.convert(b);

		assertEquals("brand", data.getBrand());
		assertEquals("category", data.getCategory());

	}

	@Test
	public void testConvertBrandFormToBrandPojo() {
		BrandForm b = new BrandForm();
		b.setBrand("brand");
		b.setCategory("category");

		BrandPojo p = ConvertorUtil.convert(b);

		assertEquals("brand", p.getBrand());
		assertEquals("category", p.getCategory());
	}

	@Test
	public void testConvertListBrandPojoToBrandData() {
		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");
		b.setId(1);

		BrandPojo b1 = new BrandPojo();
		b1.setBrand("brand 1");
		b1.setCategory("category 1");
		b1.setId(2);

		List<BrandPojo> brandList = new ArrayList<BrandPojo>();
		brandList.add(b);
		brandList.add(b1);

		List<BrandData> list = ConvertorUtil.convertBrands(brandList);

		assertEquals("brand", list.get(0).getBrand());
		assertEquals("category", list.get(0).getCategory());
		assertEquals("brand 1", list.get(1).getBrand());
		assertEquals("category 1", list.get(1).getCategory());

	}

	@Test
	public void testConvertProductPojoToProductData() {

		ProductPojo p = new ProductPojo();
		p.setBarcode("barcode");
		p.setId(1);
		p.setMrp(10.1);
		p.setName("product");

		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");
		b.setId(1);

		p.setBrand(b);

		ProductData data = ConvertorUtil.convert(p);

		assertEquals("barcode", data.getBarcode());
		assertEquals("product", data.getProduct());
		assertEquals(1, data.getId());
		assertEquals(1, data.getBrandId());
//	 todo: assert for floating numbers
		assertEquals((double) 10.1, (double) data.getMrp(), DELTA);

	}

	@Test
	public void testConvertProductFormToProductPojo() throws ApiException {
		ProductForm f = new ProductForm();
		f.setBarcode("barcode");
		f.setMrp(10.1);
		f.setProduct("product");

		BrandPojo b = new BrandPojo();
		b.setId(1);

		ProductPojo pojo = ConvertorUtil.convert(f, b);

		assertEquals("barcode", pojo.getBarcode());
		assertEquals("product", pojo.getName());
		assertEquals(b, pojo.getBrand());
	}

	@Test
	public void testConvertListProductPojoToProductData() {
		ProductPojo p = new ProductPojo();
		p.setBarcode("barcode");
		p.setId(1);
		p.setMrp(10.1);
		p.setName("product");

		BrandPojo b = new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");
		b.setId(1);

		p.setBrand(b);

		ProductPojo p1 = new ProductPojo();
		p1.setBarcode("barcode1");
		p1.setId(2);
		p1.setMrp(10.2);
		p1.setName("product1");
		p1.setBrand(b);

		List<ProductPojo> list1 = new ArrayList<ProductPojo>();
		list1.add(p);
		list1.add(p1);

		List<ProductData> list = ConvertorUtil.convertProducts(list1);

		assertEquals("barcode", list.get(0).getBarcode());
		assertEquals("product", list.get(0).getProduct());
		assertEquals(1, list.get(0).getBrandId());
		assertEquals(1, list.get(0).getId());
		assertEquals((double) 10.1, list.get(0).getMrp(), DELTA);

		assertEquals("barcode1", list.get(1).getBarcode());
		assertEquals("product1", list.get(1).getProduct());
		assertEquals(1, list.get(1).getBrandId());
		assertEquals(2, list.get(1).getId());
		assertEquals((double) 10.2, list.get(1).getMrp(), DELTA);

	}

	@Test
	public void testConvertInventoryPojoToInventoryData() {
		InventoryPojo p = new InventoryPojo();

		ProductPojo product = new ProductPojo();

		product.setId(1);
		p.setId(2);
		p.setProduct(product);
		p.setQuantity(10);

		InventoryData data = ConvertorUtil.convert(p);

		InventoryData test = new InventoryData();
		test.setId(2);
		test.setProduct(1);
		test.setQuantity(10);

		assertEquals(test.getId(), data.getId());
		assertEquals(test.getProduct(), data.getProduct());
		assertEquals(test.getQuantity(), data.getQuantity());

	}

	@Test
	public void testConvertInventoryFormToInventoryPojo() throws ApiException {
		InventoryForm f = new InventoryForm();
		f.setQuantity(10);

		ProductPojo p = new ProductPojo();
		InventoryPojo pojo = ConvertorUtil.convert(f, p);

		InventoryPojo test = new InventoryPojo();
		test.setProduct(p);
		test.setQuantity(10);

		assertEquals(test.getProduct(), pojo.getProduct());
		assertEquals(test.getQuantity(), pojo.getQuantity());

	}

	@Test
	public void testConvertListInventoryPojoToInventoryData() {
		ProductPojo product = new ProductPojo();
		product.setId(1);

		InventoryPojo p = new InventoryPojo();
		p.setId(2);
		p.setProduct(product);
		p.setQuantity(10);

		InventoryPojo p1 = new InventoryPojo();
		p1.setId(3);
		p1.setProduct(product);
		p1.setQuantity(11);

		List<InventoryPojo> list = new ArrayList<InventoryPojo>();
		list.add(p);
		list.add(p1);

		List<InventoryData> data = ConvertorUtil.convertInventories(list);

		assertEquals(1, data.get(0).getProduct());
		assertEquals(2, data.get(0).getId());
		assertEquals(10, data.get(0).getQuantity());

		assertEquals(1, data.get(1).getProduct());
		assertEquals(3, data.get(1).getId());
		assertEquals(11, data.get(1).getQuantity());

	}

	@Test
	public void testConvertOrderPojoToOrderData() {
		OrderPojo b = new OrderPojo();
		ZonedDateTime datetime = ZonedDateTime.now();
		b.setOrderDate(datetime);
		b.setId(1);
		OrderItemPojo p1 = new OrderItemPojo();
		p1.setId(1);
		OrderItemPojo p2 = new OrderItemPojo();
		p2.setId(2);

		List<OrderItemPojo> set = new ArrayList<OrderItemPojo>();
		set.add(p1);
		set.add(p2);

		b.setOrderItems(set);

		OrderData data = ConvertorUtil.convert(b);
		List<Integer> s = new ArrayList<Integer>();
		s.add(1);
		s.add(2);

		assertEquals(datetime, data.getDatetime());
		
	}

	@Test
	public void testConvertListOrderPojoToOrderData() {
		OrderPojo b = new OrderPojo();
		ZonedDateTime datetime = ZonedDateTime.now();
		b.setOrderDate(datetime);
		b.setId(1);

		OrderItemPojo p1 = new OrderItemPojo();
		p1.setId(1);
		OrderItemPojo p2 = new OrderItemPojo();
		p2.setId(2);

		List<OrderItemPojo> set = new ArrayList<OrderItemPojo>();
		set.add(p1);
		set.add(p2);

		b.setOrderItems(set);

		OrderPojo b1 = new OrderPojo();
		ZonedDateTime datetime1 = ZonedDateTime.now();
		b1.setOrderDate(datetime1);
		b1.setId(2);

		OrderItemPojo p3 = new OrderItemPojo();
		p3.setId(3);
		OrderItemPojo p4 = new OrderItemPojo();
		p4.setId(4);

		List<OrderItemPojo> set1 = new ArrayList<OrderItemPojo>();
		set1.add(p3);
		set1.add(p4);

		b1.setOrderItems(set1);

		List<OrderPojo> order = new ArrayList<OrderPojo>();
		order.add(b);
		order.add(b1);

		List<OrderData> list = ConvertorUtil.convertOrders(order);

		List<Integer> s = new ArrayList<Integer>();
		s.add(1);
		s.add(2);

		List<Integer> s1 = new ArrayList<Integer>();
		s1.add(3);
		s1.add(4);

		assertEquals(datetime, list.get(0).getDatetime());
		
		assertEquals(1, list.get(0).getId());

		assertEquals(datetime1, list.get(1).getDatetime());
		
		assertEquals(2, list.get(1).getId());

	}

	@Test
	public void testConvertOrderItemFormToOrderItemPojo() {

		OrderItemForm form = new OrderItemForm();
		form.setBarcode("barcode");
		form.setOrderId(1);
		form.setQuantity(9);

		ProductPojo product = new ProductPojo();
		product.setId(1);
		product.setBarcode("barcode");
		product.setMrp(10.1);

		OrderPojo order = new OrderPojo();
		order.setId(1);

		OrderItemPojo pojo = ConvertorUtil.convert(form, product, order);

		assertEquals(order, pojo.getOrderpojo());
		assertEquals(product, pojo.getProduct());
		assertEquals((Integer) 9, pojo.getQuantity());

//		assertEquals((double) 90.9, (double) pojo.getSellingPrice(), DELTA);

	}

	@Test
	public void testConvertOrderItemPojoToOrderItemData() {
		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setId(1);
		item.setQuantity(10);

		OrderPojo order = new OrderPojo();
		order.setId(1);

		ProductPojo product = new ProductPojo();
		product.setId(1);
		product.setBarcode("barcode");
		product.setName("product");
		product.setMrp(10.1);

		item.setOrderpojo(order);
		item.setProduct(product);

		OrderItemData data = ConvertorUtil.convert(item);

		assertEquals("barcode", data.getBarcode());
		assertEquals(1, data.getId());
		assertEquals(1, data.getOrderId());
		assertEquals(10, data.getQuantity());
		assertEquals("product", data.getProduct());

		// check double
		assertEquals(100.1, data.getSellingPrice(), DELTA);
		

	}

	@Test
	public void testConvertListOrderItemPojoToOrderItemData() {
		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setId(1);
		item.setQuantity(10);

		OrderPojo order = new OrderPojo();
		order.setId(1);

		ProductPojo product = new ProductPojo();
		product.setId(1);
		product.setBarcode("barcode");
		product.setName("product");
		product.setMrp(10.1);

		item.setOrderpojo(order);
		item.setProduct(product);

		OrderItemPojo item1 = new OrderItemPojo();
		item1.setSellingPrice(100.2);
		item1.setId(2);
		item1.setQuantity(11);

		OrderPojo order1 = new OrderPojo();
		order1.setId(2);

		ProductPojo product1 = new ProductPojo();
		product1.setId(2);
		product1.setBarcode("barcode1");
		product1.setName("product1");
		product1.setMrp(10.2);

		item1.setOrderpojo(order1);
		item1.setProduct(product1);

		List<OrderItemPojo> list = new ArrayList<OrderItemPojo>();
		list.add(item);
		list.add(item1);

		List<OrderItemData> data = ConvertorUtil.convertOrderItems(list);
		assertEquals("barcode", data.get(0).getBarcode());
		assertEquals(1, data.get(0).getId());
		assertEquals(1, data.get(0).getOrderId());
		assertEquals(10, data.get(0).getQuantity());
		assertEquals("product", data.get(0).getProduct());

		assertEquals("barcode1", data.get(1).getBarcode());
		assertEquals(2, data.get(1).getId());
		assertEquals(2, data.get(1).getOrderId());
		assertEquals(11, data.get(1).getQuantity());
		assertEquals("product1", data.get(1).getProduct());

	}

}
