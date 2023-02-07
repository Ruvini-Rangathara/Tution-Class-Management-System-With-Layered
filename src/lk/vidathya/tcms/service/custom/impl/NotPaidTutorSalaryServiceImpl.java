package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ExtraClassHallReservationDAO;
import lk.vidathya.tcms.dao.custom.NotPaidTutorSalaryDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.NotPaidTutorSalaryDTO;
import lk.vidathya.tcms.dto.SuperDTO;
import lk.vidathya.tcms.entity.NotPaidTutorSalary;
import lk.vidathya.tcms.service.custom.NotPaidTutorSalaryService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NotPaidTutorSalaryServiceImpl implements NotPaidTutorSalaryService {
    private final NotPaidTutorSalaryDAO notPaidTutorSalaryDAO;
    private final Convertor convertor;
    private final Connection connection;

    public NotPaidTutorSalaryServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        notPaidTutorSalaryDAO= DaoFactory.getInstance().getDao(connection, DaoTypes.NOT_PAID_TUTOR_SALARY_DAO_IMPL );
        convertor=new Convertor();
    }


    @Override
    public boolean add(NotPaidTutorSalaryDTO notPaidTutorSalaryDTO) throws SQLException, ClassNotFoundException {
        return notPaidTutorSalaryDAO.add(convertor.toNotPaidTutorSalary(notPaidTutorSalaryDTO));
    }

    @Override
    public boolean update(NotPaidTutorSalaryDTO notPaidTutorSalaryDTO) throws SQLException, ClassNotFoundException {
        return notPaidTutorSalaryDAO.update(convertor.toNotPaidTutorSalary(notPaidTutorSalaryDTO));
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public NotPaidTutorSalaryDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public double getExistSalary(String tutorId) throws SQLException, ClassNotFoundException {
        return notPaidTutorSalaryDAO.getExistSalary(tutorId);
    }

    @Override
    public List getAllNotPaidDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException {
        return notPaidTutorSalaryDAO.getAllNotPaidDataByMonthYear(monthName, year);
    }

    @Override
    public double getNotPaidTutorSalaryOfATutor(String tutorId, String classCode) throws SQLException, ClassNotFoundException {
        return notPaidTutorSalaryDAO.getNotPaidTutorSalaryOfATutor(tutorId, classCode);
    }

    @Override
    public boolean updateNotPaidTutorSalaryAfter(String tutorId, String classCode) throws SQLException, ClassNotFoundException {
        return notPaidTutorSalaryDAO.updateNotPaidTutorSalaryAfter(tutorId, classCode);
    }
}
