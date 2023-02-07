package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.ExtraClassHallReservationDTO;
import lk.vidathya.tcms.entity.ExtraClassHallReservation;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface ExtraClassHallReservationService  extends SuperService<ExtraClassHallReservationDTO> {

    String getNewId() throws SQLException, ClassNotFoundException;
    List getDataToViewTable() throws SQLException, ClassNotFoundException;
    String getHallReservationNo(String eClassCode) throws SQLException, ClassNotFoundException;
    String getLastHallReservationNo() throws SQLException, ClassNotFoundException;

}
