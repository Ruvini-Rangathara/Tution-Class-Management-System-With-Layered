package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.HallReservationDTO;
import lk.vidathya.tcms.entity.HallReservation;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface HallReservationService  extends SuperService<HallReservationDTO> {

    String getHallReservationNo(String classCode) throws SQLException, ClassNotFoundException;
    List getDataToViewTable() throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
    String getNewId() throws SQLException, ClassNotFoundException;
}
