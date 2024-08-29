package com.example.dao;

import com.example.models.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final String url = "jdbc:mysql://localhost:3306/LinkDin";
    private final String username = "root";
    private final String password = "";

    public int saveConnectionInfo(String email, String phoneNumber, String phoneType, String address, LocalDate birthday, String birthdayAccess, String otherWay) {
        if (email.length() > 40) {
            throw new IllegalArgumentException("Email length exceeds the allowed limit of 40 characters");
        }

        String sql = "INSERT INTO connectioninfos (email, phoneNumber, phoneType, address, birthday, birthdayAccess, otherWay) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, email);
            statement.setString(2, phoneNumber);
            statement.setString(3, phoneType);
            statement.setString(4, address);
            statement.setDate(5, birthday != null ? Date.valueOf(birthday) : null);
            statement.setString(6, birthdayAccess);
            statement.setString(7, otherWay);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int saveJob(String jobTitle, String employmentType, String companyName, String location, String locationType, boolean activity, LocalDate startToWork, LocalDate endToWork, String jobDescription) {
        String sql = "INSERT INTO jobs (title, employmentType, companyName, location, locationType, activity, startToWork, endToWork, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, jobTitle);
            statement.setString(2, employmentType);
            statement.setString(3, companyName);
            statement.setString(4, location);
            statement.setString(5, locationType);
            statement.setBoolean(6, activity);
            statement.setDate(7, startToWork != null ? Date.valueOf(startToWork) : null);
            statement.setDate(8, endToWork != null ? Date.valueOf(endToWork) : null);
            statement.setString(9, jobDescription);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int saveEducation(String schoolName, String fieldOfStudy, LocalDate educationStart, LocalDate educationEnd, Double grade, String activities, String educationDescription) {
        String sql = "INSERT INTO educations (schoolName, fieldOfStudy, startDate, endDate, grade, activitiesAndSocieties, descriptions) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, schoolName);
            statement.setString(2, fieldOfStudy);
            statement.setDate(3, educationStart != null ? Date.valueOf(educationStart) : null);
            statement.setDate(4, educationEnd != null ? Date.valueOf(educationEnd) : null);
            statement.setDouble(5, grade != null ? grade : 0.0);
            statement.setString(6, activities);
            statement.setString(7, educationDescription);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean updateUser(User user, String originalEmail) {
        String sql = "UPDATE users SET firstName = ?, lastName = ?, additionalName = ?, email = ?, title = ?, imagePathProfile = ?, imagePathBackground = ?, connectionInfoId = ?, jobId = ?, educationId = ?, country = ?, city = ?, profession = ?, biography = ? WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getAdditionalName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getTitle());
            statement.setString(6, user.getProfileImage());
            statement.setString(7, user.getBackgroundImage());
            statement.setInt(8, user.getConnectionInfoId());
            statement.setInt(9, user.getJobId());
            statement.setInt(10, user.getEducationId());
            statement.setString(11, user.getCountry());
            statement.setString(12, user.getCity());
            statement.setString(13, user.getProfession());
            statement.setString(14, user.getBiography());
            statement.setString(15, originalEmail);

            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserEmail(User user, String originalEmail) {
        String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";
        String updateSql = "UPDATE users SET email = ? WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement checkStatement = connection.prepareStatement(checkSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

            checkStatement.setString(1, user.getEmail());
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return false;
                }
            }

            updateStatement.setString(1, user.getEmail());
            updateStatement.setString(2, originalEmail);

            int affectedRows = updateStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be null or empty");
            }

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailTaken(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User loginUser(String email, String hashedPassword) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"), resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<User> searchUsers(String query) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE firstName LIKE ? OR lastName LIKE ? OR email LIKE ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String likeQuery = "%" + query + "%";
            statement.setString(1, likeQuery);
            statement.setString(2, likeQuery);
            statement.setString(3, likeQuery);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setFirstName(resultSet.getString("firstName"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setEmail(resultSet.getString("email"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    private Connection connect() {
        String url = "jdbc:mariadb://127.0.0.1:3306/LinkDin";
        String user = "root";
        String password = "";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public List<User> getFollowers(int userId) {
        System.out.println(userId);
        String sql = "SELECT * FROM followers WHERE userId = ?";
        List<User> followers = new ArrayList<>();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int userId1 = rs.getInt("followerId");
                System.out.println(userId1);
                User user = getUserById(userId1);
                followers.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return followers;
    }

    public List<User> getFollowings(int userId) {
        String sql = "SELECT u.id, u.firstName, u.lastName FROM followers f JOIN users u ON f.userId = u.id WHERE f.followerId = ?";
        List<User> followings = new ArrayList<>();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("firstName") , rs.getString("lastName"));
                followings.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return followings;
    }

    public void followUser(int userId, int followerId) {
        String checkUserSql = "SELECT COUNT(*) FROM users WHERE id = ?";
        String insertSql = "INSERT INTO followers(userId, followerId) VALUES(?, ?)";
        System.out.println(userId);

        try (Connection conn = this.connect()) {
            try (PreparedStatement pstmt = conn.prepareStatement(checkUserSql)) {
                pstmt.setInt(1, userId);
                try (ResultSet rs = pstmt.executeQuery()) {

                    if (rs.next() && rs.getInt(1) == 0) {
                        System.out.println("UserId does not exist.");
                        return;
                    }
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(checkUserSql)) {
                pstmt.setInt(1, followerId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        System.out.println("FollowerId does not exist.");
                        return;
                    }
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, followerId);
                pstmt.executeUpdate();
                System.out.println("Follow operation successful.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void sendConnectionRequest(int userId, int targetUserId, String notes) {
        String sql = "INSERT INTO connection_requests(userId, targetUserId, notes) VALUES(?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, targetUserId);
            pstmt.setString(3, notes);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getUserById(int userId) {
        User user = null;
        String query = "SELECT firstName, lastName, email, city, country, profession FROM users WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");
                String profession = resultSet.getString("profession");

                user = new User(firstName, lastName, email, city, country, profession);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
