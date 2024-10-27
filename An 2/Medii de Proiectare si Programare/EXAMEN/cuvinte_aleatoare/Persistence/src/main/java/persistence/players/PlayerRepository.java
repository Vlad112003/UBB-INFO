package persistence.players;

import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PlayerRepository implements IPlayerRepository {
    private final Utils utils;
    private String sqlCommand;
    private static final Logger logger = LogManager.getLogger();

    public PlayerRepository(Properties properties) {
        this.utils = new Utils(properties);
    }

    private Iterable<Player> executeQuery() {
        List<Player> players = new ArrayList<>();
        Connection connection = utils.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                players.add(new Player(resultSet.getInt("id"), resultSet.getString("username")));
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        if (players.isEmpty())
            return null;
        return players;
    }

    @Override
    public Player findByUsername(String username) {
        logger.traceEntry();
        this.sqlCommand = "SELECT * FROM players WHERE username = '" + username + "'";
        Iterable<Player> players = executeQuery();
        if (players == null)
            return null;
        return players.iterator().next();
    }
}
