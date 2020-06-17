package com.increff.employee.controller;

import java.util.ArrayList;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InventoryApiController {

	@Autowired
	private InventoryService inventoryservice;

	@Autowired
	private ProductService productservice;

	@ApiOperation(value = "Adds an inventory item")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
	public void add(@RequestBody InventoryForm form) throws ApiException {
		ProductPojo p = productservice.get(form.getProduct());
		if(p.getQuantity()!=null)
		{
			throw new ApiException("the inventory for this product already exists. Please update the inventory");
		}
		InventoryPojo inv = convert(form, p);
		inventoryservice.add(inv);

	}

	@ApiOperation(value = "Deletes an inventory item")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		inventoryservice.delete(id);
	}

	@ApiOperation(value = "Gets a inventory item by id")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
	public InventoryData get(@PathVariable int id) throws ApiException {
		InventoryPojo p = inventoryservice.get(id);
		return convert(p, id);
	}

	@ApiOperation(value = "Gets list of all inventory data")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
	public List<InventoryData> getAll() {
		List<InventoryPojo> list = inventoryservice.getAll();
		return convert(list);
	}
	
	@ApiOperation(value = "Updates an inventory")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody InventoryForm form) throws ApiException {
		ProductPojo p = productservice.get(form.getProduct());
		if(p.getQuantity()!=null)
		{
			throw new ApiException("the inventory for this product already exists. Please update the inventory");
		}
		InventoryPojo inv = convert(form, p);
		inventoryservice.update(id, inv);
	}
	
	private static InventoryData convert(InventoryPojo p, int id) {
		InventoryData d = new InventoryData();
		d.setId(id);
		d.setProduct(p.getProduct().getId());
		d.setQuantity(p.getQuantity());
		return d;
	}

	private static InventoryPojo convert(InventoryForm f, ProductPojo b) throws ApiException {
		InventoryPojo p = new InventoryPojo();
		p.setProduct(b);
		p.setQuantity(f.getQuantity());
		return p;
	}
	
	public static List<InventoryData> convert(List<InventoryPojo> list)
	{
		List<InventoryData> list2 = new ArrayList<InventoryData>();
		for (InventoryPojo p : list) {
			int id = p.getId();
			list2.add(convert(p, id));
		}
		return list2;
	}
	
}
