package ro.ubbcluj.cs.map.lab72nd.repository;

import ro.ubbcluj.cs.map.lab72nd.domain.*;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class UserDatabase extends AbstractDatabaseRepository<UUID, User> {
    public UserDatabase(String url, String username, String password){
        super(url, username, password, "users");
        loadData();
    }

    public static Repository<UUID, User> getInstance() {
        return new UserDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
    }

    @Override
    public void loadData(){
        findAll_DB().forEach(super::save);
    }

    @Override
    public Optional<User> findOne(UUID id){
        User user = null;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM" + tableName + " WHERE id = " + id.toString() + ";");
            ResultSet resultSet = statement.executeQuery();) {

            resultSet.next();

            UUID id_ = UUID.fromString(resultSet.getString("id"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");

            user = new User(firstName, lastName, email, password);
            user.setId(id_);

            return Optional.of(user);
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return Optional.of(user);
    }

    @Override
    public Iterable<User> findAll(){
        return findAll_DB();
    }

    @Override
    protected Iterable<User> findAll_DB(){
        Set<User> users = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + ";");
            ResultSet resultSet = statement.executeQuery();) {

            while(resultSet.next()){
                UUID id = UUID.fromString(resultSet.getString("id"));
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                User user = new User(firstName, lastName, email, password);
                user.setId(id);
                users.add(user);
            }
            return users;
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return users;
    }

    @Override
    public Optional<User> delete(UUID id) {
        Optional<User> user = super.delete(id);
        if(user.isPresent()){
            try(Connection connection = DriverManager.getConnection(url, username, password)){
                PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = '" + id.toString() + "';");
                statement.executeUpdate();
                return user;
            }
            catch(SQLException e){
                System.out.println(e);
            }
        }
        return user;
    }

    //tabelul users are 5 coloane: id, firstName, lastName, email, password, toate sunt string-uri (varchar(100))
    @Override
    public Optional<User> save(User entity){
        Optional<User> u = super.save(entity);

        if (u == null){
            try(Connection connection = DriverManager.getConnection(url, username, password)){
                PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?);");
                statement.setString(1, entity.getId().toString());
                statement.setString(2, entity.getFirstName());
                statement.setString(3, entity.getLastName());
                statement.setString(4, entity.getEmail());
                statement.setString(5, entity.getPassword());
                statement.executeUpdate();
            }
            catch(SQLException e){
                System.out.println(e);
            }
        }
        return u;
    }

    @Override
    public Optional<User> update(User entity){
        Optional<User> user = super.update(entity);
        if(!user.equals(entity)){
            try(Connection connection = DriverManager.getConnection(url, username, password)){
                Statement statement = connection.createStatement();
                String sql = "UPDATE " + tableName + "SET firstName = '" + entity.getFirstName() + "' where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + "SET lastName = '" + entity.getLastName() + "' where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + "SET email = '" + entity.getEmail() + "' where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + "SET password = '" + entity.getPassword() +"' where id = '" + entity.getId().toString() +"';";
                statement.executeUpdate(sql);
            }
            catch(SQLException e){
                System.out.println(e);
            }
        }
        return user;
    }


}

