package com.increff.pos.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.SalesReport;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.OrderStatus;
import com.increff.pos.pojo.ProductPojo;

@Service
public class OrderItemService extends AbstractService {

	@Autowired
	private OrderItemDao dao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private OrderDao orderDao;

	@Transactional(rollbackFor = ApiException.class)
	public OrderItemPojo add(OrderItemPojo p) throws ApiException {

		OrderPojo orderPojo = orderDao.select(p.getOrderpojo().getId());
		checkNotNull(orderPojo, "Order ID does not exist");

		if (orderPojo.getStatus() == OrderStatus.CLOSE) {
			throw new ApiException("Order is purchased. Cannot add items");
		}

		ProductPojo productPojo = productDao.select(p.getProduct().getId());
		checkNotNull(productPojo, "Product ID does not exist");

		checkPositive(p.getSellingPrice(), "Selling Price cannot be less than zero");
		checkPositive(p.getQuantity(), "Quantity cannot be less than zero");

		OrderItemPojo item = dao.selectByProductAndOrder(productPojo, orderPojo);
		if (item != null) {
			return update(item.getId(), p.getQuantity(), p.getSellingPrice());
		}
		return dao.insert(p);

	}

	@Transactional(readOnly = true)
	public OrderItemPojo get(int id) throws ApiException {
		OrderItemPojo p = dao.select(id);
		checkNotNull(p, "OrderItem ID does not exist");

		return p;
	}

	@Transactional(readOnly = true)
	public OrderItemPojo getByProductAndOrder(ProductPojo product, OrderPojo orderpojo) throws ApiException {
		return dao.selectByProductAndOrder(product, orderpojo);

	}

	@Transactional(readOnly = true)
	public List<OrderItemPojo> getByOrderId(int orderId) throws ApiException {

		OrderPojo pojo = orderDao.select(orderId);
		checkNotNull(pojo, "Order ID does not exist");
		return dao.selectByOrder(pojo);

	}

	@Transactional(readOnly = true)
	public List<OrderItemPojo> getByProductId(int productId) throws ApiException {
		ProductPojo pojo = productDao.select(productId);
		checkNotNull(pojo, "Product ID does not exist");

		return dao.selectByProduct(pojo);
	}

	@Transactional(rollbackFor = ApiException.class)
	public OrderItemPojo update(int id, int quantity, double sellingPrice) throws ApiException {
		checkPositive(sellingPrice, "Selling Price cannot be less than zero");
		checkPositive(quantity, "Quantity cannot be less than zero");

		OrderItemPojo existing = dao.select(id);
		checkNotNull(existing, "Order Item ID does not exist");

		existing.setSellingPrice(sellingPrice);
		existing.setQuantity(quantity);

		return existing;

	}

	@Transactional(rollbackFor = ApiException.class)
	public void delete(int id) throws ApiException {

		OrderItemPojo p = dao.select(id);
		checkNotNull(p, "OrderItem ID does not exist");

		if (p.getOrderpojo().getStatus() == OrderStatus.CLOSE) {
			throw new ApiException("Order is purchased. Cannot delete items.");
		}
		dao.delete(id);
	}

	public List<SalesReport> getByParams(List<String> brands, List<String> categories, ZonedDateTime startDate,
			ZonedDateTime endDate) {
		return dao.selectByParams(brands, categories, startDate, endDate);
	}

}
