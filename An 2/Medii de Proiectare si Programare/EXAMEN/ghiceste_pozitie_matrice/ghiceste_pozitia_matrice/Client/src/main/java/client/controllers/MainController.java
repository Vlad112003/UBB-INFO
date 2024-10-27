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
    public Label indiciu11;
    @FXML
    public Label indiciu21;
    @FXML
    public Label indiciu31;
    @FXML
    public Label indiciu41;
    @FXML
    public Label indiciu12;
    @FXML
    public Label indiciu22;
    @FXML
    public Label indiciu32;
    @FXML
    public Label indiciu42;
    @FXML
    public Label indiciu13;
    @FXML
    public Label indiciu23;
    @FXML
    public Label indiciu33;
    @FXML
    public Label indiciu43;
    @FXML
    public Label indiciu14;
    @FXML
    public Label indiciu24;
    @FXML
    public Label indiciu34;
    @FXML
    public Label indiciu44;
    @FXML
    public Label numberOfTries;
    @FXML
    public Label hintPosition;
    @FXML
    public TableView<Game> scoresTable;
    @FXML
    public TableColumn<Game, String> usernameColumn;
    @FXML
    public TableColumn<Game, String> dateColumn;
    @FXML
    public TableColumn<Game, String> timeColumn;
    @FXML
    public TableColumn<Game, Integer> numberOfTriesColumn;
    @FXML
    public TableColumn<Game, String> hintColumn;

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
        numberOfTriesColumn.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(Objects.equals(param.getValue().getIndiciu(), "") ?
                        10 : param.getValue().getTries().size()));
        hintColumn.setCellValueFactory(new PropertyValueFactory<>("indiciu"));
        try {
            scores.setAll(service.getScores());
            System.out.println(scores);
            for (int index = scores.size() - 1; index >= 0; index = index - 1) {
                if (Objects.equals(scores.get(index).getPlayer(), loggedInPlayer.getUsername())) {
                    scores.remove(index);
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        scoresTable.setItems(scores.sorted(Comparator.comparingInt(o -> o.getTries().size())));
    }

    private boolean isGameFinished(String output) {
        try {
            Double.parseDouble(output);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private String clickAction(int x, int y) {
        try {
            return service.choosePosition(loggedInPlayer.getId(), x, y);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void finishGame() {
        DTOFinishedGame finishedGame;
        try {
            finishedGame = service.getFinishedGameInfo(loggedInPlayer.getId());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
            return;
        }
        numberOfTries.setText("Number of tries: " + finishedGame.getNumberOfTries());
        hintPosition.setText("Hint position: " + finishedGame.getHintPosition());
    }

    @Override
    public void gameFinished(Collection<Game> games) {
        scores.setAll(games);
        scoresTable.setItems(scores.sorted(Comparator.comparingInt(o -> o.getTries().size())));
    }

    public void indiciu11Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(1, 1);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu11.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu11.setText(output);
    }

    public void indiciu21Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(2, 1);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu21.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu21.setText(output);
    }

    public void indiciu31Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(3, 1);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu31.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu31.setText(output);
    }

    public void indiciu41Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(4, 1);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu41.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu41.setText(output);
    }

    public void indiciu12Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(1, 2);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu12.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu12.setText(output);
    }

    public void indiciu22Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(2, 2);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu22.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu22.setText(output);
    }

    public void indiciu32Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(3, 2);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu32.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu32.setText(output);
    }

    public void indiciu42Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(4, 2);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu42.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu42.setText(output);
    }

    public void indiciu13Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(1, 3);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu13.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu13.setText(output);
    }

    public void indiciu23Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(2, 3);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu23.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu23.setText(output);
    }

    public void indiciu33Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(3, 3);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu33.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu33.setText(output);
    }

    public void indiciu43Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(4, 3);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu43.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu43.setText(output);
    }

    public void indiciu14Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(1, 4);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu14.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu14.setText(output);
    }

    public void indiciu24Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(2, 4);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu24.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu24.setText(output);
    }

    public void indiciu34Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(3, 4);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu34.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu34.setText(output);
    }

    public void indiciu44Click() {
        if (gameFinished) {
            return;
        }
        String output = clickAction(4, 4);
        if (isGameFinished(output)) {
            if (!output.equals("Finished")) {
                indiciu44.setText(output);
            }
            gameFinished = true;
            finishGame();
            return;
        }
        indiciu44.setText(output);
    }
}