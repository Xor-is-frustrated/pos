package com.increff.employee.service;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;

public class TSVTest {

	@Test
	public void testBrandFile() {
		InputStream is = null; 
		is = TSVTest.class.getResourceAsStream("/com/increff/employee/brand.tsv");
		assertNotNull(is);
	}
	
	@Test
	public void testProductFile() {
		InputStream is = null; 
		is = TSVTest.class.getResourceAsStream("/com/increff/employee/product.tsv");
		assertNotNull(is);
	}
	
	@Test
	public void testInventoryFile() {
		InputStream is = null; 
		is = TSVTest.class.getResourceAsStream("/com/increff/employee/inventory.tsv");
		assertNotNull(is);
	}
 
}
