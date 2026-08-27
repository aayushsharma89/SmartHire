package com.smarthire.controller;

import java.io.IOException;
import java.util.List;

import com.smarthire.dao.CompanyDAO;
import com.smarthire.model.Company;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/company")
public class CompanyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CompanyDAO companyDAO;

    // =========================================================
    // INITIALIZE DAO
    // =========================================================

    @Override
    public void init() {

        companyDAO = new CompanyDAO();
    }

    // =========================================================
    // GET REQUEST
    // =========================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // -----------------------------------------------------
        // Get existing session
        // -----------------------------------------------------

        HttpSession session =
                request.getSession(false);

        // -----------------------------------------------------
        // Check login
        // -----------------------------------------------------

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // -----------------------------------------------------
        // Check recruiter role
        // -----------------------------------------------------

        String role =
                (String) session.getAttribute("role");

        if (!"RECRUITER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // -----------------------------------------------------
        // Get recruiter ID
        // -----------------------------------------------------

        int recruiterId =
                (Integer) session.getAttribute(
                        "userId"
                );

        // -----------------------------------------------------
        // Get action
        // -----------------------------------------------------

        String action =
                request.getParameter("action");

        // =====================================================
        // EDIT COMPANY
        // =====================================================

        if ("edit".equals(action)) {

            String idParameter =
                    request.getParameter("id");

            if (idParameter == null ||
                idParameter.isBlank()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/company"
                );

                return;
            }

            try {

                int companyId =
                        Integer.parseInt(
                                idParameter
                        );

                // -------------------------------------------------
                // Get company only if it belongs to this recruiter
                // -------------------------------------------------

                Company company =
                        companyDAO
                        .getCompanyByIdForRecruiter(
                                companyId,
                                recruiterId
                        );

                if (company == null) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/company"
                    );

                    return;
                }

                request.setAttribute(
                        "editCompany",
                        company
                );

                request.setAttribute(
                        "showForm",
                        true
                );

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/company"
                );

                return;
            }
        }

        // =====================================================
        // NEW COMPANY
        // =====================================================

        else if ("new".equals(action)) {

            request.setAttribute(
                    "showForm",
                    true
            );
        }

        // =====================================================
        // DELETE COMPANY
        // =====================================================

        else if ("delete".equals(action)) {

            String idParameter =
                    request.getParameter("id");

            if (idParameter != null &&
                !idParameter.isBlank()) {

                try {

                    int companyId =
                            Integer.parseInt(
                                    idParameter
                            );

                    // ---------------------------------------------
                    // Delete only recruiter's own company
                    // ---------------------------------------------

                    boolean deleted =
                            companyDAO.deleteCompany(
                                    companyId,
                                    recruiterId
                            );

                    if (!deleted) {

                        System.out.println(
                                "Company delete failed."
                        );
                    }

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Invalid company ID."
                    );
                }
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/company"
            );

            return;
        }

        // =====================================================
        // SHOW COMPANIES
        // =====================================================

        List<Company> companies =
                companyDAO
                .getCompaniesByRecruiterId(
                        recruiterId
                );

        request.setAttribute(
                "companies",
                companies
        );

        // =====================================================
        // FORWARD TO JSP
        // =====================================================

        request.getRequestDispatcher(
                "/company.jsp"
        ).forward(
                request,
                response
        );
    }

    // =========================================================
    // POST REQUEST
    // =========================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // -----------------------------------------------------
        // Get existing session
        // -----------------------------------------------------

        HttpSession session =
                request.getSession(false);

        // -----------------------------------------------------
        // Check login
        // -----------------------------------------------------

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // -----------------------------------------------------
        // Check recruiter role
        // -----------------------------------------------------

        String role =
                (String) session.getAttribute("role");

        if (!"RECRUITER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // -----------------------------------------------------
        // Get recruiter ID
        // -----------------------------------------------------

        int recruiterId =
                (Integer) session.getAttribute(
                        "userId"
                );

        // -----------------------------------------------------
        // Read form fields
        // -----------------------------------------------------

        String idParameter =
                request.getParameter("id");

        String companyName =
                request.getParameter(
                        "companyName"
                );

        String description =
                request.getParameter(
                        "description"
                );

        String location =
                request.getParameter(
                        "location"
                );

        String website =
                request.getParameter(
                        "website"
                );

        // =====================================================
        // BASIC VALIDATION
        // =====================================================

        if (companyName == null ||
            companyName.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/company?action=new"
            );

            return;
        }

        if (description == null) {
            description = "";
        }

        if (location == null) {
            location = "";
        }

        if (website == null) {
            website = "";
        }

        // =====================================================
        // CREATE NEW COMPANY
        // =====================================================

        if (idParameter == null ||
            idParameter.isBlank()) {

            Company company =
                    new Company(
                            recruiterId,
                            companyName.trim(),
                            description.trim(),
                            location.trim(),
                            website.trim()
                    );

            boolean added =
                    companyDAO.addCompany(
                            company
                    );

            if (!added) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/company?action=new&error=add"
                );

                return;
            }
        }

        // =====================================================
        // UPDATE EXISTING COMPANY
        // =====================================================

        else {

            try {

                int companyId =
                        Integer.parseInt(
                                idParameter
                        );

                // -------------------------------------------------
                // Verify ownership before updating
                // -------------------------------------------------

                Company existingCompany =
                        companyDAO
                        .getCompanyByIdForRecruiter(
                                companyId,
                                recruiterId
                        );

                if (existingCompany == null) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/company"
                    );

                    return;
                }

                Company company =
                        new Company(
                                companyId,
                                recruiterId,
                                companyName.trim(),
                                description.trim(),
                                location.trim(),
                                website.trim()
                        );

                boolean updated =
                        companyDAO.updateCompany(
                                company
                        );

                if (!updated) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/company?action=edit&id="
                                    + companyId
                                    + "&error=update"
                    );

                    return;
                }

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/company"
                );

                return;
            }
        }

        // =====================================================
        // RETURN TO COMPANY MANAGEMENT
        // =====================================================

        response.sendRedirect(
                request.getContextPath()
                        + "/company"
        );
    }
}