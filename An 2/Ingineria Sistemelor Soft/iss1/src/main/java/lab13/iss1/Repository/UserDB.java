package lab13.iss1.Repository;

import lab13.iss1.Domain.User;
import lab13.iss1.Domain.UserType;
import lab13.iss1.Repository.Utils.Crypto;
import lab13.iss1.Repository.Utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class UserDB {
    private JdbcUtils jdbcUtils;

    public UserDB(Properties props){
        jdbcUtils = new JdbcUtils(props);
    }

    public User findOne(String email){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from user where email=?")){
            preStmt.setString(1, email);
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String userType = result.getString("userType");
                    User user = new User(email, username, password, UserType.valueOf(userType));
                    return user;
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return null;
    }

    public ArrayList<User> findAll(){
        Connection con = jdbcUtils.getConnection();
        ArrayList<User> users = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from user")){
            try (var result = preStmt.executeQuery()) {
                while (result.next()) {
                    String email = result.getString("email");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String userType = result.getString("userType");
                    User user = new User(email, username, password, UserType.valueOf(userType));
                    users.add(user);
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return users;
    }

    public User save(User user){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into user (email, username, password, userType) values (?, ?, ?, ?)")){
            preStmt.setString(1, user.getEmail());
            preStmt.setString(2, user.getUsername());
            String password = user.getPassword();
            String pass = Crypto.getHash(password);
            preStmt.setString(3, pass);
            preStmt.setString(4, user.getUserType().toString());
            preStmt.executeUpdate();
            return user;
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return null;
    }

    public void update(User user){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update user set username=?, password=?, userType=? where email=?")){
            preStmt.setString(1, user.getUsername());
            preStmt.setString(2, user.getPassword());
            preStmt.setString(3, user.getUserType().toString());
            preStmt.setString(4, user.getEmail());
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
    }

    public void delete(String email){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from user where email=?")){
            preStmt.setString(1, email);
            preStmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
    }

    public int size(){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select count(*) as [size] from user")){
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("size");
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return 0;
    }

    public boolean checkuserPassword(String email, String password){
        Connection con = jdbcUtils.getConnection();
        User user = findOne(email);
        if(user == null)
            return false;
        try(PreparedStatement preStmt = con.prepareStatement("select password from user where email=?")){
            preStmt.setString(1, email);
            try (var result = preStmt.executeQuery()) {
                if (result.next()) {
                    String pass = result.getString("password");
                    return pass.equals(Crypto.getHash(password));
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error DB "+e);
        }
        return false;
    }

    public User logUser(String email, String password){
        if(checkuserPassword(email, password)){
            return findOne(email);
        }
        return null;
    }
}
