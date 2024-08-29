module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.httpserver;
    requires java.sql;
    requires java.sql.rowset;
    requires jjwt;
    requires javafx.graphics;

    opens com.example to javafx.fxml;
    opens com.example.fxml to javafx.fxml;
    opens com.example.controllers to javafx.fxml; // اضافه کردن این خط برای باز کردن پکیج کنترلرها

    exports com.example;
    exports com.example.Http;
    exports com.example.controllers;
    exports com.example.dao;
    exports com.example.models;
    exports com.example.util;
}