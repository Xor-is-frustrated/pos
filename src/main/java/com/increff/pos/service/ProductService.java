package com.increff.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;

@Service
public class ProductService extends AbstractService{

	@Autowired
	private ProductDao dao;
	
	@Autowired
	private BrandDao brandDao;

	@Transactional(rollbackFor = ApiException.class)
	public ProductPojo add(ProductPojo p) throws ApiException {
		normalizeProduct(p);
		
		ProductPojo existing = dao.selectByBarcode(p.getBarcode());
		
		checkNull(existing,"Product barcode already exists.");
		checkZero(p.getBarcode().length(),"Barcode cannot be empty.");
		checkZero(p.getName().length(),"Product name cannot be empty.");
		checkPositive(p.getMrp(), "MRP cannot be less than zero");

		BrandPojo b = brandDao.select(p.getBrand().getId());
		checkNotNull(b, "Brand Id does not exist");
		
		return dao.insert(p);
	}

	@Transactional(readOnly=true)
	public ProductPojo getByBarcode(String barcode) throws ApiException {
		ProductPojo p = dao.selectByBarcode(barcode);
		checkNotNull(p, "Product barcode does not exist");
		return p;
		
	}

	@Transactional(readOnly=true)
	public ProductPojo get(int id) throws ApiException {
		ProductPojo p =dao.select(id);
		checkNotNull(p, "Product ID does not exist");
		
		return p;
		
	}
	
	@Transactional(readOnly=true)
	public List<ProductPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(int id, ProductPojo p) throws ApiException {
		normalizeProduct(p);
		
		
		ProductPojo existing = dao.selectByBarcode(p.getBarcode());
		if (existing != null && existing.getId() != id) {
			throw new ApiException("Product barcode already exists.");
		}

		checkPositive(p.getMrp(), "MRP cannot be less than zero");
		checkZero(p.getBarcode().length(), "Barcode cannot be empty.");
		checkZero(p.getName().length(),"Product name cannot be empty.");                  

		BrandPojo b = brandDao.select( p.getBrand().getId());
		checkNotNull(b, "Brand ID does not exist");
		

		
	
		ProductPojo ex = dao.select(id);
		checkNotNull(ex, "Product ID does not exist");
			
		ex.setName(p.getName());
		ex.setMrp(p.getMrp());
		ex.setBarcode(p.getBarcode());

	}
	
	public static void normalizeProduct(ProductPojo p) {
		p.setName(p.getName().toLowerCase().trim());
	}



}
