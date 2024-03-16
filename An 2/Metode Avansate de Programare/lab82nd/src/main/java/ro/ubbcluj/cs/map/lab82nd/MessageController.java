package ro.ubbcluj.cs.map.lab82nd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.lab82nd.controller.MessageAlert;
import ro.ubbcluj.cs.map.lab82nd.domain.Message;
import ro.ubbcluj.cs.map.lab82nd.domain.User;
import ro.ubbcluj.cs.map.lab82nd.service.Service;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageController {
    private Service service;
    private User userFrom;
    private User userTo;
    private Stage dialogStage;

    public void setService(Service service, User userFrom, User userTo, Stage stage) {
        this.service = service;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.dialogStage = stage;
//        this.service.getMessagesToUserForUser(userFrom, userTo).forEach(System.out::println);
        initModel();
    }

    @FXML
    private ListView<String> listView;



    @FXML
    TextArea messageTextField;

    @FXML
    void onPressExit(){
        dialogStage.close();
    }

    @FXML
    void onPressSend(){
        String selected = listView.getSelectionModel().getSelectedItem();

        if(selected == null){
        try {
            service.sendMessage(userFrom, userTo, messageTextField.getText());
            initModel();
            messageTextField.deleteText(0, messageTextField.getLength());
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else{
            String[] split = selected.split(": ");
            String message = split[1];
            try {
                UUID uuid = service.getMessageIdByMessage(message);
                service.sendReply(userFrom, userTo, messageTextField.getText(), uuid);
                initModel();
                messageTextField.deleteText(0, messageTextField.getLength());
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
    }

    ObservableList<String> messagesFromModel = FXCollections.observableArrayList();
    void initModel(){
        Iterable<Message> messagesTo = service.getMessagesToUserForUser(userFrom, userTo);
        Iterable<Message> messagesFrom = service.getMessagesFromUserForUser(userFrom, userTo);
        ArrayList<Message> messages = new ArrayList<>();
        for (Message m : messagesTo){
            messages.add(m);
        }
        for (Message m : messagesFrom){
            messages.add(m);
        }
        messages.sort(Comparator.comparing(Message::getDate));
        ArrayList<String> messagesString = new ArrayList<>();
        for(Message message : messages){
            if(message.getReplyTo() == null){
            String from = service.getUserByUUID(message.getFrom()).getFirstName() + " " + service.getUserByUUID(message.getFrom()).getLastName()+ "@";
            String ora;
            if (message.getDate().getHour() < 10){
                ora = "0" + message.getDate().getHour();
            }
            else{
                ora = "" + message.getDate().getHour();
            }
            String minut;
            if (message.getDate().getMinute() < 10){
                minut = "0" + message.getDate().getMinute();
            }
            else{
                minut = "" + message.getDate().getMinute();
            }
            from = from + ora + ":" + minut + ": ";
            String mesaj = message.getMessage();
            String mesajFinal = from + mesaj;
            messagesString.add(mesajFinal);
            }
            else {
                String from = service.getUserByUUID(message.getFrom()).getFirstName() + " " + service.getUserByUUID(message.getFrom()).getLastName()+ "@";
                String ora;
                if (message.getDate().getHour() < 10){
                    ora = "0" + message.getDate().getHour();
                }
                else{
                    ora = "" + message.getDate().getHour();
                }
                String minut;
                if (message.getDate().getMinute() < 10){
                    minut = "0" + message.getDate().getMinute();
                }
                else{
                    minut = "" + message.getDate().getMinute();
                }
                from = from + ora + ":" + minut;
                String replyTo = service.getMessageByID(message.getReplyTo());
                from = from + "#reply to: \"" + replyTo + "\": ";
                String mesaj = message.getMessage();
                String mesajFinal = from + mesaj;
                messagesString.add(mesajFinal);
            }
        }
        messagesFromModel.setAll(StreamSupport.stream(messagesString.spliterator(), false).collect(Collectors.toList()));
        listView.setItems(messagesFromModel);

        listView.scrollTo(messagesString.size() - 1);
    }

    public void onPressRefresh(){
        initModel();
    }
}
