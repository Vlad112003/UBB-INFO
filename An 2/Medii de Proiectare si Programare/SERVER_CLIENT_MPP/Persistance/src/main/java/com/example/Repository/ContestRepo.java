package com.example.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.Domain.Contest;
import com.example.Domain.DistEnum;
import com.example.Domain.StyleEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class ContestRepo implements ContestInterface{
    private JdbcUtils dbUtils;

    private Connection connection;

    private static final Logger logger = LogManager.getLogger();

    public ContestRepo(Properties props) {
        logger.info("Initializing ContestRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        connection = dbUtils.getConnection();
    }


    @Override
    public Iterable<Contest> find_all() {
        logger.traceEntry("finding all cars");
        List<Contest> contests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from contest")) {

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                UUID id = UUID.fromString(set.getString("id"));
                DistEnum distanta = DistEnum.valueOf(set.getString("distance"));
                StyleEnum stil = StyleEnum.valueOf(set.getString("style"));
                contests.add(new Contest(id, distanta, stil));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(contests);
        return contests;
    }

    @Override
    public void delete(Contest entity) {
        logger.traceEntry("deleting contest {}", entity);
        try (PreparedStatement statement = connection.prepareStatement("delete from Contest where id=?")) {
            statement.setString(1, entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }

    @Override
    public void save(Contest entity) {
        logger.traceEntry("saving contest {}", entity);
        try (PreparedStatement statement = connection.prepareStatement("insert into Contest(id, distance, style) values(?,?,?)")) {
            statement.setString(1, entity.getId().toString());
            statement.setString(2, entity.getDistance().toString());
            statement.setString(3, entity.getStyle().toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(UUID id,Contest entity) {
        logger.traceEntry("updating contest {}", id);
        try (PreparedStatement statement = connection.prepareStatement("update Contest set distance=?, style=? " +
                "where id=? ")) {
            statement.setString(1, entity.getDistance().toString());
            statement.setString(2, entity.getStyle().toString());
            statement.setString(3, id.toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }

}
