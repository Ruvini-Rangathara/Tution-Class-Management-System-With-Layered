package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.Hall;

import java.sql.SQLException;
import java.util.List;

public interface HallDAO  extends CrudDAO<Hall> {
    String getLastId() throws SQLException, ClassNotFoundException;
    List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException;
    int getAllCount() throws SQLException, ClassNotFoundException;
}
