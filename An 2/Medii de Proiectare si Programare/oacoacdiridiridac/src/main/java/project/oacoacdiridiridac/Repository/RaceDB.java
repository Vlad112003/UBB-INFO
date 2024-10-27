package project.oacoacdiridiridac.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.oacoacdiridiridac.Repository.*;
import project.oacoacdiridiridac.Domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class RaceDB implements RaceRepoInterface {

    private List<Race> races = new ArrayList<>();

    private JdbcUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public RaceDB(Properties props) {
        logger.info("Initializing RaceDBRepository");
        jdbcUtils = new JdbcUtils(props);
    }


    @Override
    public Race getRaceByEngine(int engine) {
        logger.info("Entry getRaceByEngine with engine {}", engine);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from races where engineCapacity = ?")){
            preStmt.setInt(1, engine);
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String id = result.getString("id");
                    int engineCapacity = result.getInt("engineCapacity");
                    int noRacers = result.getInt("NoRacers");
                    Race race = new Race(engineCapacity, noRacers);
                    race.setId(UUID.fromString(id));
                    logger.info("Exit getRaceByEngine with result {}", race);
                    return race;
                }
            }
            catch (SQLException e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.info("Exit getRaceByEngine with result null");
        return null;
    }


    @Override
    public Race findOne(UUID uuid) {
        logger.info("Entry find one with {}", uuid);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from races where id = ?")) {
            preStmt.setString(1, uuid.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String id = result.getString("id");
                    int engineCapacity = result.getInt("engineCapacity");
                    int noRacers = result.getInt("NoRacers");
                    Race race = new Race(engineCapacity, noRacers);
                    race.setId(UUID.fromString(id));
                    logger.info("Exit findOne with result {}", race);
                    return race;
                }
            }
            catch (SQLException e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.info("Exit findOne with result null");
        return null;
    }

    @Override
    public Iterable<Race> findAll() {
        Connection con = jdbcUtils.getConnection();
        races.clear();
        try (PreparedStatement preStmt = con.prepareStatement ("select * from races")) {
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    String id = result.getString("id");
                    int engineCapacity = result.getInt("engineCapacity");
                    int noRacers = result.getInt("NoRacers");
                    Race race = new Race(engineCapacity, noRacers);
                    race.setId(UUID.fromString(id));
                    races.add(race);
                }
            }
            catch (SQLException e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB " + e);

        }
        return races;
    }

    @Override
    public Race save(Race entity) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Races (id, engineCapacity, NoRacers) values (?, ?, ?)")) {
            preStmt.setString(1, entity.getId().toString());
            preStmt.setInt(2, entity.getEngineCapacity());
            preStmt.setInt(3, entity.getNoRacers());
            races.add(entity);

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return entity;
    }

    @Override
    public Race delete(UUID uuid) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Races where id = ?")) {
            preStmt.setString(1, uuid.toString());
            preStmt.executeUpdate();
            Race race = findOne(uuid);
            races.remove(race);
            return race;
        }
        catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Race update(Race entity) {
        logger.info("Entry update with entity {}", entity);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Races set engineCapacity=?, NoRacers=? where id=?"))
        {
            Race race = findOne(entity.getId());
            races.remove(race);
            preStmt.setInt(1, entity.getEngineCapacity());
            preStmt.setInt(2, entity.getNoRacers());
            preStmt.setString(3, entity.getId().toString());
            preStmt.executeUpdate();
            Race newRace = new Race(entity.getEngineCapacity(), entity.getNoRacers());
            newRace.setId(entity.getId());
            races.add(newRace);
            return newRace;
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.info("Exit not updated with result {}", entity);
        return entity;
    }

    @Override
    public int size() {
        logger.info("Entry size");
        logger.info("Exit size");
        return races.size();
    }

    public boolean addOrganizerToRace(UUID raceId, UUID organizerId){
        logger.info("Entry addOrganizerToRace with raceId {} and organizerId {}", raceId, organizerId);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into organizers (id_user, id_race) values (?, ?)")){
            preStmt.setString(1, organizerId.toString());
            preStmt.setString(2, raceId.toString());
            preStmt.executeUpdate();
            logger.info("Exit addOrganizerToRace with result true");
            return true;
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.info("Exit addOrganizerToRace with result false");
        return false;
    }

    public boolean addTeamToRace(UUID raceId, UUID teamId) {
        logger.info("Entry addTeamToRace with raceId {} and teamId {}", raceId, teamId);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into racing_teams (id_team, id_race) values (?, ?)")) {
            preStmt.setString(1, teamId.toString());
            preStmt.setString(2, raceId.toString());
            preStmt.executeUpdate();
            logger.info("Exit addTeamToRace with result true");
            return true;
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.info("Exit addTeamToRace with result false");
        return false;
    }

    public Race getRaceByOrganizer(String id_organizer){
        logger.info("Entry getTeamByOrganizer with id_organizer {}", id_organizer);
        Race race = null;
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from organizers where id_user = ?")){
            preStmt.setString(1, id_organizer);
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String id_user = result.getString("id_user");
                    String id_race = result.getString("id_race");
                    race = findOne(UUID.fromString(id_race));
                    logger.info("Exit getTeamByOrganizer with result {}", race);
                    return race;
                }
            }
            catch (SQLException e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.info("Exit getTeamByOrganizer with result null");
        return race;
    }

}