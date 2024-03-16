package ro.ubbcluj.cs.map.lab82nd.repository;

import ro.ubbcluj.cs.map.lab82nd.domain.User;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class UserPageDatabase implements PagingRepository<UUID, User> {

    private String url;
    private String username;
    private String password;

    public UserPageDatabase(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        List<User> users = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            PreparedStatement pagePreparedStatement = connection.prepareStatement("SELECT * FROM users " +
                    "LIMIT ? OFFSET ?");

            PreparedStatement countPreparedStatement = connection.prepareStatement
                ("SELECT COUNT(*) AS count FROM users ");

        ) {
            pagePreparedStatement.setInt(1, pageable.getPageSize());
            pagePreparedStatement.setInt(2, pageable.getPageSize() * pageable.getPageNumber());
            try (ResultSet pageResultSet = pagePreparedStatement.executeQuery();
                 ResultSet countResultSet = countPreparedStatement.executeQuery(); ) {
                while (pageResultSet.next()) {
                    UUID id = UUID.fromString(pageResultSet.getString("id"));
                    String firstName = pageResultSet.getString("firstName");
                    String lastName = pageResultSet.getString("lastName");
                    String email = pageResultSet.getString("email");
                    String encryptedPassword = pageResultSet.getString("password");
                    String password = decrypt(encryptedPassword);

                    User user = new User(firstName, lastName, email, password);
                    user.setId(id);
                    users.add(user);
                }
                int totalCount = 0;
                if(countResultSet.next()) {
                    totalCount = countResultSet.getInt("count");
                }

                return new Page<>(users, totalCount);

            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findOne(UUID uuid) {
        User user = null;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = '" + uuid.toString() + "';");
            ResultSet resultSet = statement.executeQuery();) {

            resultSet.next();

            UUID id_ = UUID.fromString(resultSet.getString("id"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            String encryptedPassword = resultSet.getString("password");
            String password = decrypt(encryptedPassword);

            user = new User(firstName, lastName, email, password);
            user.setId(id_);

            return Optional.of(user);
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return Optional.of(user);
    }

    @Override
    public Iterable<User> findAll() {
        return findAll_DB();
    }

    protected Iterable<User> findAll_DB(){
        Set<User> users = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users;");
            ResultSet resultSet = statement.executeQuery();) {

            while(resultSet.next()){
                UUID id = UUID.fromString(resultSet.getString("id"));
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String encryptedPassword = resultSet.getString("password");
                String password = decrypt(encryptedPassword);

                User user = new User(firstName, lastName, email, password);
                user.setId(id);
                users.add(user);
            }
            return users;
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return users;
    }

    @Override
    public Optional<User> save(User entity) {
        Iterable<User> all = findAll_DB();
        User u = null;
        for(User us : all){
            if(us.getEmail().equals(entity.getEmail())){
                u = us;
            }
        }

        if (u == null) {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?);");
                statement.setString(1, entity.getId().toString());
                statement.setString(2, entity.getFirstName());
                statement.setString(3, entity.getLastName());
                statement.setString(4, entity.getEmail());
                String unencryptedPassword = entity.getPassword();
                String encryptedPassword = encrypt(unencryptedPassword);
                statement.setString(5, encryptedPassword);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return Optional.of(entity);
//        return u;
    }

    @Override
    public Optional<User> delete(UUID uuid) {
        Optional<User> user = findOne(uuid);
        if(user != null){
            try(Connection connection = DriverManager.getConnection(url, username, password)){
                PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = '" + uuid.toString() + "';");
                statement.executeUpdate();
                return user;
            }
            catch(SQLException e){
                System.out.println(e);
            }
        }
        return user;
    }

    @Override
    public Optional<User> update(User entity) {
        Optional<User> user = findOne(entity.getId());
        if(!user.equals(entity)){
            try(Connection connection = DriverManager.getConnection(url, username, password)){
                Statement statement = connection.createStatement();
                String sql = "UPDATE users SET firstName = '" + entity.getFirstName() + "' where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE users SET lastName = '" + entity.getLastName() + "' where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE users SET email = '" + entity.getEmail() + "' where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
                String unencryptedPassword = entity.getPassword();
                String encryptedPassword = encrypt(unencryptedPassword);
                sql = "UPDATE users SET password = '" + encryptedPassword +"' where id = '" + entity.getId().toString() +"';";
                statement.executeUpdate(sql);
            }
            catch(SQLException e){
                System.out.println(e);
            }
        }
        return user;
    }

    @Override
    public int size() {
        int size = 0;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM users;");
            ResultSet resultSet = statement.executeQuery();) {

            resultSet.next();
            size = resultSet.getInt("count");
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return size;
    }

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    public void prepareSecretKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String encrypt(String password){
        try {
            prepareSecretKey("secret");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    private String decrypt(String password){
        try {
            prepareSecretKey("secret");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
