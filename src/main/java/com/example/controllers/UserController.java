package com.example.controllers;

import com.example.dao.UserDAO;
import com.example.models.User;
import com.example.util.PasswordUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class UserController {

    private static final String SECRET_KEY = "secret";

    public static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String[] params = getRequestBody(exchange).split("&");
                String firstName = params[0].split("=")[1];
                String lastName = params[1].split("=")[1];
                String email = params[2].split("=")[1];
                String password = params[3].split("=")[1];

                UserDAO userDAO = new UserDAO();
                if (userDAO.isEmailTaken(email)) {
                    sendResponse(exchange, "Email is already taken", 400);
                    return;
                }

                String hashedPassword = PasswordUtils.hashPassword(password);
                User user = new User(firstName, lastName, email, hashedPassword);
                if (userDAO.registerUser(user)) {
                    sendResponse(exchange, "User registered successfully", 200);
                } else {
                    sendResponse(exchange, "Failed to register user", 500);
                }
            }
        }
    }

    public static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String[] params = getRequestBody(exchange).split("&");
                String email = params[0].split("=")[1];
                String password = params[1].split("=")[1];

                UserDAO userDAO = new UserDAO();
                User user = userDAO.loginUser(email, PasswordUtils.hashPassword(password));

                if (user != null) {
                    String token = Jwts.builder()
                            .setSubject(user.getEmail())
                            .claim("role", "user")
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                            .compact();

                    sendResponse(exchange, token, 200);
                } else {
                    sendResponse(exchange, "Invalid email or password", 400);
                }
            }
        }
    }

    private static String getRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    private static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
