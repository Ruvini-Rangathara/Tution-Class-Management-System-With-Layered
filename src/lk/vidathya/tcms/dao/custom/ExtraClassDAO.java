package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.ExtraClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ExtraClassDAO extends CrudDAO<ExtraClass> {
    String getLastId() throws SQLException, ClassNotFoundException;
    List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException;
    List<ExtraClass> getDataToScheduleTable(String date) throws SQLException, ClassNotFoundException;
}
