package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.AttendanceDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.AttendanceDTO;
import lk.vidathya.tcms.dto.SuperDTO;
import lk.vidathya.tcms.entity.Attendance;
import lk.vidathya.tcms.service.custom.AttendanceService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceDAO attendanceDAO;
    private final Convertor convertor;
    private final Connection connection;

    public AttendanceServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        attendanceDAO= DaoFactory.getInstance().getDao(connection, DaoTypes.ATTENDANCE_DAO_IMPL );
        convertor=new Convertor();
    }


    @Override
    public boolean add(AttendanceDTO attendanceDTO) throws SQLException, ClassNotFoundException {
        boolean isExist = attendanceDAO.existByPk(
                attendanceDTO.getStudentId(),
                attendanceDTO.getClassCode(),
                attendanceDTO.getDate(),
                attendanceDTO.getYear()
        );
        if(isExist){
            boolean isAdd = attendanceDAO.add(convertor.toAttendance(attendanceDTO));
            return isAdd;
        }
        return false;
    }

    @Override
    public boolean update(AttendanceDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public AttendanceDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean existByPk(Object... args) {

        return false;
    }

    @Override
    public List<String> getStudentIds(Object... args) throws SQLException, ClassNotFoundException {
        List<String> list = attendanceDAO.getStudentIds(args);
        return list;
    }

    @Override
    public List<Attendance> getStudentsAttendance(Object... args) throws SQLException, ClassNotFoundException {
        List<Attendance> list = attendanceDAO.getStudentsAttendance(args);
        return null;
    }
}
