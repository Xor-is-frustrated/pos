package com.increff.pos.model;

public class ProductForm {

	private String barcode;
	private Double mrp;
	private int brandId;
	private String product;

	public String getBarcode() {
		return barcode;
	} 

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Double getMrp() {
		return mrp;
	}   

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brand) {
		this.brandId = brand;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

}
