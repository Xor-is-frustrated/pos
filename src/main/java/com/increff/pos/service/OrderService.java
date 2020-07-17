package com.increff.pos.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.OrderStatus;

@Service
public class OrderService extends AbstractService {

	@Autowired
	private OrderDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public OrderPojo add() throws ApiException {
		OrderPojo p = new OrderPojo();
		p.setOrderDate(ZonedDateTime.now());
		p.setStatus(OrderStatus.OPEN);
		return dao.insert(p);
	}

	@Transactional(readOnly = true)
	public OrderPojo get(int id) throws ApiException {
		return checkOrderId(id);
	}

	@Transactional(readOnly = true)
	public OrderPojo checkOrderId(int id) throws ApiException {
		OrderPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Order ID does not exist");
		}
		return p;
	}

	@Transactional(readOnly = true)
	public List<OrderPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(int id) throws ApiException {
		OrderPojo ex = checkOrderId(id);
		ex.setOrderDate(ZonedDateTime.now());
	}

	@Transactional(rollbackFor = ApiException.class)
	public OrderPojo closeAndUpdateTime(int id) throws ApiException {
		OrderPojo ex = checkOrderId(id);
		ex.setStatus(OrderStatus.CLOSE);
		ex.setOrderDate(ZonedDateTime.now());
		return ex;
	}

	public void delete(int id) {
		dao.delete(id);
		
	}

}
