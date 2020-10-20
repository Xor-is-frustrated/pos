package com.increff.pos.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.util.ConvertorUtil;

@Service
public class BrandDto {

	@Autowired
	private BrandService brandService; 
	// adding comments to it and changing it to brandForm
	public BrandData add(BrandForm brandForm) throws ApiException {
		BrandPojo pojo = ConvertorUtil.convert(brandForm);
		BrandPojo brand = brandService.add(pojo);
		return ConvertorUtil.convert(brand);         
	}

	public BrandData get(int id) throws ApiException {
		BrandPojo pojo = brandService.get(id);
		return ConvertorUtil.convert(pojo);
	}

	public List<BrandData> getItAll() {
		List<BrandPojo> list = brandService.getAll();
		return ConvertorUtil.convertBrands(list);
	}

	public void update(int id, BrandForm form) throws ApiException {
		BrandPojo pojo = ConvertorUtil.convert(form);
		brandService.update(id, pojo);
	}

	public List<String> getDistinctCategories() {
		return brandService.getDistinctCategories();
	}

	public List<String> getDistinctBrands() {
		return brandService.getDistinctBrands();
	}

}
