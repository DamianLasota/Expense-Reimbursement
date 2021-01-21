package com.revature.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.dao.*;
import com.revature.models.Ticket;
import com.revature.models.User;

public class Service {

	private static UserDao uDao = new UserDaoImp();
	private static TicketDao tDao = new TicketDaoImp();
	
	public boolean createUser(User user) {
		return uDao.insertUser(user);
	}
	
	public boolean checkUsername(String username) {
		List<String> userList = uDao.selectAllUsernames();
		for(String u: userList) {
			if(u.equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean checkEmail(String email) {
		List<String> userList = uDao.selectAllEmails();
		for(String u: userList) {
			if(u.equals(email)) {
				return false;
			}
		}
		return true;
	}
	
	public User checkLogin(String username, String password) {
		return uDao.selectUserByUsernameAndPassword(username, password);
	}
	
	public boolean createTicket(Ticket ticket) {
		return tDao.insertTicket(ticket);
	}
	
	public List<Ticket> checkTicketsByUser(int userId) {
		return tDao.selectTicketsByUserId(userId);
	}
	
	public Map<Ticket, User> checkAllTickets() {
		Map<Ticket, User> ticketMap = new HashMap<>();
		List<Ticket> ticketList = tDao.selectAllTickets();
		for(Ticket t: ticketList) {
			ticketMap.put(t, uDao.selectUserById(t.getUserId()));
		}
		return ticketMap;
	}
	
	public boolean updateTicket(Ticket ticket) {
		return tDao.updateTicket(ticket);
	}
	
}
