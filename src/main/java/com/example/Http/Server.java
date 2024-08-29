package com.example.Http;

import com.example.Main;
import com.example.controllers.UserController;
import com.example.dao.UserDAO;
import com.example.models.User;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import javafx.application.Application;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class Server {

    public static void main(String[] args) throws IOException {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new ClientHandler());
        server.createContext("/register", new UserController.RegisterHandler());
        server.createContext("/login", new UserController.LoginHandler());

        server.setExecutor(null);
        new Thread(() -> {
            server.start();
            System.out.println("Server is listening on port " + port);
        }).start();
        Application.launch(Main.class, args);
    }
}