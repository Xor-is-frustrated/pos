package com.increff.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/api/brand")
public class BrandController {

	@Autowired
	private BrandDto dto;

	@ApiOperation(value = "Adds a brand")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public void add(@RequestBody BrandForm form) throws ApiException {
		dto.addForm(form);
	}
  
	@ApiOperation(value = "Gets a brand by id")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public BrandData get(@PathVariable int id) throws ApiException {
		return dto.getIt(id);
	}

	@ApiOperation(value = "Gets list of all brands")
	@RequestMapping(method = RequestMethod.GET)
	public List<BrandData> getAll() {
		return dto.getItAllNow();

	}

	@ApiOperation(value = "Updates a brand")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException {
		dto.updateIt(id, form);
	}

	@ApiOperation(value = "Gets distinct list of categories")
	@RequestMapping(path = "/distinctcategories", method = RequestMethod.GET)
	public List<String> getAllCategories() {
		return dto.getDistinctCategories();

	}

	@ApiOperation(value = "Gets distinct list of brands")
	@RequestMapping(path = "/distinctbrands", method = RequestMethod.GET)
	public List<String> getAllBrands() {
		return dto.getDistinctBrands();

	}
	
	
}
