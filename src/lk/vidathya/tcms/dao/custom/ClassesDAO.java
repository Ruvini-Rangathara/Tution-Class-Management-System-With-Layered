package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.HallReservationDTO;
import lk.vidathya.tcms.entity.Classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ClassesDAO  extends CrudDAO<Classes> {
    String getLastId() throws SQLException, ClassNotFoundException;
    List<String> loadIdToComboBox() throws SQLException, ClassNotFoundException;
    List<String> loadGradeToComboBox() throws SQLException, ClassNotFoundException;
    List<Classes> getDataToScheduleTable(String dayName) throws SQLException, ClassNotFoundException;
}
