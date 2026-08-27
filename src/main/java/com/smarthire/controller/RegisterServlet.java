package com.smarthire.controller;

import java.io.IOException;

import com.smarthire.dao.UserDAO;
import com.smarthire.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User user = new User(
                name,
                email,
                password,
                role
        );

        boolean registered = userDAO.registerUser(user);

        if (registered) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );

        } else {

            request.setAttribute(
                    "error",
                    "Registration failed. Email may already exist."
            );

            request.getRequestDispatcher(
                    "/register.jsp"
            ).forward(request, response);
        }
    }
}