package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;

	@Transactional
	public void add(OrderItemPojo p) throws ApiException {
		dao.insert(p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderItemPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public OrderItemPojo getCheck(int id) throws ApiException {
		OrderItemPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("item with given ID does not exist, id: " + id);
		}
		return p;
	}

	@Transactional
	public List<OrderItemPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional
	public List<OrderItemPojo> getnull() {
		return dao.selectnull();
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, OrderItemPojo p) throws ApiException {
		OrderItemPojo ex = getCheck(id);
		ex.setSellingPrice(p.getSellingPrice());
		ex.setProductId(p.getProductId());
		ex.setQuantity(p.getQuantity());
		ex.setOrderpojo(p.getOrderpojo());
		dao.update(ex);
	}

	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		dao.delete(id);
	}

}
