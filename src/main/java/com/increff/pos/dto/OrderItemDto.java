package com.increff.pos.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.ReportForm;
import com.increff.pos.model.SalesReport;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.OrderStatus;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertorUtil;

@Service
public class OrderItemDto {

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ProductService productservice;

	@Autowired
	private OrderService orderService;

	
	//1. Checks for valid barcode and order status.
	//	2. Checks the inventory, and reduces the inventory quantity.
	//	3. If the item is already present in the order, updates the quantity of the order item.
	@Transactional(rollbackFor = ApiException.class)
	public OrderItemData add(OrderItemForm form) throws ApiException {
		ProductPojo product = productservice.getByBarcode(form.getBarcode());

		OrderPojo order = orderService.get(form.getOrderId());
		if (order.getStatus() == OrderStatus.CLOSE) {
			throw new ApiException("Order is purchased. Cannot add items.");
		}
		

		// reducing inventory quantity
		InventoryPojo inventory = inventoryService.getByProductId(product.getId());
		if(inventory==null) {
			throw new ApiException("Inventory for this product does not exist.");
		}
		int inventoryquantity = inventory.getQuantity();
		int itemQuantity = form.getQuantity();   

		if (itemQuantity > inventoryquantity) {
			throw new ApiException(
					"Quantity exceeds inventory quantity. Please reduce the quantity to " + (inventoryquantity));
		}

		int updatedQuantity = inventoryquantity - itemQuantity;

		inventoryService.update(inventory.getId(), updatedQuantity);       

		OrderItemPojo item = orderItemService.getByProductAndOrder(product, order);
		if (item != null) {// item already exists in order. So Update the quantity.

			int quantity = form.getQuantity() + item.getQuantity();
			 
			double sellingPrice = item.getSellingPrice();
			return ConvertorUtil.convert(orderItemService.update(item.getId(), quantity, sellingPrice));

		} else {// item does not exist in the order. Add item
			OrderItemPojo orderItemPojo = ConvertorUtil.convert(form, product, order);
			return ConvertorUtil.convert(orderItemService.add(orderItemPojo));
		}
	}

	
//	1. Checks for order status.
//	2. Increases the inventory quantity.
//	3. Deletes the order item from the order.
	@Transactional(rollbackFor = ApiException.class)
	public void delete(int id) throws ApiException {

		OrderItemPojo p = orderItemService.get(id);
		if (p.getOrderpojo().getStatus() == OrderStatus.CLOSE) {
			throw new ApiException("Order is purchased. Cannot delete items.");
		}

		// updating inventory quantity
		InventoryPojo pojo = inventoryService.getByProductId(p.getProduct().getId());
		int quantity = p.getQuantity() + pojo.getQuantity();

		inventoryService.update(pojo.getId(), quantity);

		// deleting order item
		orderItemService.delete(id);

	}

	public OrderItemData get(int id) throws ApiException {
		OrderItemPojo p = orderItemService.get(id);
		return ConvertorUtil.convert(p);
	}

	public List<OrderItemData> getByOrderid(int orderId) throws ApiException {
		List<OrderItemPojo> list = orderItemService.getByOrderId(orderId);
		return ConvertorUtil.convertOrderItems(list);
	}

	//1. Checks for valid barcode and order status.
	//	2. Checks the inventory, and reduces the inventory quantity.
	//	3. If the item is already present in the order, updates the quantity of the order item.
	@Transactional(rollbackFor = ApiException.class)
	public void update(int id, OrderItemForm form) throws ApiException {

		ProductPojo productPojo = productservice.getByBarcode(form.getBarcode());
		OrderPojo order = orderService.get(form.getOrderId());
		if (order.getStatus() == OrderStatus.CLOSE) {
			throw new ApiException("Order is purchased. Cannot update items.");
		}


		int itemPreviousQuantity = orderItemService.get(id).getQuantity();

		// checking and changing inventory quantity
		int inventoryQuantity = inventoryService.getByProductId(productPojo.getId()).getQuantity();
		int itemUpdatedQuantity = form.getQuantity();
		inventoryQuantity += itemPreviousQuantity;
		inventoryQuantity -= itemUpdatedQuantity; 

		if (inventoryQuantity < 0) {
			throw new ApiException("Quantity exceeds inventory quantity. Please reduce the quantity to "
					+ (inventoryQuantity + itemUpdatedQuantity));
		}

		// updating inventory
		InventoryPojo inv = inventoryService.getByProductId(productPojo.getId());
		inventoryService.update(inv.getId(), inventoryQuantity);

		// updating order item table
		orderItemService.update(id, form.getQuantity(), form.getSellingPrice());
	}

	public List<SalesReport> getByParams(ReportForm form) {

		final ZoneId id = ZoneId.systemDefault();
		
		ZonedDateTime startDate = (ZonedDateTime.ofInstant(form.getStartDate().toInstant(), id));
		ZonedDateTime endDate = (ZonedDateTime.ofInstant(form.getEndDate().toInstant(), id));

		List<String> categories = form.getCategories();
		List<String> brands = form.getBrands();
		
		List<SalesReport> list = orderItemService.getByParams(brands, categories, startDate, endDate);

//		return ConvertorUtil.convertSalesReport(list); 
		return list;

	}

}
