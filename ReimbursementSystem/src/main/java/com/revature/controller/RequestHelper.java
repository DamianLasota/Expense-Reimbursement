package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHelper {

	public static void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String endpoint = req.getRequestURI();
		
		switch(endpoint) {
			case "/ReimbursementSystem/master/check":
				switch(req.getMethod()) {
					case "GET":
						LoginController.checkUser(req, resp);
						break;
					case "POST":
						LoginController.checkSession(req, resp);
						break;
					default:
						resp.setStatus(405);
				}
				break;
			case "/ReimbursementSystem/master/login":
				switch(req.getMethod()) {
					case "POST":
						LoginController.login(req, resp);
						break;
					default:
						resp.setStatus(405);
				}
				break;
			case "/ReimbursementSystem/master/logout":
				switch(req.getMethod()) {
					case "POST":
						LoginController.logout(req, resp);
						break;
					default:
						resp.setStatus(405);
				}
				break;
			case "/ReimbursementSystem/master/signup":
				switch(req.getMethod()) {
					case "POST":
						LoginController.signUp(req, resp);
						break;
					default:
						resp.setStatus(405);
				}
				break;
			case "/ReimbursementSystem/master/employee":
				switch(req.getMethod()) {
					case "GET":
						EmployeeController.getTickets(req, resp);
						break;
					case "POST":
						EmployeeController.submitTicket(req, resp);
						break;
					default:
						resp.setStatus(405);
				}
				break;
			case "/ReimbursementSystem/master/finance":
				switch(req.getMethod()) {
					case "GET":
						FinanceController.getTickets(req, resp);
						break;
					case "PUT":
						FinanceController.updateTicket(req, resp);
						break;
					default:
						resp.setStatus(405);
				}
				break;
			default:
				resp.setStatus(404);
				resp.sendRedirect(req.getContextPath() + "/login.html");
		}
	}
	
}
