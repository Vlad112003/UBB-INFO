package project.kidscompetitions.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.kidscompetitions.Domain.Competition;
import project.kidscompetitions.Repository.interfaces.ICompetitionRepository;
import project.kidscompetitions.dbConnection.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class CompetitionRepository implements ICompetitionRepository {
    private Logger logger= LogManager.getLogger();

    private JdbcUtils dbConnection;
    private Connection connection;

    public CompetitionRepository(Properties properties) {
        logger.info("Initializing CompetitionRepository with properties: {} ",properties);
        this.dbConnection=new JdbcUtils(properties);
        this.connection= dbConnection.getConnection();

    }

    @Override
    public Optional<Competition> findOne(Long aLong) {
        logger.traceEntry("Finding competitions with id {}" + aLong);
        try(PreparedStatement statement=connection.prepareStatement("select * from competition where idComp=?")){
            statement.setLong(1,aLong);
            ResultSet resultSet= statement.executeQuery();
            while (resultSet.next())
            {
                long id= resultSet.getLong("idComp");
                String ageGroups= resultSet.getString("ageGroup");
                String name= resultSet.getString("name");
                Competition competition= new Competition(ageGroups, name);
                competition.setId(id);
                logger.traceExit("Competition with id {} found" + aLong);
                return Optional.of(competition);
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error DB "+ e);
        }
        logger.traceExit("No competition found with id {}" + aLong);
        return Optional.empty();
    }

    @Override
    public Iterable<Competition> findAll() {
        logger.traceEntry("Getting all the athletics tests entry");
        ArrayList<Competition> competitions=new ArrayList<>();
        try(PreparedStatement statement=connection.prepareStatement("select * from competition")){

            ResultSet resultSet= statement.executeQuery();
            logger.info("ResultSet");
            while (resultSet.next())
            {
                long id= resultSet.getLong("idComp");
                String ageGroups= resultSet.getString("ageGroup");
                String name= resultSet.getString("name");
                Competition competition= new Competition(ageGroups, name);
                competition.setId(id);
                competitions.add(competition);
            }
            logger.traceExit("Got all athletics tests successfully");
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("DB failed in findAll athletics tests" + e);
        }
        return competitions;
    }

    @Override
    public Optional<Competition> save(Competition entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Competition> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Competition> update(Competition entity) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }

}
