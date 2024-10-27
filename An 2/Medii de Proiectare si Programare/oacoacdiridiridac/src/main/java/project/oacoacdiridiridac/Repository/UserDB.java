package project.oacoacdiridiridac.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.oacoacdiridiridac.Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class UserDB implements UserRepoInterface{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public UserDB(Properties props) {

        dbUtils=new JdbcUtils(props);
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
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    String email = result.getString("email");
                    String password = result.getString("password");
                    String type = result.getString("type");
                    User user = new User(firstName, lastName, email, password, type);
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
        List<User> users = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from users")) {
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    UUID id = UUID.fromString(result.getString("id"));
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    String email = result.getString("email");
                    String password = result.getString("password");
                    String type = result.getString("type");
                    User user = new User(firstName, lastName, email, password, type);
                    user.setId(id);
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
        logger.info("Exit findAll");
        return users;

    }

    @Override
    public User save(User entity) {
        logger.info("Entry save with entity {}", entity);
       Connection con = dbUtils.getConnection();
       try(PreparedStatement preStmt = con.prepareStatement("insert into users values(?,?,?,?,?,?)")){
           preStmt.setString(1, entity.getId().toString());
           preStmt.setString(2, entity.getFirstName());
           preStmt.setString(3, entity.getLastName());
           preStmt.setString(4, entity.getEmail());
           preStmt.setString(5, entity.getPassword());
           preStmt.setString(6, entity.getType().toString());
           preStmt.executeUpdate();

       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       logger.info("Exit save with result {}", entity);
       return entity;

    }

    @Override
    public User delete(UUID uuid) {
        logger.info("Entry delete with id {}", uuid);
        Connection con = dbUtils.getConnection();
        User user = findOne(uuid);
        try(PreparedStatement preStmt = con.prepareStatement("delete from users where id=?")){
            preStmt.setString(1, uuid.toString());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.info("Exit delete with result {}", user);
        return user;
    }

    @Override
    public User update(User entity) {
        logger.info("Entry update with entity {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update users set firstName=?, lastName=?, email=?, password=?, type=? where id=?")){
            preStmt.setString(1, entity.getFirstName());
            preStmt.setString(2, entity.getLastName());
            preStmt.setString(3, entity.getEmail());
            preStmt.setString(4, entity.getPassword());
            preStmt.setString(5, entity.getType().toString());
            preStmt.setString(6, entity.getId().toString());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.info("Exit update with result {}", entity);
        return entity;
    }

    @Override
    public int size() {
        logger.info("Entry size");
        List<User> users = (List<User>) findAll();
        logger.info("Exit size");
        return users.size();
    }

    @Override
    public User findByEmail(String email) {
        logger.info("Entry findByEmail with email {}", email);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from users where email=?")){
            preStmt.setString(1, email);
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    UUID id = UUID.fromString(result.getString("id"));
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    String password = result.getString("password");
                    String type = result.getString("type");
                    User user = new User(firstName, lastName, email, password, type);
                    user.setId(id);
                    logger.info("Exit findByEmail with result {}", user);
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
        logger.info("Exit findByEmail with result null");
        return null;
    }

    @Override
    public Iterable<User> findByType(String type) {
        logger.info("Entry findByType with type {}", type);
        Connection con = dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from users where type=?")){
            preStmt.setString(1, type);
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    UUID id = UUID.fromString(result.getString("id"));
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    String email = result.getString("email");
                    String password = result.getString("password");
                    User user = new User(firstName, lastName, email, password, type);
                    user.setId(id);
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
        logger.info("Exit findByType with result {}", users);
        return users;
    }
}
