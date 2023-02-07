package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.Staff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface StaffDAO extends CrudDAO<Staff> {
    String getLastId() throws SQLException, ClassNotFoundException;
    List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException;
    List<String> getStaffEmailAddress() throws SQLException, ClassNotFoundException;
    ResultSet getImage(String staffId) throws SQLException, ClassNotFoundException;
    boolean isExistImage(String staffId) throws SQLException, ClassNotFoundException;
}
