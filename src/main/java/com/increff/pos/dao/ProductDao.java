package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.ProductPojo;

@Repository
public class ProductDao extends AbstractDao {

	private static String selectById = "select p from ProductPojo p where id=:id";
	private static String selectByBarcode = "select p from ProductPojo p where barcode=:barcode";
	private static String selectAll = "select p from ProductPojo p order by p.id";  

	@Transactional   
	public ProductPojo insert(ProductPojo b) {                                   
		em().persist(b); 
		return b;
	}

	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(selectAll, ProductPojo.class);
		return query.getResultList();
	}

	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(selectById, ProductPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public ProductPojo selectByBarcode(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(selectByBarcode, ProductPojo.class);
		query.setParameter("barcode", barcode);
		return getSingle(query);
	}

}
