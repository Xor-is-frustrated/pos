package com.increff.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.ReportForm;
import com.increff.pos.model.SalesReport;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/api/orderitem")
public class OrderItemController {

	@Autowired
	private OrderItemDto dto;

	@ApiOperation(value = "Adds an item")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody OrderItemForm form) throws ApiException {
		dto.add(form);
	}

	@ApiOperation(value = "Deletes an item")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		dto.delete(id);
	}

	@ApiOperation(value = "Gets list of an item ")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public OrderItemData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}

	@ApiOperation(value = "Gets list of items by orderId")
	@RequestMapping(path = "/order/{id}", method = RequestMethod.GET)
	public List<OrderItemData> getByOrderId(@PathVariable int id) throws ApiException {
		return dto.getByOrderid(id);
	}

	@ApiOperation(value = "Updates a item")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody OrderItemForm form) throws ApiException {
		dto.update(id, form);
	}

	@ApiOperation(value = "gets report of sales")
	@RequestMapping(path = "/report", method = RequestMethod.POST)
	public List<SalesReport> update(@RequestBody ReportForm form) throws ApiException {
		return dto.getByParams(form);
	}

}
