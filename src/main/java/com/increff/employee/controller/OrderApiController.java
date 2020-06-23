package com.increff.employee.controller;

//import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderInvoice;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.pdf.PDFFromFOP;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.util.ControllerUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderApiController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;

	@ApiOperation(value = "Gets an order")
	@RequestMapping(path = "/api/order/test", method = RequestMethod.GET)
	private void performTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ApiException {

		// creating new order
		OrderPojo p = new OrderPojo();
		p.setDatetime(LocalDateTime.now());
		orderService.add(p);

		// retrieving the newly created order
		p = orderService.getCurrentOrder();
		int id = p.getId();

		// items ordered for the current order
		List<OrderItemPojo> list = orderItemService.getCurrentItems();
		Set<OrderItemPojo> hSet = new HashSet<OrderItemPojo>();
		for (OrderItemPojo x : list)
			hSet.add(x);

		// updating the items list with its order id
		for (OrderItemPojo pojo : list) {
			OrderItemPojo p1 = pojo;
			p1.setOrderpojo(p);
			orderItemService.update(pojo.getId(), p1);
		}

		List<OrderItemData> list2 = ControllerUtil.convertOrderItem(list);
		// total cost of items for this order
		double sellingprice = getSellingPrice(list2);

		// creating invoice with all the items and total selling price
		OrderInvoice order = new OrderInvoice();
		order.setItems(list2);
		order.setSellingprice(sellingprice);

		// converting java object to XML
		PDFFromFOP.javaToXML(order);

		// converting XML to PDF
		PDFFromFOP.generatePDF(id);

		// writing back the PDF to HttpResponse
		PDFFromFOP.generateResponse(response);

	}

	@ApiOperation(value = "Deletes a order")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		Set<OrderItemPojo> orderitems = orderService.get(id).getOrderitem();
		for (OrderItemPojo pojo : orderitems) {
			orderItemService.delete(pojo.getId());
		}
		orderService.delete(id);
	}

	@ApiOperation(value = "Gets an order by id")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
	public OrderData get(@PathVariable int id) throws ApiException {
		OrderPojo p = orderService.get(id);
		return ControllerUtil.convert(p);
	}

	@ApiOperation(value = "Gets list of all orders")
	@RequestMapping(path = "/api/order/all", method = RequestMethod.GET)
	public List<OrderData> getAll() {
		List<OrderPojo> list = orderService.getAll();
		return ControllerUtil.convert(list);
	}

	@ApiOperation(value = "Updates an order")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id) throws ApiException {
		OrderPojo p = new OrderPojo();
		p.setDatetime(LocalDateTime.now());
		orderService.update(id, p);
	}

	public static double getSellingPrice(List<OrderItemData> list) {
		double cost = 0;
		for (OrderItemData data : list) {
			cost += data.getSellingprice();
		}
		cost = Math.round(cost * 100.0) / 100.0;
		return cost;
	}

}
