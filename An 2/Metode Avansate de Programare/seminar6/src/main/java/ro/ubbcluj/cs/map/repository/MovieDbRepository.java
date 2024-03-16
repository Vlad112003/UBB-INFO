package ro.ubbcluj.cs.map.repository;

import ro.ubbcluj.cs.map.domain.Entity;
import ro.ubbcluj.cs.map.domain.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class MovieDbRepository implements Repository<Long, Movie>{

    private String url;
    private String user;
    private String password;

    public MovieDbRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Optional<Movie> findOne(Long id) {
        try(Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement  = connection.prepareStatement("SELECT * FROM movies WHERE id=?");)
        {
            statement.setLong(1,id);
            ResultSet r = statement.executeQuery();
            if (r.next()){
                String title = r.getString("title");
                String director = r.getString("director");
                int year = r.getInt("year");
                Movie m1 = new Movie(title, director,year);
                m1.setId(id);
                return Optional.of(m1);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Movie> findAll() {
        try(Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement  = connection.prepareStatement("SELECT * FROM movies");)
        {
            ArrayList<Movie> list = new ArrayList<>();
            ResultSet r = statement.executeQuery();
            while (r.next()){
                String title = r.getString("title");
                String director = r.getString("director");
                int year = r.getInt("year");
                Long id = r.getLong("id");
                Movie m1 = new Movie(title, director,year);
                m1.setId(id);
                list.add(m1);
            }
            return list;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Movie> save(Movie entity) {

        try(Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement  = connection.prepareStatement("INSERT INTO movies(title,director,year) VALUES (?,?,?)");)
        {
            statement.setString(1,entity.getTitle());
            statement.setString(2,entity.getDirector());
            statement.setInt(3,entity.getYear());
            int affectedRows = statement.executeUpdate();
            return affectedRows!=0? Optional.empty():Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Movie> delete(Long id) {
        try(Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement  = connection.prepareStatement("DELETE FROM movies WHERE ID = ?");)
        {
            var cv = findOne(id);
            statement.setLong(1,id);
            int affectedRows = statement.executeUpdate();
            return affectedRows==0? Optional.empty():cv;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Movie> update(Movie entity) {
        try(Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement  = connection.prepareStatement("UPDATE movies SET title = ?, director = ?, year = ? WHERE id = ?");)
        {
            statement.setString(1,entity.getTitle());
            statement.setString(2,entity.getDirector());
            statement.setInt(3,entity.getYear());
            statement.setLong(4,entity.getId());
            int affectedRows = statement.executeUpdate();
            return affectedRows!=0? Optional.empty():Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
