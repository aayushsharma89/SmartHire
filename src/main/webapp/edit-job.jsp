<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.smarthire.model.Job" %>
<%@ page import="com.smarthire.model.User" %>

<%
    User user =
        (User) session.getAttribute("user");

    Job job =
        (Job) request.getAttribute("job");

    if (user == null ||
        !"RECRUITER".equals(user.getRole()) ||
        job == null ||
        user.getId() != job.getRecruiterId()) {

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

    <title>Edit Job</title>

    <style>

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
        }

        .container {
            width: 90%;
            max-width: 800px;
            margin: 40px auto;
        }

        .box {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        .group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 7px;
        }

        input,
        textarea,
        select {
            width: 100%;
            padding: 11px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        textarea {
            min-height: 120px;
            resize: vertical;
        }

        button {
            background: #2563eb;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

    </style>

</head>

<body>

<div class="container">

    <div class="box">

        <h2>
            Edit Job
        </h2>

        <form action="jobs" method="post">

            <input
                type="hidden"
                name="action"
                value="update"
            >

            <input
                type="hidden"
                name="id"
                value="<%= job.getId() %>"
            >

            <input
                type="hidden"
                name="companyId"
                value="<%= job.getCompanyId() %>"
            >

            <div class="group">

                <label>
                    Job Title
                </label>

                <input
                    type="text"
                    name="title"
                    value="<%= job.getTitle() %>"
                    required
                >

            </div>

            <div class="group">

                <label>
                    Description
                </label>

                <textarea
                    name="description"
                    required
                ><%= job.getDescription() %></textarea>

            </div>

            <div class="group">

                <label>
                    Requirements
                </label>

                <textarea
                    name="requirements"
                ><%= job.getRequirements() %></textarea>

            </div>

            <div class="group">

                <label>
                    Location
                </label>

                <input
                    type="text"
                    name="location"
                    value="<%= job.getLocation() %>"
                    required
                >

            </div>

            <div class="group">

                <label>
                    Salary
                </label>

                <input
                    type="text"
                    name="salary"
                    value="<%= job.getSalary() %>"
                >

            </div>

            <div class="group">

                <label>
                    Job Type
                </label>

                <select name="jobType">

                    <option
                        value="FULL_TIME"
                        <%= "FULL_TIME".equals(job.getJobType())
                            ? "selected" : "" %>
                    >
                        Full Time
                    </option>

                    <option
                        value="PART_TIME"
                        <%= "PART_TIME".equals(job.getJobType())
                            ? "selected" : "" %>
                    >
                        Part Time
                    </option>

                    <option
                        value="INTERNSHIP"
                        <%= "INTERNSHIP".equals(job.getJobType())
                            ? "selected" : "" %>
                    >
                        Internship
                    </option>

                    <option
                        value="CONTRACT"
                        <%= "CONTRACT".equals(job.getJobType())
                            ? "selected" : "" %>
                    >
                        Contract
                    </option>

                </select>

            </div>

            <div class="group">

                <label>
                    Skills
                </label>

                <input
                    type="text"
                    name="skills"
                    value="<%= job.getSkills() %>"
                    required
                >

            </div>

            <button type="submit">
                Update Job
            </button>

        </form>

    </div>

</div>

</body>
</html>