package com.example.Http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ClientHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String response;

        if (path.equals("/")) {
            response = getWelcomePage();
        } else if (path.equals("/login")) {
            response = getLoginPage();
        } else if (path.equals("/signup")) {
            response = getSignupPage();
        } else {
            response = "404 Not Found";
        }

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String getWelcomePage() {
        return "<html><body>" +
                "<h1>Welcome</h1>" +
                "<a href='/login'>Login</a><br>" +
                "<a href='/signup'>Sign Up</a>" +
                "</body></html>";
    }

    private String getLoginPage() {
        return "<html><body>" +
                "<h1>Login</h1>" +
                "<form action='/login' method='post'>" +
                "Email: <input type='text' name='email'><br>" +
                "Password: <input type='password' name='password'><br>" +
                "<input type='submit' value='Login'>" +
                "</form>" +
                "<a href='/signup'>Sign Up</a>" +
                "</body></html>";
    }

    private String getSignupPage() {
        return "<html><body>" +
                "<h1>Sign Up</h1>" +
                "<form action='/signup' method='post'>" +
                "First Name: <input type='text' name='firstName'><br>" +
                "Last Name: <input type='text' name='lastName'><br>" +
                "Email: <input type='text' name='email'><br>" +
                "Password: <input type='password' name='password'><br>" +
                "Confirm Password: <input type='password' name='confirmPassword'><br>" +
                "<input type='submit' value='Sign Up'>" +
                "</form>" +
                "<a href='/login'>Login</a>" +
                "</body></html>";
    }
}
