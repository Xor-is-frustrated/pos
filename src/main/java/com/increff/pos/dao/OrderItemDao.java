package com.increff.pos.dao;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.model.SalesReport;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;

@Repository
public class OrderItemDao extends AbstractDao {

	private static String selectById = "select p from OrderItemPojo p where id=:id";
	private static String selectByProduct = "select p from OrderItemPojo p where p.product=:product";
	private static String deleteById = "delete from OrderItemPojo p where id=:id";
	private static String selectByProductAndOrder = "select p from OrderItemPojo p where p.product=:product and p.orderpojo=:orderpojo";
	private static String selectByOrder = "select p from OrderItemPojo p where p.orderpojo=:orderpojo order by p.id";
	private static String selectByParams = "select NEW com.increff.pos.model.SalesReport(p.product.brand.category, sum(p.sellingPrice*p.quantity)) from OrderItemPojo p where p.product.brand.brand in :brands and p.product.brand.category in :categories and p.orderpojo.orderDate >= :startDate and p.orderpojo.orderDate <= :endDate group by p.product.brand.category";

	@Transactional
	public OrderItemPojo insert(OrderItemPojo b) {
		em().persist(b);
		return b;
	}

	public OrderItemPojo select(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(selectById, OrderItemPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public OrderItemPojo selectByProductAndOrder(ProductPojo product, OrderPojo orderpojo) {
		TypedQuery<OrderItemPojo> query = getQuery(selectByProductAndOrder, OrderItemPojo.class);
		query.setParameter("product", product);
		query.setParameter("orderpojo", orderpojo);
		return getSingle(query);
	}

	public List<OrderItemPojo> selectByProduct(ProductPojo product) {
		TypedQuery<OrderItemPojo> query = getQuery(selectByProduct, OrderItemPojo.class);
		query.setParameter("product", product);
		return query.getResultList();
	}

	public List<OrderItemPojo> selectByOrder(OrderPojo orderpojo) {
		TypedQuery<OrderItemPojo> query = getQuery(selectByOrder, OrderItemPojo.class);
		query.setParameter("orderpojo", orderpojo);
		return query.getResultList();
	}

	public int delete(int id) {
		Query query = em().createQuery(deleteById);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
//create model for object
	public List<SalesReport> selectByParams(List<String> brands, List<String> categories, ZonedDateTime startDate,
			ZonedDateTime endDate) {
		TypedQuery<SalesReport> query = getQuery(selectByParams, SalesReport.class);
		query.setParameter("categories", categories);
		query.setParameter("brands", brands);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		
		return query.getResultList();
	}

}
