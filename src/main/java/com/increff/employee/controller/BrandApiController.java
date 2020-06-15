package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BrandApiController {

	@Autowired
	private BrandService brandservice;
	@Autowired
	private ProductService productservice;

	@ApiOperation(value = "Adds a brand")
	@RequestMapping(path = "/api/brand", method = RequestMethod.POST)
	public void add(@RequestBody BrandForm form) throws ApiException {
		BrandPojo p = convert(form);
		brandservice.add(p);
	}

	@ApiOperation(value = "Deletes a brand")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		Set<ProductPojo> p = brandservice.get(id).getProduct();
		for (ProductPojo pojo : p) {
			productservice.delete(pojo.getId());
		}
		brandservice.delete(id);
	}

	@ApiOperation(value = "Gets a brand by id")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
	public BrandData get(@PathVariable int id) throws ApiException {
		BrandPojo p = brandservice.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets list of all brands")
	@RequestMapping(path = "/api/brand", method = RequestMethod.GET)
	public List<BrandData> getAll() {
		List<BrandPojo> list = brandservice.getAll();
		return convert(list);
	}

	
	@ApiOperation(value = "Updates a brand")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
		BrandPojo p = convert(f);
		brandservice.update(id, p);
	}

	private static BrandData convert(BrandPojo p) {
		BrandData d = new BrandData();
		d.setBrand(p.getBrand());
		d.setCategory(p.getCategory());
		d.setId(p.getId());
		
		Set<ProductPojo> pojoset = p.getProduct();
		Set<String> s = new HashSet<String>();
		Iterator<ProductPojo> value = pojoset.iterator();
		while (value.hasNext()) {
			ProductPojo test = (ProductPojo) value.next();
			s.add(test.getProduct());
		}
		d.setProduct(s);
		
		return d;
	}

	private static BrandPojo convert(BrandForm f) {
		BrandPojo p = new BrandPojo();
		p.setBrand(f.getBrand());
		p.setCategory(f.getCategory());
		return p;
	}
	
	public static List<BrandData> convert(List<BrandPojo>list)
	{
		List<BrandData> list2 = new ArrayList<BrandData>();
		for (BrandPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

}
