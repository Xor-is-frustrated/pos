package com.increff.pos.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertorUtil;

@Service
public class InventoryDto {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ProductService productService;

	public InventoryData add(InventoryForm form) throws ApiException {
		ProductPojo pojo = productService.get(form.getProduct());
		InventoryPojo inv = ConvertorUtil.convert(form, pojo);
		inv = inventoryService.add(inv);
		return ConvertorUtil.convert(inv);

	}

	public InventoryData get(int id) throws ApiException {
		InventoryPojo pojo = inventoryService.get(id);
		return ConvertorUtil.convert(pojo);
	}

	public List<InventoryData> getAll() {
		List<InventoryPojo> list = inventoryService.getAll();
		return ConvertorUtil.convertInventories(list);

	}

	public List<InventoryData> getInventoryReport() {
		List<Object[]> list = inventoryService.getSum();
		return ConvertorUtil.convertInventoryReport(list);
	}

	public void update(int id, InventoryForm form) throws ApiException {
		inventoryService.update(id, form.getQuantity());
	}

}
