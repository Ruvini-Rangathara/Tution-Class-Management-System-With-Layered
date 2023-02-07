package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.Attendance;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceDAO extends CrudDAO<Attendance> {
    List<String> getStudentIds(Object... args) throws SQLException, ClassNotFoundException;
    List<Attendance> getStudentsAttendance(Object... args) throws SQLException, ClassNotFoundException;

}
