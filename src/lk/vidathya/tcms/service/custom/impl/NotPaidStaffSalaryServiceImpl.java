package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ExtraClassHallReservationDAO;
import lk.vidathya.tcms.dao.custom.NotPaidStaffSalaryDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.NotPaidStaffSalaryDTO;
import lk.vidathya.tcms.dto.SuperDTO;
import lk.vidathya.tcms.entity.NotPaidStaffSalary;
import lk.vidathya.tcms.service.custom.NotPaidStaffSalaryService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NotPaidStaffSalaryServiceImpl implements NotPaidStaffSalaryService {
    private final NotPaidStaffSalaryDAO notPaidStaffSalaryDAO;
    private final Convertor convertor;
    private final Connection connection;

    public NotPaidStaffSalaryServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        notPaidStaffSalaryDAO= DaoFactory.getInstance().getDao(connection, DaoTypes.NOT_PAID_STAFF_SALARY_DAO_IMPL );
        convertor=new Convertor();
    }

    @Override
    public boolean add(NotPaidStaffSalaryDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(NotPaidStaffSalaryDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public NotPaidStaffSalaryDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public List getAllByMonthYear(String month, int year) throws SQLException, ClassNotFoundException {
        return notPaidStaffSalaryDAO.getAllByMonthYear(month, year);
    }
}
