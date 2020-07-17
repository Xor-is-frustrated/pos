package com.increff.pos.model;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReportForm {


	private List<String> categories;
	private List<String> brands;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
	private ZonedDateTime startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
	private ZonedDateTime endDate;
	
	
	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> category) {
		this.categories = category;
	}

	public List<String> getBrands() {
		return brands;
	}

	public void setBrands(List<String> brand) {
		this.brands = brand;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(String startdate) {
		this.startDate = ZonedDateTime.parse(startdate);
	}

	public ZonedDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(String enddate) {
		this.endDate = ZonedDateTime.parse(enddate);
	}

}
