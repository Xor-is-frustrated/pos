package com.increff.employee.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.OrderPojo;

@Repository
public class OrderDao extends AbstractDao {

	private static String deleteId = "delete from OrderPojo p where id=:id";
	private static String selectId = "select p from OrderPojo p where id=:id";
	private static String selectAll = "select p from OrderPojo p";
	private static String selectCurrentOrder = "select p from OrderPojo p where p.orderitem IS EMPTY";

	@Transactional
	public void insert(OrderPojo b) {
		em().persist(b);
	}

	public int delete(int id) {
		Query query = em().createQuery(deleteId);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public List<OrderPojo> selectAll() {
		TypedQuery<OrderPojo> query = getQuery(selectAll, OrderPojo.class);
		return query.getResultList();
	}

	public OrderPojo select(int id) {
		TypedQuery<OrderPojo> query = getQuery(selectId, OrderPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public OrderPojo selectCurrentOrder() {
		TypedQuery<OrderPojo> query = getQuery(selectCurrentOrder, OrderPojo.class);
		return getSingle(query);
	}

	public void update(OrderPojo p) {
	}
}
