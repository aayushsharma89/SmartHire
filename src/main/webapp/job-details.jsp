<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.smarthire.model.Job" %>
<%@ page import="com.smarthire.model.User" %>

<%
    User user =
        (User) session.getAttribute("user");

    Job job =
        (Job) request.getAttribute("job");

    if (user == null || job == null) {

        response.sendRedirect(
            request.getContextPath() +
            "/login.jsp"
        );

        return;
    }
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>SmartHire - Job Details</title>

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
            margin-left: 18px;
        }

        .container {
            width: 90%;
            max-width: 850px;
            margin: 40px auto;
        }

        .card {
            background: white;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        h1 {
            color: #1e3a8a;
        }

        .info {
            margin: 10px 0;
            color: #555;
        }

        .section {
            margin-top: 25px;
        }

        .btn {
            background: #2563eb;
            color: white;
            padding: 11px 18px;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            cursor: pointer;
        }

    </style>

</head>

<body>

<div class="navbar">

    <strong>SmartHire</strong>

    <div>

        <a href="jobs">
            Jobs
        </a>

        <a href="logout">
            Logout
        </a>

    </div>

</div>

<div class="container">

    <div class="card">

        <h1>
            <%= job.getTitle() %>
        </h1>

        <div class="info">
            <strong>Location:</strong>
            <%= job.getLocation() %>
        </div>

        <div class="info">
            <strong>Salary:</strong>
            <%= job.getSalary() %>
        </div>

        <div class="info">
            <strong>Job Type:</strong>
            <%= job.getJobType() %>
        </div>

        <div class="info">
            <strong>Skills:</strong>
            <%= job.getSkills() %>
        </div>

        <div class="section">

            <h3>
                Description
            </h3>

            <p>
                <%= job.getDescription() %>
            </p>

        </div>

        <div class="section">

            <h3>
                Requirements
            </h3>

            <p>
                <%= job.getRequirements() %>
            </p>

        </div>

        <% if ("JOB_SEEKER".equals(user.getRole())) { %>

            <div class="section">

                <form
                    action="application"
                    method="post"
                >

                    <input
                        type="hidden"
                        name="action"
                        value="apply"
                    >

                    <input
                        type="hidden"
                        name="jobId"
                        value="<%= job.getId() %>"
                    >

                    <button
                        type="submit"
                        class="btn"
                    >
                        Apply Now
                    </button>

                </form>

            </div>

        <% } %>

        <% if ("RECRUITER".equals(user.getRole()) &&
               user.getId() == job.getRecruiterId()) { %>

            <div class="section">

                <a
                    href="jobs?action=edit&id=<%= job.getId() %>"
                    class="btn"
                >
                    Edit Job
                </a>

            </div>

        <% } %>

    </div>

</div>

</body>
</html>