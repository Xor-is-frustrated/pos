package com.increff.pos.model;

import java.time.ZonedDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Invoice")
public class OrderInvoice {
	
	private double sellingprice;
	private String orderDate;
	private String receiptDate;
	
	private int id;
	private List<OrderItemData> items;

	public List<OrderItemData> getItems() {
		return items;
	}

	public void setItems(List<OrderItemData> items) {
		this.items = items;
	}


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

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	
	

}
