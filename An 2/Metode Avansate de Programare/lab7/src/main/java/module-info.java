module ro.ubbcluj.cs.map.lab7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ro.ubbcluj.cs.map.lab7 to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab7.controller to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab7.domain to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab7.repository to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab7.service to javafx.fxml;
    exports ro.ubbcluj.cs.map.lab7;
    exports ro.ubbcluj.cs.map.lab7.controller;
    exports ro.ubbcluj.cs.map.lab7.domain;
    exports ro.ubbcluj.cs.map.lab7.repository;
    exports ro.ubbcluj.cs.map.lab7.service;
}