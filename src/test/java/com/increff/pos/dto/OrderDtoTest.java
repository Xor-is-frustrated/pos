package com.increff.pos.dto;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;

public class OrderDtoTest extends AbstractUnitTest {

	@Autowired
	private OrderDto dto;

	@Test
	public void testAdd() throws ApiException {
		dto.add();
	}

}
