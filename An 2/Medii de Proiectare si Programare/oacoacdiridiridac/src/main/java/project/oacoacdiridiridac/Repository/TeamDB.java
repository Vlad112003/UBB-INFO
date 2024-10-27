package project.oacoacdiridiridac.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.oacoacdiridiridac.Domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class TeamDB implements TeamRepoInterface{

    private JdbcUtils jdbcUtils;

    private static final Logger logger= LogManager.getLogger();

    public TeamDB(Properties props) {
        jdbcUtils=new JdbcUtils(props);
        logger.info("Initializing TeamDBRepository");
    }


    @Override
    public Team findOne(UUID uuid) {
        logger.info("Entry find one with {}", uuid);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from teams where id=?")){
            preStmt.setString(1, uuid.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String name = result.getString("name");
                    Team team = new Team(name);
                    team.setId(uuid);
                    logger.info("Exit findOne with result {}", team);
                    return team;
                }
            } catch (Exception e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        catch (Exception e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit findOne with result null");
        return null;
    }

    @Override
    public Iterable<Team> findAll() {
        logger.info("Entry findAll");
       Connection con = jdbcUtils.getConnection();
        List<Team> teams = new ArrayList<>();
         try(PreparedStatement preStmt = con.prepareStatement("select * from teams")) {
              try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                     UUID id = UUID.fromString(result.getString("id"));
                     String name = result.getString("name");
                     Team team = new Team(name);
                     team.setId(id);
                     teams.add(team);
                }
              } catch (Exception e) {
                logger.error(e);
                System.out.println("Error DB " + e);
              }
         }
         catch (Exception e){
              logger.error(e);
              System.out.println("Error DB "+e);
         }
         logger.info("Exit findAll with result {}", teams);
         return teams;
    }

    @Override
    public Team save(Team entity) {
        logger.info("Entry save with entity {}", entity);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into teams (id, name) values (?, ?)")){
            preStmt.setString(1, entity.getId().toString());
            preStmt.setString(2, entity.getName());
            preStmt.executeUpdate();
        }
        catch (Exception e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit save with result {}", entity);
        return entity;
    }

    @Override
    public Team delete(UUID uuid) {
        logger.info("Entry delete with {}", uuid);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from teams where id=?")){
            preStmt.setString(1, uuid.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String name = result.getString("name");
                    Team team = new Team(name);
                    team.setId(uuid);
                    logger.info("Exit delete with result {}", team);
                    return team;
                }
            } catch (Exception e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        catch (Exception e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit delete with result null");
        return null;
    }

    @Override
    public Team update(Team entity) {
        logger.info("Entry update with entity {}", entity);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update teams set name=? where id=?")){
            preStmt.setString(1, entity.getName());
            preStmt.setString(3, entity.getId().toString());
            preStmt.executeUpdate();
        }
        catch (Exception e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit update with result {}", entity);
        return entity;
    }

    @Override
    public int size() {
        logger.info("Entry size");
        List<Team> teams = (List<Team>) findAll();
        logger.info("Exit size with result {}", teams.size());
        return teams.size();
    }

    @Override
    public Team getTeamByName(String name) {
        logger.info("Entry getTeamByName with name {}", name);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from teams where name=?")){
            preStmt.setString(1, name);
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    UUID id = UUID.fromString(result.getString("id"));
                    Team team = new Team(name);
                    team.setId(id);
                    logger.info("Exit getTeamByName with result {}", team);
                    return team;
                }
            } catch (Exception e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        catch (Exception e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit getTeamByName with result null");
        return null;
    }

    @Override
    public Iterable<Team> getTeamByRacer(String id_user) {
        logger.info("Entry getTeamByRacer with id_user {}", id_user);
        Connection con = jdbcUtils.getConnection();
        List<Team> teams = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from members where id_user=?")){
            preStmt.setString(1, id_user);
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    String id_team = result.getString("id_team");
                    Team team = findOne(UUID.fromString(id_team));
                    teams.add(team);
                }
                logger.info("Exit getTeamByRacer with result {}", teams);
                return teams;
            } catch (Exception e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        catch (Exception e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit getTeamByRacer with result null");
        return null;

    }

    public void addUserToTeam(String userId, String teamId){
        logger.info("Entry addUserToTeam with userId {} and teamId {}", userId, teamId);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into members (id_user, id_team) values (?, ?)")){
            preStmt.setString(1, userId);
            preStmt.setString(2, teamId);
            preStmt.executeUpdate();
        }
        catch (Exception e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit addUserToTeam");

    }

    public String getMembers(String teamId) {
        logger.info("Entry getMembers with teamId {}", teamId);
        Connection con = jdbcUtils.getConnection();
        String members = "";
        try(PreparedStatement preStmt = con.prepareStatement("select * from members where id_team=?")){
            preStmt.setString(1, teamId);
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    String id_user = result.getString("id_user");
                    members += id_user + ",";
                }
                logger.info("Exit getMembers with result {}", members);
                return members;
            } catch (Exception e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        catch (Exception e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Exit getMembers with result null");
        return null;
    }


}
