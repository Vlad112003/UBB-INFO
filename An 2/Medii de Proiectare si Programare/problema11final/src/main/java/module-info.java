module project.problema11final {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires java.sql;
    requires org.apache.logging.log4j;

    opens project.problema11final to javafx.fxml;
    opens project.problema11final.Repository to javafx.fxml;
    opens project.problema11final.Domain to javafx.fxml;
    opens project.problema11final.Service to javafx.fxml;
    opens project.problema11final.Service.DTOs to javafx.base;
    exports project.problema11final;
}