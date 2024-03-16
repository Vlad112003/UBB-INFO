package repository;

import domain.FriendShip;
import domain.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class FriendshipDatabase extends AbstractDatabaseRepository<UUID, FriendShip> {

    Repository userRepo;

    public FriendshipDatabase(String url, String username, String password, Repository userRepo){
        super(url, username, password, "friendships");
        this.userRepo = userRepo;
        loadData();
    }


    @Override
    protected void loadData() {
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
            LocalDateTime from = LocalDateTime.parse(resultSet.getString("friendsFrom"));

            Optional u1 = userRepo.findOne(id_user1);
            Optional u2 = userRepo.findOne(id_user2);

            friendship = new FriendShip(u1, u2, from);
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
        Set<FriendShip> friendships = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("Select * from " + tableName + ";");
            ResultSet resultSet = statement.executeQuery();)
        {
            while(resultSet.next()){
                UUID id_ = UUID.fromString(resultSet.getString("id"));
                UUID id_user1 = UUID.fromString(resultSet.getString("id_user1"));
                UUID id_user2 = UUID.fromString(resultSet.getString("id_user2"));
                LocalDateTime from = LocalDateTime.parse(resultSet.getString("friendsFrom"));

                Optional u1 = userRepo.findOne(id_user1);
                Optional u2 = userRepo.findOne(id_user2);

                FriendShip friendship = new FriendShip(u1, u2, from);
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
    public Optional<FriendShip> save (FriendShip entity){
        Optional<FriendShip> f = super.save(entity);
        if (f == null){
            try(Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + " "
                        + "VALUES ('" + entity.getId().toString() +"', '" + entity.getFriendsFrom().toString() + "', '"
                        + entity.getUser1().getId().toString() + "', '" + entity.getUser2().getId().toString()  + "');");
                ResultSet resultSet = statement.executeQuery();
            )
            {

            }
            catch (SQLException e){
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

}
