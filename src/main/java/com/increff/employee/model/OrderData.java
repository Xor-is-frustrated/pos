package com.increff.employee.model;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderData {

	private int id;
	private LocalDateTime datetime;
	private Set<Integer> itemid;

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

	public Set<Integer> getItemid() {
		return itemid;
	}

	public void setItemid(Set<Integer> itemid) {
		this.itemid = itemid;
	}

}
