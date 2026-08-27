package com.smarthire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smarthire.model.Application;
import com.smarthire.model.Company;
import com.smarthire.model.User;
import com.smarthire.util.DBConnection;

public class AdminDAO {

    // =========================================================
    // DASHBOARD STATISTICS
    // =========================================================

    public int getTotalUsers() {
        return getCount("SELECT COUNT(*) FROM users");
    }

    public int getTotalJobSeekers() {
        return getCount(
                "SELECT COUNT(*) FROM users " +
                "WHERE role = 'JOB_SEEKER'"
        );
    }

    public int getTotalRecruiters() {
        return getCount(
                "SELECT COUNT(*) FROM users " +
                "WHERE role = 'RECRUITER'"
        );
    }

    public int getTotalCompanies() {
        return getCount(
                "SELECT COUNT(*) FROM companies"
        );
    }

    public int getTotalJobs() {
        return getCount(
                "SELECT COUNT(*) FROM jobs"
        );
    }

    public int getTotalApplications() {
        return getCount(
                "SELECT COUNT(*) FROM applications"
        );
    }

    private int getCount(String sql) {

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // =========================================================
    // GET ALL USERS
    // =========================================================

    public List<User> getAllUsers() {

        List<User> users =
                new ArrayList<>();

        String sql =
                "SELECT id, name, email, role, created_at " +
                "FROM users " +
                "ORDER BY created_at DESC";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User();

                user.setId(
                        resultSet.getInt("id")
                );

                user.setName(
                        resultSet.getString("name")
                );

                user.setEmail(
                        resultSet.getString("email")
                );

                user.setRole(
                        resultSet.getString("role")
                );

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // =========================================================
    // DELETE USER
    // =========================================================

    public boolean deleteUser(int userId) {

        String sql =
                "DELETE FROM users WHERE id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // GET ALL COMPANIES
    // =========================================================

    public List<Company> getAllCompanies() {

        List<Company> companies =
                new ArrayList<>();

        String sql =
                "SELECT * FROM companies " +
                "ORDER BY created_at DESC";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Company company =
                        new Company();

                company.setId(
                        resultSet.getInt("id")
                );

                company.setRecruiterId(
                        resultSet.getInt("recruiter_id")
                );

                company.setCompanyName(
                        resultSet.getString("company_name")
                );

                company.setDescription(
                        resultSet.getString("description")
                );

                company.setLocation(
                        resultSet.getString("location")
                );

                company.setWebsite(
                        resultSet.getString("website")
                );

                companies.add(company);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }

    // =========================================================
    // DELETE COMPANY
    // =========================================================

    public boolean deleteCompany(int companyId) {

        String sql =
                "DELETE FROM companies WHERE id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, companyId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // GET ALL APPLICATIONS
    // =========================================================

    public List<Application> getAllApplications() {

        List<Application> applications =
                new ArrayList<>();

        String sql =
                "SELECT * FROM applications " +
                "ORDER BY applied_at DESC";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Application application =
                        new Application();

                application.setId(
                        resultSet.getInt("id")
                );

                application.setJobId(
                        resultSet.getInt("job_id")
                );

                application.setApplicantId(
                        resultSet.getInt("applicant_id")
                );

                application.setResumeId(
                        resultSet.getInt("resume_id")
                );

                application.setStatus(
                        resultSet.getString("status")
                );

                applications.add(application);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return applications;
    }

    // =========================================================
    // DELETE APPLICATION
    // =========================================================

    public boolean deleteApplication(
            int applicationId) {

        String sql =
                "DELETE FROM applications " +
                "WHERE id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, applicationId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}