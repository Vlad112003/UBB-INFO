package client.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Game;
import model.Player;
import services.IObserver;
import services.IService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class MainController implements IObserver {

    @FXML
    public Label numberOfTries;
//    private final ObservableList<Game> scores = FXCollections.observableArrayList();

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
//        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("player"));
//        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
//        numberOfTriesColumn.setCellValueFactory(param ->
//                new ReadOnlyObjectWrapper<>(Objects.equals(param.getValue().getIndiciu(), "") ?
//                        10 : param.getValue().getTries().size()));
//        hintColumn.setCellValueFactory(new PropertyValueFactory<>("indiciu"));
//        try {
//            scores.setAll(service.getScores());
//            System.out.println(scores);
//            for (int index = scores.size() - 1; index >= 0; index = index - 1) {
//                if (Objects.equals(scores.get(index).getPlayer(), loggedInPlayer.getUsername())) {
//                    scores.remove(index);
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        scoresTable.setItems(scores.sorted(Comparator.comparingInt(o -> o.getTries().size())));
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
//        DTOFinishedGame finishedGame;
//        try {
//            finishedGame = service.getFinishedGameInfo(loggedInPlayer.getId());
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
//            alert.show();
//            return;
//        }
//        numberOfTries.setText("Number of tries: " + finishedGame.getNumberOfTries());
//        hintPosition.setText("Hint position: " + finishedGame.getHintPosition());
    }

    @Override
    public void gameFinished(Collection<Game> games) {
//        scores.setAll(games);
//        scoresTable.setItems(scores.sorted(Comparator.comparingInt(o -> o.getTries().size())));
    }

}