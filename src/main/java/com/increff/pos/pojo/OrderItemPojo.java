package com.increff.pos.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"order_id", "product_id"}))
public class OrderItemPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@Column(nullable = false)
	private Double sellingPrice;

	@ManyToOne
	@JoinColumn(name = "order_id",nullable=false)
	private OrderPojo orderpojo;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private ProductPojo product;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProductPojo getProduct() {
		return product;
	}

	public void setProduct(ProductPojo product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public OrderPojo getOrderpojo() {
		return orderpojo;
	}

	public void setOrderpojo(OrderPojo orderpojo) {
		this.orderpojo = orderpojo;
	}

}
