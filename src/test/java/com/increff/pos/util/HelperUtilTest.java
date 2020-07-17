package com.increff.pos.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.increff.pos.model.OrderItemData;
import com.increff.pos.service.AbstractUnitTest;

public class HelperUtilTest extends AbstractUnitTest {

	private static final double DELTA = 1e-15;

	//get product revenue
	// get local date
	@Test
	public void testGetSellingPrice() {
		OrderItemData data= new OrderItemData();
		data.setSellingPrice(100.1);
		
		OrderItemData data1= new OrderItemData();
		data1.setSellingPrice(111.1);
		
		List<OrderItemData>list= new ArrayList<OrderItemData>();
		list.add(data);
		list.add(data1);
		
		double price = HelperUtil.getSellingPrice(list);
//		assertEquals((double)211.2, (double)price,DELTA);
		
		
	}
	
}
