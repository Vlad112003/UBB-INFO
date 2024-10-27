module ro.ubbcluj.iss1nou {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens ro.ubbcluj.iss1nou to javafx.fxml;
    exports ro.ubbcluj.iss1nou;
}