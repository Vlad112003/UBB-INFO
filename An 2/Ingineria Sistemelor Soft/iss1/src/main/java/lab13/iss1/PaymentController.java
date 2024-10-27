package lab13.iss1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lab13.iss1.Domain.Order;
import lab13.iss1.Domain.OrderStatus;
import lab13.iss1.Service.Service;

import java.util.ArrayList;
import java.util.UUID;

public class PaymentController {
    private Service service;
    private Stage stage;

    public void setService(Stage stage, Service service){
        this.stage = stage;
        this.service = service;

        initModel();
    }

    @FXML
    TableView<Order> orderTable;
    @FXML
    TableColumn<String, Order> userColumn;
    @FXML
    TableColumn<UUID, Order> gameColumn;
    @FXML
    TableColumn<OrderStatus, Order> statusColumn;

    @FXML
    Button exitButton;
    @FXML
    Button approveButton;
    @FXML
    Button declineButton;

    @FXML
    ObservableList<Order> orderModel = FXCollections.observableArrayList();

    public void onPressExit(){
        stage.close();
    }

    @FXML
    void initialize(){
        userColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderTable.setItems(orderModel);
    }

    public void initModel(){
        ArrayList<Order> orders = service.getAllOrders();
        orderModel.setAll(orders);
    }

    public void reload(){
        ArrayList<Order> orders = service.getAllOrders();
        orderModel.setAll(orders);
    }

    public void onPressApprove(){
        Order order = orderTable.getSelectionModel().getSelectedItem();
        service.approveOrder(order.getId());
        reload();
    }

    public void onPressDecline(){
        Order order = orderTable.getSelectionModel().getSelectedItem();
        service.declineOrder(order.getId());
        reload();
    }


}
