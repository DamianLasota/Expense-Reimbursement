package com.revature.dao;

import java.util.List;

import com.revature.models.Ticket;

public interface TicketDao {
	
	public boolean insertTicket(Ticket ticket);
	
	public List<Ticket> selectTicketsByUserId(int userId);
//	public List<Ticket> selectTicketsByStatus(String status);
	public List<Ticket> selectAllTickets();
	
	public boolean updateTicket(Ticket ticket);
	
	public boolean deleteTicket(int ticketId);

}
