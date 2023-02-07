package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.StaffDTO;
import lk.vidathya.tcms.entity.Staff;
import lk.vidathya.tcms.service.SuperService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface StaffService  extends SuperService<StaffDTO> {

    String getNewId() throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
    List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException;
    List<String> getStaffEmailAddress() throws SQLException, ClassNotFoundException;
    ResultSet getImage(String staffId) throws SQLException, ClassNotFoundException;
    boolean isExistImage(String staffId) throws SQLException, ClassNotFoundException;
}
