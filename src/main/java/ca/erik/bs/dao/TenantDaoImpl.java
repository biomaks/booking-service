package ca.erik.bs.dao;

import ca.erik.bs.model.Tenant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Erik Khalimov.
 */
public class TenantDaoImpl implements TenantDao {

    private final Connection connection;

    public TenantDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Tenant get(int key) {
        String sql = "SELECT * FROM tenant WHERE id = ?;";
        Tenant tenant = new Tenant();
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setInt(1, key);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            tenant.setId(rs.getInt("id"));
            tenant.setFirstName(rs.getString("first_name"));
            tenant.setLastName(rs.getString("last_name"));
            tenant.setMiddleName(rs.getString("middle_name"));
            tenant.setPhoneNumber(rs.getString("phone_number"));
            tenant.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenant;
    }

    public void save(Tenant tenant) {
        String sql = "INSERT INTO tenant (first_name, last_name, middle_name, phone_number, email) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pstm;
        try {
            pstm = this.connection.prepareStatement(sql);
            pstm.setString(1, tenant.getFirstName());
            pstm.setString(2, tenant.getLastName());
            pstm.setString(3, tenant.getMiddleName());
            pstm.setString(4, tenant.getPhoneNumber());
            pstm.setString(5, tenant.getEmail());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Tenant tenant) {
        String sql = "UPDATE tenant SET first_name=?, middle_name=?, last_name=?, phone_number=?, email=? WHERE id=?;";
        PreparedStatement pstm;
        try {
            pstm = this.connection.prepareStatement(sql);
            pstm.setString(1, tenant.getFirstName());
            pstm.setString(2, tenant.getMiddleName());
            pstm.setString(3, tenant.getLastName());
            pstm.setString(4, tenant.getPhoneNumber());
            pstm.setString(5, tenant.getEmail());
            pstm.setInt(6, tenant.getId());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Tenant Tenant) {

    }
}