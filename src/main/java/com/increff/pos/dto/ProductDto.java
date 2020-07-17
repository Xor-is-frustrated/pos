package com.increff.pos.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertorUtil;

@Service
public class ProductDto {

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	public ProductData add(ProductForm form) throws ApiException {
		BrandPojo brandPojo = brandService.get(form.getBrandId());
		ProductPojo productPojo = ConvertorUtil.convert(form, brandPojo);
		ProductPojo product=productService.add(productPojo);
		return ConvertorUtil.convert(product);
	}

	public ProductData get(int id) throws ApiException {
		ProductPojo pojo = productService.get(id);
		return ConvertorUtil.convert(pojo);
	}

	public List<ProductData> getAll() {
		List<ProductPojo> list = productService.getAll();
		return ConvertorUtil.convertProducts(list);
		
	}

	public void update(int id, ProductForm form) throws ApiException {

		BrandPojo brandPojo = brandService.get(form.getBrandId());
		ProductPojo productPojo = ConvertorUtil.convert(form, brandPojo);
		productService.update(id, productPojo);

	}

}
