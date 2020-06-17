package com.increff.employee.pojo;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ProductPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String product;
	private double mrp;
	private String barcode;
	
	@ManyToOne
	@JoinColumn(name="brand_product")
	private BrandPojo brand;
	
	@OneToOne(mappedBy="product",fetch = FetchType.EAGER)
	private InventoryPojo quantity;
	
	@OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
	private Set<OrderItemPojo> item;
		

	public Set<OrderItemPojo> getItem() {
		return item;
	}

	public void setItem(Set<OrderItemPojo> item) {
		this.item = item;
	}

	public InventoryPojo getQuantity() {
		return quantity;
	}

	public void setQuantity(InventoryPojo quantity) {
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BrandPojo getBrand() {
		return brand;
	}

	public void setBrand(BrandPojo brand) {
		this.brand = brand;
	}

	
	
	
}
