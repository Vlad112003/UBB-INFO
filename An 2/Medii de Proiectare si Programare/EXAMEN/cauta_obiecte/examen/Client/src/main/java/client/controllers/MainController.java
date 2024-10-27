package client.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DTOFinishedGame;
import model.Game;
import model.Player;
import services.IObserver;
import services.IService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class MainController implements IObserver {
    @FXML
    public Label box1;
    @FXML
    public Label box2;
    @FXML
    public Label box3;
    @FXML
    public Label box4;
    @FXML
    public Label box5;
    @FXML
    public Label box6;
    @FXML
    public Label box7;
    @FXML
    public Label box8;
    @FXML
    public Label box9;
    @FXML
    public Label score;
    @FXML
    public Label flagsPosition;
    @FXML
    public TextField guessText;
    @FXML
    public TableView<Game> scoresTable;
    @FXML
    public TableColumn<Game, String> usernameColumn;
    @FXML
    public TableColumn<Game, String> dateColumn;
    @FXML
    public TableColumn<Game, String> timeColumn;
    @FXML
    public TableColumn<Game, Integer> scoreColumn;
    @FXML
    public TableColumn<Game, Integer> numberOfFlagsGuessedColumn;

    private final ObservableList<Game> scores = FXCollections.observableArrayList();

    private IService service;
    private Player loggedInPlayer;
    private boolean gameFinished = false;

    public void setService(IService service) {
        this.service = service;
    }

    public void setLoggedInPlayer(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public void initialise() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("player"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        numberOfFlagsGuessedColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getFound().size()));
        try {
            scores.setAll(service.getScores());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        scoresTable.setItems(scores.sorted(Comparator.comparingInt(Game::getScore).reversed()));
    }

    private void finishGame() {
        DTOFinishedGame finishedGame;
        try {
            finishedGame = service.getFinishedGameInfo(loggedInPlayer.getId());
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
            return;
        }
        score.setText("Score: " + finishedGame.getScore());
        flagsPosition.setText("Flags position: " + finishedGame.getFlagsPosition());
    }

    public void guessAction() {
        if (gameFinished) {
            return;
        }
        try {
            int guess = Integer.parseInt(guessText.getText());
            if (guess < 1 || guess > 9) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Guess must be between 1 and 9", ButtonType.OK);
                alert.show();
                return;
            }
            String output = service.guess(loggedInPlayer.getId(), guess);
            if (output.contains("W")) {
                gameFinished = true;
                finishGame();
                output = output.substring(0, 1);
            }
            switch (guess) {
                case 1 -> box1.setText(output);
                case 2 -> box2.setText(output);
                case 3 -> box3.setText(output);
                case 4 -> box4.setText(output);
                case 5 -> box5.setText(output);
                case 6 -> box6.setText(output);
                case 7 -> box7.setText(output);
                case 8 -> box8.setText(output);
                case 9 -> box9.setText(output);
            }
            guessText.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameFinished(Collection<Game> gamesList) {
        scores.setAll(gamesList);
        scoresTable.setItems(scores.sorted(Comparator.comparingInt(Game::getScore).reversed()));
    }
}