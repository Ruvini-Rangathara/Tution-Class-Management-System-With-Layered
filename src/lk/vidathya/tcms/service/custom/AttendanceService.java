package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dto.AttendanceDTO;
import lk.vidathya.tcms.entity.Attendance;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceService extends SuperService<AttendanceDTO> {

    List<String> getStudentIds(Object... args) throws SQLException, ClassNotFoundException;
    List<Attendance> getStudentsAttendance(Object... args) throws SQLException, ClassNotFoundException;
}
