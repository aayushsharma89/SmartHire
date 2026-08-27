<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.User" %>

<%
    User admin =
            (User) session.getAttribute("user");

    if (admin == null ||
        !"ADMIN".equals(admin.getRole())) {

        response.sendRedirect(
                request.getContextPath() +
                "/login.jsp"
        );

        return;
    }

    List<User> users =
            (List<User>) request.getAttribute("users");
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>SmartHire - Manage Users</title>

    <style>

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
        }

        .navbar {
            background: #1e3a8a;
            color: white;
            padding: 18px 40px;
            display: flex;
            justify-content: space-between;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
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

        .delete {
            background: #dc2626;
            color: white;
            padding: 8px 12px;
            text-decoration: none;
            border-radius: 5px;
        }

        .delete:hover {
            background: #b91c1c;
        }

    </style>

</head>

<body>

<div class="navbar">

    <strong>
        SmartHire Admin
    </strong>

    <div>

        <a href="<%= request.getContextPath() %>/admin">
            Dashboard
        </a>

        <a href="<%= request.getContextPath() %>/logout">
            Logout
        </a>

    </div>

</div>

<div class="container">

    <div class="box">

        <h2>
            Manage Users
        </h2>

        <table>

            <thead>

                <tr>

                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Action</th>

                </tr>

            </thead>

            <tbody>

                <% if (users != null) { %>

                    <% for (User user : users) { %>

                        <tr>

                            <td>
                                <%= user.getId() %>
                            </td>

                            <td>
                                <%= user.getName() %>
                            </td>

                            <td>
                                <%= user.getEmail() %>
                            </td>

                            <td>
                                <%= user.getRole() %>
                            </td>

                            <td>

                                <% if (user.getId()
                                        != admin.getId()) { %>

                                    <a
                                        class="delete"
                                        href="admin?action=users&delete=<%= user.getId() %>"
                                        onclick="return confirm('Delete this user?');"
                                    >
                                        Delete
                                    </a>

                                <% } else { %>

                                    Current Admin

                                <% } %>

                            </td>

                        </tr>

                    <% } %>

                <% } %>

            </tbody>

        </table>

    </div>

</div>

</body>

</html>