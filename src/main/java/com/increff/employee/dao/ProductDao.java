package com.increff.employee.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.ProductPojo;

@Repository
public class ProductDao extends AbstractDao {

	private static String deleteId = "delete from ProductPojo p where id=:id";
	private static String selectId = "select p from ProductPojo p where id=:id";
	private static String selectBarcode = "select p from ProductPojo p where barcode=:barcode";
	private static String selectAll = "select p from ProductPojo p";
	

	@Transactional
	public void insert(ProductPojo b) {
		em().persist(b);
	}

	public int delete(int id) {
		Query query = em().createQuery(deleteId);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(selectAll, ProductPojo.class);
		return query.getResultList();
	}

	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(selectId, ProductPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public ProductPojo select(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(selectBarcode, ProductPojo.class);
		query.setParameter("barcode", barcode);
		return getSingle(query);
	}
	

	public void update(ProductPojo p) {
	}
}
