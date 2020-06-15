package com.increff.employee.pojo;

import java.util.Set;

//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BrandPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String brand;
	private String category;

	@OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
	private Set<ProductPojo> product;

	public int getId() {
		return id;
	}

	public Set<ProductPojo> getProduct() {
		return product;
	}

	public void setProduct(Set<ProductPojo> product) {
		this.product = product;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
