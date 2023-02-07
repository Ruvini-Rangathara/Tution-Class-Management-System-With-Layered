package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.HallReservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface HallReservationDAO extends CrudDAO<HallReservation> {
    String getHallReservationNo(String classCode) throws SQLException, ClassNotFoundException;
    List getDataToViewTable() throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
}
