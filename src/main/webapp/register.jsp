<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>SmartHire - Register</title>

    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        body {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: #f4f7fb;
        }

        .register-container {
            width: 430px;
            background: white;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.12);
        }

        .register-container h1 {
            text-align: center;
            margin-bottom: 10px;
            color: #222;
        }

        .register-container p {
            text-align: center;
            margin-bottom: 25px;
            color: #666;
        }

        .form-group {
            margin-bottom: 17px;
        }

        .form-group label {
            display: block;
            margin-bottom: 7px;
            font-weight: bold;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 11px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        .register-btn {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 6px;
            background: #2563eb;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        .register-btn:hover {
            background: #1d4ed8;
        }

        .login-link {
            text-align: center;
            margin-top: 18px;
        }

        .login-link a {
            text-decoration: none;
            color: #2563eb;
        }

        .error {
            background: #fee2e2;
            color: #b91c1c;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 15px;
            text-align: center;
        }

    </style>

</head>

<body>

<div class="register-container">

    <h1>SmartHire</h1>

    <p>Create your account</p>

    <%
        String error = (String) request.getAttribute("error");

        if (error != null) {
    %>

        <div class="error">
            <%= error %>
        </div>

    <%
        }
    %>

    <form action="register" method="post">

        <div class="form-group">

            <label for="name">
                Full Name
            </label>

            <input
                type="text"
                id="name"
                name="name"
                placeholder="Enter your full name"
                required
            >

        </div>

        <div class="form-group">

            <label for="email">
                Email
            </label>

            <input
                type="email"
                id="email"
                name="email"
                placeholder="Enter your email"
                required
            >

        </div>

        <div class="form-group">

            <label for="password">
                Password
            </label>

            <input
                type="password"
                id="password"
                name="password"
                placeholder="Create a password"
                minlength="6"
                required
            >

        </div>

        <div class="form-group">

            <label for="role">
                Account Type
            </label>

            <select id="role" name="role" required>

                <option value="">
                    Select account type
                </option>

                <option value="JOB_SEEKER">
                    Job Seeker
                </option>

                <option value="RECRUITER">
                    Recruiter
                </option>

            </select>

        </div>

        <button type="submit" class="register-btn">
            Create Account
        </button>

    </form>

    <div class="login-link">

        Already have an account?

        <a href="login.jsp">
            Login
        </a>

    </div>

</div>

</body>
</html>