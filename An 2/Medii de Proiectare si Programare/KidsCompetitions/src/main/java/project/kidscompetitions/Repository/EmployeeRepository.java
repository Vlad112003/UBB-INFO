package project.kidscompetitions.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.kidscompetitions.Domain.Employee;
import project.kidscompetitions.Repository.interfaces.IEmployeeRepository;
import project.kidscompetitions.dbConnection.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class EmployeeRepository implements IEmployeeRepository {

    private Logger logger= LogManager.getLogger();
    private JdbcUtils dbConnection;
    private Connection connection;

    public EmployeeRepository(Properties properties) {
        logger.info("Initializing Employee with properties: {} ",properties);
        this.dbConnection=new JdbcUtils(properties);
        this.connection= dbConnection.getConnection();
    }

    @Override
    public Optional<Employee> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public Optional<Employee> save(Employee entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> update(Employee entity) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Optional<Employee> findOneByUsername(String user) {
        logger.traceEntry("Finding employee with id {}" + user);
        try(PreparedStatement statement=connection.prepareStatement("SELECT * from employee where username= ?")){
            statement.setString(1,user);
            ResultSet resultSet= statement.executeQuery();
            if (resultSet.next())
            {
                String username=resultSet.getString("username");
                String pass=resultSet.getString("password");
                Employee employee=new Employee(username, pass);
                logger.traceExit("Employee with id {} found" + user);
                return Optional.of(employee);
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error db "+ e);
        }
        logger.traceExit("No employee found with id {}" + user);
        return Optional.empty();
    }
}


