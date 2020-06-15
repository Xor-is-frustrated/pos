package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderItemApiController {

	@Autowired
	private OrderItemService service;

	@Autowired
	private ProductService productservice;

	@ApiOperation(value = "Adds a item")
	@RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
	public void add(@RequestBody OrderItemForm form) throws ApiException {
		double price = productservice.get(form.getProductId()).getMrp() * form.getQuantity();
		OrderItemPojo p = convert(form, price);
		service.add(p);
	}

	@ApiOperation(value = "Deletes a item")
	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		service.delete(id);
	}

	@ApiOperation(value = "Gets a item by id")
	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.GET)
	public OrderItemData get(@PathVariable int id) throws ApiException {
		OrderItemPojo p = service.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets list of all items")
	@RequestMapping(path = "/api/orderitem", method = RequestMethod.GET)
	public List<OrderItemData> getAll() {
		List<OrderItemPojo> list = service.getAll();
		return convert(list);
	}

	@ApiOperation(value = "Updates a item")
	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody OrderItemForm form) throws ApiException {
		double price = productservice.get(form.getProductId()).getMrp()* form.getQuantity();
		OrderItemPojo p = convert(form, price);
		service.update(id, p);
	}

	private static OrderItemData convert(OrderItemPojo p) {
		OrderItemData d = new OrderItemData();
		d.setSellingprice(p.getSellingPrice());
		d.setId(p.getId());
		d.setProductId(p.getProductId());
		d.setQuantity(p.getQuantity());
		return d;
	}

	private static OrderItemPojo convert(OrderItemForm f, double price) {
		OrderItemPojo p = new OrderItemPojo();
		p.setQuantity(f.getQuantity());
		p.setProductId(f.getProductId());
		p.setSellingPrice(price);
		return p;
	}
	private static List<OrderItemData> convert(List<OrderItemPojo>list)
	{
		List<OrderItemData> list2 = new ArrayList<OrderItemData>();
		for (OrderItemPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}
}
