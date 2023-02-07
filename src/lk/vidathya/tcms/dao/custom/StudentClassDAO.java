package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.StudentClass;

import java.sql.SQLException;
import java.util.List;

public interface StudentClassDAO extends CrudDAO<StudentClass> {
    List<String> getStudentIds(String classCode) throws SQLException, ClassNotFoundException;
    List<String> getGuardianNic(String classCodes) throws SQLException, ClassNotFoundException;
    List getClassesOfAStudent(String studentId) throws SQLException, ClassNotFoundException;
    boolean existStudent(String classCode, String studentId) throws SQLException, ClassNotFoundException;
    List<String> getStudentsOfAClass(String classCode) throws SQLException, ClassNotFoundException;
    boolean isExistStudentInAClass(String studentId, String classCode) throws SQLException, ClassNotFoundException;
    List getAllStudentsOfAClass(String classCode) throws SQLException, ClassNotFoundException;
}
