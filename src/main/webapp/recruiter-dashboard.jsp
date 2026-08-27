<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.smarthire.model.User" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>SmartHire - Recruiter Dashboard</title>

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

        /* ================= MAIN ================= */

        .container {
            width: 90%;
            max-width: 1100px;
            margin: 40px auto;
        }

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
     * Get the logged-in user.
     */
    User user = (User) session.getAttribute("user");

    /*
     * If no user is logged in,
     * redirect to login page.
     */
    if (user == null) {

        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );

        return;
    }

    /*
     * Only RECRUITER can access this dashboard.
     */
    if (!"RECRUITER".equals(user.getRole())) {

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

        <a href="recruiter-dashboard.jsp">
            Dashboard
        </a>

        <a href="company">
            Company
        </a>

        <a href="jobs">
            Jobs
        </a>

        <a href="logout">
            Logout
        </a>

    </div>

</div>


<!-- ================= MAIN CONTAINER ================= -->

<div class="container">

    <!-- Welcome -->

    <div class="welcome-box">

        <h2>
            Welcome,
            <%= user.getName() %>!
        </h2>

        <p>
            Manage your company, job postings,
            and applicants from your dashboard.
        </p>

    </div>


    <!-- Dashboard cards -->

    <div class="cards">


        <!-- Company Profile -->

        <div class="card">

            <h3>
                Company Profile
            </h3>

            <p>
                Create or update your company
                information and profile.
            </p>

            <a href="company">
                Manage Company
            </a>

        </div>


        <!-- Post Job -->

        <div class="card">

            <h3>
                Post a Job
            </h3>

            <p>
                Create a new job posting
                for potential candidates.
            </p>

            <a href="jobs?action=post">
                Post Job
            </a>

        </div>


        <!-- My Jobs -->

        <div class="card">

            <h3>
                My Jobs
            </h3>

            <p>
                View and manage the jobs
                you have posted.
            </p>

            <a href="jobs">
                View Jobs
            </a>

        </div>


        <!-- Applicants -->

        <div class="card">

            <h3>
                Applicants
            </h3>

            <p>
                Review candidates who have
                applied to your job postings.
            </p>

            <a href="<%= request.getContextPath() %>/application?action=all" class="btn">
			    View Applicants
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