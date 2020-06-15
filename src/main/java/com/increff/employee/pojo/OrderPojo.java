package com.increff.employee.pojo;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class OrderPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime datetime;
	
	@OneToMany(mappedBy = "orderpojo",fetch = FetchType.EAGER)
	private Set<OrderItemPojo> orderitem;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public Set<OrderItemPojo> getOrderitem() {
		return orderitem;
	}

	public void setOrderitem(Set<OrderItemPojo> orderitem) {
		this.orderitem = orderitem;
	}


	
	
	
}
