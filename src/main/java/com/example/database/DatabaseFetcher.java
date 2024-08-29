package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseFetcher {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mariadb://127.0.0.1:3306/LinkDIn";
        String username = "root";
        String password = "";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();

            String query = "SELECT * FROM connectioninfos";
            resultSet = statement.executeQuery(query);
            System.out.println("Connection Infos:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("User Link: " + resultSet.getString("userLink"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("Phone Number: " + resultSet.getString("phoneNumber"));
                System.out.println("Phone Type: " + resultSet.getString("phoneType"));
                System.out.println("Address: " + resultSet.getString("address"));
                System.out.println("Birthday: " + resultSet.getDate("birthday"));
                System.out.println("Birthday Access: " + resultSet.getString("birthdayAccess"));
                System.out.println("Other Way: " + resultSet.getString("otherWay"));
                System.out.println("------------------------------");
            }

            query = "SELECT * FROM educations";
            resultSet = statement.executeQuery(query);
            System.out.println("Educations:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("School Name: " + resultSet.getString("schoolName"));
                System.out.println("Field Of Study: " + resultSet.getString("fieldOfStudy"));
                System.out.println("Start Date: " + resultSet.getDate("startDate"));
                System.out.println("End Date: " + resultSet.getDate("endDate"));
                System.out.println("Grade: " + resultSet.getDouble("grade"));
                System.out.println("Activities And Societies: " + resultSet.getString("activitiesAndSocieties"));
                System.out.println("Profile Changes: " + resultSet.getString("profileChanges"));
                System.out.println("Descriptions: " + resultSet.getString("descriptions"));
                System.out.println("------------------------------");
            }

            query = "SELECT * FROM jobs";
            resultSet = statement.executeQuery(query);
            System.out.println("Jobs:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Title: " + resultSet.getString("title"));
                System.out.println("Employment Type: " + resultSet.getString("employmentType"));
                System.out.println("Company Name: " + resultSet.getString("companyName"));
                System.out.println("Location: " + resultSet.getString("location"));
                System.out.println("Location Type: " + resultSet.getString("locationType"));
                System.out.println("Activity: " + resultSet.getInt("activity"));
                System.out.println("Start To Work: " + resultSet.getDate("startToWork"));
                System.out.println("End To Work: " + resultSet.getDate("endToWork"));
                System.out.println("Description: " + resultSet.getString("description"));
                System.out.println("------------------------------");
            }

            query = "SELECT * FROM users";
            resultSet = statement.executeQuery(query);
            System.out.println("Users:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("First Name: " + resultSet.getString("firstName"));
                System.out.println("Last Name: " + resultSet.getString("lastName"));
                System.out.println("Additional Name: " + resultSet.getString("additionalName"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("Title: " + resultSet.getString("title"));
                System.out.println("Image Path Profile: " + resultSet.getString("imagePathProfile"));
                System.out.println("Image Path Background: " + resultSet.getString("imagePathBackground"));
                System.out.println("Job ID: " + resultSet.getInt("jobId"));
                System.out.println("Education ID: " + resultSet.getInt("educationId"));
                System.out.println("Connection Info ID: " + resultSet.getInt("connectionInfoId"));
                System.out.println("Country: " + resultSet.getString("country"));
                System.out.println("City: " + resultSet.getString("city"));
                System.out.println("Profession: " + resultSet.getString("profession"));
                System.out.println("Password: " + resultSet.getString("password"));
                System.out.println("------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
