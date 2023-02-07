package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.HallReservationDTO;
import lk.vidathya.tcms.entity.Classes;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface ClassesServices  extends SuperService<ClassesDTO> {
    boolean update(ClassesDTO classesDTO, HallReservationDTO hallReservationDTO) throws SQLException, ClassNotFoundException;
    boolean add(ClassesDTO classesDTO, HallReservationDTO hallReservationDTO) throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
    String getNewId() throws SQLException, ClassNotFoundException;
    List<String> loadIdToComboBox() throws SQLException, ClassNotFoundException;
    List<String> loadGradeToComboBox() throws SQLException, ClassNotFoundException;
    List<ClassesDTO> getDataToScheduleTable(String dayName) throws SQLException, ClassNotFoundException;


}
