package com.increff.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;

@Service
public class BrandService extends AbstractService {

	@Autowired
	private BrandDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public BrandPojo add(BrandPojo p) throws ApiException {

		normalizeBrand(p);

		BrandPojo existing = dao.selectByBrandAndCategory(p.getBrand(), p.getCategory());
		checkNull(existing, "Brand category combination already exists");
		checkZero(p.getBrand().length(),"Brand name cannot be empty.");
		checkZero(p.getCategory().length(),"Category name cannot be empty.");

		BrandPojo brandPojo = dao.insert(p);
		return brandPojo;
	}

	@Transactional(readOnly = true)
	public BrandPojo get(int id) throws ApiException {
		BrandPojo p = dao.select(id);
		checkNotNull(p, "Brand ID does not exist");
		return p;
	}

	@Transactional(readOnly = true)
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(readOnly = true)
	public List<String> getDistinctCategories() {
		return dao.selectDistinctCategories();
	}

	@Transactional(readOnly = true)
	public List<String> getDistinctBrands() {
		return dao.selectDistinctBrands();
	}

	@Transactional(readOnly = true)
	public List<BrandPojo> getByCategory(String category) {
		return dao.selectByCategory(category);
	}

	@Transactional(readOnly = true)
	public List<BrandPojo> getByBrand(String brand) {
		return dao.selectByBrand(brand);
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(int id, BrandPojo p) throws ApiException {
		normalizeBrand(p);

		BrandPojo pojo = dao.selectByBrandAndCategory(p.getBrand(), p.getCategory());
		if (pojo != null && pojo.getId() != id) {
			throw new ApiException("Brand category combination already exists");
		}
		checkZero(p.getBrand().length(),"Brand name cannot be empty.");
		checkZero(p.getCategory().length(),"Category name cannot be empty.");
		BrandPojo existing = dao.select(id);
		checkNotNull(existing, "Brand ID does not exist");
		existing.setBrand(p.getBrand());
		existing.setCategory(p.getCategory());

	}

	@Transactional(readOnly = true)
	public BrandPojo getByBrandAndCategory(String brand, String category) throws ApiException {
		normalizeString(brand);
		normalizeString(category);
		BrandPojo p = dao.selectByBrandAndCategory(brand, category);
		checkNotNull(p, "Brand category combination does not exists");

		return p;

	}

	public static void normalizeBrand(BrandPojo p) {
		p.setBrand(p.getBrand().toLowerCase().trim());
		p.setCategory(p.getCategory().toLowerCase().trim());
	}

	public static String normalizeString(String s) {
		s = s.toLowerCase().trim();
		return s;
	}

}
