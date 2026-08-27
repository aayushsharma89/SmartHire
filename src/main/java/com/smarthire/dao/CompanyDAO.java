package com.smarthire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smarthire.model.Company;
import com.smarthire.util.DBConnection;

public class CompanyDAO {

    // =========================================================
    // ADD COMPANY
    // =========================================================

    public boolean addCompany(Company company) {

        String sql =
                "INSERT INTO companies " +
                "(recruiter_id, company_name, description, location, website) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    company.getRecruiterId()
            );

            statement.setString(
                    2,
                    company.getCompanyName()
            );

            statement.setString(
                    3,
                    company.getDescription()
            );

            statement.setString(
                    4,
                    company.getLocation()
            );

            statement.setString(
                    5,
                    company.getWebsite()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // GET ALL COMPANIES OF A RECRUITER
    // =========================================================

    public List<Company> getCompaniesByRecruiterId(
            int recruiterId) {

        List<Company> companies =
                new ArrayList<>();

        String sql =
                "SELECT * FROM companies " +
                "WHERE recruiter_id = ? " +
                "ORDER BY id DESC";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    recruiterId
            );

            try (ResultSet resultSet =
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
                            resultSet.getString(
                                    "company_name"
                            )
                    );

                    company.setDescription(
                            resultSet.getString(
                                    "description"
                            )
                    );

                    company.setLocation(
                            resultSet.getString(
                                    "location"
                            )
                    );

                    company.setWebsite(
                            resultSet.getString(
                                    "website"
                            )
                    );

                    companies.add(company);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return companies;
    }


    // =========================================================
    // GET ONE COMPANY BY ID FOR A RECRUITER
    // =========================================================

    public Company getCompanyByIdForRecruiter(
            int companyId,
            int recruiterId) {

        String sql =
                "SELECT * FROM companies " +
                "WHERE id = ? AND recruiter_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    companyId
            );

            statement.setInt(
                    2,
                    recruiterId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    Company company =
                            new Company();

                    company.setId(
                            resultSet.getInt("id")
                    );

                    company.setRecruiterId(
                            resultSet.getInt("recruiter_id")
                    );

                    company.setCompanyName(
                            resultSet.getString(
                                    "company_name"
                            )
                    );

                    company.setDescription(
                            resultSet.getString(
                                    "description"
                            )
                    );

                    company.setLocation(
                            resultSet.getString(
                                    "location"
                            )
                    );

                    company.setWebsite(
                            resultSet.getString(
                                    "website"
                            )
                    );

                    return company;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // UPDATE COMPANY
    // =========================================================

    public boolean updateCompany(
            Company company) {

        String sql =
                "UPDATE companies SET " +
                "company_name = ?, " +
                "description = ?, " +
                "location = ?, " +
                "website = ? " +
                "WHERE id = ? AND recruiter_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    company.getCompanyName()
            );

            statement.setString(
                    2,
                    company.getDescription()
            );

            statement.setString(
                    3,
                    company.getLocation()
            );

            statement.setString(
                    4,
                    company.getWebsite()
            );

            statement.setInt(
                    5,
                    company.getId()
            );

            statement.setInt(
                    6,
                    company.getRecruiterId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // DELETE COMPANY
    // =========================================================

    public boolean deleteCompany(
            int companyId,
            int recruiterId) {

        String sql =
                "DELETE FROM companies " +
                "WHERE id = ? AND recruiter_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    companyId
            );

            statement.setInt(
                    2,
                    recruiterId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // GET ALL COMPANIES
    // Used later by Admin
    // =========================================================

    public List<Company> getAllCompanies() {

        List<Company> companies =
                new ArrayList<>();

        String sql =
                "SELECT * FROM companies " +
                "ORDER BY id DESC";

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
                        resultSet.getString(
                                "company_name"
                        )
                );

                company.setDescription(
                        resultSet.getString(
                                "description"
                        )
                );

                company.setLocation(
                        resultSet.getString(
                                "location"
                        )
                );

                company.setWebsite(
                        resultSet.getString(
                                "website"
                        )
                );

                companies.add(company);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return companies;
    }
}