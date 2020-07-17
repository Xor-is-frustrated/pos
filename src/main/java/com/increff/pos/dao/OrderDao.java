package com.increff.pos.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.OrderPojo;

@Repository
public class OrderDao extends AbstractDao {

	private static String selectById = "select p from OrderPojo p where id=:id";
	private static String selectAll = "select p from OrderPojo p";
	private static String deleteById = "delete from OrderPojo p where id=:id";

	@Transactional
	public OrderPojo insert(OrderPojo b) {
		em().persist(b);
		return b;
	}

	public List<OrderPojo> selectAll() {
		TypedQuery<OrderPojo> query = getQuery(selectAll, OrderPojo.class);
		return query.getResultList();
	}

	public OrderPojo select(int id) {
		TypedQuery<OrderPojo> query = getQuery(selectById, OrderPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public void delete(int id) {
		Query query = em().createQuery(deleteById);
		query.setParameter("id", id);
		query.executeUpdate();
		
	}

}
