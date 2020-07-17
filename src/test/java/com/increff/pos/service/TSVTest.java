package com.increff.pos.service;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;

public class TSVTest {

	@Test
	public void testBrandFile() {
		InputStream is = null; 
		is = TSVTest.class.getResourceAsStream("/com/increff/pos/brand.tsv");
		assertNotNull(is);
	}
	
	@Test
	public void testProductFile() {
		InputStream is = null; 
		is = TSVTest.class.getResourceAsStream("/com/increff/pos/product.tsv");
		assertNotNull(is);
	}
	
	@Test
	public void testInventoryFile() {
		InputStream is = null; 
		is = TSVTest.class.getResourceAsStream("/com/increff/pos/inventory.tsv");
		assertNotNull(is);
	}
 
}
