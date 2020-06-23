package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.ControllerUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InventoryApiController {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ProductService productService;

	@ApiOperation(value = "Adds an inventory item")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
	public void add(@RequestBody InventoryForm form) throws ApiException {
		ProductPojo p = productService.get(form.getProduct());
		
		//inventory for this product already exists 
		if (p.getQuantity() != null) {
			throw new ApiException("the inventory for this product already exists. Please update the inventory");
		}
		
		//creating an inventory for this product
		InventoryPojo inv = ControllerUtil.convert(form, p);
		inventoryService.add(inv);

	}

	@ApiOperation(value = "Deletes an inventory item")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		inventoryService.delete(id);
	}

	@ApiOperation(value = "Gets a inventory item by id")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
	public InventoryData get(@PathVariable int id) throws ApiException {
		InventoryPojo p = inventoryService.get(id);
		return ControllerUtil.convert(p, id);
	}

	@ApiOperation(value = "Gets list of all inventory data")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
	public List<InventoryData> getAll() {
		List<InventoryPojo> list = inventoryService.getAll();
		return ControllerUtil.convertInventory(list);
	}

	@ApiOperation(value = "Updates an inventory")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody InventoryForm form) throws ApiException {
		ProductPojo p = productService.get(form.getProduct());
		InventoryPojo inv = ControllerUtil.convert(form, p);
		inventoryService.update(id, inv);
	}

	

}
