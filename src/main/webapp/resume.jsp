<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.smarthire.model.User" %>
<%@ page import="com.smarthire.model.Resume" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>SmartHire - My Resume</title>

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

        .navbar a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
        }

        .container {
            width: 90%;
            max-width: 700px;
            margin: 40px auto;
        }

        .box {
            background: white;
            padding: 30px;
            border-radius: 12px;

            box-shadow:
                0 5px 15px rgba(0,0,0,0.08);
        }

        h2 {
            margin-bottom: 10px;
        }

        .description {
            color: #666;
            margin-bottom: 25px;
        }

        .resume-info {
            background: #f8fafc;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 25px;
        }

        .resume-info p {
            margin-bottom: 10px;
            color: #444;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }

        input[type="file"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .btn {
            background: #2563eb;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 15px;
        }

        .btn:hover {
            background: #1d4ed8;
        }

        .success {
            background: #dcfce7;
            color: #166534;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .note {
            color: #666;
            font-size: 13px;
            margin-top: 10px;
        }

    </style>

</head>

<body>

<%
    User user =
            (User) session.getAttribute("user");

    if (user == null ||
        !"JOB_SEEKER".equals(user.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                        + "/login.jsp"
        );

        return;
    }

    Resume resume =
            (Resume) request.getAttribute("resume");

    String error =
            request.getParameter("error");
%>

<div class="navbar">

    <div class="logo">
        SmartHire
    </div>

    <div>

        <a href="jobseeker-dashboard.jsp">
            Dashboard
        </a>

        <a href="jobs">
            Jobs
        </a>

        <a href="logout">
            Logout
        </a>

    </div>

</div>

<div class="container">

    <div class="box">

        <h2>
            My Resume
        </h2>

        <p class="description">
            Upload your latest resume for job applications.
        </p>

        <% if ("nofile".equals(error)) { %>

            <div class="error">
                Please select a resume file.
            </div>

        <% } %>

        <% if ("type".equals(error)) { %>

            <div class="error">
                Only PDF, DOC and DOCX files are allowed.
            </div>

        <% } %>

        <% if ("database".equals(error)) { %>

            <div class="error">
                Resume information could not be saved.
            </div>

        <% } %>

        <% if (resume != null) { %>

            <div class="resume-info">

                <p>
                    <strong>Current Resume:</strong>
                    <%= resume.getFileName() %>
                </p>

                <p>
                    <strong>Status:</strong>
                    Uploaded successfully
                </p>

            </div>

        <% } else { %>

            <div class="resume-info">

                <p>
                    No resume uploaded yet.
                </p>

            </div>

        <% } %>

        <form
                action="resume"
                method="post"
                enctype="multipart/form-data">

            <div class="form-group">

                <label for="resumeFile">
                    Select Resume
                </label>

                <input
                        type="file"
                        id="resumeFile"
                        name="resumeFile"
                        accept=".pdf,.doc,.docx"
                        required
                >

                <p class="note">
                    PDF, DOC or DOCX only. Maximum size: 5 MB.
                </p>

            </div>

            <button
                    type="submit"
                    class="btn">

                Upload Resume

            </button>

        </form>

    </div>

</div>

</body>
</html>