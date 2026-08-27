package com.smarthire.dao;

import com.smarthire.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.smarthire.model.User;
import com.smarthire.util.DBConnection;

public class UserDAO {

    // =========================================================
    // REGISTER USER
    // =========================================================

	public boolean registerUser(User user) {

	    String sql =
	            "INSERT INTO users " +
	            "(name, email, password, role) " +
	            "VALUES (?, ?, ?, ?)";

	    try (Connection connection =
	                 DBConnection.getConnection();
	         PreparedStatement statement =
	                 connection.prepareStatement(sql)) {

	        statement.setString(
	                1,
	                user.getName()
	        );

	        statement.setString(
	                2,
	                user.getEmail()
	        );

	        String hashedPassword =
	                PasswordUtil.hashPassword(
	                        user.getPassword()
	                );

	        statement.setString(
	                3,
	                hashedPassword
	        );

	        statement.setString(
	                4,
	                user.getRole()
	        );

	        return statement.executeUpdate() > 0;

	    } catch (SQLException e) {

	        e.printStackTrace();
	        return false;
	    }
	}
    // =========================================================
    // LOGIN USER
    // =========================================================

	public User loginUser(
	        String email,
	        String password) {

	    String sql =
	            "SELECT * FROM users " +
	            "WHERE email = ?";

	    try (Connection connection =
	                 DBConnection.getConnection();
	         PreparedStatement statement =
	                 connection.prepareStatement(sql)) {

	        statement.setString(
	                1,
	                email
	        );

	        try (ResultSet resultSet =
	                     statement.executeQuery()) {

	            if (resultSet.next()) {

	                String storedPassword =
	                        resultSet.getString(
	                                "password"
	                        );

	                String hashedPassword =
	                        PasswordUtil.hashPassword(
	                                password
	                        );

	                /*
	                 * New accounts:
	                 * password in database is SHA-256.
	                 */

	                if (hashedPassword.equals(
	                        storedPassword)) {

	                    return mapUser(resultSet);
	                }

	                /*
	                 * Existing accounts:
	                 * password is still plain text.
	                 *
	                 * This temporary compatibility check
	                 * allows your existing admin/recruiter
	                 * accounts to continue working.
	                 */

	                if (password.equals(
	                        storedPassword)) {

	                    return mapUser(resultSet);
	                }
	            }
	        }

	    } catch (SQLException e) {

	        e.printStackTrace();
	    }

	    return null;
	}
    // =========================================================
    // GET USER BY ID
    // =========================================================

    public User getUserById(int userId) {

        String sql =
                "SELECT * FROM users " +
                "WHERE id = ?";

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

                    return mapUser(resultSet);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // UPDATE USER PROFILE
    // =========================================================

    public boolean updateProfile(User user) {

        String sql =
                "UPDATE users " +
                "SET name = ?, email = ? " +
                "WHERE id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    user.getName()
            );

            statement.setString(
                    2,
                    user.getEmail()
            );

            statement.setInt(
                    3,
                    user.getId()
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // CHECK EMAIL EXISTS
    // =========================================================

    public boolean emailExists(String email) {

        String sql =
                "SELECT id FROM users " +
                "WHERE email = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    email
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next();
            }

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // MAP RESULTSET TO USER OBJECT
    // =========================================================

    private User mapUser(
            ResultSet resultSet)
            throws SQLException {

        User user =
                new User();

        user.setId(
                resultSet.getInt("id")
        );

        user.setName(
                resultSet.getString("name")
        );

        user.setEmail(
                resultSet.getString("email")
        );

        user.setPassword(
                resultSet.getString("password")
        );

        user.setRole(
                resultSet.getString("role")
        );

        return user;
    }
}