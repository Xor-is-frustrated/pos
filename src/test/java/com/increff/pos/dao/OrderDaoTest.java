package com.increff.pos.dao;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.OrderStatus;
import com.increff.pos.service.AbstractUnitTest;

public class OrderDaoTest extends AbstractUnitTest {

	@Autowired
	private OrderDao dao;

	@Test
	public void testInsert() {
		OrderPojo b = new OrderPojo();
		b.setOrderDate(ZonedDateTime.now());
		b.setStatus(OrderStatus.OPEN);
		dao.insert(b);

	}

	@Test
	public void testSelectAll() {
		OrderPojo b = new OrderPojo();
		b.setOrderDate(ZonedDateTime.now());
		b.setStatus(OrderStatus.OPEN);
		dao.insert(b);
		List<OrderPojo> pojo = dao.selectAll();
		assertEquals(b.getOrderDate(), pojo.get(0).getOrderDate());
	}

	@Test
	public void testSelectId() {
		OrderPojo b = new OrderPojo();
		b.setOrderDate(ZonedDateTime.now());
		b.setStatus(OrderStatus.OPEN);
		int id = dao.insert(b).getId();
		OrderPojo pojo = dao.select(id);
		assertEquals(b.getOrderDate(), pojo.getOrderDate());

	}

}
