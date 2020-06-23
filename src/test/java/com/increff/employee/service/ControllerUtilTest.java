package com.increff.employee.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.ControllerUtil;

public class ControllerUtilTest extends AbstractUnitTest{
	


	@Test
	public void testConvertBrandPojo() {
		
		BrandPojo b= new BrandPojo();
		b.setBrand("brand");
		b.setCategory("category");
		
		ProductPojo p1= new ProductPojo();
		p1.setBrand(b);
		p1.setProduct("product 1");
		
		ProductPojo p2= new ProductPojo();
		p2.setBrand(b);
		p2.setProduct("product 2");
		
		Set<ProductPojo>set=new HashSet<ProductPojo>();
		set.add(p1);
		set.add(p2);
		
		b.setProduct(set);
		
		BrandData data =ControllerUtil.convert(b);
		Set<String> s = new HashSet<String>();
		s.add("product 1");
		s.add("product 2");
		
		assertEquals("brand", data.getBrand());
		assertEquals("category", data.getCategory());
		assertEquals(s, data.getProduct());
		
	}
	
	@Test
	public void testConvertBrandForm() {
			BrandForm b = new BrandForm();
			b.setBrand("brand");
			b.setCategory("category");
			
			BrandPojo p= ControllerUtil.convert(b);
			
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
			
			p.setProduct(product);
			p.setQuantity(10);
			
			InventoryData data= ControllerUtil.convert(p, 2);
			
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
		InventoryPojo pojo=ControllerUtil.convert(f, p);
		
		InventoryPojo test=new InventoryPojo();
		test.setProduct(p);
		test.setQuantity(10);
		
		assertEquals(test.getProduct(), pojo.getProduct());
		assertEquals(test.getQuantity(), pojo.getQuantity());
		
	}
	
	@Test
	public void testConvertOrderPojo() {
		OrderPojo b= new OrderPojo();
		LocalDateTime datetime= LocalDateTime.now();
		b.setDatetime(datetime);

		OrderItemPojo p1= new OrderItemPojo();
		p1.setId(1);
		OrderItemPojo p2= new OrderItemPojo();
		p2.setId(2);

		Set<OrderItemPojo>set=new HashSet<OrderItemPojo>();
		set.add(p1);
		set.add(p2);
		
		b.setOrderitem(set);
		
		OrderData data =ControllerUtil.convert(b);
		Set<Integer> s = new HashSet<Integer>();
		s.add(1);
		s.add(2);
		
		assertEquals(datetime, data.getDatetime());
		assertEquals(s, data.getItemid());
	}
	
	
	@Test
	public void testConvertProductPojo() {
		ProductPojo p= new ProductPojo();
		p.setBarcode("barcode");
		p.setMrp(10);
		p.setProduct("product");
		
		BrandPojo b= new BrandPojo();
		b.setId(1);
		
		p.setBrand(b);
		
		ProductData data= ControllerUtil.convert(p);
		
		assertEquals(1, data.getBrand());
		assertEquals(0, data.getQuantity());
		assertEquals("barcode", data.getBarcode());
		assertEquals("product", data.getProduct());

	}
	
	@Test
	public void testConvertProductForm() throws ApiException {
		ProductForm f= new ProductForm();
		f.setBarcode("barcode");
		f.setMrp(10);
		f.setProduct("product");
		
		BrandPojo b= new BrandPojo();
		b.setId(1);
		
		ProductPojo pojo= ControllerUtil.convert(f, b);
		
		assertEquals("barcode", pojo.getBarcode());
		assertEquals("product", pojo.getProduct());
		assertEquals(b, pojo.getBrand());
	}
	
	

}
