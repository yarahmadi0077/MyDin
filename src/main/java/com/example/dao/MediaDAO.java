package com.example.dao;

import com.example.models.Media;

import java.sql.*;

public class MediaDAO {
    private final String url = "jdbc:mysql://localhost:3306/LinkDIn";
    private final String user = "root";
    private final String password = "";

    public int addMedia(Media media) {
        String query = "INSERT INTO media (path) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, media.getPath());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
