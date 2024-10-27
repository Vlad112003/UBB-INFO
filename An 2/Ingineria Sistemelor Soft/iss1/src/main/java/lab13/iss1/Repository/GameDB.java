package lab13.iss1.Repository;

import lab13.iss1.Domain.Game;
import lab13.iss1.Domain.GameStatus;
import lab13.iss1.Domain.GenreType;
import lab13.iss1.Repository.Utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class GameDB {
    private JdbcUtils jdbcUtils;

    public GameDB(Properties props){
        jdbcUtils = new JdbcUtils(props);
    }

    public Game findOne(String name){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from game where name=?")){
            preStmt.setString(1, name);
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    UUID id = UUID.fromString(result.getString("id"));
                    String genre = result.getString("genre");
                    int playtime = result.getInt("playtime");
                    String status = result.getString("status");
                    float price = result.getFloat("price");
                    Game game = new Game(id, name, GenreType.valueOf(genre), playtime, GameStatus.valueOf(status), price);
                    return game;
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return null;
    }

    public ArrayList<Game> findAll(){
        Connection con = jdbcUtils.getConnection();
        ArrayList<Game> games = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from game")){
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    UUID id = UUID.fromString(result.getString("id"));
                    String name = result.getString("name");
                    String genre = result.getString("genre");
                    int playtime = result.getInt("playtime");
                    String status = result.getString("status");
                    float price = result.getFloat("price");
                    Game game = new Game(id, name, GenreType.valueOf(genre), playtime, GameStatus.valueOf(status), price);
                    games.add(game);
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return games;
    }

    public Game save(Game game){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into game (id, name, genre, playtime, status, price) values (?, ?, ?, ?, ?, ?)")){
            preStmt.setString(1, game.getId().toString());
            preStmt.setString(2, game.getName());
            preStmt.setString(3, game.getGenre().toString());
            preStmt.setInt(4, game.getPlaytime());
            preStmt.setString(5, game.getStatus().toString());
            preStmt.setFloat(6, game.getPrice());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return game;
    }

    public void update(Game game){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update game set genre=?, playtime=?, status=?, price=? where name=?")){
            preStmt.setString(1, game.getGenre().toString());
            preStmt.setInt(2, game.getPlaytime());
            preStmt.setString(3, game.getStatus().toString());
            preStmt.setFloat(4, game.getPrice());
            preStmt.setString(5, game.getName());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
    }

    public void delete(String name){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from game where name=?")){
            preStmt.setString(1, name);
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
    }

    public Game findByUUID(UUID uuid){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from game where id=?")){
            preStmt.setString(1, uuid.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String name = result.getString("name");
                    String genre = result.getString("genre");
                    int playtime = result.getInt("playtime");
                    String status = result.getString("status");
                    float price = result.getFloat("price");
                    Game game = new Game(uuid, name, GenreType.valueOf(genre), playtime, GameStatus.valueOf(status), price);
                    return game;
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return null;
    }

    public int size(){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from game")){
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return 0;
    }

    public void updatePlaytime(UUID id, int playtime){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update game set playtime=? where id=?")){
            preStmt.setInt(1, playtime);
            preStmt.setString(2, id.toString());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
    }
}
