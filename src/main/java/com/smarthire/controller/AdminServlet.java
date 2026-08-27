package com.smarthire.controller;

import java.io.IOException;
import java.util.List;

import com.smarthire.dao.AdminDAO;
import com.smarthire.model.Application;
import com.smarthire.model.Company;
import com.smarthire.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AdminDAO adminDAO;

    @Override
    public void init() {
        adminDAO = new AdminDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // =====================================================
        // ADMIN LOGIN CHECK
        // =====================================================

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect(
                    request.getContextPath() +
                    "/login.jsp"
            );

            return;
        }

        String role =
                (String) session.getAttribute("role");

        if (!"ADMIN".equals(role)) {

            response.sendRedirect(
                    request.getContextPath() +
                    "/login.jsp"
            );

            return;
        }

        String action =
                request.getParameter("action");

        // =====================================================
        // MANAGE USERS
        // =====================================================

        if ("users".equals(action)) {

            showUsers(
                    request,
                    response
            );

            return;
        }

        // =====================================================
        // MANAGE COMPANIES
        // =====================================================

        if ("companies".equals(action)) {

            showCompanies(
                    request,
                    response
            );

            return;
        }

        // =====================================================
        // MANAGE APPLICATIONS
        // =====================================================

        if ("applications".equals(action)) {

            showApplications(
                    request,
                    response
            );

            return;
        }

        // =====================================================
        // DEFAULT = ADMIN DASHBOARD
        // =====================================================

        showDashboard(
                request,
                response
        );
    }

    // =========================================================
    // ADMIN DASHBOARD
    // =========================================================

    private void showDashboard(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "totalUsers",
                adminDAO.getTotalUsers()
        );

        request.setAttribute(
                "totalJobSeekers",
                adminDAO.getTotalJobSeekers()
        );

        request.setAttribute(
                "totalRecruiters",
                adminDAO.getTotalRecruiters()
        );

        request.setAttribute(
                "totalCompanies",
                adminDAO.getTotalCompanies()
        );

        request.setAttribute(
                "totalJobs",
                adminDAO.getTotalJobs()
        );

        request.setAttribute(
                "totalApplications",
                adminDAO.getTotalApplications()
        );

        request.getRequestDispatcher(
                "/admin-dashboard.jsp"
        ).forward(
                request,
                response
        );
    }

    // =========================================================
    // USERS
    // =========================================================

    private void showUsers(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String delete =
                request.getParameter("delete");

        if (delete != null) {

            int userId =
                    Integer.parseInt(delete);

            adminDAO.deleteUser(userId);
        }

        List<User> users =
                adminDAO.getAllUsers();

        request.setAttribute(
                "users",
                users
        );

        request.getRequestDispatcher(
                "/admin-users.jsp"
        ).forward(
                request,
                response
        );
    }

    // =========================================================
    // COMPANIES
    // =========================================================

    private void showCompanies(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String delete =
                request.getParameter("delete");

        if (delete != null) {

            int companyId =
                    Integer.parseInt(delete);

            adminDAO.deleteCompany(
                    companyId
            );
        }

        List<Company> companies =
                adminDAO.getAllCompanies();

        request.setAttribute(
                "companies",
                companies
        );

        request.getRequestDispatcher(
                "/admin-companies.jsp"
        ).forward(
                request,
                response
        );
    }

    // =========================================================
    // APPLICATIONS
    // =========================================================

    private void showApplications(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String delete =
                request.getParameter("delete");

        if (delete != null) {

            int applicationId =
                    Integer.parseInt(delete);

            adminDAO.deleteApplication(
                    applicationId
            );
        }

        List<Application> applications =
                adminDAO.getAllApplications();

        request.setAttribute(
                "applications",
                applications
        );

        request.getRequestDispatcher(
                "/admin-applications.jsp"
        ).forward(
                request,
                response
        );
    }
}