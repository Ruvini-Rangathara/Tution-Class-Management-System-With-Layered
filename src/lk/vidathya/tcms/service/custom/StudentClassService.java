package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.StudentClassDTO;
import lk.vidathya.tcms.entity.StudentClass;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface StudentClassService  extends SuperService<StudentClassDTO> {

    List<String> getStudentIds(String classCode) throws SQLException, ClassNotFoundException;
    List<String> getGuardianNic(String classCodes) throws SQLException, ClassNotFoundException;
    List getClassesOfAStudent(String studentId) throws SQLException, ClassNotFoundException;
    boolean existStudent(String classCode, String studentId) throws SQLException, ClassNotFoundException;
    List<String> getStudentsOfAClass(String classCode) throws SQLException, ClassNotFoundException;
    boolean isExistStudentInAClass(String studentId, String classCode) throws SQLException, ClassNotFoundException;
    List getAllStudentsOfAClass(String classCode) throws SQLException, ClassNotFoundException;
}
