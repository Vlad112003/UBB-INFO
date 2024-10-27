package com.example.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.Domain.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class AdminRepo implements AdminInterface {

    private JdbcUtils dbUtils;
    private Connection connection;
    private static final Logger logger = LogManager.getLogger();

    public AdminRepo(Properties props) {
        logger.info("Initializing AdminRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        connection = dbUtils.getConnection();
        try{
            System.out.println("MUIEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE \n\n\n\n\n");
            System.out.println(connection.getMetaData().getURL());
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public Iterable<Admin> find_all() {
        logger.traceEntry("finding all admins");
        List<Admin> admins = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from Admin")) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                UUID id = UUID.fromString(set.getString("id"));
                String username = set.getString("username");
                String password = set.getString("password");
                admins.add(new Admin(id, username, password));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(admins);
        return admins;
    }

    public Admin find_by_username_and_password(String username,String password){
        logger.traceEntry("finding admin by username {}", username);
        try (PreparedStatement statement = connection.prepareStatement("select * from Admin where username=? and password=?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                UUID id = UUID.fromString(set.getString("id"));
                return new Admin(id, username, password);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public void delete(Admin entity) {
        logger.traceEntry("deleting admin {}", entity);
        try (PreparedStatement statement = connection.prepareStatement("delete from Admin where id=?")) {
            statement.setString(1, entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }

    @Override
    public void save(Admin entity) {
        logger.traceEntry("saving admin {}", entity);
        try (PreparedStatement statement = connection.prepareStatement("insert into Admin(id, username, password) values(?,?,?)")) {
            statement.setString(1, entity.getId().toString());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getPassword());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(UUID id, Admin entity) {
        logger.traceEntry("updating admin {}", id);
        try (PreparedStatement statement = connection.prepareStatement("update Admin set username=?, password=? " +
                "where id=?")) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
            statement.setString(3, id.toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }



}

