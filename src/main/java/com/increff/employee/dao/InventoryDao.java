package com.increff.employee.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.InventoryPojo;

@Repository
public class InventoryDao extends AbstractDao {

	private static String delete_id = "delete from InventoryPojo p where id=:id";
	private static String select_id = "select p from InventoryPojo p where id=:id";
	private static String select_all = "select p from InventoryPojo p";

	@Transactional
	public void insert(InventoryPojo b) {
		em().persist(b);
	}

	public int delete(int id) {
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public List<InventoryPojo> selectAll() {
		TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
		return query.getResultList();
	}

	public InventoryPojo select(int id) {
		TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public void update(InventoryPojo p) {
	}

}
