package lab13.iss1;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lab13.iss1.Domain.Game;
import lab13.iss1.Domain.GenreType;
import lab13.iss1.Service.Service;

import java.util.ArrayList;

public class AdminController {

    private Service service;

    private Stage stage;



    public void setService(Stage stage, Service service){
        this.service = service;
        this.stage = stage;

        initModel();
    }


    public void onLogoutButtonClicked(){
        stage.close();
    }

    @FXML
    TextField nameField;
    @FXML
    TextField priceField;

    @FXML
    ComboBox<GenreType> genreField;

    @FXML
    Button addButton;
    @FXML
    Button deleteButton;

    public void onPressDelete(){
        String name = nameField.getText();
        service.deleteGame(name);
        reload();
    }

    public void onPressAdd(){
        String name = nameField.getText();
        float price = Float.parseFloat(priceField.getText());
        GenreType genre = genreField.getValue();


        service.addGame(name, String.valueOf(genre), price);
        Game game = service.getGame(name);
        reload();
    }

    public void onPressUpdate(){
        String name = nameField.getText();
        float price = Float.parseFloat(priceField.getText());
        GenreType genre = genreField.getValue();

        service.updateGame(name, String.valueOf(genre), price);
        Game game = service.getGame(name);
        reload();

    }

    @FXML
    Button updateButton;

    @FXML
    Button logoutButton;

    @FXML
    TableView<Game> gameTable;
    @FXML
    TableColumn<GenreType, Game> genreColumn;
    @FXML
    TableColumn<Float, Game> priceColumn;
    @FXML
    TableColumn<String, Game> titleColumn;

    @FXML
    ObservableList<Game> gameModel = FXCollections.observableArrayList();

    @FXML
    ObservableList<GenreType> genreModel = FXCollections.observableArrayList();

    @FXML
    void initialize(){
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gameTable.setItems(gameModel);

        genreField.setItems(genreModel);

        gameTable.getSelectionModel().selectedItemProperty().addListener((Observable observable) -> {
            Game game = gameTable.getSelectionModel().getSelectedItem();
            if(game != null){
                nameField.setText(game.getName());
                priceField.setText(String.valueOf(game.getPrice()));
                genreField.setValue(game.getGenre());
            }
        });
    }

    public void reload(){
        ArrayList<Game> games = service.getAllGames();
        gameModel.setAll(games);

    }

    public void initModel(){
        ArrayList<Game> games = service.getAllGames();
        gameModel.setAll(games);

        GenreType[] genres = GenreType.values();
        genreModel.setAll(genres);

    }
}
