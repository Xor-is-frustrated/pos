package com.increff.employee.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.BrandPojo;

@Repository
public class BrandDao extends AbstractDao {

	private static String delete_id = "delete from BrandPojo p where id=:id";
	private static String select_id = "select p from BrandPojo p where id=:id";
	private static String select_brand = "select p from BrandPojo p where brand=:brand";
	private static String select_brand_category = "select p from BrandPojo p where brand=:brand and category=:category";
	private static String select_all = "select p from BrandPojo p";
	private static String select_categories = "select p from BrandPojo p group by p.category";
	private static String select_brands = "select p from BrandPojo p group by p.brand";
	private static String select_distinct_category = "select p from BrandPojo p where category=:category";
	private static String select_distinct_brand = "select p from BrandPojo p where brand=:brand";

	@Transactional
	public void insert(BrandPojo b) {
		em().persist(b);
	}

	public int delete(int id) {
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
		return query.getResultList();
	}
	
	public List<BrandPojo> selectCategories() {
		TypedQuery<BrandPojo> query = getQuery(select_categories, BrandPojo.class);
		return query.getResultList();
	}
	
	public List<BrandPojo> selectBrands() {
		TypedQuery<BrandPojo> query = getQuery(select_brands, BrandPojo.class);
		return query.getResultList();
	}
	
	public List<BrandPojo> selectDistinctCategories(String category) {
		TypedQuery<BrandPojo> query = getQuery(select_distinct_category, BrandPojo.class);
		query.setParameter("category", category);
		return query.getResultList();
	}
	public List<BrandPojo> selectDistinctBrands(String brand) {
		TypedQuery<BrandPojo> query = getQuery(select_distinct_brand, BrandPojo.class);
		query.setParameter("brand", brand);
		return query.getResultList();
	}

	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public BrandPojo select(String brand) {
		TypedQuery<BrandPojo> query = getQuery(select_brand, BrandPojo.class);
		query.setParameter("brand", brand);
		return getSingle(query);
	}
	
	public BrandPojo select(String brand,String category) {
		TypedQuery<BrandPojo> query = getQuery(select_brand_category, BrandPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		return getSingle(query);
	}

	public void update(BrandPojo p) {
	}

}
