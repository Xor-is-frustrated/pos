package com.increff.employee.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Invoice")
public class OrderInvoice {
	
	private double sellingprice;
	
	private int id;
	private List<OrderItemData> items;

	public List<OrderItemData> getItems() {
		return items;
	}

	public void setItems(List<OrderItemData> items) {
		this.items = items;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getSellingprice() {
		return sellingprice;
	}

	public void setSellingprice(double sellingprice) {
		this.sellingprice = sellingprice;
	}

}
