module ro.ubbcluj.cs.map.lab72nd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ro.ubbcluj.cs.map.lab72nd to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab72nd.controller to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab72nd.domain to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab72nd.repository to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab72nd.service to javafx.fxml;
    exports ro.ubbcluj.cs.map.lab72nd;
    exports ro.ubbcluj.cs.map.lab72nd.controller;
    exports ro.ubbcluj.cs.map.lab72nd.domain;
    exports ro.ubbcluj.cs.map.lab72nd.repository;
    exports ro.ubbcluj.cs.map.lab72nd.service;
}