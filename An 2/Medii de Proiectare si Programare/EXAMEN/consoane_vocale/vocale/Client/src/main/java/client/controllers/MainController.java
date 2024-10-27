package client.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class MainController  implements IObserver {
    public Label cuvantLbl;
    public Label literaLbl;
    public TextField literaTxt;
    public Button btn;
    public Label puncteLbl;
    @FXML
    public TableView<Game> scoresTable;
    @FXML
    public TableColumn<Game, String> usernameColumn;
    @FXML
    public TableColumn<Game, String> timeColumn;
    @FXML
    public TableColumn<Game, Integer> numberOfTriesColumn;
    @FXML
    public TableColumn<Game, Integer> pctColumn;
    private IService service;
    private Player loggedInPlayer;
    private boolean gameFinished = false;
    private final ObservableList<Game> scores = FXCollections.observableArrayList();

    public void setService(IService service) {
        this.service = service;

    }

    public void setLoggedInPlayer(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }


    public void initialise() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("player"));
        pctColumn.setCellValueFactory(new PropertyValueFactory<>("pct"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        numberOfTriesColumn.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(param.getValue().getTries().size()));
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
        scoresTable.setItems(scores.sorted(Comparator.comparingInt(o -> o.getTries().size())));

        Game rez;
        try {
            rez = this.service.getGame(loggedInPlayer.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cuvantLbl.setText(rez.getMasca());
        puncteLbl.setText(String.valueOf(rez.getPct()));
    }

    public void finishGame(){
        try {
            var rez = this.service.getGame(loggedInPlayer.getId());
            if(Objects.equals(rez.getCuvant(), cuvantLbl.getText())){
                gameFinished=true;
                this.btn.setDisable(true);
                this.puncteLbl.setText(String.valueOf(rez.getPct()));

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void handleLitera(ActionEvent actionEvent) throws Exception {
//        finishGame();
        if(!gameFinished){
        var rez=this.service.chooseLitera(loggedInPlayer.getId(),literaTxt.getText());
        cuvantLbl.setText(rez);}
        finishGame();
    }

    @Override
    public void gameFinished(Collection<Game> games) throws Exception {
        this.initialise();
//        this.finishGame();
    }
}
