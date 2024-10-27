package client.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Configuration;
import model.Game;
import model.Player;
import services.IObserver;
import services.IService;

import javax.swing.*;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainController implements IObserver {

    @FXML
    public Label numberOfPct;
    @FXML
    public Label poz1;
    @FXML
    public Label poz8;
    @FXML
    public Label poz9;
    @FXML
    public Label poz2;
    @FXML
    public Label poz10;
    @FXML
    public Label poz5;
    @FXML
    public Label poz4;
    @FXML
    public Label poz3;
    @FXML
    public Label poz7;
    @FXML
    public Label poz6;
    @FXML
    public TableView<Game> tableView;
    @FXML
    public TableColumn<Game, String> usernameCol;
    @FXML
    public TableColumn<Game,Integer> pctCol;
    @FXML
    public TableColumn durataCol;
    private final ObservableList<Game> scores = FXCollections.observableArrayList();

    private IService service;
    private Player loggedInPlayer;
    private boolean gameFinished = false;
    private Integer p1;
    private Configuration configuration;
    private Game game;

    public void setService(IService service) {
        this.service = service;
    }

    public void setLoggedInPlayer(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public void initialise() {
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("player"));
        pctCol.setCellValueFactory(new PropertyValueFactory<>("pct"));
        durataCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> data) {
                Game game = data.getValue();
                LocalTime startTime = LocalTime.parse(game.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                LocalTime endTime = LocalTime.now(); // Assuming current time is the end time of the game
                Duration duration = Duration.between(startTime, endTime);
                long durationInSeconds = duration.getSeconds();
                String durationString = String.valueOf(durationInSeconds);
                return new SimpleStringProperty(durationString);
            }
        });
        try {
            scores.setAll(service.getScores());
            System.out.println(scores);
//            for (int index = scores.size() - 1; index >= 0; index = index - 1) {
//                if (Objects.equals(scores.get(index).getPlayer(), loggedInPlayer.getUsername())) {
//                    scores.remove(index);
//                    break;
//                }
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        pctCol.setCellValueFactory(new PropertyValueFactory<>("pct"));
        tableView.setItems(scores.sorted(Comparator.comparingInt(o -> o.getTries().size())));
        p1=0;
        try {
            game = service.getOneByUsername(loggedInPlayer.getUsername());
            var conf=game.getConf();
            this.configuration=service.getConfById(conf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

    public Label checkLbl(String lbl){
        if("poz1".equals(lbl)){
            return poz1;
        } else if ("poz2".equals(lbl)) {
            return poz2;
        } else if ("poz3".equals(lbl)) {
            return poz3;
        } else if ("poz4".equals(lbl)) {
            return poz4;
        } else if ("poz5".equals(lbl)) {
            return poz5;
        } else if ("poz6".equals(lbl)) {
            return poz6;
        } else if ("poz7".equals(lbl)) {
            return poz7;
        } else if ("poz8".equals(lbl)) {
            return poz8;
        } else if ("poz9".equals(lbl)) {
            return poz9;
        }else{
            return poz10;
        }
    }
    public void poz1Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=1;
            this.poz1.setText(configuration.getPoz1());
        }else{
            try {
                this.poz1.setText(configuration.getPoz1());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,1);
                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz1.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void poz8Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=8;
            this.poz8.setText(configuration.getPoz8());
        }else{
            try {
                this.poz8.setText(configuration.getPoz8());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,8);
                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz8.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void poz9Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=9;
            this.poz9.setText(configuration.getPoz9());
        }else{
            try {
                this.poz9.setText(configuration.getPoz9());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,9);
                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz9.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void poz2Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=2;
            this.poz2.setText(configuration.getPoz2());
        }else{
            try {
                this.poz2.setText(configuration.getPoz2());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,2);
                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz2.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void poz10Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=10;
            this.poz10.setText(configuration.getPoz10());
        }else{
            try {
                this.poz10.setText(configuration.getPoz10());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,10);
                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz10.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void poz5Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=5;
            this.poz5.setText(configuration.getPoz5());
        }else{
            try {
                this.poz5.setText(configuration.getPoz5());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,5);
                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz5.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void poz4Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=4;
            this.poz4.setText(configuration.getPoz4());
        }else{
            try {
                this.poz4.setText(configuration.getPoz4());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,4);
                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz4.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void poz3Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=3;
            this.poz3.setText(configuration.getPoz3());
        }else{
            try {
                this.poz3.setText(configuration.getPoz3());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,3);
                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz3.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void poz7Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=7;
            this.poz7.setText(configuration.getPoz7());
        }else{
            try {
                this.poz7.setText(configuration.getPoz7());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,7);

                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz7.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void poz6Click(MouseEvent mouseEvent) {
        if(p1==0){
            p1=6;
            this.poz6.setText(configuration.getPoz6());
        }else{
            try {
                this.poz6.setText(configuration.getPoz6());
                var rez=service.choosePosition(loggedInPlayer.getId(),p1,6);

                wait(1000);
                if(Objects.equals(rez, "Unequal")){
                    this.poz6.setText("");
                    String lbl="poz"+p1;
                    Label l=checkLbl(lbl);
                    l.setText("");
                }
                p1=0;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}