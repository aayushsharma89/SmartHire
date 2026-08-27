<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.smarthire.model.User" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>SmartHire - Job Seeker Dashboard</title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background-color: #f4f7fb;
            min-height: 100vh;
        }

        /* ================= NAVBAR ================= */

        .navbar {
            background-color: #1e3a8a;
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
            gap: 25px;
        }

        .nav-links a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        .nav-links a:hover {
            text-decoration: underline;
        }

        /* ================= CONTAINER ================= */

        .container {
            width: 90%;
            max-width: 1100px;
            margin: 40px auto;
        }

        /* ================= WELCOME ================= */

        .welcome-box {
            background-color: white;
            padding: 30px;
            border-radius: 12px;
            margin-bottom: 30px;

            box-shadow:
                0 5px 15px rgba(0, 0, 0, 0.08);
        }

        .welcome-box h2 {
            margin-bottom: 10px;
            color: #222;
        }

        .welcome-box p {
            color: #666;
        }

        /* ================= CARDS ================= */

        .cards {
            display: grid;

            grid-template-columns:
                repeat(auto-fit, minmax(220px, 1fr));

            gap: 20px;
        }

        .card {
            background-color: white;
            padding: 25px;
            border-radius: 12px;

            box-shadow:
                0 5px 15px rgba(0, 0, 0, 0.08);

            transition: transform 0.2s;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card h3 {
            margin-bottom: 12px;
            color: #1e3a8a;
        }

        .card p {
            color: #666;
            line-height: 1.5;
            margin-bottom: 20px;
        }

        .card a {
            display: inline-block;

            background-color: #2563eb;
            color: white;

            text-decoration: none;

            padding: 10px 18px;

            border-radius: 6px;
        }

        .card a:hover {
            background-color: #1d4ed8;
        }

        /* ================= FOOTER ================= */

        footer {
            text-align: center;
            padding: 25px;
            margin-top: 50px;
            color: #777;
        }

    </style>

</head>

<body>

<%
    /*
     * Get the logged-in user from the session.
     */
    User user = (User) session.getAttribute("user");

    /*
     * If no user is logged in,
     * send the user back to login page.
     */
    if (user == null) {

        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );

        return;
    }

    /*
     * Only JOB_SEEKER can access this page.
     */
    if (!"JOB_SEEKER".equals(user.getRole())) {

        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );

        return;
    }
%>

<!-- ================= NAVBAR ================= -->

<div class="navbar">

    <div class="logo">
        SmartHire
    </div>

    <div class="nav-links">

        <a href="jobseeker-dashboard.jsp">
            Dashboard
        </a>

        <a href="jobs">
            Jobs
        </a>

        <a href="application">
            Applications
        </a>

        <a href="resume">
            Resume
        </a>

        <a href="logout">
            Logout
        </a>

    </div>

</div>


<!-- ================= MAIN CONTAINER ================= -->

<div class="container">

    <!-- Welcome section -->

    <div class="welcome-box">

        <h2>
            Welcome,
            <%= user.getName() %>!
        </h2>

        <p>
            Find your next career opportunity with SmartHire.
        </p>

    </div>


    <!-- Dashboard cards -->

    <div class="cards">


        <!-- Browse Jobs -->

        <div class="card">

            <h3>
                Browse Jobs
            </h3>

            <p>
                Search and explore available
                job opportunities posted by recruiters.
            </p>

            <a href="jobs">
                View Jobs
            </a>

        </div>


        <!-- Applications -->

        <div class="card">

            <h3>
                My Applications
            </h3>

            <p>
                View and track all your
                submitted job applications.
            </p>

            <a href="application">
                View Applications
            </a>

        </div>


        <!-- Resume -->

        <div class="card">

            <h3>
                My Resume
            </h3>

            <p>
                Upload and manage your resume
                for job applications.
            </p>

            <a href="resume">
                Manage Resume
            </a>

        </div>


        <!-- Profile -->

        <div class="card">

            <h3>
                My Profile
            </h3>

            <p>
                View your SmartHire account
                information.
            </p>

            <a href="profile">
                View Profile
            </a>

        </div>

    </div>

</div>


<!-- ================= FOOTER ================= -->

<footer>

    <p>
        &copy; 2026 SmartHire.
        All Rights Reserved.
    </p>

</footer>

</body>
</html>