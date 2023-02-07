package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.ExtraClassHallReservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ExtraClassHallReservationDAO extends CrudDAO<ExtraClassHallReservation> {
    List getDataToViewTable() throws SQLException, ClassNotFoundException;
    String getHallReservationNo(String eClassCode) throws SQLException, ClassNotFoundException;
    String getLastHallReservationNo() throws SQLException, ClassNotFoundException;
}
