package project.kidscompetitions.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project.kidscompetitions.Domain.Competition;
import project.kidscompetitions.Domain.Participant;
import project.kidscompetitions.Domain.SignUp;
import project.kidscompetitions.Repository.interfaces.ISignUpRepository;
import project.kidscompetitions.dbConnection.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class SignUpRepository implements ISignUpRepository {

    private Logger logger= LogManager.getLogger();
    private JdbcUtils dbConnection;
    private Connection connection;
    private ParticipantRepository participantRepository;
    private CompetitionRepository competitionRepository;


    public SignUpRepository(Properties properties) {
        logger.traceEntry("Constructor entry with properties {}",properties);
        this.dbConnection = new JdbcUtils(properties);
        this.connection = dbConnection.getConnection();
        participantRepository = new ParticipantRepository(properties);
        competitionRepository = new CompetitionRepository(properties);

    }

    @Override
    public Optional<SignUp> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<SignUp> findAll() {
        return null;
    }

    @Override
    public Optional<SignUp> save(SignUp entity) {
        logger.traceEntry("Entry to save sign up");
        try (PreparedStatement statement=connection.prepareStatement("insert into signUp(idParticipant, idComp) \n" +
                "values (?,?)"))
        {
            statement.setLong(1, entity.getParticipant().getId());
            statement.setLong(2, entity.getCompetition().getId());
            int resultSet= statement.executeUpdate();
            if(resultSet !=0)
            {   logger.traceExit("Added sign up {}"+ entity);
                return Optional.of(entity);}
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error db sign up "+e);
        }
        logger.traceExit("NO sign up added");
        return Optional.empty();
    }

    @Override
    public Optional<SignUp> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<SignUp> update(SignUp entity) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterable<Participant> findAllSignUps(Long idTest) {
        logger.traceEntry("Entry the filter");
        ArrayList<Participant> filteredSignUps=new ArrayList<>();
        try (PreparedStatement statement=connection.prepareStatement("select * from signUp where idComp=?")) {

            statement.setLong(1, idTest);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id= resultSet.getLong("idSignUp");
                long participant= resultSet.getLong("idParticipant");
                long comp= resultSet.getLong("idComp");
                Optional<Participant> part = participantRepository.findOne(participant);
                filteredSignUps.add(part.get());
            }
            logger.traceExit("Got all the comps successfully");
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("DB filter error "+ e);
        }
        return filteredSignUps;
    }
}



