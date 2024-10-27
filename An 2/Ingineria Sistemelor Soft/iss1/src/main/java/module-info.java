module lab13.iss1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;


    opens lab13.iss1 to javafx.fxml;
    opens lab13.iss1.Domain to javafx.base;
    opens lab13.iss1.Repository to javafx.base;
    opens lab13.iss1.Service to javafx.base;
    exports lab13.iss1;
}