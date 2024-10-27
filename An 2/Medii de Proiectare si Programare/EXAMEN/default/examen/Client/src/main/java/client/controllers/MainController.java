package client.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.Player;
import services.IObserver;
import services.IService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class MainController implements IObserver {

    public Label lbl11;
    public Label lbl12;
    public Label lbl13;
    public Label lbl21;
    public Label lbl22;
    public Label lbl23;
    public Label lbl31;
    public Label lbl32;
    public Label lbl33;
    public Label pozCalcLbl;
    @FXML
    TableColumn usernameColumn;
    @FXML
    TableColumn dateColumn;
    @FXML
    TableColumn timeColumn;
    @FXML
    TableColumn pctColumn;
    private final ObservableList<Game> scores = FXCollections.observableArrayList();
    @FXML
    TableView table;

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
        pctColumn.setCellValueFactory(new PropertyValueFactory<>("pct"));
        try {
            scores.setAll(service.getScores());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        table.setItems(scores.sorted(Comparator.comparingInt(o -> o.getTries().size())));
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

    public void handle11(MouseEvent mouseEvent) {
        if(Objects.equals(this.lbl11.getText(), "")) {
            this.lbl11.setText("X");
            try {
                var rez=this.service.choosePosition(this.loggedInPlayer.getId(),1,1);
                this.pozCalcLbl.setText(rez);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handle12(MouseEvent mouseEvent) {
        if(Objects.equals(this.lbl12.getText(), ""))
            this.lbl12.setText("X");
        try {
            var rez=this.service.choosePosition(this.loggedInPlayer.getId(),1,2);
            this.pozCalcLbl.setText(rez);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void handle13(MouseEvent mouseEvent) {
        if(Objects.equals(this.lbl13.getText(), ""))
            this.lbl13.setText("X");
        try {
            var rez=this.service.choosePosition(this.loggedInPlayer.getId(),1,3);
            this.pozCalcLbl.setText(rez);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void handle21(MouseEvent mouseEvent) {
        if(Objects.equals(this.lbl21.getText(), ""))
            this.lbl21.setText("X");
        try {
            var rez=this.service.choosePosition(this.loggedInPlayer.getId(),2,1);
            this.pozCalcLbl.setText(rez);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handle22(MouseEvent mouseEvent) {
        if(Objects.equals(this.lbl22.getText(), ""))
            this.lbl22.setText("X");
        try {
            var rez=this.service.choosePosition(this.loggedInPlayer.getId(),2,2);
            this.pozCalcLbl.setText(rez);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handle23(MouseEvent mouseEvent) {
        if(Objects.equals(this.lbl23.getText(), ""))
            this.lbl23.setText("X");
        try {
            var rez=this.service.choosePosition(this.loggedInPlayer.getId(),2,3);
            this.pozCalcLbl.setText(rez);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handle31(MouseEvent mouseEvent) {
        if(Objects.equals(this.lbl31.getText(), ""))
            this.lbl31.setText("X");
        try {
            var rez=this.service.choosePosition(this.loggedInPlayer.getId(),3,1);
            this.pozCalcLbl.setText(rez);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handle32(MouseEvent mouseEvent) {
        if(Objects.equals(this.lbl32.getText(), ""))
            this.lbl32.setText("X");
        try {
            var rez=this.service.choosePosition(this.loggedInPlayer.getId(),3,2);
            this.pozCalcLbl.setText(rez);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handle33(MouseEvent mouseEvent) {
        if(Objects.equals(this.lbl33.getText(), ""))
            this.lbl33.setText("X");
        try {
            var rez=this.service.choosePosition(this.loggedInPlayer.getId(),3,3);
            this.pozCalcLbl.setText(rez);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}