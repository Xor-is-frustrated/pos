package com.increff.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController 
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
	private InventoryDto dto;

	@ApiOperation(value = "Adds an inventory item")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody InventoryForm form) throws ApiException {
		dto.add(form);

	}

	@ApiOperation(value = "Gets an inventory item by id")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public InventoryData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}

	@ApiOperation(value = "Gets list of all inventory")
	@RequestMapping(method = RequestMethod.GET)
	public List<InventoryData> getAll() {
		return dto.getAll();
	} 
	
	@ApiOperation(value = "Gets report of  inventory")
	@RequestMapping(path = "/report", method = RequestMethod.GET)
	public List<InventoryData> getInventoryReport() { 
		return dto.getInventoryReport();
	}

	@ApiOperation(value = "Updates an inventory")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody InventoryForm form) throws ApiException {
		dto.update(id, form);
	}

}
