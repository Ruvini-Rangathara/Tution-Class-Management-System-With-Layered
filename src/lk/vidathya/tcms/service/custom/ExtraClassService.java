package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dto.ExtraClassDTO;
import lk.vidathya.tcms.dto.ExtraClassHallReservationDTO;
import lk.vidathya.tcms.entity.ExtraClass;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface ExtraClassService  extends SuperService<ExtraClassDTO> {
    boolean update(ExtraClassDTO extraClassDTO, ExtraClassHallReservationDTO extraClassHallReservationDTO) throws SQLException;
    String getNewId() throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
    List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException;
    List<ExtraClass> getDataToScheduleTable(String date) throws SQLException, ClassNotFoundException;
    boolean add(ExtraClassDTO extraClassDTO, ExtraClassHallReservationDTO extraClassHallReservationDTO) throws SQLException, ClassNotFoundException;
}
