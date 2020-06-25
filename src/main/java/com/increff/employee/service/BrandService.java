package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;

@Service
public class BrandService {

	@Autowired
	private BrandDao dao;

	@Transactional
	public void add(BrandPojo p) throws ApiException {
		normalize(p);
		BrandPojo existing = dao.select(p.getBrand(), p.getCategory());
		if (existing != null) {
			throw new ApiException("Brand category combination already exists");
		}
		dao.insert(p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo get(String brand) throws ApiException {
		return getCheck(brand);
	}

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public BrandPojo getCheck(int id) throws ApiException {
		BrandPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Brand with given ID does not exist, id: " + id);
		}
		return p;
	}

	@Transactional
	public BrandPojo getCheck(String brand) throws ApiException {
		BrandPojo p = dao.select(brand);
		if (p == null) {
			throw new ApiException("Brand with given brand_name does not exist, brand: " + brand);
		}
		return p;
	}

	@Transactional
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional
	public List<BrandPojo> getCategories() {
		return dao.selectCategories();
	}

	@Transactional
	public List<BrandPojo> getBrands() {
		return dao.selectBrands();
	}

	@Transactional
	public List<BrandPojo> getDistinctCategories(String category) {
		return dao.selectDistinctCategories(category);
	}

	@Transactional
	public List<BrandPojo> getDistinctBrands(String brand) {
		return dao.selectDistinctBrands(brand);
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, BrandPojo p) throws ApiException {
		normalize(p);
		BrandPojo existing = dao.select(p.getBrand(), p.getCategory());
		if (existing != null && existing.getId()!=id) {
			throw new ApiException("Brand category combination already exists");
		}
		BrandPojo ex = getCheck(id);
		ex.setBrand(p.getBrand());
		ex.setCategory(p.getCategory());
		ex.setProduct(p.getProduct());
		dao.update(ex);
	}

	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		dao.delete(id);
	}

	protected static void normalize(BrandPojo p) {
		p.setBrand(p.getBrand().toLowerCase().trim());
		p.setCategory(p.getCategory().toLowerCase().trim());
	}

}
