package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;

@Service
public class ProductService {

	@Autowired
	private ProductDao dao;

	@Transactional
	public void add(ProductPojo p) throws ApiException {
		normalize(p);
		ProductPojo existing = dao.select(p.getBarcode());
		if (existing != null) {
			throw new ApiException("given product barcode already exists");
		}
		dao.insert(p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo get(String barcode) throws ApiException {
		return getCheck(barcode);
	}

	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public ProductPojo getCheck(int id) throws ApiException {
		ProductPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Product with given ID does not exist, id: " + id);
		}
		return p;
	}

	@Transactional
	public ProductPojo getCheck(String product) throws ApiException {
		ProductPojo p = dao.select(product);
		if (p == null) {
			throw new ApiException("Product with given product_name does not exist, product: " + product);
		}
		return p;
	}

	@Transactional
	public List<ProductPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, ProductPojo p) throws ApiException {
		normalize(p);
		ProductPojo ex = getCheck(id);
		ex.setBrand(p.getBrand());
		ex.setProduct(p.getProduct());
		ex.setMrp(p.getMrp());
		ex.setBarcode(p.getBarcode());
		ex.setQuantity(p.getQuantity());
		dao.update(ex);
	}

	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	protected static void normalize(ProductPojo p) {
		p.setProduct(p.getProduct().toLowerCase().trim());
	}

}
