package ro.ubbcluj.cs.map.lab82nd.repository;

import ro.ubbcluj.cs.map.lab82nd.domain.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MessageDatabase extends AbstractDatabaseRepository<UUID, Message>{

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public MessageDatabase(String url, String username, String password) {
        super(url, username, password, "messages");
        loadData();
    }

    @Override
    public void loadData(){
        findAll_DB().forEach(super::save);
    }

    @Override
    public Optional<Message> findOne(UUID id){
        Message message = null;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM" + tableName + "WHERE id = " + id.toString() + ";");
            ResultSet resultSet = statement.executeQuery();) {

            resultSet.next();
            UUID id_ = UUID.fromString(resultSet.getString("id"));
            UUID from = UUID.fromString(resultSet.getString("from"));
            UUID to = UUID.fromString(resultSet.getString("to"));
            String messageStr = resultSet.getString("message");
            String date = resultSet.getString("date");
            String replyToStr = resultSet.getString("replyto");
            UUID replyTo = null;
            if(!Objects.equals(replyToStr, "null")){
                replyTo = UUID.fromString(replyToStr);
            }


            LocalDateTime parsed = LocalDateTime.parse(date, formatter);

            message = new Message(from, to, messageStr, parsed, replyTo);
            message.setId(id_);

            return Optional.of(message);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return Optional.ofNullable(message);
    }

    @Override
    protected Iterable<Message> findAll_DB() {
        Set<Message> messages = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + ";");
            ResultSet resultSet = statement.executeQuery();) {

            while(resultSet.next()){
                UUID id = UUID.fromString(resultSet.getString("id"));
                UUID from = UUID.fromString(resultSet.getString("from"));
                UUID to = UUID.fromString(resultSet.getString("to"));
                String message = resultSet.getString("message");
                String date = resultSet.getString("date");
                String replyToStr = resultSet.getString("replyto");
                UUID replyTo = null;
                if(!Objects.equals(replyToStr, "null")){
                    replyTo = UUID.fromString(replyToStr);
                }

                LocalDateTime parsed = LocalDateTime.parse(date, formatter);

                Message message1 = new Message(from, to, message, parsed, replyTo);
                message1.setId(id);
                messages.add(message1);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return messages;
    }


    @Override
    public Optional<Message> delete(UUID id) {
        Optional<Message> message = super.delete(id);
        if(message.isPresent()){
            try(Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = " + id.toString() + ";");)
            {
                statement.executeUpdate();
                return message;
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return message;
    }

    //tabelul messages are 5 coloane, id, from, to, message, date, toate varchar

    @Override
    public Optional<Message> save (Message entity){
        Optional<Message> message = super.save(entity);
        if(message == null){
            try(Connection connection = DriverManager.getConnection(url, username, password);)
            {
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO " + tableName + " VALUES (" + "'" + entity.getId().toString() + "'" +", " +"'"+ entity.getFrom().toString() +"' "+ ", " + "'" + entity.getTo().toString() + "'" + ", " + "'" + entity.getMessage()+ "'" + ", " + "'"+  entity.getDate().format(formatter) +"'"+ ", '" + entity.getReplyTo() +"'" + ");";
                statement.executeUpdate(sql);
                return message;
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return message;
    }

    @Override
    public Optional<Message> update(Message entity){
        Optional<Message> message = super.update(entity);
        if(message.isPresent()){
            try(Connection connection = DriverManager.getConnection(url, username, password)){
                Statement statement = connection.createStatement();
                String sql = "UPDATE " + tableName + " SET from = '" + entity.getFrom().toString() + "', to = '" + entity.getTo().toString() + "' , message = '" + entity.getMessage() + "' , date = '" + entity.getDate().format(formatter) + "' , reply to = '"+ entity.getReplyTo() + "' WHERE id = '" + entity.getId().toString() + "' ;";

                statement.executeUpdate(sql);
            }
            catch (SQLException e){
                System.out.println(e);
            }
        }
        return message;
    }


}
