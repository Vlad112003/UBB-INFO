package org.charity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.charity.CharitableCase;
import org.charity.Donation;
import org.charity.Donor;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DonationRepository implements IDonationRepository {
    private final JdbcUtils dbUtils;
    private final DonorRepository donorRepository;
    private final CharitableCaseRepository caseRepository;

    private static final Logger logger = LogManager.getLogger(DonationRepository.class);

    public DonationRepository(Properties props, DonorRepository donorRepository, CharitableCaseRepository caseRepository) {
        logger.info("Initializing DonationRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
        this.donorRepository = donorRepository;
        this.caseRepository = caseRepository;
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from Donation")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        return 0;
    }

    @Override
    public void save(Donation entity) {
        logger.traceEntry("saving donation {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Donation (charitable_case_id, amount) values (?,?)")) {
            preStmt.setInt(1, entity.getCharitableCase().getId());
            preStmt.setDouble(2, entity.getAmount());
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting donation with id {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Donation where id=?")) {
            preStmt.setInt(1, integer);
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public void update(Integer integer, Donation entity) {
        logger.traceEntry("updating donation with id {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Donation set donor_id=?, charitable_case_id=?, amount=? where id=?")) {
            preStmt.setInt(1, entity.getDonor().getId());
            preStmt.setInt(2, entity.getCharitableCase().getId());
            preStmt.setDouble(3, entity.getAmount());
            preStmt.setInt(4, integer);
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public Donation findOne(Integer integer) {
        logger.traceEntry("finding donation with id {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Donation where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    int donorId = result.getInt("donor_id");
                    int caseId = result.getInt("charitable_case_id");
                    double amount = result.getDouble("amount");

                    Donor donor = donorRepository.findOne(donorId);
                    CharitableCase charitableCase = caseRepository.findOne(caseId);

                    Donation donation = new Donation(id, donor, charitableCase, amount);
                    logger.traceExit(donation);
                    return donation;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("No donation found with id {}", integer);
        return null;
    }

    @Override
    public Iterable<Donation> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Donation> donations = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Donation")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int donorId = result.getInt("donor_id");
                    int caseId = result.getInt("charitable_case_id");
                    double amount = result.getDouble("amount");

                    Donor donor = donorRepository.findOne(donorId);
                    CharitableCase charitableCase = caseRepository.findOne(caseId);

                    Donation donation = new Donation(id, donor, charitableCase, amount);
                    donations.add(donation);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(donations);
        return donations;
    }
}
