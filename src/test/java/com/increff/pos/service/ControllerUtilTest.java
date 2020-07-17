package com.increff.pos.service;

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
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.ConvertorUtil;

public class ControllerUtilTest extends AbstractUnitTest{
	


	@Test
	public void testConvertBrandPojo() {
		
		BrandPojo b= new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");
		b.setId(1);
		ProductPojo p1= new ProductPojo();
		p1.setBrand(b);
		p1.setName("product 1");
		
		ProductPojo p2= new ProductPojo();
		p2.setBrand(b);
		p2.setName("product 2");
		
		List<ProductPojo>set=new ArrayList<ProductPojo>();
		set.add(p1);
		set.add(p2);
		
		b.setProducts(set);
		
		BrandData data =ConvertorUtil.convert(b);
		List<String> s = new ArrayList<String>();
		s.add("product 1");
		s.add("product 2");
		
		assertEquals("brand", data.getBrand());
		assertEquals("category", data.getCategory());

		
	}
	
	@Test
	public void testConvertBrandForm() {
			BrandForm b = new BrandForm();
			b.setBrand("brand");
			b.setCategory("category");
			
			BrandPojo p= ConvertorUtil.convert(b);
			
			BrandPojo pojo=new BrandPojo();
			pojo.setBrand("brand");
			pojo.setCategory("category");
			assertEquals(pojo.getBrand(), p.getBrand());
			assertEquals(pojo.getCategory(), p.getCategory());
	}
	
	@Test
	public void testConvertInventoryPojo() {
			InventoryPojo p=new InventoryPojo();
			
			ProductPojo product= new ProductPojo();
			
			product.setId(1);
			p.setId(2);
			p.setProduct(product);
			p.setQuantity(10);
			
			InventoryData data= ConvertorUtil.convert(p);
			
			InventoryData test = new InventoryData();
			test.setId(2);
			test.setProduct(1);
			test.setQuantity(10);
			
			assertEquals(test.getId(), data.getId());
			assertEquals(test.getProduct(), data.getProduct());
			assertEquals(test.getQuantity(), data.getQuantity());
	
	}
	
	@Test
	public void testConvertInventoryForm() throws ApiException {
		InventoryForm f=new InventoryForm();
		f.setQuantity(10);
		
		ProductPojo p= new ProductPojo();
		InventoryPojo pojo=ConvertorUtil.convert(f, p);
		
		InventoryPojo test=new InventoryPojo();
		test.setProduct(p);
		test.setQuantity(10);
		
		assertEquals(test.getProduct(), pojo.getProduct());
		assertEquals(test.getQuantity(), pojo.getQuantity());
		
	}
	
	@Test
	public void testConvertOrderPojo() {
		OrderPojo b= new OrderPojo();
		ZonedDateTime datetime= ZonedDateTime.now();
		b.setOrderDate(datetime);
		b.setId(1);
		OrderItemPojo p1= new OrderItemPojo();
		p1.setId(1);
		OrderItemPojo p2= new OrderItemPojo();
		p2.setId(2);

		List<OrderItemPojo>set=new ArrayList<OrderItemPojo>();
		set.add(p1);
		set.add(p2);
		
		b.setOrderItems(set);
		
		OrderData data =ConvertorUtil.convert(b);
		List<Integer> s = new ArrayList<Integer>();
		s.add(1);
		s.add(2);
		
		assertEquals(datetime, data.getDatetime());
		
	}
	
	
	@Test
	public void testConvertProductPojo() {
		ProductPojo p= new ProductPojo();
		p.setBarcode("barcode");
		p.setMrp((double) 10);
		p.setName("product");
		p.setId(1);
		BrandPojo b= new BrandPojo();
		b.setId(1);
		
		p.setBrand(b);
		
		ProductData data= ConvertorUtil.convert(p);
		
		assertEquals(1, data.getBrandId());
		assertEquals("barcode", data.getBarcode());
		assertEquals("product", data.getProduct());

	}
	
	@Test
	public void testConvertProductForm() throws ApiException {
		ProductForm f= new ProductForm();
		f.setBarcode("barcode");
		f.setMrp((double) 10);
		f.setProduct("product");
		
		BrandPojo b= new BrandPojo();
		b.setId(1);
		
		ProductPojo pojo= ConvertorUtil.convert(f, b);
		
		assertEquals("barcode", pojo.getBarcode());
		assertEquals("product", pojo.getName());
		assertEquals(b, pojo.getBrand());
	}
	
	

}
