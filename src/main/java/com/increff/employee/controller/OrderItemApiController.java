package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.ControllerUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderItemApiController {

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ProductService productservice;

	@ApiOperation(value = "Adds an item")
	@RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
	public void add(@RequestBody OrderItemForm form) throws ApiException {

		ProductPojo pojo = productservice.get(form.getBarcode());
		OrderItemPojo p = ControllerUtil.convert(form, pojo);

		// reducing inventory quantity
		int quantity = ControllerUtil.getQuantity(pojo, p);
		quantity -= p.getQuantity();

		// updating inventory
		InventoryPojo inv = pojo.getQuantity();
		inv.setQuantity(quantity);
		inventoryService.update(inv.getId(), inv);

		// updating order item table
		orderItemService.add(p);
	}

	@ApiOperation(value = "Deletes an item")
	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {

		OrderItemPojo p = orderItemService.get(id);

		// updating inventory quantity
		InventoryPojo pojo = p.getProduct().getQuantity();
		int quantity = p.getQuantity() + pojo.getQuantity();
		pojo.setQuantity(quantity);
		inventoryService.update(pojo.getId(), pojo);

		// deleting order item
		orderItemService.delete(id);
	}

	@ApiOperation(value = "Gets an item by id")
	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.GET)
	public OrderItemData get(@PathVariable int id) throws ApiException {
		OrderItemPojo p = orderItemService.get(id);
		return ControllerUtil.convert(p);
	}

	@ApiOperation(value = "Gets list of all current items of this order")
	@RequestMapping(path = "/api/orderitem", method = RequestMethod.GET)
	public List<OrderItemData> getAll() {
		List<OrderItemPojo> list = orderItemService.getCurrentItems();
		return ControllerUtil.convertOrderItemPojo(list);
	}

	@ApiOperation(value = "Gets list of all items")
	@RequestMapping(path = "/api/orderitem/all", method = RequestMethod.GET)
	public List<OrderItemData> getAllItems() {
		List<OrderItemPojo> list = orderItemService.getAll();
		return ControllerUtil.convertOrderItemPojo(list);
	}

	@ApiOperation(value = "Updates a item")
	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody OrderItemForm form) throws ApiException {
		ProductPojo pojo = productservice.get(form.getBarcode());
		OrderItemPojo p = ControllerUtil.convert(form, pojo);
		int itemPreviousQuantity= orderItemService.get(id).getQuantity();
		
		//checking and changing inventory quantity
		int inventoryQuantity = ControllerUtil.getQuantity(pojo, p, itemPreviousQuantity);

		// updating inventory
		InventoryPojo inv = pojo.getQuantity();
		inv.setQuantity(inventoryQuantity);
		inventoryService.update(inv.getId(), inv);

		// updating order item table
		orderItemService.update(id, p);
	}

}
