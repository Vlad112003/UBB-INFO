package ro.ubbcluj.cs.map.lab7.repository;

import ro.ubbcluj.cs.map.lab7.domain.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

//import static com.sun.tools.javac.util.Constants.format;

public class FriendshipDatabase extends AbstractDatabaseRepository<UUID, FriendShip> {

//    Repository<UUID, User> userRepo;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public FriendshipDatabase(String url, String username, String password){
        super(url, username, password, "friendships");
        loadData();
    }


    @Override
    public void loadData() {
        findAll_DB().forEach(super::save);
    }

    @Override
    public Optional<FriendShip> findOne(UUID id){
        FriendShip friendship = null;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM" + tableName + "WHERE id = " + id.toString() + ";");
            ResultSet resultSet = statement.executeQuery();) {

            resultSet.next();
            UUID id_ = UUID.fromString(resultSet.getString("id"));
            UUID id_user1 = UUID.fromString(resultSet.getString("id_user1"));
            UUID id_user2 = UUID.fromString(resultSet.getString("id_user2"));
            String from = resultSet.getString("friendsFrom");

            LocalDateTime parsed = LocalDateTime.parse(from, formatter);

            Optional<User> u1 = retrieveUser(id_user1);
            Optional<User> u2 = retrieveUser(id_user2);

            friendship = new FriendShip(u1, u2, parsed);
            friendship.setId(id_);

            return Optional.of(friendship);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return Optional.of(friendship);
    }
    @Override
    protected Iterable<FriendShip> findAll_DB() {
//        var audo = userRepo.findAll();
        Set<FriendShip> friendships = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("Select * from " + tableName + ";");
            ResultSet resultSet = statement.executeQuery();)
        {
            while(resultSet.next()){
                UUID id_ = UUID.fromString(resultSet.getString("id"));
                UUID id_user1 = UUID.fromString(resultSet.getString("id_user1"));
                UUID id_user2 = UUID.fromString(resultSet.getString("id_user2"));
                String from = resultSet.getString("friendsFrom");
//                System.out.println(from);
//                LocalDate parsed = LocalDate.parse(from);
                LocalDateTime parsed = LocalDateTime.parse(from);
//                System.out.println(parsed);

//                System.out.println(from);

                Optional<User> u1 = retrieveUser(id_user1);
                Optional<User> u2 = retrieveUser(id_user2);

                FriendShip friendship = new FriendShip(u1, u2, parsed);
                friendship.setId(id_);

                friendships.add(friendship);
            }
            return friendships;
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return friendships;
    }

    @Override
    public Optional<FriendShip> delete(UUID uuid) {
        Optional<FriendShip> f = super.delete(uuid);
        if (f.isPresent()){
            try(Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement("DELETE FROM friendships WHERE id = "+ "'"+ uuid.toString() + "'" +";");
            ) {
                statement.executeUpdate();
                return f;
            }
            catch (SQLException e){
                System.out.println(e);
            }
        }
        return f;
    }


    //tabelul friendships are 4 coloane: id, id_user1, id_user2, friendsFrom, toate sunt string-uri (varchar(100)).
    @Override
    public Optional<FriendShip> save (FriendShip entity){
        Optional<FriendShip> f = super.save(entity);

        if (f == null){
            try(Connection connection = DriverManager.getConnection(url, username, password)){
                PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?, ?, ?, ?);");
                String unparsed = entity.getFriendsFrom().format(formatter);
                LocalDateTime parsed = LocalDateTime.parse(unparsed,formatter);
                statement.setString(1, entity.getId().toString());
                statement.setString(2, entity.getUser1().getId().toString());
                statement.setString(3, entity.getUser2().getId().toString());
                statement.setString(4, parsed.toString());
                statement.executeUpdate();
            }
            catch(SQLException e){
                System.out.println(e);
            }
        }
        return f;
    }

    @Override
    public Optional<FriendShip> update(FriendShip entity) {
        Optional<FriendShip> friendShip = super.update(entity);
        if(! friendShip.equals(entity))
        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "UPDATE " + tableName + " set id1 = '" + entity.getUser1().getId().toString() + "' " + " where id = '" + entity.toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + " set id2 = '" + entity.getUser2().getId().toString() + "' " + " where id = '" + entity.toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + " set friendsFrom = '" + entity.getFriendsFrom().toString() + "' " + " where id = '" + entity.toString() + "';";
                statement.executeUpdate(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }

        return friendShip;
    }


    private Optional<User> retrieveUser(UUID userId) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?;");
        ) {
            statement.setString(1, userId.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                User user = new User(firstName, lastName, email, password);
                user.setId(id);

                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

}

