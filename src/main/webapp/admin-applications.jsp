<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.Application" %>

<%
    Object role = session.getAttribute("role");

    if (!"ADMIN".equals(role)) {

        response.sendRedirect(
                request.getContextPath()
                        + "/login.jsp"
        );

        return;
    }

    List<Application> applications =
            (List<Application>) request.getAttribute(
                    "applications"
            );
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>SmartHire - Manage Applications</title>

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

        .nav-links {
            display: flex;
            gap: 20px;
        }

        .nav-links a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        .nav-links a:hover {
            text-decoration: underline;
        }

        .container {
            width: 92%;
            max-width: 1100px;
            margin: 40px auto;
        }

        .box {
            background: white;
            padding: 30px;
            border-radius: 12px;

            box-shadow:
                0 5px 15px rgba(0, 0, 0, 0.08);
        }

        .box h2 {
            color: #222;
            margin-bottom: 10px;
        }

        .description {
            color: #666;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th,
        td {
            padding: 14px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #f1f5f9;
            color: #333;
        }

        tr:hover {
            background: #f8fafc;
        }

        .status {
            font-weight: bold;
        }

        .delete-btn {
            display: inline-block;

            background: #dc2626;
            color: white;

            padding: 8px 12px;

            border-radius: 5px;

            text-decoration: none;
        }

        .delete-btn:hover {
            background: #b91c1c;
        }

        .back-btn {
            display: inline-block;

            margin-top: 20px;

            background: #2563eb;
            color: white;

            padding: 10px 16px;

            border-radius: 6px;

            text-decoration: none;
        }

        .back-btn:hover {
            background: #1d4ed8;
        }

        .empty {
            padding: 30px;
            text-align: center;
            color: #666;
            background: #f8fafc;
            border-radius: 8px;
        }

        @media (max-width: 800px) {

            .container {
                width: 95%;
            }

            .box {
                padding: 20px;
                overflow-x: auto;
            }

            table {
                min-width: 800px;
            }

        }

    </style>

</head>

<body>

<!-- ================= NAVBAR ================= -->

<div class="navbar">

    <div class="logo">
        SmartHire Admin
    </div>

    <div class="nav-links">

        <a href="<%= request.getContextPath() %>/admin">
            Dashboard
        </a>

        <a href="<%= request.getContextPath() %>/logout">
            Logout
        </a>

    </div>

</div>


<!-- ================= MAIN CONTENT ================= -->

<div class="container">

    <div class="box">

        <h2>
            Manage Applications
        </h2>

        <p class="description">
            View and manage all job applications submitted
            by candidates.
        </p>


        <% if (applications == null ||
               applications.isEmpty()) { %>

            <div class="empty">

                <h3>
                    No Applications Found
                </h3>

                <p style="margin-top: 8px;">
                    No job applications are currently
                    available.
                </p>

            </div>

        <% } else { %>


            <table>

                <thead>

                    <tr>

                        <th>
                            Application ID
                        </th>

                        <th>
                            Job ID
                        </th>

                        <th>
                            Applicant ID
                        </th>

                        <th>
                            Resume ID
                        </th>

                        <th>
                            Status
                        </th>

                        <th>
                            Action
                        </th>

                    </tr>

                </thead>


                <tbody>

                    <% for (Application app :
                            applications) { %>

                        <tr>

                            <td>
                                <%= app.getId() %>
                            </td>

                            <td>
                                <%= app.getJobId() %>
                            </td>

                            <td>
                                <%= app.getApplicantId() %>
                            </td>

                            <td>
                                <%= app.getResumeId() > 0
                                    ? app.getResumeId()
                                    : "None" %>
                            </td>

                            <td class="status">
                                <%= app.getStatus() %>
                            </td>

                            <td>

                                <a
                                    href="<%= request.getContextPath() %>/admin?action=applications&delete=<%= app.getId() %>"
                                    class="delete-btn"
                                    onclick="return confirm('Are you sure you want to delete this application?');"
                                >
                                    Delete
                                </a>

                            </td>

                        </tr>

                    <% } %>

                </tbody>

            </table>


        <% } %>


        <a
            href="<%= request.getContextPath() %>/admin"
            class="back-btn"
        >
            ← Back to Dashboard
        </a>

    </div>

</div>

</body>

</html>