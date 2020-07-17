package com.increff.pos.util;

import java.util.List;

import com.increff.pos.model.OrderItemData;

public class HelperUtil {

	public static double getSellingPrice(List<OrderItemData> list) {
		double cost = 0;
		for (OrderItemData data : list) {
			cost += data.getSellingPrice() * data.getQuantity();
		}
		cost = Math.round(cost * 100.0) / 100.0;
		return cost;
	}

}
