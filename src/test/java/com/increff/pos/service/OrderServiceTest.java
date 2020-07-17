package com.increff.pos.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.OrderPojo;

public class OrderServiceTest extends AbstractUnitTest {

	@Autowired
	private OrderService service;

	@Test
	public void testAdd() throws ApiException {
		service.add();
	}

	@Test
	public void testGet() throws ApiException {
		OrderPojo expected = service.add();
		OrderPojo actual = service.get(expected.getId());

		assertEquals(expected, actual);
	}

	@Test
	public void testGetAll() throws ApiException {
		OrderPojo expected = service.add();
		OrderPojo expected1 = service.add();
		List<OrderPojo> list = service.getAll();

		assertEquals(expected, list.get(0));
		assertEquals(expected1, list.get(1));

	}

}
