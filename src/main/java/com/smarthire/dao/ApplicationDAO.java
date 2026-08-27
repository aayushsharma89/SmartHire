package com.smarthire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smarthire.model.Applicant;
import com.smarthire.model.Application;
import com.smarthire.util.DBConnection;

public class ApplicationDAO {

    // =========================================================
    // APPLY FOR JOB
    // =========================================================

    public boolean applyForJob(Application application) {

        String sql =
                "INSERT INTO applications " +
                "(job_id, applicant_id, resume_id, status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    application.getJobId()
            );

            statement.setInt(
                    2,
                    application.getApplicantId()
            );

            if (application.getResumeId() > 0) {

                statement.setInt(
                        3,
                        application.getResumeId()
                );

            } else {

                statement.setNull(
                        3,
                        java.sql.Types.INTEGER
                );
            }

            statement.setString(
                    4,
                    application.getStatus()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // GET APPLICATIONS BY APPLICANT
    // =========================================================

    public List<Application> getApplicationsByApplicant(
            int applicantId) {

        List<Application> applications =
                new ArrayList<>();

        String sql =
                "SELECT * FROM applications " +
                "WHERE applicant_id = ? " +
                "ORDER BY applied_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    applicantId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    applications.add(
                            mapApplication(resultSet)
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return applications;
    }


    // =========================================================
    // GET APPLICATIONS BY ONE JOB
    // Used from My Jobs -> Applicants
    // =========================================================

    public List<Application> getApplicationsByJob(
            int jobId) {

        List<Application> applications =
                new ArrayList<>();

        String sql =
                "SELECT * FROM applications " +
                "WHERE job_id = ? " +
                "ORDER BY applied_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    jobId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    applications.add(
                            mapApplication(resultSet)
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return applications;
    }


    // =========================================================
    // GET APPLICANTS BY ONE JOB
    // Includes candidate name and email
    // =========================================================

    public List<Applicant> getApplicantsByJob(
            int jobId) {

        List<Applicant> applicants =
                new ArrayList<>();

        String sql =
                "SELECT " +
                "a.id AS application_id, " +
                "a.job_id, " +
                "a.applicant_id, " +
                "a.resume_id, " +
                "a.status, " +
                "a.applied_at, " +
                "u.name AS applicant_name, " +
                "u.email AS applicant_email " +
                "FROM applications a " +
                "INNER JOIN users u " +
                "ON a.applicant_id = u.id " +
                "WHERE a.job_id = ? " +
                "ORDER BY a.applied_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    jobId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    applicants.add(
                            mapApplicant(resultSet)
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return applicants;
    }


    // =========================================================
    // GET ALL APPLICANTS FOR A RECRUITER
    // Used from Recruiter Dashboard
    // =========================================================

    public List<Applicant> getApplicantsByRecruiter(
            int recruiterId) {

        List<Applicant> applicants =
                new ArrayList<>();

        String sql =
                "SELECT " +
                "a.id AS application_id, " +
                "a.job_id, " +
                "a.applicant_id, " +
                "a.resume_id, " +
                "a.status, " +
                "a.applied_at, " +
                "u.name AS applicant_name, " +
                "u.email AS applicant_email, " +
                "j.title AS job_title " +
                "FROM applications a " +
                "INNER JOIN users u " +
                "ON a.applicant_id = u.id " +
                "INNER JOIN jobs j " +
                "ON a.job_id = j.id " +
                "WHERE j.recruiter_id = ? " +
                "ORDER BY a.applied_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    recruiterId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Applicant applicant =
                            mapApplicant(resultSet);

                    /*
                     * job_title is useful for the
                     * recruiter dashboard's all-applicants view.
                     */

                    try {
                        applicant.setJobTitle(
                                resultSet.getString(
                                        "job_title"
                                )
                        );
                    } catch (SQLException e) {
                        /*
                         * This keeps the DAO compatible
                         * if jobTitle is not yet present
                         * in the Applicant model.
                         */
                    }

                    applicants.add(
                            applicant
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return applicants;
    }


    // =========================================================
    // UPDATE APPLICATION STATUS
    // =========================================================

    public boolean updateApplicationStatus(
            int applicationId,
            String status) {

        String sql =
                "UPDATE applications " +
                "SET status = ? " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    status
            );

            statement.setInt(
                    2,
                    applicationId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // DELETE APPLICATION
    // =========================================================

    public boolean deleteApplication(
            int applicationId) {

        String sql =
                "DELETE FROM applications " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    applicationId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // MAP APPLICATION
    // =========================================================

    private Application mapApplication(
            ResultSet resultSet)
            throws SQLException {

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

        return application;
    }


    // =========================================================
    // MAP APPLICANT
    // =========================================================

    private Applicant mapApplicant(
            ResultSet resultSet)
            throws SQLException {

        Applicant applicant =
                new Applicant();

        applicant.setApplicationId(
                resultSet.getInt(
                        "application_id"
                )
        );

        applicant.setJobId(
                resultSet.getInt(
                        "job_id"
                )
        );

        applicant.setApplicantId(
                resultSet.getInt(
                        "applicant_id"
                )
        );

        applicant.setResumeId(
                resultSet.getInt(
                        "resume_id"
                )
        );

        applicant.setApplicantName(
                resultSet.getString(
                        "applicant_name"
                )
        );

        applicant.setApplicantEmail(
                resultSet.getString(
                        "applicant_email"
                )
        );

        applicant.setStatus(
                resultSet.getString(
                        "status"
                )
        );

        return applicant;
    }
}