package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.entity.Tutor;
import lk.vidathya.tcms.service.SuperService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface TutorService  extends SuperService<TutorDTO> {
    String getNewId() throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
    List<String> loadTutorIdToComboBox() throws SQLException, ClassNotFoundException;
    List<String> loadSubjectToComboBox() throws SQLException, ClassNotFoundException;
    int getTutorCount() throws SQLException, ClassNotFoundException;
    List<String> getTutorEmailAddress() throws SQLException, ClassNotFoundException;
    boolean isExistImage(String tutorId) throws SQLException, ClassNotFoundException;
    ResultSet getImage(String tutorId) throws SQLException, ClassNotFoundException;
}
