package persistence.cuvinte;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CuvinteRepository implements ICuvinteRepository{
    private final Utils utils;
    private String sqlCommand;
    private static final Logger logger = LogManager.getLogger();

    public CuvinteRepository(Properties properties) {
        this.utils = new Utils(properties);
    }
    private Iterable<String> executeQuery() {
        List<String> words = new ArrayList<>();
        Connection connection = utils.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                words.add(resultSet.getString("cuvant"));
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        if (words.isEmpty())
            return null;
        return words;
    }

    @Override
    public Iterable<String> getRandomWords() {
        logger.traceEntry();
        this.sqlCommand = "SELECT cuvant FROM cuvinte ORDER BY RANDOM() LIMIT 5" ;
        Iterable<String> words = executeQuery();
        return words;
    }
}
