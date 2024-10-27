module project.oacoacdiridiridac {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires java.sql;
    requires org.apache.logging.log4j;

    opens project.oacoacdiridiridac to javafx.fxml;
    exports project.oacoacdiridiridac;
    exports project.oacoacdiridiridac.Controller;
    exports project.oacoacdiridiridac.Service;
    exports project.oacoacdiridiridac.Domain;
    opens project.oacoacdiridiridac.Controller to javafx.fxml;
}