<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.smarthire.model.User" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>SmartHire - Admin Dashboard</title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f4f7fb;
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
        }

        .container {
            width: 92%;
            max-width: 1200px;
            margin: 40px auto;
        }

        .welcome {
            background: white;
            padding: 30px;
            border-radius: 12px;
            margin-bottom: 30px;

            box-shadow:
                0 5px 15px rgba(0,0,0,0.08);
        }

        .welcome h2 {
            margin-bottom: 8px;
        }

        .welcome p {
            color: #666;
        }

        .stats {
            display: grid;

            grid-template-columns:
                repeat(auto-fit, minmax(200px, 1fr));

            gap: 20px;

            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 12px;

            box-shadow:
                0 5px 15px rgba(0,0,0,0.08);
        }

        .stat-card h3 {
            color: #555;
            font-size: 16px;
            margin-bottom: 12px;
        }

        .stat-number {
            font-size: 32px;
            font-weight: bold;
            color: #1e3a8a;
        }

        .menu {
            display: grid;

            grid-template-columns:
                repeat(auto-fit, minmax(230px, 1fr));

            gap: 20px;
        }

        .menu-card {
            background: white;
            padding: 25px;
            border-radius: 12px;

            box-shadow:
                0 5px 15px rgba(0,0,0,0.08);
        }

        .menu-card h3 {
            color: #1e3a8a;
            margin-bottom: 10px;
        }

        .menu-card p {
            color: #666;
            line-height: 1.5;
            margin-bottom: 18px;
        }

        .btn {
            display: inline-block;

            background: #2563eb;
            color: white;

            text-decoration: none;

            padding: 10px 16px;

            border-radius: 6px;
        }

        .btn:hover {
            background: #1d4ed8;
        }

        footer {
            text-align: center;
            padding: 30px;
            color: #777;
        }
        
        .menu-card a {
				    display: inline-block;
				    background: #2563eb;
				    color: white;
				    text-decoration: none;
				    padding: 10px 16px;
				    border-radius: 6px;
				    font-weight: normal;
				}
				
				.menu-card a:hover {
				    background: #1d4ed8;
				}

    </style>

</head>

<body>

<%
    User user =
            (User) session.getAttribute("user");

    if (user == null ||
        !"ADMIN".equals(user.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                        + "/login.jsp"
        );

        return;
    }

    Integer totalUsers =
            (Integer) request.getAttribute(
                    "totalUsers"
            );

    Integer totalJobSeekers =
            (Integer) request.getAttribute(
                    "totalJobSeekers"
            );

    Integer totalRecruiters =
            (Integer) request.getAttribute(
                    "totalRecruiters"
            );

    Integer totalCompanies =
            (Integer) request.getAttribute(
                    "totalCompanies"
            );

    Integer totalJobs =
            (Integer) request.getAttribute(
                    "totalJobs"
            );

    Integer totalApplications =
            (Integer) request.getAttribute(
                    "totalApplications"
            );
%>

<div class="navbar">

    <div class="logo">
        SmartHire Admin
    </div>

    <div>

        <a href="admin">
            Dashboard
        </a>

        <a href="logout">
            Logout
        </a>

    </div>

</div>

<div class="container">

    <div class="welcome">

        <h2>
            Welcome, <%= user.getName() %>!
        </h2>

        <p>
            Manage and monitor the SmartHire
            recruitment platform.
        </p>

    </div>


    <!-- ================= STATISTICS ================= -->

    <div class="stats">

        <div class="stat-card">

            <h3>
                Total Users
            </h3>

            <div class="stat-number">
                <%= totalUsers != null
                    ? totalUsers
                    : 0 %>
            </div>

        </div>


        <div class="stat-card">

            <h3>
                Job Seekers
            </h3>

            <div class="stat-number">
                <%= totalJobSeekers != null
                    ? totalJobSeekers
                    : 0 %>
            </div>

        </div>


        <div class="stat-card">

            <h3>
                Recruiters
            </h3>

            <div class="stat-number">
                <%= totalRecruiters != null
                    ? totalRecruiters
                    : 0 %>
            </div>

        </div>


        <div class="stat-card">

            <h3>
                Companies
            </h3>

            <div class="stat-number">
                <%= totalCompanies != null
                    ? totalCompanies
                    : 0 %>
            </div>

        </div>


        <div class="stat-card">

            <h3>
                Jobs
            </h3>

            <div class="stat-number">
                <%= totalJobs != null
                    ? totalJobs
                    : 0 %>
            </div>

        </div>


        <div class="stat-card">

            <h3>
                Applications
            </h3>

            <div class="stat-number">
                <%= totalApplications != null
                    ? totalApplications
                    : 0 %>
            </div>

        </div>

    </div>


    <!-- ================= MANAGEMENT ================= -->

    <div class="menu">

    <div class="menu-card">

        <h3>
            Manage Users
        </h3>

        <p>
            View and manage Job Seeker
            and Recruiter accounts.
        </p>

        <a href="admin?action=users">
            Manage Users
        </a>

    </div>


    <div class="menu-card">

        <h3>
            Manage Companies
        </h3>

        <p>
            View and manage companies
            registered on SmartHire.
        </p>

        <a href="admin?action=companies">
            Manage Companies
        </a>

    </div>


    <div class="menu-card">

        <h3>
            Manage Jobs
        </h3>

        <p>
            Review and manage job
            postings.
        </p>

        <a href="jobs">
            Manage Jobs
        </a>

    </div>


    <div class="menu-card">

        <h3>
            Applications
        </h3>

        <p>
            Monitor applications submitted
            by candidates.
        </p>

        <a href="admin?action=applications">
            View Applications
        </a>

    </div>

</div>
<footer>

    &copy; 2026 SmartHire.
    All Rights Reserved.

</footer>

</body>

</html>