package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ExtraClassHallReservationDAO;
import lk.vidathya.tcms.dao.custom.IncomeAndExpenditureDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.IncomeAndExpenditureDTO;
import lk.vidathya.tcms.dto.SuperDTO;
import lk.vidathya.tcms.entity.IncomeAndExpenditure;
import lk.vidathya.tcms.service.custom.IncomeAndExpenditureService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class IncomeAndExpenditureServiceImpl implements IncomeAndExpenditureService {
    private final IncomeAndExpenditureDAO incomeAndExpenditureDAO;
    private final Convertor convertor;
    private final Connection connection;

    public IncomeAndExpenditureServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        incomeAndExpenditureDAO= DaoFactory.getInstance().getDao(connection, DaoTypes.INCOME_AND_EXPENDITURE_DAO_IMPL );
        convertor=new Convertor();
    }


    @Override
    public boolean add(IncomeAndExpenditureDTO incomeAndExpenditureDTO) throws SQLException, ClassNotFoundException {
        return incomeAndExpenditureDAO.add(convertor.toIncomeExpenditure(incomeAndExpenditureDTO));
    }

    @Override
    public boolean update(IncomeAndExpenditureDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public IncomeAndExpenditureDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public List getIncomeDataTable(int year, String monthName) throws SQLException, ClassNotFoundException {
        return incomeAndExpenditureDAO.getIncomeDataTable(year, monthName);
    }

    @Override
    public List getExpenditureDataTable(int year, String monthName) throws SQLException, ClassNotFoundException {
        return incomeAndExpenditureDAO.getExpenditureDataTable(year, monthName);
    }
}
