package com.increff.pos.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderDto dto;

	@ApiOperation(value = "Creats an order")
	@RequestMapping(method = RequestMethod.POST)
	public int add() throws ApiException {
		return dto.add().getId();
	}

	@ApiOperation(value = "Closes an order ")
	@RequestMapping(path = "/purchase/{id}", method = RequestMethod.GET)
	public void close(@PathVariable int id) throws ApiException {
		dto.closeOrder(id);
	} 
	
	@ApiOperation(value = "Deletes an order ")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {    
		dto.delete(id);
	} 

	@ApiOperation(value = "Generates PDF ")
	@RequestMapping(path = "/receipt/{id}", method = RequestMethod.GET)
	private void performTask(@PathVariable int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ApiException, JAXBException {
		dto.getPdf(id, response);
	}

}
