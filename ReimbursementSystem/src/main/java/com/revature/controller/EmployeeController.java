package com.revature.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.service.Service;

public class EmployeeController {

	private static final Logger loggy = Logger.getLogger(EmployeeController.class);
	public static Service service = new Service();
	
	public static void getTickets(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		ObjectMapper om = new ObjectMapper();
		HttpSession sesh = req.getSession();
		User user = (User) sesh.getAttribute("user");
		List<Ticket> ticketList = service.checkTicketsByUser(user.getUserId());
		resp.getWriter().write(om.writeValueAsString(ticketList));
		resp.setStatus(200);
	}
	
	public static void submitTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		Ticket ticket = om.readValue(req.getReader(), com.revature.models.Ticket.class);
		HttpSession sesh = req.getSession();
		User user = (User) sesh.getAttribute("user");
		ticket.setUserId(user.getUserId());
		if(service.createTicket(ticket)) {
			loggy.info(user.getFname() + " " + user.getLname() + " (" + user.getUsername() + ") submitted a ticket");
			resp.setStatus(200);
		} else {
			loggy.error("Failed to create a ticket");
			resp.setStatus(500);
		}
	}
}
