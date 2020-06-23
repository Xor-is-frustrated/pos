package com.increff.employee.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.InventoryReport;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;

public class ControllerUtil {

	public static BrandData convert(BrandPojo p) {
		BrandData d = new BrandData();
		d.setBrand(p.getBrand());
		d.setCategory(p.getCategory());
		d.setId(p.getId());

		Set<ProductPojo> pojoset = p.getProduct();
		Set<String> s = new HashSet<String>();
		Iterator<ProductPojo> value = pojoset.iterator();
		while (value.hasNext()) {
			ProductPojo test = (ProductPojo) value.next();
			s.add(test.getProduct());
		}
		d.setProduct(s);

		return d;
	}

	public static BrandPojo convert(BrandForm f) {
		BrandPojo p = new BrandPojo();
		p.setBrand(f.getBrand());
		p.setCategory(f.getCategory());
		return p;
	}  

	public static List<BrandData> convertBrand(List<BrandPojo> list) {
		List<BrandData> list2 = new ArrayList<BrandData>();
		for (BrandPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	public static double getRevenue(List<BrandPojo> list, List<String> category, LocalDate d1, LocalDate d2) {
		double revenue = 0;
		for (BrandPojo pojo : list) {
			if (category.contains(pojo.getCategory()))
				revenue += getRevenue(pojo.getProduct(), d1, d2);
		}
		revenue = Math.round(revenue * 100.0) / 100.0;
		return revenue;
	}


	public static InventoryData convert(InventoryPojo p, int id) {
		InventoryData d = new InventoryData();
		d.setId(id);
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

	public static List<InventoryData> convertInventory(List<InventoryPojo> list) {
		List<InventoryData> list2 = new ArrayList<InventoryData>();
		for (InventoryPojo p : list) {
			int id = p.getId();
			list2.add(convert(p, id));
		}
		return list2;
	}
	
	public static List<String> convertCategoryList(List<BrandPojo>list)
	{
		List<String>list2=new ArrayList<String>();
		for(BrandPojo p:list)   
		{ 
			list2.add(p.getCategory());
		}
		return list2; 
	}
	
	public static List<String> convertBrandList(List<BrandPojo>list)
	{
		List<String>list2=new ArrayList<String>();
		for(BrandPojo p:list)
		{
			list2.add(p.getBrand());
		}
		return list2;
	}
	
	public static OrderData convert(OrderPojo p) {
		OrderData d = new OrderData();
		d.setDatetime(p.getDatetime());
		d.setId(p.getId());
		Set<OrderItemPojo> pojoset = p.getOrderitem();
		Set<Integer> s = new HashSet<Integer>();
		Iterator<OrderItemPojo> value = pojoset.iterator();
		while (value.hasNext()) {
			OrderItemPojo test = (OrderItemPojo) value.next();
			s.add(test.getId());
		}
		d.setItemid(s);
		return d;
	}

	public static List<OrderData> convert(List<OrderPojo> list) {
		List<OrderData> list2 = new ArrayList<OrderData>();
		for (OrderPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	public static List<OrderItemData> convertOrderItem(List<OrderItemPojo> list) {
		List<OrderItemData> list2 = new ArrayList<OrderItemData>();
		for (OrderItemPojo pojo : list) {
			OrderItemData data = new OrderItemData();
			double roundedDouble = Math.round(pojo.getSellingPrice() * 100.0) / 100.0;
			data.setSellingprice(roundedDouble);
			data.setMrp(pojo.getProduct().getMrp());
			data.setProduct(pojo.getProduct().getProduct());
			data.setQuantity(pojo.getQuantity());
			list2.add(data);
		}
		return list2;
	}
	
	public static ProductData convert(ProductPojo p) {
		ProductData d = new ProductData();
		d.setBrand(p.getBrand().getId());
		double roundedDouble = Math.round(p.getMrp() * 100.0) / 100.0;
		d.setMrp(roundedDouble);
		d.setId(p.getId());
		d.setBarcode(p.getBarcode());
		d.setProduct(p.getProduct());
		InventoryPojo inv = p.getQuantity();
		int val = 0;
		if (inv != null) {
			val = inv.getQuantity();
		}
		d.setQuantity(val);
		return d;
	}

	public static ProductPojo convert(ProductForm f, BrandPojo b) throws ApiException {

		ProductPojo p = new ProductPojo();
		p.setProduct(f.getProduct());
		p.setBrand(b);
		p.setMrp(f.getMrp());
		p.setBarcode(f.getBarcode());

		return p;
	}
	
	public static List<InventoryReport> convertToInventory(List<BrandPojo> list, List<String> category, List<String> brand,
			LocalDate d1, LocalDate d2) {
		List<InventoryReport> list2 = new ArrayList<InventoryReport>();

		for (BrandPojo pojo : list) {

			if (!category.contains(pojo.getCategory()) || !brand.contains(pojo.getBrand()))
				continue;

			InventoryReport inv = new InventoryReport();
			inv.setBrand(pojo.getBrand());
			inv.setCategory(pojo.getCategory());
			int quantity = getQuantity(pojo.getProduct());
			inv.setQuantity(quantity);
			double revenue = getRevenue(pojo.getProduct(), d1, d2);
			inv.setRevenue(revenue);
			list2.add(inv);
		}
		return list2;
	}

	private static int getQuantity(Set<ProductPojo> p) {
		int quantity = 0;
		for (ProductPojo pojo : p) {
			if (pojo.getQuantity() != null) {
				quantity = quantity + pojo.getQuantity().getQuantity();
			}
		}
		return quantity;
	}

	public static double getRevenue(Set<ProductPojo> p, LocalDate d1, LocalDate d2) {
		double revenue = 0;
		for (ProductPojo pojo : p) {
			revenue += getProductRevenue(pojo.getItem(), d1, d2);
		}
		revenue = Math.round(revenue * 100.0) / 100.0;
		return revenue;
	}

	public static double getProductRevenue(Set<OrderItemPojo> p, LocalDate d1, LocalDate d2) {
		double revenue = 0;
		for (OrderItemPojo pojo : p) {
			if (pojo.getOrderpojo() != null) {
				LocalDateTime date = pojo.getOrderpojo().getDatetime();
				LocalDate d = date.toLocalDate();

				if (d.compareTo(d1) >= 0 && d.compareTo(d2) <= 0) {
					revenue += pojo.getSellingPrice();
				}
			}
		}
		revenue = Math.round(revenue * 100.0) / 100.0;
		return revenue;
	}
	
	public static double getInitialRevenue(List<BrandPojo> list, List<String> brand, LocalDate d1, LocalDate d2) {
		double revenue = 0;
		for (BrandPojo pojo : list) {
			if (brand.contains(pojo.getBrand()))
				revenue += getRevenue(pojo.getProduct(), d1, d2);
		}
		revenue = Math.round(revenue * 100.0) / 100.0;
		return revenue;
	}

	public static OrderItemData convert(OrderItemPojo p) {
		OrderItemData d = new OrderItemData();
		d.setBarcode(p.getProduct().getBarcode());
		double roundedDouble = Math.round(p.getSellingPrice() * 100.0) / 100.0;
		d.setSellingprice(roundedDouble);
		d.setId(p.getId());
		d.setProduct(p.getProduct().getProduct());
		d.setQuantity(p.getQuantity());
		d.setMrp(p.getProduct().getMrp());
		return d;
	}

	public static OrderItemPojo convert(OrderItemForm f, ProductPojo p) {
		OrderItemPojo item = new OrderItemPojo();
		double price = p.getMrp() * f.getQuantity();

		item.setQuantity(f.getQuantity());
		item.setProduct(p);
		item.setSellingPrice(price);
		return item;
	}

	public static List<OrderItemData> convertOrderItemPojo(List<OrderItemPojo> list) {
		List<OrderItemData> list2 = new ArrayList<OrderItemData>();
		for (OrderItemPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	public static int getQuantity(ProductPojo pojo, OrderItemPojo p) throws ApiException {
		int quantity = 0;
		if (pojo.getQuantity() == null) {
			throw new ApiException("quantity exceeds invent ory quantity, please reduce the quantity to" + quantity);
		}
		quantity = pojo.getQuantity().getQuantity();
		if (quantity < p.getQuantity()) {
			throw new ApiException("quantity exceeds inventory quantity, please reduce the quantity" + quantity);
		}
		
		return quantity;
	}

	public static LocalDate getLocalDate(Date date)
	{
		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}
	



}
