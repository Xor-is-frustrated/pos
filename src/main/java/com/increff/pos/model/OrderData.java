package com.increff.pos.model;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.increff.pos.pojo.OrderStatus;

public class OrderData {

	private int id;
	private ZonedDateTime datetime;
	private List<Integer> itemid;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public Enum<OrderStatus> getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ZonedDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(ZonedDateTime datetime) {
		this.datetime = datetime;
	}

	public List<Integer> getItemid() {
		return itemid;
	}

	public void setItemid(List<Integer> itemid) {
		this.itemid = itemid;
	}

}
