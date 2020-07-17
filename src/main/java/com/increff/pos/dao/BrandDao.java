package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.BrandPojo;

@Repository
public class BrandDao extends AbstractDao {

	private static String selectById = "select p from BrandPojo p where id=:id";
	private static String selectByBrand = "select p from BrandPojo p where brand=:brand";
	private static String selectByCategory = "select p from BrandPojo p where category=:category";
	private static String selectByBrandAndCategory = "select p from BrandPojo p where brand=:brand and category=:category";
	private static String selectAll = "select p from BrandPojo p order by p.id";
	private static String selectDistinctCategories = "select distinct p.category from BrandPojo p";
	private static String selectDistinctBrands = "select distinct p.brand from BrandPojo p";

	@Transactional
	public BrandPojo insert(BrandPojo b) {
		em().persist(b);
		return b;
	}

	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(selectById, BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public List<BrandPojo> selectByCategory(String category) {
		TypedQuery<BrandPojo> query = getQuery(selectByCategory, BrandPojo.class);
		query.setParameter("category", category);
		return query.getResultList();
	}

	public List<BrandPojo> selectByBrand(String brand) {
		TypedQuery<BrandPojo> query = getQuery(selectByBrand, BrandPojo.class);
		query.setParameter("brand", brand);
		return query.getResultList();
	}

	public BrandPojo selectByBrandAndCategory(String brand, String category) {
		TypedQuery<BrandPojo> query = getQuery(selectByBrandAndCategory, BrandPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		return getSingle(query);
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(selectAll, BrandPojo.class);
		return query.getResultList();
	}

	public List<String> selectDistinctCategories() {
		TypedQuery<String> query = getQuery(selectDistinctCategories, String.class);
		return query.getResultList();
	}

	public List<String> selectDistinctBrands() {
		TypedQuery<String> query = getQuery(selectDistinctBrands, String.class);
		return query.getResultList();
	}

}
