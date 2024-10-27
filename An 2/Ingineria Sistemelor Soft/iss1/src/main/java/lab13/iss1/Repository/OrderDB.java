package lab13.iss1.Repository;

import lab13.iss1.Domain.Order;
import lab13.iss1.Domain.OrderStatus;
import lab13.iss1.Repository.Utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class OrderDB {
    private JdbcUtils jdbcUtils;

    public OrderDB(Properties props){
        jdbcUtils = new JdbcUtils(props);
    }

    public Order findByGameId(UUID id){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from orders where gameID=?")){
            preStmt.setString(1, id.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    UUID orderID = UUID.fromString(result.getString("id"));
                    String email = result.getString("email");
                    String status = result.getString("status");
                    LocalDateTime purchaseDate = result.getTimestamp("purchaseDate").toLocalDateTime();
                    Order order = new Order(orderID, email, id, OrderStatus.valueOf(status), purchaseDate);
                    return order;
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return null;
    }

    public Order findOne(UUID id){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from orders where id=?")){
            preStmt.setString(1, id.toString());
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String email = result.getString("email");
                    UUID gameID = UUID.fromString(result.getString("gameID"));
                    String status = result.getString("status");
                    LocalDateTime purchaseDate = result.getTimestamp("purchaseDate").toLocalDateTime();
                    Order order = new Order(id, email, gameID, OrderStatus.valueOf(status), purchaseDate);
                    return order;
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return null;
    }

    public ArrayList<Order> findAll(){
        Connection con = jdbcUtils.getConnection();
        ArrayList<Order> orders = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from orders")){
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    UUID id = UUID.fromString(result.getString("id"));
                    String email = result.getString("email");
                    UUID gameID = UUID.fromString(result.getString("gameID"));
                    String status = result.getString("status");
                    LocalDateTime purchaseDate = result.getTimestamp("purchaseDate").toLocalDateTime();
                    Order order = new Order(id, email, gameID, OrderStatus.valueOf(status), purchaseDate);
                    orders.add(order);
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return orders;
    }

    public Order save(Order order){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into orders (id, email, gameID, status, purchaseDate) values (?, ?, ?, ?, ?)")){
            preStmt.setString(1, order.getId().toString());
            preStmt.setString(2, order.getEmail());
            preStmt.setString(3, order.getGameID().toString());
            preStmt.setString(4, order.getStatus().toString());
            preStmt.setTimestamp(5, java.sql.Timestamp.valueOf(order.getPurchaseDate()));
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return null;
    }

    public Order delete(UUID id){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from orders where id=?")){
            preStmt.setString(1, id.toString());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return null;
    }

    public Order update(Order order){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update orders set email=?, gameID=?, status=?, purchaseDate=? where id=?")){
            preStmt.setString(1, order.getEmail());
            preStmt.setString(2, order.getGameID().toString());
            preStmt.setString(3, order.getStatus().toString());
            preStmt.setTimestamp(4, java.sql.Timestamp.valueOf(order.getPurchaseDate()));
            preStmt.setString(5, order.getId().toString());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return null;
    }

    public int size(){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select count(*) as SIZE from orders")){
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
}
