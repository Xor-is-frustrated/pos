package com.increff.pos.dto;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderInvoice;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.pdf.PDFFromFOP;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.OrderStatus;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.util.ConvertorUtil;
import com.increff.pos.util.HelperUtil;

@Service
public class OrderDto {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemService orderItemService;

	public OrderData add() throws ApiException {
		OrderPojo pojo = orderService.add();
		return ConvertorUtil.convert(pojo);
	}

	public OrderData closeOrder(int id) throws ApiException {
		OrderPojo order = orderService.closeAndUpdateTime(id);
		return ConvertorUtil.convert(order);
	}

	public void getPdf(int id, HttpServletResponse response) throws ApiException, JAXBException, IOException {

		// retrieving order from order id
		OrderPojo orderPojo = orderService.get(id);

		if (orderPojo.getStatus() == OrderStatus.OPEN) {
			throw new ApiException("Order is not purchased.");
		}

		// retrieving items of this order
		List<OrderItemPojo> list = orderPojo.getOrderItems();

		List<OrderItemData> orderItemList = ConvertorUtil.convertOrderItems(list);

		// total cost of all items for this order
		double sellingprice = HelperUtil.getSellingPrice(orderItemList);

		// creating invoice with all the items and total selling price
		OrderInvoice order = new OrderInvoice();
		order.setItems(orderItemList);
		order.setSellingprice(sellingprice);
		order.setId(id);
		order.setOrderDate(orderPojo.getOrderDate().toString().substring(0,10)+" "+orderPojo.getOrderDate().toString().substring(11,19));

		// converting java object to XML
		PDFFromFOP.javaToXML(order);

		// converting XML to PDF          
		PDFFromFOP.generatePDF(id);

		// writing back the PDF to HttpResponse
		PDFFromFOP.generateResponse(response);
	}

	@Transactional(rollbackFor = ApiException.class)   
	public void delete(int id) throws ApiException {
		OrderPojo order = orderService.get(id);
		List<OrderItemPojo> items = order.getOrderItems();
		for (OrderItemPojo pojo : items) {
			orderItemService.delete(pojo.getId());
		}
		orderService.delete(id);
		
	}

}
