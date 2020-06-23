package com.increff.employee.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.OrderItemPojo;

@Repository
public class OrderItemDao extends AbstractDao {

	private static String deleteId = "delete from OrderItemPojo p where id=:id";
	private static String selectId = "select p from OrderItemPojo p where id=:id";
	private static String selectAll = "select p from OrderItemPojo p";
	private static String selectCurrentItems = "select p from OrderItemPojo p where p.orderpojo is NULL";

	@Transactional
	public void insert(OrderItemPojo b) {
		em().persist(b);
	}

	public int delete(int id) {
		Query query = em().createQuery(deleteId);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public List<OrderItemPojo> selectAll() {
		TypedQuery<OrderItemPojo> query = getQuery(selectAll, OrderItemPojo.class);
		return query.getResultList();
	}

	public List<OrderItemPojo> selectCurrentItems() {
		TypedQuery<OrderItemPojo> query = getQuery(selectCurrentItems, OrderItemPojo.class);
		return query.getResultList();
	}

	public OrderItemPojo select(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(selectId, OrderItemPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public void update(OrderItemPojo p) {
	}

}
