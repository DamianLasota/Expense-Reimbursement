package com.revature.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.service.Service;

public class FinanceController {
	
	private static final Logger loggy = Logger.getLogger(FinanceController.class);
	public static Service service = new Service();
	
	public static void getTickets(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		ObjectMapper om = new ObjectMapper();
		Map<Ticket, User> ticketMap = service.checkAllTickets();
		List<Object> ticketList = new ArrayList<>();
		List<Object> userList = new ArrayList<>();
		for(Entry<Ticket, User> entry: ticketMap.entrySet()) {
			ticketList.add(entry.getKey());
			userList.add(entry.getValue());
		}
		Map<String, List<Object>> newTicketMap = new HashMap<>();
		newTicketMap.put("Ticket", ticketList);
		newTicketMap.put("User", userList);
		resp.getWriter().write(om.writeValueAsString(newTicketMap));
		resp.setStatus(200);
	}
	
	public static void updateTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		Ticket ticket = om.readValue(req.getReader(), com.revature.models.Ticket.class);
		HttpSession sesh = req.getSession();
		User user = (User) sesh.getAttribute("user");
		if(service.updateTicket(ticket)) {
			loggy.info(user.getFname() + " " + user.getLname() + " (" + user.getUsername() + ") " + ticket.getStatus() + " a ticket");
			resp.setStatus(200);
		} else {
			loggy.error("Failed to update a ticket");
			resp.setStatus(500);
		}
	}

}
