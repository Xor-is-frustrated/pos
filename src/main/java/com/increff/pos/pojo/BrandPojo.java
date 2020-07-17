package com.increff.pos.pojo;

import java.util.List;

import javax.persistence.Column;
//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "brand", "category" }))
public class BrandPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private String category;

	@OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
	private List<ProductPojo> products;

	public Integer getId() {
		return id;
	}

	public List<ProductPojo> getProducts() {
		return products;
	}

	public void setProducts(List<ProductPojo> product) {
		this.products = product;
	}

	public void setId(Integer id) {
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
