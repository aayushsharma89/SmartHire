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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        int userId =
                (Integer) session.getAttribute("userId");

        User user =
                userDAO.getUserById(userId);

        request.setAttribute(
                "profileUser",
                user
        );

        request.getRequestDispatcher(
                "/profile.jsp"
        ).forward(
                request,
                response
        );
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        int userId =
                (Integer) session.getAttribute("userId");

        String name =
                request.getParameter("name");

        String email =
                request.getParameter("email");

        User user =
                new User();

        user.setId(userId);
        user.setName(name);
        user.setEmail(email);

        boolean updated =
                userDAO.updateProfile(user);

        if (updated) {

            session.setAttribute(
                    "userName",
                    name
            );

            User updatedUser =
                    userDAO.getUserById(userId);

            session.setAttribute(
                    "user",
                    updatedUser
            );
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/profile"
                        + (updated
                            ? "?success=1"
                            : "?error=1")
        );
    }
}