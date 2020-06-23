package com.increff.employee.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;

public class InventoryServiceTest extends AbstractUnitTest{

	@Autowired
	private InventoryService service;
	
	@Test
	public void testAdd() throws ApiException {
		ProductPojo p = new ProductPojo();
		p.setProduct("product");
		p.setBarcode("a1b1");
		p.setMrp(10.1);
		InventoryPojo inv= new InventoryPojo();
		inv.setProduct(p);
		inv.setQuantity(10);
		service.add(inv);
	}
	

	
	@Test
	public void testDelete() throws ApiException {
		service.delete(1);
	}
	
	@Test
	public void testGetAll() throws ApiException {
		service.getAll();
	}
	


}
