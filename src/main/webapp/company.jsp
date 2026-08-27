<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.User" %>
<%@ page import="com.smarthire.model.Company" %>

<%
    User user =
            (User) session.getAttribute("user");

    if (user == null ||
        !"RECRUITER".equals(user.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                        + "/login.jsp"
        );

        return;
    }

    List<Company> companies =
            (List<Company>)
            request.getAttribute("companies");

    Company editCompany =
            (Company)
            request.getAttribute("editCompany");

    Boolean showFormAttribute =
            (Boolean)
            request.getAttribute("showForm");

    boolean showForm =
            Boolean.TRUE.equals(
                    showFormAttribute
            );

    boolean editing =
            editCompany != null;
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>
        SmartHire - Manage Companies
    </title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f4f7fb;
            min-height: 100vh;
        }

        .navbar {
            background: #1e3a8a;
            color: white;
            padding: 18px 40px;

            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 25px;
            font-weight: bold;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            font-weight: bold;
        }

        .container {
            width: 92%;
            max-width: 1100px;
            margin: 40px auto;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .header h2 {
            color: #222;
        }

        .add-btn {
            background: #2563eb;
            color: white;
            padding: 11px 18px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
        }

        .add-btn:hover {
            background: #1d4ed8;
        }

        .form-box {
            background: white;
            padding: 30px;
            border-radius: 12px;
            margin-bottom: 30px;

            box-shadow:
                0 5px 15px rgba(0, 0, 0, 0.08);
        }

        .form-box h2 {
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 7px;
            font-weight: bold;
        }

        input,
        textarea {
            width: 100%;
            padding: 11px;

            border: 1px solid #ccc;
            border-radius: 6px;

            font-size: 14px;
        }

        textarea {
            min-height: 110px;
            resize: vertical;
        }

        .submit-btn {
            background: #2563eb;
            color: white;
            border: none;

            padding: 11px 18px;
            border-radius: 6px;

            cursor: pointer;
            font-size: 15px;
        }

        .cancel-btn {
            display: inline-block;
            margin-left: 10px;

            padding: 10px 16px;

            background: #64748b;
            color: white;

            text-decoration: none;
            border-radius: 6px;
        }

        .company-grid {
            display: grid;

            grid-template-columns:
                repeat(auto-fit, minmax(300px, 1fr));

            gap: 20px;
        }

        .company-card {
            background: white;
            padding: 25px;
            border-radius: 12px;

            box-shadow:
                0 5px 15px rgba(0, 0, 0, 0.08);
        }

        .company-card h3 {
            color: #1e3a8a;
            margin-bottom: 15px;
        }

        .company-card p {
            color: #666;
            margin: 8px 0;
            line-height: 1.5;
        }

        .actions {
            margin-top: 20px;
        }

        .btn {
            display: inline-block;

            padding: 9px 14px;
            border-radius: 6px;

            text-decoration: none;
            color: white;

            margin-right: 8px;
        }

        .edit-btn {
            background: #2563eb;
        }

        .delete-btn {
            background: #dc2626;
        }

        .empty {
            background: white;
            padding: 35px;

            text-align: center;

            border-radius: 12px;

            color: #666;

            box-shadow:
                0 5px 15px rgba(0, 0, 0, 0.08);
        }

        .error {
            background: #fee2e2;
            color: #991b1b;

            padding: 12px;
            border-radius: 6px;

            margin-bottom: 20px;
        }

        @media (max-width: 650px) {

            .navbar {
                padding: 18px 20px;
            }

            .header {
                flex-direction: column;
                align-items: flex-start;
                gap: 15px;
            }

        }

    </style>

</head>

<body>

<div class="navbar">

    <div class="logo">
        SmartHire
    </div>

    <div>

        <a href="recruiter-dashboard.jsp">
            Dashboard
        </a>

        <a href="jobs?action=myJobs">
            My Jobs
        </a>

        <a href="logout">
            Logout
        </a>

    </div>

</div>


<div class="container">

    <!-- =====================================================
         ADD / EDIT COMPANY FORM
         ===================================================== -->

    <% if (showForm) { %>

        <div class="form-box">

            <h2>
                <%= editing
                        ? "Edit Company"
                        : "Add New Company" %>
            </h2>

            <%
                String error =
                        request.getParameter("error");

                if ("add".equals(error)) {
            %>

                <div class="error">
                    Company could not be added.
                </div>

            <%
                }

                if ("update".equals(error)) {
            %>

                <div class="error">
                    Company could not be updated.
                </div>

            <%
                }
            %>


            <form
                action="company"
                method="post"
            >

                <% if (editing) { %>

                    <input
                        type="hidden"
                        name="id"
                        value="<%= editCompany.getId() %>"
                    >

                <% } %>


                <div class="form-group">

                    <label for="companyName">
                        Company Name
                    </label>

                    <input
                        type="text"
                        id="companyName"
                        name="companyName"
                        value="<%= editing
                                ? editCompany.getCompanyName()
                                : "" %>"
                        placeholder="Enter company name"
                        required
                    >

                </div>


                <div class="form-group">

                    <label for="description">
                        Description
                    </label>

                    <textarea
                        id="description"
                        name="description"
                        placeholder="Describe your company"
                        required
                    ><%= editing
                            ? editCompany.getDescription()
                            : "" %></textarea>

                </div>


                <div class="form-group">

                    <label for="location">
                        Location
                    </label>

                    <input
                        type="text"
                        id="location"
                        name="location"
                        value="<%= editing
                                ? editCompany.getLocation()
                                : "" %>"
                        placeholder="Company location"
                        required
                    >

                </div>


                <div class="form-group">

                    <label for="website">
                        Website
                    </label>

                    <input
                        type="url"
                        id="website"
                        name="website"
                        value="<%= editing
                                ? editCompany.getWebsite()
                                : "" %>"
                        placeholder="https://example.com"
                    >

                </div>


                <button
                    type="submit"
                    class="submit-btn"
                >
                    <%= editing
                            ? "Update Company"
                            : "Create Company" %>
                </button>


                <a
                    href="company"
                    class="cancel-btn"
                >
                    Cancel
                </a>

            </form>

        </div>

    <% } %>


    <!-- =====================================================
         HEADER
         ===================================================== -->

    <div class="header">

        <h2>
            My Companies
        </h2>

        <a
            href="company?action=new"
            class="add-btn"
        >
            + Add New Company
        </a>

    </div>


    <!-- =====================================================
         COMPANY LIST
         ===================================================== -->

    <% if (companies == null ||
           companies.isEmpty()) { %>

        <div class="empty">

            <h3>
                No Companies Found
            </h3>

            <p style="margin-top: 10px;">
                You haven't created a company yet.
            </p>

        </div>

    <% } else { %>

        <div class="company-grid">

            <% for (Company company : companies) { %>

                <div class="company-card">

                    <h3>
                        <%= company.getCompanyName() %>
                    </h3>


                    <p>

                        <strong>
                            Location:
                        </strong>

                        <%= company.getLocation() %>

                    </p>


                    <p>

                        <strong>
                            Description:
                        </strong>

                        <br>

                        <%= company.getDescription() %>

                    </p>


                    <% if (company.getWebsite() != null &&
                           !company.getWebsite().isBlank()) { %>

                        <p>

                            <strong>
                                Website:
                            </strong>

                            <%= company.getWebsite() %>

                        </p>

                    <% } %>


                    <div class="actions">

                        <a
                            href="company?action=edit&id=<%= company.getId() %>"
                            class="btn edit-btn"
                        >
                            Edit
                        </a>


                        <a
                            href="company?action=delete&id=<%= company.getId() %>"
                            class="btn delete-btn"

                            onclick="return confirm(
                                'Are you sure you want to delete this company?'
                            );"
                        >
                            Delete
                        </a>

                    </div>

                </div>

            <% } %>

        </div>

    <% } %>

</div>

</body>

</html>