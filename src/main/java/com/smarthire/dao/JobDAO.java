package com.smarthire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smarthire.model.Job;
import com.smarthire.util.DBConnection;

public class JobDAO {

    public boolean addJob(Job job) {

        String sql =
                "INSERT INTO jobs " +
                "(recruiter_id, company_id, title, description, " +
                "requirements, location, salary, job_type, skills) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, job.getRecruiterId());
            statement.setInt(2, job.getCompanyId());
            statement.setString(3, job.getTitle());
            statement.setString(4, job.getDescription());
            statement.setString(5, job.getRequirements());
            statement.setString(6, job.getLocation());
            statement.setString(7, job.getSalary());
            statement.setString(8, job.getJobType());
            statement.setString(9, job.getSkills());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Job> getAllJobs() {

        List<Job> jobs = new ArrayList<>();

        String sql =
                "SELECT * FROM jobs " +
                "ORDER BY created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                jobs.add(mapJob(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jobs;
    }

    public List<Job> getJobsByRecruiter(
            int recruiterId) {

        List<Job> jobs = new ArrayList<>();

        String sql =
                "SELECT * FROM jobs " +
                "WHERE recruiter_id = ? " +
                "ORDER BY created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, recruiterId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                jobs.add(mapJob(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jobs;
    }

    public Job getJobById(int jobId) {

        String sql =
                "SELECT * FROM jobs WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return mapJob(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Job getJobByIdForRecruiter(
            int jobId,
            int recruiterId) {

        String sql =
                "SELECT * FROM jobs " +
                "WHERE id = ? AND recruiter_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);
            statement.setInt(2, recruiterId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return mapJob(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateJob(Job job) {

        String sql =
                "UPDATE jobs SET " +
                "company_id = ?, " +
                "title = ?, " +
                "description = ?, " +
                "requirements = ?, " +
                "location = ?, " +
                "salary = ?, " +
                "job_type = ?, " +
                "skills = ? " +
                "WHERE id = ? AND recruiter_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, job.getCompanyId());
            statement.setString(2, job.getTitle());
            statement.setString(3, job.getDescription());
            statement.setString(4, job.getRequirements());
            statement.setString(5, job.getLocation());
            statement.setString(6, job.getSalary());
            statement.setString(7, job.getJobType());
            statement.setString(8, job.getSkills());
            statement.setInt(9, job.getId());
            statement.setInt(10, job.getRecruiterId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteJob(
            int jobId,
            int recruiterId) {

        String sql =
                "DELETE FROM jobs " +
                "WHERE id = ? AND recruiter_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);
            statement.setInt(2, recruiterId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Job> searchJobs(
            String keyword,
            String location,
            String jobType) {

        List<Job> jobs =
                new ArrayList<>();

        StringBuilder sql =
                new StringBuilder(
                    "SELECT * FROM jobs WHERE 1=1 "
                );

        List<String> parameters =
                new ArrayList<>();

        if (keyword != null &&
            !keyword.isBlank()) {

            sql.append(
                "AND (LOWER(title) LIKE ? " +
                "OR LOWER(description) LIKE ? " +
                "OR LOWER(skills) LIKE ?) "
            );

            String value =
                    "%" + keyword.toLowerCase() + "%";

            parameters.add(value);
            parameters.add(value);
            parameters.add(value);
        }

        if (location != null &&
            !location.isBlank()) {

            sql.append(
                "AND LOWER(location) LIKE ? "
            );

            parameters.add(
                "%" + location.toLowerCase() + "%"
            );
        }

        if (jobType != null &&
            !jobType.isBlank()) {

            sql.append(
                "AND job_type = ? "
            );

            parameters.add(jobType);
        }

        sql.append(
            "ORDER BY created_at DESC"
        );

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                         sql.toString())) {

            for (int i = 0;
                 i < parameters.size();
                 i++) {

                statement.setString(
                        i + 1,
                        parameters.get(i)
                );
            }

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                jobs.add(mapJob(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jobs;
    }

    private Job mapJob(
            ResultSet resultSet)
            throws SQLException {

        Job job = new Job();

        job.setId(
                resultSet.getInt("id"));

        job.setRecruiterId(
                resultSet.getInt("recruiter_id"));

        job.setCompanyId(
                resultSet.getInt("company_id"));

        job.setTitle(
                resultSet.getString("title"));

        job.setDescription(
                resultSet.getString("description"));

        job.setRequirements(
                resultSet.getString("requirements"));

        job.setLocation(
                resultSet.getString("location"));

        job.setSalary(
                resultSet.getString("salary"));

        job.setJobType(
                resultSet.getString("job_type"));

        job.setSkills(
                resultSet.getString("skills"));

        return job;
    }
}