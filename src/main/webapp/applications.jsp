<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.Application" %>
<%@ page import="com.smarthire.model.User" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>SmartHire - My Applications</title>

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
        }

        .logo {
            font-size: 25px;
            font-weight: bold;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        .container {
            width: 90%;
            max-width: 1000px;
            margin: 40px auto;
        }

        .box {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th,
        td {
            padding: 14px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #f1f5f9;
        }

        .status {
            font-weight: bold;
        }

        .empty {
            text-align: center;
            color: #666;
            padding: 30px;
        }

    </style>

</head>

<body>

<%
    User user = (User) session.getAttribute("user");

    if (user == null ||
        !"JOB_SEEKER".equals(user.getRole())) {

        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );

        return;
    }

    List<Application> applications =
        (List<Application>) request.getAttribute("applications");
%>

<div class="navbar">

    <div class="logo">
        SmartHire
    </div>

    <div>

        <a href="jobseeker-dashboard.jsp">
            Dashboard
        </a>

        &nbsp;&nbsp;

        <a href="logout">
            Logout
        </a>

    </div>

</div>

<div class="container">

    <div class="box">

        <h2>
            My Applications
        </h2>

        <% if (applications == null ||
               applications.isEmpty()) { %>

            <div class="empty">

                You have not applied for any jobs yet.

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
                            Resume ID
                        </th>

                        <th>
                            Status
                        </th>

                    </tr>

                </thead>

                <tbody>

				    <% for (Application app : applications) { %>
				
				        <tr>
				
				            <td>
				                <%= app.getId() %>
				            </td>
				
				            <td>
				                <%= app.getJobId() %>
				            </td>
				
				            <td>
				                <%= app.getResumeId() > 0
				                    ? app.getResumeId()
				                    : "Not selected" %>
				            </td>
				
				            <td class="status">
				                <%= app.getStatus() %>
				            </td>
				
				        </tr>
				
				    <% } %>
				
				</tbody>
				
            </table>

        <% } %>

    </div>

</div>

</body>
</html>