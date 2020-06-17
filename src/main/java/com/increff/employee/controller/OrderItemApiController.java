package com.increff.employee.controller;

import java.util.ArrayList;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderItemApiController {

	@Autowired
	private OrderItemService service;


	@Autowired
	private InventoryService inventoryservice;
	
	@Autowired
	private ProductService productservice;

	@ApiOperation(value = "Adds a item")
	@RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
	public void add(@RequestBody OrderItemForm form) throws ApiException {
		
		ProductPojo pojo=productservice.get(form.getBarcode());
		OrderItemPojo p = convert(form, pojo);
		int quantity=0;
		if(pojo.getQuantity()==null)
		{
			throw new ApiException("quantity exceeds inventory quantity, please reduce the quantity to"+quantity);
		}
		quantity=pojo.getQuantity().getQuantity();
		if( quantity <p.getQuantity())
		{
			throw new ApiException("quantity exceeds inventory quantity, please reduce the quantity"+quantity);
		}
		quantity-=p.getQuantity();
		InventoryPojo inv = pojo.getQuantity();
		inv.setQuantity(quantity);
		inventoryservice.update(inv.getId(), inv);
		service.add(p);
	}
 
	@ApiOperation(value = "Deletes a item")
	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		service.delete(id);
	}

	@ApiOperation(value = "Gets a item by id")
	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.GET)
	public OrderItemData get(@PathVariable int id) throws ApiException {
		OrderItemPojo p = service.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets list of all items")
	@RequestMapping(path = "/api/orderitem", method = RequestMethod.GET)
	public List<OrderItemData> getAll() {
		List<OrderItemPojo> list = service.getnull();
		return convert(list);
	}
 
	@ApiOperation(value = "Updates a item")
	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody OrderItemForm form) throws ApiException {
		ProductPojo pojo=productservice.get(form.getBarcode());
		OrderItemPojo p = convert(form, pojo);
		int quantity= inventoryservice.get(id).getQuantity();
		quantity-=p.getQuantity();
		int quantity1=0;
		if(pojo.getQuantity()==null)
		{
			throw new ApiException("quantity exceeds inventory quantity, please reduce the quantity to"+quantity1);
		}
		quantity1=pojo.getQuantity().getQuantity();
		if( quantity <p.getQuantity())
		{
			throw new ApiException("quantity exceeds inventory quantity, please reduce the quantity"+quantity1);
		}
		quantity1-=quantity;
		InventoryPojo inv = pojo.getQuantity();
		inv.setQuantity(quantity1);
		inventoryservice.update(inv.getId(), inv);
		service.update(id, p);
	}
	
	private static OrderItemData convert(OrderItemPojo p) {
		OrderItemData d = new OrderItemData();
		d.setBarcode(p.getProduct().getBarcode());
		d.setSellingprice(p.getSellingPrice());
		d.setId(p.getId());
		d.setProduct(p.getProduct().getProduct());
		d.setQuantity(p.getQuantity());
		d.setMrp(p.getProduct().getMrp());
		return d;
	}
	
	private static OrderItemPojo convert(OrderItemForm f, ProductPojo p) {
		OrderItemPojo item = new OrderItemPojo();
		double price=p.getMrp()*f.getQuantity();
		
		item.setQuantity(f.getQuantity());
		item.setProduct(p);		
		item.setSellingPrice(price);
		return item;
	}
	private static List<OrderItemData> convert(List<OrderItemPojo>list)
	{
		List<OrderItemData> list2 = new ArrayList<OrderItemData>();
		for (OrderItemPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}
}
