package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Ticket;
import com.revature.util.ConnectionFactory;

public class TicketDaoImp implements TicketDao {

	@Override
	public boolean insertTicket(Ticket ticket) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "INSERT INTO reimbursement_ticket (amount, ticket_type, description, time_submitted, status, user_id) values (?,?,?,?,?,?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, ticket.getAmount());
			ps.setString(2, ticket.getType());
			ps.setString(3, ticket.getDescription());
			ps.setTimestamp(4, ticket.getTimeSubmitted());
			ps.setString(5, ticket.getStatus());
			ps.setInt(6, ticket.getUserId());
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Ticket> selectTicketsByUserId(int userId) {
		List<Ticket> ticketList = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM reimbursement_ticket WHERE user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ticketList.add(new Ticket(
						rs.getInt(1),
						rs.getDouble(2),
						rs.getString(3),
						rs.getString(4),
						rs.getTimestamp(5),
						rs.getString(6),
						rs.getInt(7)));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return ticketList;
	}

	@Override
	public List<Ticket> selectAllTickets() {
		List<Ticket> ticketList = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM reimbursement_ticket;";
			Statement s = conn.createStatement();		
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				ticketList.add(new Ticket(
						rs.getInt(1),
						rs.getDouble(2),
						rs.getString(3),
						rs.getString(4),
						rs.getTimestamp(5),
						rs.getString(6),
						rs.getInt(7)));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return ticketList;
	}

	@Override
	public boolean updateTicket(Ticket ticket) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "UPDATE reimbursement_ticket SET amount = ?, ticket_type = ?, description = ?, time_submitted = ?, status = ?, user_id = ? WHERE ticket_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, ticket.getAmount());
			ps.setString(2, ticket.getType());
			ps.setString(3, ticket.getDescription());
			ps.setTimestamp(4, ticket.getTimeSubmitted());
			ps.setString(5, ticket.getStatus());
			ps.setInt(6, ticket.getUserId());
			ps.setInt(7, ticket.getTicketId());
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteTicket(int ticketId) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "DELETE FROM reimbursement_ticket WHERE ticket_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ticketId);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
