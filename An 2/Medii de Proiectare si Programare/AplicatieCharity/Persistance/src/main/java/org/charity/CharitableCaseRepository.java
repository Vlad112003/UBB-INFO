package org.charity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CharitableCaseRepository implements ICharitableCase {
    private final JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger(CharitableCaseRepository.class);

    public CharitableCaseRepository(Properties props) {
        logger.info("Initializing CharitableCaseRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from CharitableCase")) {
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
    public void save(CharitableCase entity) {
        logger.traceEntry("saving charitable case {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into CharitableCase (id, caseName, amount) values (?,?,?)")) {
            preStmt.setInt(1, entity.getId());
            preStmt.setString(2, entity.getCaseName());
            preStmt.setDouble(3, entity.getAmount());
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting charitable case with id {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from CharitableCase where id=?")) {
            preStmt.setInt(1, integer);
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public void update(Integer integer, CharitableCase entity) {
        logger.traceEntry("updating charitable case with id {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update CharitableCase set caseName=?, amount=? where id=?")) {
            preStmt.setString(1, entity.getCaseName());
            preStmt.setDouble(2, entity.getAmount());
            preStmt.setInt(3, integer);
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public CharitableCase findOne(Integer integer) {
        logger.traceEntry("finding charitable case with id {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from CharitableCase where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String caseName = result.getString("caseName");
                    double amount = result.getDouble("amount");
                    CharitableCase charitableCase = new CharitableCase(id, caseName, amount);
                    logger.traceExit(charitableCase);
                    return charitableCase;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("No charitable case found with id {}", integer);
        return null;
    }

    @Override
    public Iterable<CharitableCase> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<CharitableCase> cases = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from CharitableCase")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String caseName = result.getString("caseName");
                    double amount = result.getDouble("amount");
                    CharitableCase charitableCase = new CharitableCase(id, caseName, amount);
                    cases.add(charitableCase);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(cases);
        return cases;
    }
}
