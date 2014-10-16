package com.ei.itop.incidentmgnt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ei.itop.incidentmgnt.service.TransactionService;

@Controller
@RequestMapping("/trans")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value="/all")
	public void queryTransList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
	}
	
	@RequestMapping(value="/add")
	public void addTrans(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
	}
	
	@RequestMapping(value="/modify")
	public void modifyTrans(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
	}
	
}
