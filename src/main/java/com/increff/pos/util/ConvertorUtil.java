package com.increff.pos.util;

import java.util.ArrayList;
import java.util.List;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.model.SalesReport;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;

public class ConvertorUtil {
	

	public static BrandData convert(BrandPojo p) {
		BrandData d = new BrandData();
		d.setBrand(p.getBrand());
		d.setCategory(p.getCategory());
		d.setId(p.getId());

		return d;
	}

	public static BrandPojo convert(BrandForm f) {
		BrandPojo p = new BrandPojo();
		p.setBrand(f.getBrand());
		p.setCategory(f.getCategory());
		return p;
	}

	public static List<BrandData> convertBrands(List<BrandPojo> list) {
		List<BrandData> list2 = new ArrayList<BrandData>();
		for (BrandPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	public static ProductData convert(ProductPojo p) {
		ProductData data = new ProductData();
		data.setBarcode(p.getBarcode());
		data.setBrandId(p.getBrand().getId());
		data.setMrp(p.getMrp());
		data.setId(p.getId());
		data.setProduct(p.getName());

		return data;
	}

	public static ProductPojo convert(ProductForm f, BrandPojo b) throws ApiException {

		ProductPojo p = new ProductPojo();
		p.setName(f.getProduct());
		p.setBrand(b);
		p.setMrp(f.getMrp());
		p.setBarcode(f.getBarcode());

		return p;
	}

	public static List<ProductData> convertProducts(List<ProductPojo> list) {
		List<ProductData> productDataList = new ArrayList<ProductData>();
		for (ProductPojo p : list) {
			productDataList.add(ConvertorUtil.convert(p));
		}
		return productDataList;
	}

	public static InventoryData convert(InventoryPojo p) {
		InventoryData d = new InventoryData();
		d.setId(p.getId());
		d.setProduct(p.getProduct().getId());
		d.setQuantity(p.getQuantity());
		return d;
	}

	public static InventoryPojo convert(InventoryForm f, ProductPojo b) throws ApiException {
		InventoryPojo p = new InventoryPojo();
		p.setProduct(b);
		p.setQuantity(f.getQuantity());
		return p;
	}

	public static List<InventoryData> convertInventories(List<InventoryPojo> list) {
		List<InventoryData> list2 = new ArrayList<InventoryData>();
		for (InventoryPojo p : list) {

			list2.add(convert(p));
		}
		return list2;
	}

	public static OrderData convert(OrderPojo p) {
		OrderData d = new OrderData();
		d.setDatetime(p.getOrderDate());
		d.setId(p.getId());
		d.setStatus(p.getStatus());

		return d;
	}

	public static List<OrderData> convertOrders(List<OrderPojo> list) {
		List<OrderData> list2 = new ArrayList<OrderData>();
		for (OrderPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	public static OrderItemPojo convert(OrderItemForm f, ProductPojo p, OrderPojo order) {
		OrderItemPojo item = new OrderItemPojo();
		item.setQuantity(f.getQuantity());
		item.setProduct(p);
		item.setSellingPrice(f.getSellingPrice());
		item.setOrderpojo(order);
		return item;
	}

	public static OrderItemData convert(OrderItemPojo p) {
		OrderItemData d = new OrderItemData();
		d.setBarcode(p.getProduct().getBarcode());
		double roundedDouble = Math.round(p.getSellingPrice() * 100.0) / 100.0;
		d.setSellingPrice(roundedDouble);
		d.setId(p.getId());
		d.setProduct(p.getProduct().getName());
		d.setQuantity(p.getQuantity());
		d.setOrderId(p.getOrderpojo().getId());
		double totalCost = p.getQuantity() * p.getSellingPrice();
		totalCost = Math.round(totalCost * 100.0) / 100.0;
		d.setItemTotalCost(totalCost);
		return d;
	}

	public static List<OrderItemData> convertOrderItems(List<OrderItemPojo> list) {
		List<OrderItemData> list2 = new ArrayList<OrderItemData>();
		for (OrderItemPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

//	public static List<SalesReport> convertSalesReport(List<SalesReport> list) {
//		List<SalesReport> data = new ArrayList<SalesReport>();
//		for (SalesReport obj : list) {
//			SalesReport report = new SalesReport();
//			report.setCategory((String) obj[0]);
//			double q = (double) obj[1];
//			q = Math.round(q * 100.0) / 100.0;
//			report.setRevenue(q);
//			data.add(report);
//		}
//		return data;
//	}

	public static List<InventoryData> convertInventoryReport(List<Object[]> list) {
		List<InventoryData> data = new ArrayList<InventoryData>();
		for (Object[] obj : list) {
			InventoryData report = new InventoryData();
			report.setBrand((String) obj[0]);
			report.setCategory((String) obj[1]);
			Long value = (Long) obj[2];
			report.setQuantity(value.intValue());
			data.add(report);
		}
		return data;
	}

}
