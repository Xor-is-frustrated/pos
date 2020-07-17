package com.increff.pos.model;

public class OrderItemData extends OrderItemForm {

	private int id;
	private double sellingPrice;
	private String product;
	private double ItemTotalCost;

	public double getItemTotalCost() {
		return ItemTotalCost;
	}

	public void setItemTotalCost(double itemTotalCost) {
		ItemTotalCost = itemTotalCost;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingprice) {
		this.sellingPrice = sellingprice;
	}

}
