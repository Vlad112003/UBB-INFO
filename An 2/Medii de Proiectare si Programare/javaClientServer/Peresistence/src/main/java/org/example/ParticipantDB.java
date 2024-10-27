package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.UUID;

public class ParticipantDB implements ParticipantRepoInterface{

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public ParticipantDB(Properties props) {
        dbUtils = new JdbcUtils(props);
        logger.info("Initializing ParticipantDBRepository with properties: {}", props);
    }


    @Override
    public Participant findOne(UUID uuid) {
        logger.info("Participant find one with {}", uuid);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from participants where id=?")) {
            preStmt.setString(1, uuid.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String name = result.getString("name");
                    String team = result.getString("team");
                    int engine = result.getInt("engineCapacity");
                    Participant participant = new Participant(name, team, engine);
                    participant.setId(uuid);
                    return participant;
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
    public Iterable<Participant> findAll() {
        logger.info("Entry participant findAll");
        HashSet<Participant> participants = new HashSet<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from participants")){
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    String id = result.getString("id");
                    String name = result.getString("name");
                    String team = result.getString("team");
                    int engine = result.getInt("engineCapacity");
                    Participant participant = new Participant(name, team, engine);
                    participant.setId(UUID.fromString(id));
                    participants.add(participant);
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
        logger.info("Exit participant findAll with result {}", participants);
        return participants;
    }

    @Override
    public Participant save(Participant entity) {
        logger.info("Participant save with entity {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into participants (id, name, team, engineCapacity) values (?,?,?,?)")){
            preStmt.setString(1, entity.getId().toString());
            preStmt.setString(2, entity.getName());
            preStmt.setString(3, entity.getTeam());
            preStmt.setInt(4, entity.getEngineCapacity());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit participant save with result {}", entity);
        return entity;
    }

    @Override
    public Participant delete(UUID uuid) {
        logger.info("Participant delete with {}", uuid);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from participants where id=?")){
            preStmt.setString(1, uuid.toString());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit participant delete with result {}", uuid);
        return null;
    }

    @Override
    public Participant update(Participant entity) {
        logger.info("Participant update with entity {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update participants set name=?, team=?, engineCapacity=? where id=?")){
            preStmt.setString(1, entity.getName());
            preStmt.setString(2, entity.getTeam());
            preStmt.setInt(3, entity.getEngineCapacity());
            preStmt.setString(4, entity.getId().toString());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit participant update with result {}", entity);
        return entity;
    }

    @Override
    public int size() {
        logger.info("Entry participant size");
        HashSet<Participant> participants = (HashSet<Participant>) findAll();
        int size = participants.size();
        logger.info("Exit participant size with result {}", size);
        return size;
    }
}
