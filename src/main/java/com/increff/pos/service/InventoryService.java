package com.increff.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;

@Service
public class InventoryService extends AbstractService {

	@Autowired
	private InventoryDao dao;

	@Autowired
	private ProductDao productDao;

	@Transactional(rollbackFor = ApiException.class)
	public InventoryPojo add(InventoryPojo p) throws ApiException {

		ProductPojo pojo = p.getProduct();

		InventoryPojo inv = dao.selectByProduct(pojo);
		checkNull(inv, "Inventory already exists");

		int quantity = p.getQuantity();
		checkPositive(quantity, "Quantity cannot be less than zero");

		return dao.insert(p);
	}

	@Transactional(readOnly = true)
	public InventoryPojo get(int id) throws ApiException {
		InventoryPojo p = dao.select(id);
		checkNotNull(p, "Inventory ID does not exist");

		return p;
	}

	@Transactional(readOnly = true)
	public InventoryPojo getByProductId(int id) throws ApiException {

		ProductPojo product = productDao.select(id);
		checkNotNull(product, "Product ID does not exist");

		InventoryPojo inv = dao.selectByProduct(product);
		return inv;
	}

	@Transactional(readOnly = true)
	public List<InventoryPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(int id, int updatedQuantity) throws ApiException {

		checkPositive(updatedQuantity, "Quantity cannot be less than zero");

		InventoryPojo p = dao.select(id);
		checkNotNull(p, "Inventory ID does not exist");

		p.setQuantity(updatedQuantity);
	}

	public List<Object[]> getSum() {
		return dao.selectSum();
	}

}
