package com.increff.pos.model;

public class SalesReport {

	private String category;
	private double revenue;
	
	public SalesReport(String category, double revenue) {
		this.category=category;
		this.revenue=revenue;        
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

}
