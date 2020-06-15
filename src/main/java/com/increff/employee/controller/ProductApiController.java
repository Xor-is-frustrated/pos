package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ProductApiController {

	@Autowired
	private ProductService service;

	@Autowired
	private BrandService brandservice;

	@Autowired
	private InventoryService inventoryservice;

	@ApiOperation(value = "Adds a product")
	@RequestMapping(path = "/api/product", method = RequestMethod.POST)
	public void add(@RequestBody ProductForm form) throws ApiException {

		BrandPojo b = brandservice.get(form.getBrand());
		ProductPojo p = convert(form, b);
		service.add(p);
		int id = b.getId();
		b.getProduct().add(p);
		brandservice.update(id, b);
	}

	@ApiOperation(value = "Deletes a product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		InventoryPojo ip = service.get(id).getQuantity();
		if (ip != null) {
			inventoryservice.delete(ip.getId());
		}
		service.delete(id);
	}

	@ApiOperation(value = "Gets a product by id")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
	public ProductData get(@PathVariable int id) throws ApiException {
		ProductPojo p = service.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets list of all products")
	@RequestMapping(path = "/api/product", method = RequestMethod.GET)
	public List<ProductData> getAll() {
		List<ProductPojo> list = service.getAll();
		List<ProductData> list2 = new ArrayList<ProductData>();
		for (ProductPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	@ApiOperation(value = "Updates a product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody ProductForm form) throws ApiException {

		BrandPojo b = brandservice.get(form.getBrand());

		if (b == null) {
			throw new ApiException("Brand with given brand_name does not exist, brand: " + form.getBrand());
		}
		ProductPojo p = convert(form, b);
		ProductPojo p1 = service.get(id);
		
		p.setQuantity(p1.getQuantity());
		service.update(id, p);


	}

	private static ProductData convert(ProductPojo p) {
		ProductData d = new ProductData();
		d.setBrand(p.getBrand().getId());
		d.setMrp(p.getMrp());
		d.setId(p.getId());
		d.setBarcode(p.getBarcode());
		d.setProduct(p.getProduct());
		InventoryPojo inv = p.getQuantity();
		int val = 0;
		if (inv != null) {
			val = inv.getQuantity();
		}
		d.setQuantity(val);
		return d;
	}

	private static ProductPojo convert(ProductForm f, BrandPojo b) throws ApiException {

		ProductPojo p = new ProductPojo();
		p.setProduct(f.getProduct());
		p.setBrand(b);
		p.setMrp(f.getMrp());
		p.setBarcode(f.getBarcode());

		return p;
	}

}
