package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.service.Service;

public class LoginController {
	
	private static final Logger loggy = Logger.getLogger(LoginController.class);
	public static Service service = new Service();	
	
	public static void checkSession(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sesh = req.getSession(false);
		if(sesh != null && sesh.getAttribute("user") != null) {
			User user = (User) sesh.getAttribute("user");
			String username = user.getUsername();
			String password = user.getPassword();
			if(user.equals(service.checkLogin(username, password))) {
				resp.setStatus(200);
				if(user.getType().equals("FINANCE MANAGER")) {
					resp.sendRedirect(req.getContextPath() + "/finance.html");
				} else {
					resp.sendRedirect(req.getContextPath() + "/employee.html");
				}
			} else {
				resp.setStatus(403);
				resp.sendRedirect(req.getContextPath() + "/login.html");
			}
		} else {
			resp.setStatus(200);
			resp.sendRedirect(req.getContextPath() + "/login.html");
		}
	}
	
	public static void checkUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		ObjectMapper om = new ObjectMapper();
		HttpSession sesh = req.getSession();
		User user = (User) sesh.getAttribute("user");
		resp.getWriter().write(om.writeValueAsString(user));
		resp.setStatus(200);
	}

	public static void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		User user = om.readValue(req.getReader(), com.revature.models.User.class);
		user = service.checkLogin(user.getUsername(), user.getPassword());
		if(user != null) {
			HttpSession sesh = req.getSession();
			sesh.setAttribute("user", user);
			loggy.info(user.getFname() + " " + user.getLname() + " (" + user.getUsername() + ") has logged in");
			resp.setStatus(200);
			if(user.getType().equals("FINANCE MANAGER")) {
				resp.sendRedirect(req.getContextPath() + "/finance.html");
			} else {
				resp.sendRedirect(req.getContextPath() + "/employee.html");
			}
		} else {
			resp.setStatus(403);
		}
	}
	
	public static void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		resp.setStatus(200);
		resp.sendRedirect(req.getContextPath() + "/login.html");
	}
	
	public static void signUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		User user = om.readValue(req.getReader(), com.revature.models.User.class);
		boolean usernameAvailable = service.checkUsername(user.getUsername());
		boolean emailAvailable = service.checkEmail(user.getEmail());
		if(usernameAvailable && emailAvailable) {
			if(service.createUser(user)) {
				loggy.info(user.getFname() + " " + user.getLname() + " (" + user.getUsername() + ") created an account");
				resp.setStatus(200);
				resp.sendRedirect(req.getContextPath() + "/login.html");
			} else {
				loggy.error("Failed to create an account");
				resp.setStatus(500);
			}
		} else {
			resp.setStatus(406);
			if(!usernameAvailable && !emailAvailable) {
				resp.getWriter().write("Username and Email taken");
			} else if(!usernameAvailable) {
				resp.getWriter().write("Username taken");
			} else if(!emailAvailable) {
				resp.getWriter().write("Email taken");
			}
		}
	}
	
}
