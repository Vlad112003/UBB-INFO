package org.charity.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.charity.CharitableCase;
import org.charity.CharityProxy;
import org.charity.Donation;
import org.charity.Donor;

import java.util.List;
import java.util.Objects;

public class DonationController {
    CharitableCasesController charitableCasesController;
    CharityProxy charityProxy;
    @FXML
    TableView<Donor> tableView;
    @FXML
    TableColumn<Donor, String> tableColumnName;
    @FXML
    TableColumn<Donor, String> tableColumnAddress;
    @FXML
    TableColumn<Donor, String> tableColumnPhone;
    @FXML
    TextField nameField;
    @FXML
    TextField addressField;
    @FXML
    TextField phoneField;
    @FXML
    TextField amountField;
    @FXML
    TextField searchField;
    @FXML
    Button saveButton;
    @FXML
    TextField donorIdField;

    ObservableList<Donor> model = FXCollections.observableArrayList();
    CharitableCase charitableCase;


    public void setProxy(CharityProxy proxy, CharitableCase selectedCase, CharitableCasesController charitableCasesController){
        this.charityProxy = proxy;
        this.charitableCase = selectedCase;
        this.charitableCasesController = charitableCasesController;
        initModel();
    }

    @FXML
    public void initialize(){
        donorIdField.setVisible(false);
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Donor, String>("name"));
        tableColumnAddress.setCellValueFactory(new PropertyValueFactory<Donor, String>("address"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory<Donor, String>("phone"));
        tableView.setItems(model);
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Donor selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null){
                    donorIdField.setText(selectedItem.getId().toString());
                    nameField.setText(selectedItem.getName());
                    addressField.setText(selectedItem.getAddress());
                    phoneField.setText(selectedItem.getPhone());
                }
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
        });
    }

    private void filterData(String filter) {
        if (filter == null || filter.isEmpty()) {
            tableView.setItems(model);
        } else {
            ObservableList<Donor> filteredData = FXCollections.observableArrayList();
            List<Donor> donors = charityProxy.getDonorsWithFilter(filter);
            filteredData.setAll(donors);
            tableView.setItems(filteredData);
        }
    }

    public void refresh(){
        initModel();
    }

    public void handleSave(){
        Donor donor;
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        Double amount = Double.parseDouble(amountField.getText());
        if (Objects.equals(donorIdField.getText(), "")){
            donor = new Donor(name, address, phone);
            Donation donation = new Donation(donor, charitableCase, amount);
            Integer newId = charityProxy.addDonationNewDonor(donation);
            donorIdField.setText(newId.toString());
        } else {
            Integer donorId = Integer.parseInt(donorIdField.getText());
            donor = new Donor(donorId, name, address, phone);
            Donation donation = new Donation(donor, charitableCase, amount);
            charityProxy.addDonationExistingDonor(donation);
        }
        refresh();
        charitableCasesController.refresh();
    }

    public void initModel(){
        List<Donor> donors = charityProxy.getDonorsWithFilter("");
        model.setAll(donors);
    }
}
