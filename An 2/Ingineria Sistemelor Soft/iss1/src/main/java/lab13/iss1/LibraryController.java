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
import lab13.iss1.Domain.GameStatus;
import lab13.iss1.Domain.GenreType;
import lab13.iss1.Domain.User;
import lab13.iss1.Service.Service;

import java.util.ArrayList;

public class LibraryController {
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
    TableColumn<String, Game> nameColumn;
    @FXML
    TableColumn<GenreType, Game> genreColumn;
    @FXML
    TableColumn<Integer, Game> timeColumn;
    @FXML
    TableColumn<GameStatus, Game> statusColumn;

    @FXML
    ObservableList<Game> gameModel = FXCollections.observableArrayList();

    @FXML
    void initialize(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("playtime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        gameTable.setItems(gameModel);
    }

    public void initModel(){
        gameModel.setAll(service.getGamesOwnedByUser(user.getEmail()));
    }

    public void reload(){
        gameModel.setAll(service.getGamesOwnedByUser(user.getEmail()));
    }

    @FXML
    Button exitButton;
    @FXML
    Button installButton;
    @FXML
    Button uninstallButton;
    @FXML
    TextField searchField;
    @FXML
    Button searchButton;

    public void onPressExit(){
        stage.close();
    }

    public void onPressInstall(){
        Game game = gameTable.getSelectionModel().getSelectedItem();
        service.installGame(user.getEmail(), game.getId());
        reload();
    }

    public void onPressUninstall(){
        Game game = gameTable.getSelectionModel().getSelectedItem();
        service.uninstallGame(user.getEmail(), game.getId());
        reload();
    }

    //get game names that are similar to the search field
    public void onPressSearch(){
        ArrayList<Game> games = service.getGamesOwnedByUser(user.getEmail());
        ArrayList<Game> filteredGames = new ArrayList<>();
        String search = searchField.getText().toLowerCase();
        for(Game game: games){
            if(game.getName().toLowerCase().contains(search)){
                filteredGames.add(game);
            }
        }
        gameModel.setAll(filteredGames);
    }
}
