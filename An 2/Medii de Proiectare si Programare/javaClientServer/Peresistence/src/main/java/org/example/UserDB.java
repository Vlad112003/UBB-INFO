package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.UUID;

public class UserDB implements UserRepoInterface{
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public UserDB(Properties props) {
        dbUtils = new JdbcUtils(props);
        logger.info("Initializing UserDBRepository with properties: {}", props);
    }

    @Override
    public User findOne(UUID uuid) {
        logger.info("Entry find one with {}", uuid);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from users where id=?")){
            preStmt.setString(1, uuid.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(username, password);
                    user.setId(uuid);
                    logger.info("Exit findOne with result {}", user);
                    return user;
                }
            } catch (SQLException e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        logger.info("Entry findAll");
        HashSet<User> users = new HashSet<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from users")){
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    String id = result.getString("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(username, password);
                    user.setId(UUID.fromString(id));
                    users.add(user);
                }
            } catch (SQLException e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return users;

    }

    @Override
    public User save(User entity) {
        logger.info("Entry save with entity {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into users (id, username, password) values (?,?,?)")){
            preStmt.setString(1, entity.getId().toString());
            preStmt.setString(2, entity.getUsername());
            preStmt.setString(3, entity.getPassword());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.info("Exit save with result {}", entity);
        return entity;
    }

    @Override
    public User delete(UUID uuid) {
        logger.info("Entry delete with {}", uuid);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from users where id=?")){
            preStmt.setString(1, uuid.toString());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
            return findOne(uuid);
        }
        logger.info("Exit delete with result null");
        return null;
    }

    @Override
    public User update(User entity) {
        logger.info("Entry update with entity {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update users set username=?, password=? where id=?")){
            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPassword());
            preStmt.setString(3, entity.getId().toString());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.info("Exit update with result {}", entity);
        return entity;
    }

    @Override
    public int size() {
        logger.info("Entry size");
        HashSet<User> users = (HashSet<User>) findAll();
        int size = users.size();
        logger.info("Exit size with result {}", size);
        return size;
    }
}
