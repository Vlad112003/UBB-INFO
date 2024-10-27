package org.charity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.charity.Donor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DonorRepository implements IDonorRepository{
    private final JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger(DonorRepository.class);

    public DonorRepository(Properties props) {
        logger.info("Initializing DonorRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public int size() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preSmt=con.prepareStatement("select count(*) as [SIZE] from Donor")) {
            try (var result = preSmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (Exception e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }

        return 0;
    }

    @Override
    public void save(Donor entity) {
        logger.traceEntry("saving donor {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Donor values (?,?,?,?)")){
            preStmt.setInt(1, entity.getId());
            preStmt.setString(2,entity.getName());
            preStmt.setString(3,entity.getAddress());
            preStmt.setString(4,entity.getPhone());
            preStmt.executeUpdate();
        }catch (Exception ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public Integer saveWitoutId(Donor entity) {
        logger.traceEntry("saving donor {} ",entity);
        Connection con=dbUtils.getConnection();
        int generatedId = -1;
        try(PreparedStatement preStmt=con.prepareStatement("insert into Donor(name, address, phone) values (?,?,?)", Statement.RETURN_GENERATED_KEYS)){
            preStmt.setString(1,entity.getName());
            preStmt.setString(2,entity.getAddress());
            preStmt.setString(3,entity.getPhone());
            preStmt.executeUpdate();

            ResultSet rs = preStmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }catch (Exception ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        return generatedId;
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting donor with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Donor where id=?")){
            preStmt.setInt(1,integer);
            preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void update(Integer integer, Donor entity) {
        logger.traceEntry("updating donor with id {} ",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Donor set name=?,address=?,phone=? where id=?")){
            preStmt.setString(1,entity.getName());
            preStmt.setString(2,entity.getAddress());
            preStmt.setString(3,entity.getPhone());
            preStmt.setInt(4,integer);
            preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public Donor findOne(Integer integer) {
        logger.traceEntry("finding donor with id {} ",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Donor where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String address = result.getString("address");
                    String phone = result.getString("phone");
                    Donor donor = new Donor(id, name, address, phone);
                    logger.traceExit(donor);
                    return donor;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit("No donor found with id {}",integer);
        return null;
    }

    @Override
    public Iterable<Donor> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Donor> donors=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Donor")){
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String address = result.getString("address");
                    String phone = result.getString("phone");
                    Donor donor = new Donor(id, name, address, phone);
                    donors.add(donor);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(donors);
        return donors;
    }

    @Override
    public Iterable<Donor> findAllWithFilter(String filter) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Donor> donors=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Donor WHERE name LIKE ?")){
            preStmt.setString(1, filter + "%");
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String address = result.getString("address");
                    String phone = result.getString("phone");
                    Donor donor = new Donor(id, name, address, phone);
                    donors.add(donor);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(donors);
        return donors;
    }
}
