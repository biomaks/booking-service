package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.LandlordDao;
import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.Landlord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LandlordDaoImpl extends BaseDao implements LandlordDao {

    private final String GET_QUERY = "SELECT * FROM landlord WHERE id = ?;";

    private final String SAVE_QUERY = "INSERT INTO landlord (first_name, middle_name, last_name, phone_number, email) VALUES (?, ?, ?, ?, ?);";

    private final String UPDATE_QUERY = "UPDATE landlord SET first_name=?, middle_name=?, last_name=?, phone_number=?, email=? WHERE id=?;";

    private final String DELETE_QUERY = "DELETE FROM landlord WHERE id=?;";

    private final String DELETE_ALL_QUERY = "DELETE FROM landlord;";

    private final String FIND_ALL_QUERY = "SELECT * FROM landlord;";

    private final String FIND_LANDLORD_BY_EMAIL_QUERY = "SELECT * FROM landlord WHERE email=?;";

    public LandlordDaoImpl(Connection connection) {
        super(connection);
    }

    public Landlord get(int key) throws DatabaseException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Landlord landlord = new Landlord();
        try {
            pstm = this.connection.prepareStatement(GET_QUERY);
            pstm.setInt(1, key);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;
            }
            landlord.setId(rs.getInt("id"));
            landlord.setFirstName(rs.getString("first_name"));
            landlord.setLastName(rs.getString("last_name"));
            landlord.setMiddleName(rs.getString("middle_name"));
            landlord.setPhoneNumber(rs.getString("phone_number"));
            landlord.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return landlord;
    }

    public Landlord findLandlordByEmail(String email) throws DatabaseException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Landlord landlord = new Landlord();
        try {
            pstm = this.connection.prepareStatement(FIND_LANDLORD_BY_EMAIL_QUERY);
            pstm.setString(1, email);
            rs = pstm.executeQuery();
            rs.next();
            landlord.setId(rs.getInt("id"));
            landlord.setFirstName(rs.getString("first_name"));
            landlord.setLastName(rs.getString("last_name"));
            landlord.setMiddleName(rs.getString("middle_name"));
            landlord.setPhoneNumber(rs.getString("phone_number"));
            landlord.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return landlord;
    }

    public void save(Landlord landLord) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(SAVE_QUERY);
            pstm.setString(1, landLord.getFirstName());
            pstm.setString(2, landLord.getMiddleName());
            pstm.setString(3, landLord.getLastName());
            pstm.setString(4, landLord.getPhoneNumber());
            pstm.setString(5, landLord.getEmail());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public void update(Landlord landLord) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(UPDATE_QUERY);
            pstm.setString(1, landLord.getFirstName());
            pstm.setString(2, landLord.getMiddleName());
            pstm.setString(4, landLord.getPhoneNumber());
            pstm.setString(3, landLord.getLastName());
            pstm.setString(5, landLord.getEmail());
            pstm.setInt(6, landLord.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public void delete(Landlord landLord) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(DELETE_QUERY);
            pstm.setInt(1, landLord.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public void deleteAll() throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(DELETE_ALL_QUERY);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public List<Landlord> findAll() throws DatabaseException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Landlord> landlordList = new ArrayList<Landlord>();
        try {
            pstm = this.connection.prepareStatement(FIND_ALL_QUERY);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Landlord landlord = new Landlord();
                landlord.setId(rs.getInt("id"));
                landlord.setFirstName(rs.getString("first_name"));
                landlord.setLastName(rs.getString("last_name"));
                landlord.setMiddleName(rs.getString("middle_name"));
                landlord.setPhoneNumber(rs.getString("phone_number"));
                landlord.setEmail(rs.getString("email"));
                landlordList.add(landlord);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return landlordList;
    }

}
