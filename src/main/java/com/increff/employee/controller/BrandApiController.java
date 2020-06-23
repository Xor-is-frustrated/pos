package com.increff.employee.controller;

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
public class BrandApiController {

	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private OrderItemService orderItemService;

	@ApiOperation(value = "Adds a brand")
	@RequestMapping(path = "/api/brand", method = RequestMethod.POST)
	public void add(@RequestBody BrandForm form) throws ApiException {
		BrandPojo p = ControllerUtil.convert(form);
		brandService.add(p);
	}

	@ApiOperation(value = "Deletes a brand")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		Set<ProductPojo> p = brandService.get(id).getProduct();
		for (ProductPojo pojo : p) {
			InventoryPojo ip = pojo.getQuantity();
			//deleting the child entities, if exists
			Set<OrderItemPojo> item = pojo.getItem();
			if (ip != null) {
				inventoryService.delete(ip.getId());
			}
			if (item != null) {
				for (OrderItemPojo data : item) {
					orderItemService.delete(data.getId());
				}
			}
			productService.delete(pojo.getId());
		}
		brandService.delete(id);
	}

	@ApiOperation(value = "Gets a brand by id")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
	public BrandData get(@PathVariable int id) throws ApiException {
		BrandPojo p = brandService.get(id);
		return ControllerUtil.convert(p);
	}

	@ApiOperation(value = "Gets list of all brands")
	@RequestMapping(path = "/api/brand", method = RequestMethod.GET)
	public List<BrandData> getAll() {
		List<BrandPojo> list = brandService.getAll();
		return ControllerUtil.convertBrand(list);
	}

	@ApiOperation(value = "Updates a brand")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
		BrandPojo p = ControllerUtil.convert(f);
		brandService.update(id, p);
	}

	

}
