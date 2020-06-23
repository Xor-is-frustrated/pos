package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.ControllerUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ProductApiController {

	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private OrderItemService orderItemService;

	@ApiOperation(value = "Adds a product")
	@RequestMapping(path = "/api/product", method = RequestMethod.POST)
	public void add(@RequestBody ProductForm form) throws ApiException {

		BrandPojo b = brandService.get(form.getBrand());
		ProductPojo p = ControllerUtil.convert(form, b);
		productService.add(p);
	}

	@ApiOperation(value = "Deletes a product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		ProductPojo p = productService.get(id);
		InventoryPojo ip = p.getQuantity();
		
		//deleting the child entities, if exists
		Set<OrderItemPojo> item = p.getItem();
		if (ip != null) {
			inventoryService.delete(ip.getId());
		}
		if (item != null) {
			for (OrderItemPojo pojo : item) {
				orderItemService.delete(pojo.getId());
			}
		}
		
		//deleting the product
		productService.delete(id);
	}

	@ApiOperation(value = "Gets a product by id")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
	public ProductData get(@PathVariable int id) throws ApiException {
		ProductPojo p = productService.get(id);
		return ControllerUtil.convert(p);
	}

	@ApiOperation(value = "Gets list of all products")
	@RequestMapping(path = "/api/product", method = RequestMethod.GET)
	public List<ProductData> getAll() {
		List<ProductPojo> list = productService.getAll();
		List<ProductData> list2 = new ArrayList<ProductData>();
		for (ProductPojo p : list) {
			list2.add(ControllerUtil.convert(p));
		}
		return list2;
	}

	@ApiOperation(value = "Updates a product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody ProductForm form) throws ApiException {

		BrandPojo b = brandService.get(form.getBrand());
		if (b == null) {
			throw new ApiException("Brand with given brand_name does not exist, brand: " + form.getBrand());
		}
		
		ProductPojo pojo=productService.get(form.getBarcode());
		
		if(pojo!=null) {
			if(id!=pojo.getId()) {
				throw new ApiException("The given barcode already exists for another product: " + form.getBrand());
			}
		}
		
		ProductPojo p = ControllerUtil.convert(form, b);
		ProductPojo p1 = productService.get(id);
		p.setQuantity(p1.getQuantity());
		productService.update(id, p);

	}

}
