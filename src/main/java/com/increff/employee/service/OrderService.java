package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;

	@Transactional
	public void add(OrderPojo p) throws ApiException {
		dao.insert(p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderPojo getCurrentOrder() throws ApiException {
		OrderPojo p = dao.selectCurrentOrder();
		if (p == null) {
			throw new ApiException("new order does not exist");
		}
		return p;
	}

	@Transactional
	public OrderPojo getCheck(int id) throws ApiException {
		OrderPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("order with given ID does not exist, id: " + id);
		}
		return p;
	}

	@Transactional
	public List<OrderPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, OrderPojo p) throws ApiException {
		OrderPojo ex = getCheck(id);
		ex.setDatetime(p.getDatetime());
		ex.setOrderitem(p.getOrderitem());
		dao.update(ex);
	}

	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		dao.delete(id);
	}

}
