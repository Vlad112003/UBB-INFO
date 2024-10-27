package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.UUID;

public class RaceDB implements RaceRepoInterface{
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public RaceDB(Properties props) {
        dbUtils = new JdbcUtils(props);
        logger.info("Initializing RaceDBRepository with properties: {}", props);
    }

    @Override
    public Race findOne(UUID uuid) {
        logger.info("Entry find one with {}", uuid);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from races where id=?")) {
            preStmt.setString(1, uuid.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    int engineCapacity = result.getInt("engineCapacity");
                    Race race = new Race(engineCapacity);
                    race.setId(uuid);
                    return race;
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
    public Iterable<Race> findAll() {
        logger.info("Entry findAll");
        HashSet<Race> races = new HashSet<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from races")){
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    String id = result.getString("id");
                    int engineCapacity = result.getInt("engineCapacity");
                    Race race = new Race(engineCapacity);
                    race.setId(UUID.fromString(id));
                    races.add(race);
                }
            } catch (SQLException e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return races;
    }

    @Override
    public Race save(Race entity) {
        logger.info("Entry save with entity {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into races (id, engineCapacity) values (?, ?)")){
            preStmt.setString(1, entity.getId().toString());
            preStmt.setInt(2, entity.getEngineCapacity());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return entity;
    }

    @Override
    public Race delete(UUID uuid) {
        logger.info("Entry delete with id {}", uuid);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from races where id=?")){
            preStmt.setString(1, uuid.toString());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
            return findOne(uuid);
        }
        return null;
    }

    @Override
    public Race update(Race entity) {
        logger.info("Entry update with entity {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update races set engineCapacity=? where id=?")){
            preStmt.setInt(1, entity.getEngineCapacity());
            preStmt.setString(2, entity.getId().toString());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return entity;
    }

    @Override
    public int size() {
        logger.info("Entry size");
        HashSet<Race> races = (HashSet<Race>) findAll();
        int size = races.size();
        logger.info("Exit size with result {}", size);
        return size;

    }
}