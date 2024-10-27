package project.kidscompetitions.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.kidscompetitions.Domain.Participant;
import project.kidscompetitions.Repository.interfaces.IParticipantRepository;
import project.kidscompetitions.dbConnection.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class ParticipantRepository implements IParticipantRepository {

    private Logger logger= LogManager.getLogger();
    private JdbcUtils dbConnection;
    private Connection connection;


    public ParticipantRepository(Properties properties) {
        logger.info("Initializing ParticipantRepository with properties: {} ",properties);
        this.dbConnection=new JdbcUtils(properties);
        this.connection= dbConnection.getConnection();

    }

    @Override
    public Optional<Participant> findOne(Long participant) {
        logger.traceEntry("Finding participant with id {}" + participant);
        try(PreparedStatement statement=connection.prepareStatement("select * from participant where idParticipant= ?")){
            statement.setLong(1,participant);
            ResultSet resultSet= statement.executeQuery();
            while (resultSet.next())
            {
                long id= resultSet.getLong("idParticipant");
                String name= resultSet.getString("name");
                String birthDate= resultSet.getString("dateBirth");
                Participant part= new Participant(name, birthDate);
                part.setId(id);
                logger.traceExit("Participant with id {} found" + participant);
                return Optional.of(part);
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error DB "+ e);
        }
        logger.traceExit("No participant found with id {}" + participant);
        return Optional.empty();
    }

    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry("getting all participants entry");
        ArrayList<Participant> clients=new ArrayList<>();
        try(PreparedStatement statement=connection.prepareStatement("SELECT * FROM participant")){

            ResultSet resultSet= statement.executeQuery();
            logger.info("suntem dupa resultset");
            while (resultSet.next())
            {
                long id= resultSet.getLong("idParticipant");
                String name=resultSet.getString("name");
                String birthDate = resultSet.getString("dateBirth");
                Participant client=new Participant(name,birthDate);
                client.setId(id);
                clients.add(client);
            }
            logger.traceExit("Got all participants successfully");
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Db failed in findAll participants" + e);
        }
        return clients;
    }

    @Override
    public Optional<Participant> save(Participant entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Participant> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Participant> update(Participant entity) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }
}