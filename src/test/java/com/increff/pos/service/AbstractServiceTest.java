package com.increff.pos.service;

import org.junit.Test;

public class AbstractServiceTest extends AbstractUnitTest {

	@Test(expected = ApiException.class)
	public void testCheckNotNull() throws ApiException {
		AbstractService.checkNotNull(null, "message");
	}

	@Test(expected = ApiException.class)
	public void testCheckNull() throws ApiException {
		Integer k = 10;
		AbstractService.checkNull(k, "message");
	}

	@Test(expected = ApiException.class)
	public void testCheckPositive() throws ApiException {
		AbstractService.checkPositive(-10, "message");
	}

	@Test(expected = ApiException.class)
	public void testCheckPositiveDoubles() throws ApiException {
		AbstractService.checkPositive(-10.1, "message");
	}

}
