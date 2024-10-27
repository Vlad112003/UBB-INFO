package project.problema11final.Repository;

import project.problema11final.Domain.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

public class EntryDB implements EntryRepoInterface{

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public EntryDB(Properties props) {
        dbUtils = new JdbcUtils(props);
        logger.info("Initializing EntryDBRepository with properties: {}", props);
    }
    @Override
    public Entry findOne(UUID uuid) {
        logger.info("Entry find one with {}", uuid);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from entries where id=?")) {
            preStmt.setString(1, uuid.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String raceId = result.getString("raceId");
                    UUID raceUUID = UUID.fromString(raceId);
                    String participantId = result.getString("participantId");
                    UUID participantUUID = UUID.fromString(participantId);
                    Entry entry = new Entry(raceUUID, participantUUID);
                    entry.setId(uuid);
                    logger.info("Exit findOne with result {}", entry);
                    return entry;
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
        logger.info("Exit findOne with result null");
        return null;
    }

    @Override
    public Iterable<Entry> findAll() {
        logger.info("Entry findAll");
        HashSet<Entry> entries = new HashSet<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from entries")){
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    String id = result.getString("id");
                    String raceId = result.getString("raceId");
                    UUID raceUUID = UUID.fromString(raceId);
                    String participantId = result.getString("participantId");
                    UUID participantUUID = UUID.fromString(participantId);
                    Entry entry = new Entry(raceUUID, participantUUID);
                    entry.setId(UUID.fromString(id));
                    entries.add(entry);
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
        logger.info("Exit findAll with result {}", entries);
        return entries;
    }

    @Override
    public Entry save(Entry entity) {
        logger.info("Entry save with entity {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into entries (id, raceId, participantId) values (?,?,?)")){
            preStmt.setString(1, entity.getId().toString());
            preStmt.setString(2, entity.getRaceId().toString());
            preStmt.setString(3, entity.getParticipantId().toString());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit save with entity {}", entity);
        return null;
    }

    @Override
    public Entry delete(UUID uuid) {
        logger.info("Entry delete with {}", uuid);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from entries where id=?")){
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
    public Entry update(Entry entity) {
        logger.info("Entry update with entity {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update entries set raceId=?, participantId=? where id=?")){
            preStmt.setString(1, entity.getRaceId().toString());
            preStmt.setString(2, entity.getParticipantId().toString());
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
        HashSet<Entry> entries = (HashSet<Entry>) findAll();
        int size = entries.size();
        logger.info("Exit size with result {}", size);
        return size;
    }
}
