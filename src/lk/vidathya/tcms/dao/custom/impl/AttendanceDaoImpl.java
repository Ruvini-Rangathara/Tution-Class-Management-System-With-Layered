package lk.vidathya.tcms.dao.custom.impl;

import lk.vidathya.tcms.dao.custom.AttendanceDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.Attendance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AttendanceDaoImpl implements AttendanceDAO {

    private final Connection connection;

    public AttendanceDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean add(Attendance attendance) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO StudentAttendance VALUES (?,?,?,?,?,?)",
                    attendance.getStudentId(),
                    attendance.getClassCode(),
                    attendance.getPresentOrAbsent(),
                    attendance.getDate(),
                    attendance.getYear(),
                    attendance.getMonth()
            );

        return isAdd;
    }

    @Override
    public boolean update(Attendance entity) {
        return false;
    }

    @Override
    public boolean delete(Object... args) {
        return false;
    }

    @Override
    public Attendance searchById(Object... args) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public boolean existByPk(Object... args) throws SQLException, ClassNotFoundException {
        boolean isExist = CrudUtil.execute("SELECT * FROM StudentAttendance WHERE studentId=? AND classCode=? AND date=? AND year=? ",
        args[0], args[1],args[2],args[3]);
        return isExist;
    }

    @Override
    public List<String> getStudentIds(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT studentId FROM StudentAttendance WHERE classCode=? AND date=?", args[0],args[1]);

        List<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString("studentId"));
        }
        return ids;
    }

    @Override
    public List<Attendance> getStudentsAttendance(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM StudentAttendance WHERE classCode=? AND date=?",  args[0],args[1]);
        List<Attendance> attendanceList=new ArrayList<>();
        while (result.next()){
            Attendance attendance=new Attendance(
                    result.getString("studentId"),
                    result.getString("classCode"),
                    result.getString("presentOrAbsent"),
                    result.getString("date"),
                    result.getInt("year"),
                    result.getString("month")
            );
            attendanceList.add(attendance);
        }
        return attendanceList;
    }
}
