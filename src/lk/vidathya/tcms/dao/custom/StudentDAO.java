package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    String getLastId() throws SQLException, ClassNotFoundException;
    int getStudentCount() throws SQLException, ClassNotFoundException;
    String getStudentMailAddress(String studentId) throws SQLException, ClassNotFoundException;
    List<String> getAllEmailAddress() throws SQLException, ClassNotFoundException;
    String getGuardianNic(String studentId) throws SQLException, ClassNotFoundException;
    boolean isExistImage(String studentId) throws SQLException, ClassNotFoundException;
    ResultSet getImage(String studentId) throws SQLException, ClassNotFoundException;
}
