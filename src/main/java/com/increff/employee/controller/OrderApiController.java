package com.increff.employee.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.OrderData;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderApiController {

	@Autowired
	private OrderService orderservice;

	@Autowired
	private OrderItemService orderitemservice;

	@ApiOperation(value = "Adds an order")
	@RequestMapping(path = "/api/order", method = RequestMethod.POST)
	public void add() throws ApiException {
		OrderPojo p = new OrderPojo();
		p.setDatetime(LocalDateTime.now());
		orderservice.add(p);
		p = orderservice.getsize();
		List<OrderItemPojo> list = orderitemservice.getnull();
		Set<OrderItemPojo> hSet = new HashSet<OrderItemPojo>();
		for (OrderItemPojo x : list)
			hSet.add(x);

		for (OrderItemPojo pojo : list) {
			OrderItemPojo p1 = pojo;
			p1.setOrderpojo(p);
			orderitemservice.update(pojo.getId(), p1);
		}

	}

	@ApiOperation(value = "Deletes a order")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		Set<OrderItemPojo> orderitems = orderservice.get(id).getOrderitem();
		for (OrderItemPojo pojo : orderitems) {
			orderitemservice.delete(pojo.getId());
		}
		orderservice.delete(id);
	}

	@ApiOperation(value = "Gets an order by id")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
	public OrderData get(@PathVariable int id) throws ApiException {
		OrderPojo p = orderservice.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets list of all orders")
	@RequestMapping(path = "/api/order", method = RequestMethod.GET)
	public List<OrderData> getAll() {
		List<OrderPojo> list = orderservice.getAll();
		return convert(list);
	}

	@ApiOperation(value = "Updates an order")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id) throws ApiException {
		OrderPojo p = new OrderPojo();
		p.setDatetime(LocalDateTime.now());
		orderservice.update(id, p);
	}

	private static OrderData convert(OrderPojo p) {
		OrderData d = new OrderData();
		d.setDatetime(p.getDatetime());
		d.setId(p.getId());
		Set<OrderItemPojo> pojoset = p.getOrderitem();
		Set<Integer> s = new HashSet<Integer>();
		Iterator<OrderItemPojo> value = pojoset.iterator();
		while (value.hasNext()) {
			OrderItemPojo test = (OrderItemPojo) value.next();
			s.add(test.getId());
		}
		d.setItemid(s);
		return d;
	}

	private static List<OrderData> convert(List<OrderPojo> list) {
		List<OrderData> list2 = new ArrayList<OrderData>();
		for (OrderPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}
}
