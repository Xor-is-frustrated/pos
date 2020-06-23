package com.increff.employee.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.BrandPojo;

@Repository
public class BrandDao extends AbstractDao {

	private static String deleteId = "delete from BrandPojo p where id=:id";
	private static String selectId = "select p from BrandPojo p where id=:id";
	private static String selectBrand = "select p from BrandPojo p where brand=:brand";
	private static String selectBrandCategory = "select p from BrandPojo p where brand=:brand and category=:category";
	private static String selectAll = "select p from BrandPojo p";
	private static String selectAllCategories = "select p from BrandPojo p group by p.category";
	private static String selectAllBrands = "select p from BrandPojo p group by p.brand";
	private static String selectCategory = "select p from BrandPojo p where category=:category";

	@Transactional
	public void insert(BrandPojo b) {
		em().persist(b);
	}

	public int delete(int id) {
		Query query = em().createQuery(deleteId);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(selectAll, BrandPojo.class);
		return query.getResultList();
	}

	public List<BrandPojo> selectCategories() {
		TypedQuery<BrandPojo> query = getQuery(selectAllCategories, BrandPojo.class);
		return query.getResultList();
	}

	public List<BrandPojo> selectBrands() {
		TypedQuery<BrandPojo> query = getQuery(selectAllBrands, BrandPojo.class);
		return query.getResultList();
	}

	public List<BrandPojo> selectDistinctCategories(String category) {
		TypedQuery<BrandPojo> query = getQuery(selectCategory, BrandPojo.class);
		query.setParameter("category", category);
		return query.getResultList();
	}

	public List<BrandPojo> selectDistinctBrands(String brand) {
		TypedQuery<BrandPojo> query = getQuery(selectBrand, BrandPojo.class);
		query.setParameter("brand", brand);
		return query.getResultList();
	}

	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(selectId, BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public BrandPojo select(String brand) {
		TypedQuery<BrandPojo> query = getQuery(selectBrand, BrandPojo.class);
		query.setParameter("brand", brand);
		return getSingle(query);
	}

	public BrandPojo select(String brand, String category) {
		TypedQuery<BrandPojo> query = getQuery(selectBrandCategory, BrandPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		return getSingle(query);
	}

	public void update(BrandPojo p) {
	}

}
