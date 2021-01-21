package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDao {

	public boolean insertUser(User user);
	
	public User selectUserById(int userId);
	public User selectUserByUsernameAndPassword(String username, String password);
	public List<String> selectAllUsernames();
	public List<String> selectAllEmails();
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(int userId);
	
}
