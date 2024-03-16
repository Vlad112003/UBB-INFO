package ro.ubbcluj.cs.map.Repository;

import ro.ubbcluj.cs.map.Domain.Entity;
import ro.ubbcluj.cs.map.Domain.Movie;
import ro.ubbcluj.cs.map.Repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class MovieDBRepository implements Repository<Long, Movie> {
    private String url;
    private String username;
    private String password;

    ArrayList list = new ArrayList();

    public MovieDBRepository(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Override
    public Optional findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM movies WHERE id = ?");) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                int year = resultSet.getInt("year");
                Movie m1 = new Movie(title, director, year);
                m1.setId(id);
                list.add(m1);
                return Optional.of(m1);


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

        @Override
    public Iterable findAll() {
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movies");){
            var resultSet = statement.executeQuery();
            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                int year = resultSet.getInt("year");
                Movie m1 = new Movie(title, director, year);
                m1.setId(id);
                list.add(m1);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional save(Movie entity) {
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO movies (title, director, year) VALUES (?, ?, ?)");){
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDirector());
            statement.setInt(3, entity.getYear());
            int affectedRows = statement.executeUpdate();
            if(affectedRows == 0){
                return Optional.of(entity);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional delete(Long id) {
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM movies WHERE id = ?");){
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if(affectedRows == 0){
                return Optional.empty();
            }
            return Optional.of(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional update(Movie entity) {
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("UPDATE movies SET title = ?, director = ?, year = ? WHERE id = ?");){
            statement.setString(1, ((Movie)entity).getTitle());
            statement.setString(2, ((Movie)entity).getDirector());
            statement.setInt(3, ((Movie)entity).getYear());
            statement.setLong(4, entity.getId());
            int affectedRows = statement.executeUpdate();
            if(affectedRows == 0){
                return Optional.of(entity);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
