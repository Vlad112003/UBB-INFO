package repository;

import domain.Entity;

import java.sql.*;
import java.util.Optional;

public abstract class AbstractDatabaseRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    protected String url;
    protected String username;
    protected String password;
    protected String tableName;

    public AbstractDatabaseRepository(String url, String username, String password, String tableName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        loadData();
    }

    protected abstract void loadData();

    protected abstract Iterable<E> findAll_DB();

    @Override
    public Optional<E> delete(ID id){
        Optional<E> entity = super.delete(id);
        if (entity != null) {
            try(Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement =
                        connection.prepareStatement("DELETE from " + tableName +
                                " WHERE id = " + id.toString() + ";");
                ResultSet resultSet = statement.executeQuery();) {
            }
            catch (SQLException e){
                System.out.println(e);
            }

        }
        return entity;

    }
}
