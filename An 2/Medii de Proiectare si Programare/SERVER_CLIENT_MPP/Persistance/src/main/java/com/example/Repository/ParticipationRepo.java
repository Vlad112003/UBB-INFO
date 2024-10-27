package com.example.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.Domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class ParticipationRepo implements ParticipationInterface {

    private JdbcUtils dbUtils;

    private Connection connection;

    private static final Logger logger = LogManager.getLogger();

    public ParticipationRepo(Properties props) {
        logger.info("Initializing ParticipationRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        connection = dbUtils.getConnection();
    }


    @Override
    public Iterable<Participation> find_all() {
        logger.traceEntry("finding all participations");
        List<Participation> participations = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select p.participant, p.contest, " +
                "pp.name as participant_name, pp.age as participant_age, " +
                "c.distance as contest_distance, c.style as contest_style " +
                "from participation p " +
                "inner join participant pp on p.participant = pp.id " +
                "inner join contest c on p.contest = c.id")) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                UUID participantId = UUID.fromString(set.getString("participant"));
                String participantName = set.getString("participant_name");
                int participantAge = set.getInt("participant_age");
                Participant participant = new Participant(participantId, participantName, participantAge);

                UUID contestId = UUID.fromString(set.getString("contest"));
                DistEnum contestDistance = DistEnum.valueOf(set.getString("contest_distance"));
                StyleEnum contestStyle = StyleEnum.valueOf(set.getString("contest_style"));
                Contest contest = new Contest(contestId, contestDistance, contestStyle);

                participations.add(new Participation(participant, contest));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(participations);
        return participations;
    }


    @Override
    public void delete(Participation entity) {
        logger.traceEntry("deleting participation {}", entity);
        try (PreparedStatement statement = connection.prepareStatement("delete from Participation where participant=? and contest=?")) {
            statement.setString(1, entity.getParticipant().getId().toString());
            statement.setString(2, entity.getContest().getId().toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }

    @Override
    public void save(Participation entity) {
        logger.traceEntry("saving participation {}", entity);
        try (PreparedStatement statement = connection.prepareStatement("insert into Participation(participant, contest) values(?, ?)")) {
            statement.setString(1, entity.getParticipant().getId().toString());
            statement.setString(2, entity.getContest().getId().toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(UUID id,Participation entity) {

    }

    @Override
    public int getNumberOfParticipants(UUID contest_id) {
        logger.traceEntry("getting number of participants for contest {}", contest_id);
        try (PreparedStatement statement = connection.prepareStatement("select count(*) as count from Participation where contest=?")) {
            statement.setString(1, contest_id.toString());
            ResultSet set = statement.executeQuery();
            set.next();
            int count = set.getInt("count");
            logger.traceExit(count);
            return count;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public List<Contest> getContests(UUID id_participant) {
        logger.traceEntry("getting contests for participant: {}", id_participant);
        List<Contest> contests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT c.* " +
                "FROM Contest c " +
                "JOIN Participation p ON c.id = p.contest " +
                "WHERE p.participant = ?")) {
            statement.setString(1, id_participant.toString());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                UUID id = UUID.fromString(set.getString("id"));
                DistEnum distance = DistEnum.valueOf(set.getString("distance"));
                StyleEnum style = StyleEnum.valueOf(set.getString("style"));
                contests.add(new Contest(id, distance, style));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(contests);
        return contests;
    }

}
