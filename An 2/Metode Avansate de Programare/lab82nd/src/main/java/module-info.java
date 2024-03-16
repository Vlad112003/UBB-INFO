module ro.ubbcluj.cs.map.lab82nd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ro.ubbcluj.cs.map.lab82nd to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab82nd.controller to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab82nd.domain to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab82nd.repository to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab82nd.service to javafx.fxml;
    opens ro.ubbcluj.cs.map.lab82nd.domain.validators to javafx.fxml;
    exports ro.ubbcluj.cs.map.lab82nd;
    exports ro.ubbcluj.cs.map.lab82nd.controller;
    exports ro.ubbcluj.cs.map.lab82nd.domain;
    exports ro.ubbcluj.cs.map.lab82nd.repository;
    exports ro.ubbcluj.cs.map.lab82nd.service;
    exports ro.ubbcluj.cs.map.lab82nd.domain.validators;
}