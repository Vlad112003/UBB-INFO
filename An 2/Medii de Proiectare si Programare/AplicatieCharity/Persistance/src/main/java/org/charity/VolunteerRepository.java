package org.charity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.charity.Volunteer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VolunteerRepository implements IVolunteerRepository {
    private final JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger(VolunteerRepository.class);

    public VolunteerRepository(Properties props) {
        logger.info("Initializing VolunteerRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from Volunteer")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        return 0;
    }

    @Override
    public void save(Volunteer entity) {
        logger.traceEntry("saving volunteer {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Volunteer (id, username, password) values (?,?,?)")) {
            preStmt.setInt(1, entity.getId());
            preStmt.setString(2, entity.getUsername());
            preStmt.setString(3, entity.getPassword());
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting volunteer with id {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Volunteer where id=?")) {
            preStmt.setInt(1, integer);
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Volunteer entity) {
        logger.traceEntry("updating volunteer with id {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Volunteer set username=?, password=? where id=?")) {
            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPassword());
            preStmt.setInt(3, integer);
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public Volunteer findOne(Integer integer) {
        logger.traceEntry("finding volunteer with id {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Volunteer where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    Volunteer volunteer = new Volunteer(id, username, password);
                    logger.traceExit(volunteer);
                    return volunteer;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("No volunteer found with id {}", integer);
        return null;
    }

    public Volunteer findOneByUsername(String username) {
        logger.traceEntry("finding volunteer with id {}", username);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Volunteer where username=?")) {
            preStmt.setString(1, username);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String password = result.getString("password");
                    Volunteer volunteer = new Volunteer(id, username, password);
                    logger.traceExit(volunteer);
                    return volunteer;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("No volunteer found with username {}", username);
        return null;
    }

    @Override
    public Iterable<Volunteer> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Volunteer> volunteers = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Volunteer")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    Volunteer volunteer = new Volunteer(id, username, password);
                    volunteers.add(volunteer);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(volunteers);
        return volunteers;
    }
}
