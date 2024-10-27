package com.example.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.Domain.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class ParticipantRepo implements ParticipantInterface {

    private JdbcUtils dbUtils;

    private Connection connection;

    private static final Logger logger = LogManager.getLogger();

    public ParticipantRepo(Properties props) {
        logger.info("Initializing ParticipantRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        connection = dbUtils.getConnection();
    }

    @Override
    public Iterable<Participant> find_all() {
        logger.traceEntry("finding all participants");
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from participant")) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                UUID id = UUID.fromString(set.getString("id"));
                String name = set.getString("name");
                int age = set.getInt("age");
                participants.add(new Participant(id, name, age));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(participants);
        return participants;
    }

    @Override
    public void delete(Participant entity) {
        logger.traceEntry("deleting participant {}", entity);
        try (PreparedStatement statement = connection.prepareStatement("delete from Participant where id=?")) {
            statement.setString(1, entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }

    @Override
    public void save(Participant entity) {
        logger.traceEntry("saving participant {}", entity);
        try (PreparedStatement statement = connection.prepareStatement("insert into Participant(id, name, age) values(?,?,?)")) {
            statement.setString(1, entity.getId().toString());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getAge());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(UUID id, Participant entity) {
        logger.traceEntry("updating participant {}", id);
        try (PreparedStatement statement = connection.prepareStatement("update Participant set name=?, age=? " +
                "where id=? ")) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getAge());
            statement.setString(3, id.toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }
}
