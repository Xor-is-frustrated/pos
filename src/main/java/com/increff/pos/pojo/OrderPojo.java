package com.increff.pos.pojo;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class OrderPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private ZonedDateTime orderDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "orderpojo", fetch = FetchType.EAGER)
	private List<OrderItemPojo> orderItems;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ZonedDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(ZonedDateTime datetime) {
		this.orderDate = datetime;
	}

	public List<OrderItemPojo> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemPojo> orderitem) {
		this.orderItems = orderitem;
	}

}
