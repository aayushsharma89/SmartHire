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
            request.getAttribute(
                    "companies"
            );

    if (companies == null ||
        companies.isEmpty()) {

        response.sendRedirect(
                request.getContextPath()
                        + "/company?action=new"
        );

        return;
    }
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>
        SmartHire - Post Job
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
            margin-left: 20px;
        }

        .container {
            width: 90%;
            max-width: 800px;
            margin: 40px auto;
        }

        .form-box {
            background: white;
            padding: 35px;
            border-radius: 12px;

            box-shadow:
                0 5px 15px rgba(0,0,0,0.08);
        }

        h2 {
            margin-bottom: 10px;
        }

        .info {
            color: #666;
            margin-bottom: 25px;
        }

        .group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 7px;
            font-weight: bold;
        }

        input,
        textarea,
        select {
            width: 100%;
            padding: 11px;

            border: 1px solid #ccc;
            border-radius: 6px;

            font-size: 14px;
        }

        textarea {
            min-height: 120px;
            resize: vertical;
        }

        .row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        .btn {
            width: 100%;
            padding: 13px;

            background: #2563eb;
            color: white;

            border: none;
            border-radius: 6px;

            cursor: pointer;
            font-size: 16px;
        }

        .btn:hover {
            background: #1d4ed8;
        }

        @media(max-width: 650px) {

            .row {
                grid-template-columns: 1fr;
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

        <a href="company">
            Companies
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

    <div class="form-box">

        <h2>
            Post a New Job
        </h2>

        <p class="info">
            Create a job opportunity for candidates.
        </p>


        <!-- =================================================
             COMPANY SELECTION
             ================================================= -->

        <div class="group">

            <label for="companyId">
                Select Company
            </label>

        </div>


        <form
            action="jobs"
            method="post"
        >

            <div class="group">

                <label for="companyId">
                    Company
                </label>

                <select
                    id="companyId"
                    name="companyId"
                    required
                >

                    <option value="">
                        Select Company
                    </option>

                    <% for (Company company :
                            companies) { %>

                        <option
                            value="<%= company.getId() %>"
                        >
                            <%= company.getCompanyName() %>
                            -
                            <%= company.getLocation() %>
                        </option>

                    <% } %>

                </select>

            </div>


            <!-- JOB TITLE -->

            <div class="group">

                <label for="title">
                    Job Title
                </label>

                <input
                    type="text"
                    id="title"
                    name="title"
                    placeholder="Java Full Stack Developer"
                    required
                >

            </div>


            <!-- DESCRIPTION -->

            <div class="group">

                <label for="description">
                    Job Description
                </label>

                <textarea
                    id="description"
                    name="description"
                    placeholder="Describe the job responsibilities..."
                    required
                ></textarea>

            </div>


            <!-- REQUIREMENTS -->

            <div class="group">

                <label for="requirements">
                    Requirements
                </label>

                <textarea
                    id="requirements"
                    name="requirements"
                    placeholder="Enter qualification and experience requirements..."
                ></textarea>

            </div>


            <!-- LOCATION / SALARY -->

            <div class="row">

                <div class="group">

                    <label for="location">
                        Location
                    </label>

                    <input
                        type="text"
                        id="location"
                        name="location"
                        placeholder="Noida"
                        required
                    >

                </div>


                <div class="group">

                    <label for="salary">
                        Salary
                    </label>

                    <input
                        type="text"
                        id="salary"
                        name="salary"
                        placeholder="5-8 LPA"
                    >

                </div>

            </div>


            <!-- JOB TYPE / SKILLS -->

            <div class="row">

                <div class="group">

                    <label for="jobType">
                        Job Type
                    </label>

                    <select
                        id="jobType"
                        name="jobType"
                        required
                    >

                        <option value="">
                            Select Job Type
                        </option>

                        <option value="FULL_TIME">
                            Full Time
                        </option>

                        <option value="PART_TIME">
                            Part Time
                        </option>

                        <option value="INTERNSHIP">
                            Internship
                        </option>

                        <option value="CONTRACT">
                            Contract
                        </option>

                    </select>

                </div>


                <div class="group">

                    <label for="skills">
                        Required Skills
                    </label>

                    <input
                        type="text"
                        id="skills"
                        name="skills"
                        placeholder="Java, JSP, JDBC, MySQL"
                        required
                    >

                </div>

            </div>


            <button
                type="submit"
                class="btn"
            >
                Post Job
            </button>

        </form>

    </div>

</div>

</body>

</html>