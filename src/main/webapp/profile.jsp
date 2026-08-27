<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.smarthire.model.User" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>SmartHire - My Profile</title>

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
            width: 90%;
            max-width: 650px;
            margin: 40px auto;
        }

        .box {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        .group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 7px;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 11px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        input[readonly] {
            background: #f1f5f9;
        }

        button {
            background: #2563eb;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 6px;
            cursor: pointer;
        }

        .success {
            background: #dcfce7;
            color: #166534;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 6px;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 6px;
        }

    </style>

</head>

<body>

<%
    User user =
            (User) request.getAttribute(
                    "profileUser"
            );

    if (user == null) {

        response.sendRedirect(
                request.getContextPath()
                        + "/login.jsp"
        );

        return;
    }

    String success =
            request.getParameter("success");

    String error =
            request.getParameter("error");
%>

<div class="navbar">

    <strong>SmartHire</strong>

    <div>

        <a href="jobseeker-dashboard.jsp">
            Dashboard
        </a>

        <a href="resume">
            Resume
        </a>

        <a href="logout">
            Logout
        </a>

    </div>

</div>

<div class="container">

    <div class="box">

        <h2>
            My Profile
        </h2>

        <% if ("1".equals(success)) { %>

            <div class="success">
                Profile updated successfully.
            </div>

        <% } %>

        <% if ("1".equals(error)) { %>

            <div class="error">
                Profile update failed.
            </div>

        <% } %>

        <form action="profile" method="post">

            <div class="group">

                <label>
                    Full Name
                </label>

                <input
                        type="text"
                        name="name"
                        value="<%= user.getName() %>"
                        required
                >

            </div>

            <div class="group">

                <label>
                    Email
                </label>

                <input
                        type="email"
                        name="email"
                        value="<%= user.getEmail() %>"
                        required
                >

            </div>

            <div class="group">

                <label>
                    Account Role
                </label>

                <input
                        type="text"
                        value="<%= user.getRole() %>"
                        readonly
                >

            </div>

            <button type="submit">
                Update Profile
            </button>

        </form>

    </div>

</div>

</body>
</html>