package com.smarthire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.smarthire.model.Resume;
import com.smarthire.util.DBConnection;

public class ResumeDAO {

    // =========================================================
    // ADD RESUME
    // =========================================================

    public boolean addResume(Resume resume) {

        String sql =
                "INSERT INTO resumes " +
                "(user_id, file_name, file_path) " +
                "VALUES (?, ?, ?)";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    resume.getUserId()
            );

            statement.setString(
                    2,
                    resume.getFileName()
            );

            statement.setString(
                    3,
                    resume.getFilePath()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // GET RESUME BY USER ID
    // =========================================================

    public Resume getResumeByUserId(int userId) {

        String sql =
                "SELECT id, user_id, file_name, file_path " +
                "FROM resumes " +
                "WHERE user_id = ? " +
                "ORDER BY id DESC " +
                "LIMIT 1";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    userId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapResume(resultSet);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // GET RESUME BY ID
    // =========================================================

    public Resume getResumeById(int resumeId) {

        String sql =
                "SELECT id, user_id, file_name, file_path " +
                "FROM resumes " +
                "WHERE id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    resumeId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapResume(resultSet);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // GET RESUME ONLY IF RECRUITER OWNS THE JOB
    // =========================================================

    public Resume getResumeForRecruiter(
            int resumeId,
            int recruiterId) {

        String sql =
                "SELECT DISTINCT " +
                "r.id, " +
                "r.user_id, " +
                "r.file_name, " +
                "r.file_path " +
                "FROM resumes r " +
                "INNER JOIN applications a " +
                "ON r.id = a.resume_id " +
                "INNER JOIN jobs j " +
                "ON a.job_id = j.id " +
                "WHERE r.id = ? " +
                "AND j.recruiter_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    resumeId
            );

            statement.setInt(
                    2,
                    recruiterId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapResume(resultSet);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // DELETE RESUME
    // =========================================================

    public boolean deleteResume(int resumeId) {

        String sql =
                "DELETE FROM resumes WHERE id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    resumeId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // MAP RESULTSET TO RESUME
    // =========================================================

    private Resume mapResume(
            ResultSet resultSet)
            throws SQLException {

        Resume resume =
                new Resume();

        resume.setId(
                resultSet.getInt("id")
        );

        resume.setUserId(
                resultSet.getInt("user_id")
        );

        resume.setFileName(
                resultSet.getString("file_name")
        );

        resume.setFilePath(
                resultSet.getString("file_path")
        );

        return resume;
    }
}