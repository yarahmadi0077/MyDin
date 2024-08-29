package com.example.dao;

import com.example.models.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
    private final String url = "jdbc:mysql://localhost:3306/LinkDIn";
    private final String user = "root";
    private final String password = "";

    public void addPost(Post post) {
        String query = "INSERT INTO posts (content, email, mediaId) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, post.getContent());
            statement.setString(2, post.getAuthor());
            statement.setInt(3, post.getMedia());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT p.id, p.content, p.email, p.mediaId, p.time, m.path as mediaPath FROM posts p LEFT JOIN media m ON p.mediaId = m.id ORDER BY p.time DESC";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setContent(resultSet.getString("content"));
                post.setAuthor(resultSet.getString("email"));
                post.setMedia(resultSet.getInt("mediaId"));
                post.setTimestamp(resultSet.getTimestamp("time"));
                post.setMediaPath(resultSet.getString("mediaPath"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public int addMedia(String mediaPath) {
        String query = "INSERT INTO media (path) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, mediaPath);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
