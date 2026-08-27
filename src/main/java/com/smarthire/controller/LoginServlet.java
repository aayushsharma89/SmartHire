package com.smarthire.controller;

import java.io.IOException;

import com.smarthire.dao.UserDAO;
import com.smarthire.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.loginUser(email, password);

        if (user != null) {

            HttpSession session = request.getSession();

            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("role", user.getRole());

            String role = user.getRole();

            if ("ADMIN".equals(role)) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin"
                );
            

            } else if ("RECRUITER".equals(role)) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/recruiter-dashboard.jsp"
                );

            } else if ("JOB_SEEKER".equals(role)) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/jobseeker-dashboard.jsp"
                );

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/login.jsp"
                );
            }

        } else {

            request.setAttribute(
                    "error",
                    "Invalid email or password."
            );

            request.getRequestDispatcher(
                    "/login.jsp"
            ).forward(request, response);
        }
    }
}