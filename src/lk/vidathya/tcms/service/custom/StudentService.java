package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.GuardianDTO;
import lk.vidathya.tcms.dto.StudentDTO;
import lk.vidathya.tcms.entity.Student;
import lk.vidathya.tcms.service.SuperService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface StudentService   extends SuperService<StudentDTO> {
    boolean add(StudentDTO studentDTO, GuardianDTO guardianDTO) throws SQLException, ClassNotFoundException;
    boolean update(StudentDTO studentDTO,GuardianDTO guardianDTO) throws SQLException, ClassNotFoundException;
    String getNewId() throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
    int getStudentCount() throws SQLException, ClassNotFoundException;
    String getStudentMailAddress(String studentId) throws SQLException, ClassNotFoundException;
    List<String> getAllEmailAddress() throws SQLException, ClassNotFoundException;
    String getGuardianNic(String studentId) throws SQLException, ClassNotFoundException;
    boolean isExistImage(String studentId) throws SQLException, ClassNotFoundException;
    ResultSet getImage(String studentId) throws SQLException, ClassNotFoundException;
}
