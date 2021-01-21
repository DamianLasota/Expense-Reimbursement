package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class UserDaoImp implements UserDao {

	@Override
	public boolean insertUser(User user) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "INSERT INTO reimbursement_user (username, pass, first_name, last_name, email, user_type) values (?,?,?,?,?,?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFname());
			ps.setString(4, user.getLname());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getType());
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User selectUserById(int userId) {
		User user = null;
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM reimbursement_user WHERE user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user = new User(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User selectUserByUsernameAndPassword(String username, String password) {
		User user = null;
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM reimbursement_user WHERE username = ? AND pass = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user = new User(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<String> selectAllUsernames() {
		List<String> userList = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT username FROM reimbursement_user;";
			Statement s = conn.createStatement();		
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				userList.add(rs.getString(1));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	@Override
	public List<String> selectAllEmails() {
		List<String> userList = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT email FROM reimbursement_user;";
			Statement s = conn.createStatement();		
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				userList.add(rs.getString(1));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public boolean updateUser(User user) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "UPDATE reimbursement_user SET username = ?, pass = ?, first_name = ?, last_name = ?, email = ?, user_type = ? WHERE user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFname());
			ps.setString(4, user.getLname());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getType());
			ps.setInt(7, user.getUserId());
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(int userId) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "DELETE FROM reimbursement_user WHERE user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
