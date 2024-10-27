package lab13.iss1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lab13.iss1.Domain.Game;
import lab13.iss1.Domain.GenreType;
import lab13.iss1.Domain.User;
import lab13.iss1.Service.Service;

import java.util.ArrayList;

public class StoreController {
    private Service service;
    private Stage stage;
    private User user;

    public void setService(Stage stage, Service service, User user){
        this.stage = stage;
        this.service = service;
        this.user=user;

        initModel();
    }

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
    TextField searchField;
    @FXML
    Button searchButton;
    @FXML
    Button purchaseButton;
    @FXML
    Button exitButton;

    @FXML
    void initialize(){
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gameTable.setItems(gameModel);
    }

    public void reload(){
        ArrayList<Game> games = service.getAllGames();
        gameModel.setAll(games);

    }

    public void initModel(){
        ArrayList<Game> games = service.getAllGames();
        gameModel.setAll(games);

    }

    public void onPressPurchase(){
        Game game = gameTable.getSelectionModel().getSelectedItem();
        service.purchaseGame(user.getEmail(), game.getId());
        reload();
    }

    public void onPressExit(){
        stage.close();
    }

    //get game names that are similar to the search field
    public void onPressSearch(){
        String search = searchField.getText();
        ArrayList<Game> games = service.getAllGames();
        ArrayList<Game> filteredGames = new ArrayList<>();
        for(Game game: games){
            if(game.getName().toLowerCase().contains(search.toLowerCase())){
                filteredGames.add(game);
            }
        }
        gameModel.setAll(filteredGames);
    }




}
