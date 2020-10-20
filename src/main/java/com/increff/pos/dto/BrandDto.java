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
	//adding another comment just in case and removing addForm to addIt
	public BrandData addIt(BrandForm brandForm) throws ApiException {
		BrandPojo pojo = ConvertorUtil.convert(brandForm);
		BrandPojo brand = brandService.add(pojo);
		return ConvertorUtil.convert(brand);         
	}

	public BrandData getIt(int id) throws ApiException {
		BrandPojo pojo = brandService.get(id);
		return ConvertorUtil.convert(pojo);
	}

	public List<BrandData> getItAllNow() {
		List<BrandPojo> list1 = brandService.getAll();
		return ConvertorUtil.convertBrands(list1);
	}

	public void updateIt(int id, BrandForm form) throws ApiException {
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
