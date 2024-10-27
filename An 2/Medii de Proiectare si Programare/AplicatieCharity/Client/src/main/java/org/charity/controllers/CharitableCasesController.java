package org.charity.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.charity.CharitableCase;
import org.charity.CharityProxy;
import org.charity.observer.UIObserver;

import java.io.IOException;
import java.util.List;


public class CharitableCasesController implements UIObserver {
    CharityProxy charityProxy;
    Stage loginStage;
    Stage stage;
    ObservableList<CharitableCase> model = FXCollections.observableArrayList();
    private static final Logger logger = LogManager.getLogger(CharitableCasesController.class);
    @FXML
    TableView<CharitableCase> tableView;
    @FXML
    TableColumn<CharitableCase, String> tableColumnName;
    @FXML
    TableColumn<CharitableCase, String> tableColumnAmount;
    @FXML
    Button logoutButton;

    public void setProxy(CharityProxy proxy, Stage loginStage, Stage stage){
        this.charityProxy = proxy;
        this.loginStage = loginStage;
        this.stage = stage;
        charityProxy.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize(){
        logger.traceEntry("initialising CharitableCaseController");
        tableColumnName.setCellValueFactory(new PropertyValueFactory<CharitableCase, String>("caseName"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<CharitableCase, String>("amount"));
        tableView.setItems(model);
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                CharitableCase selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    openDonationGui(selectedItem);
                }
            }
        });
        logger.traceExit();
    }

    public void refresh(){
        initModel();
    }

    public void handleLogout(){
        logger.traceEntry("logout");
        charityProxy.logout();
        stage.close();
        loginStage.show();
        logger.traceExit();
    }

    public void openDonationGui(CharitableCase selectedCase) {
        logger.traceEntry("openDonationGui");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/donationPage.fxml"));

            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Donation");
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            stage.setScene(scene);

            DonationController donationController = loader.getController();
            donationController.setProxy(charityProxy, selectedCase, this);

            logger.traceExit();
            stage.show();
        } catch (IOException e){
            logger.error(e);
        }
    }

    private void initModel() {
        List<CharitableCase> cases = charityProxy.getAllCases();
        model.setAll(cases);
    }

    @Override
    public void update(List<CharitableCase> cases) {
        model.setAll(cases);
    }
}
