package client.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import model.Configuration;
import model.Game;
import model.Player;
import services.IObserver;
import services.IService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

public class MainController implements IObserver {
    @FXML
    public Label poz1;
    @FXML
    public Label poz2;
    @FXML
    public Label poz3;
    @FXML
    public Label poz5;
    @FXML
    public Label poz4;
    public Button btnRandom;
    public Label nPosition;
    public Label n;

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

        Game game = null;
        try {
            game = service.getOneByUsername(loggedInPlayer.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var confId=game.getConf();
        Configuration conf;
        try {
            conf=service.getConfigurationById(confId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        poz1.setText(String.valueOf(conf.getPoz1()));
        poz2.setText(String.valueOf(conf.getPoz2()));
        poz3.setText(String.valueOf(conf.getPoz3()));
        poz4.setText(String.valueOf(conf.getPoz4()));
        poz5.setText(String.valueOf(conf.getPoz5()));

    }

    public void generateRandom(ActionEvent event) {
        Random random=new Random();
        int n= random.nextInt(1,4);
        nPosition.setText(String.valueOf(n));
        this.n.setText(String.valueOf(n));
        try {
            var rez=this.service.choosePosition(loggedInPlayer.getId(),n);
            if(Objects.equals(rez, "Finished")){
                btnRandom.setDisable(true);
            }else{
                var rezz=this.service.getOneByUsername(loggedInPlayer.getUsername());
                nPosition.setText(String.valueOf(rezz.getPoz_curent()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    //TODO
    //suma de bani=pct
    //scadem din pct valoarea pozitiei cand cade pe o pozitie neocupata
    //adunam 5 la pct cadn ajunge din nou la start
    //adaugam pe Game poz_curent
    //cand da click pe buton generam nr n ranndom si facem pe pozitia curenta(adaugam n si %5) si tries
    //pe tries face cu eager
    //pe tries o sa avem poz1, poz2 ,etc
    // daca ajunge pe poz1 si n =3 => in tries vine si poz4
    //gen n chiar la inceput si dupa se muta, nu incepe neaparat de e poz1

}