package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;

public class OrderItemDtoTest extends AbstractUnitTest {

	private static final double DELTA = 1e-15;

	@Autowired
	private OrderItemDto dto;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private InventoryService inventoryService;

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
		productService.add(p);

		OrderPojo order = orderService.add();

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(p);
		inv.setQuantity(100);

		int invId = inventoryService.add(inv).getId();

		OrderItemForm item = new OrderItemForm();
		item.setBarcode("barcode");
		item.setOrderId(order.getId());
		item.setQuantity(10);

		dto.add(item);

		inv = inventoryService.get(invId);

		assertEquals((Integer) 90, inv.getQuantity());

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

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(p);
		inv.setQuantity(100);

		int invId = inventoryService.add(inv).getId();

		OrderItemForm item = new OrderItemForm();
		item.setBarcode("barcode");
		item.setOrderId(order.getId());
		item.setQuantity(10);

		int itemId = dto.add(item).getId();

		OrderItemData actual = dto.get(itemId);

		assertEquals((Integer) order.getId(), (Integer) actual.getOrderId());
		assertEquals("product", actual.getProduct());
		assertEquals(10, actual.getQuantity());
//		assertEquals((double) 101, actual.getSellingPrice(), DELTA);

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

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(p);
		inv.setQuantity(100);

		int invId = inventoryService.add(inv).getId();

		OrderItemForm item = new OrderItemForm();
		item.setBarcode("barcode");
		item.setOrderId(order.getId());
		item.setQuantity(10);

		int itemId = dto.add(item).getId();

		List<OrderItemData> actual = dto.getByOrderid(order.getId());

		assertEquals((Integer) order.getId(), (Integer) actual.get(0).getOrderId());
		assertEquals("product", actual.get(0).getProduct());
		assertEquals(10, actual.get(0).getQuantity());
//		assertEquals((double) 101, actual.get(0).getSellingPrice(), DELTA);

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

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(p);
		inv.setQuantity(100);

		inventoryService.add(inv);

		OrderItemPojo item = new OrderItemPojo();
		item.setSellingPrice(100.1);
		item.setQuantity(1);
		item.setOrderpojo(order);
		item.setProduct(p);

		OrderItemPojo expected = orderItemService.add(item);

		OrderItemForm item1 = new OrderItemForm();

		item1.setQuantity(12);
		item1.setOrderId(order.getId());
		item1.setBarcode("barcode");

		dto.update(expected.getId(), item1);

		OrderItemPojo actual = orderItemService.get(expected.getId());

		assertEquals((Integer) item1.getOrderId(), actual.getOrderpojo().getId());
		assertEquals("product", actual.getProduct().getName());
		assertEquals((Integer) item1.getQuantity(), actual.getQuantity());
//		assertEquals((double) 121.2, (double) actual.getSellingPrice(), DELTA);
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

		p.setBrand(b);
		productService.add(p);

		OrderPojo order = orderService.add();

		InventoryPojo inv = new InventoryPojo();
		inv.setProduct(p);
		inv.setQuantity(100);

		int invId = inventoryService.add(inv).getId();

		OrderItemForm item = new OrderItemForm();
		item.setBarcode("barcode");
		item.setOrderId(order.getId());
		item.setQuantity(10);

		int itemId = dto.add(item).getId();

		inv = inventoryService.get(invId);

		assertEquals((Integer) 90, inv.getQuantity());

		dto.delete(itemId);
		inv = inventoryService.get(invId);

		assertEquals((Integer) 100, inv.getQuantity());

	}

}
