package com.increff.employee.model;

import java.util.HashSet;
import java.util.Set;

public class BrandData extends BrandForm {

	private int id;
	private Set<String> product = new HashSet<>();

	public Set<String> getProduct() {
		return product;
	}

	public void setProduct(Set<String> product) {
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
