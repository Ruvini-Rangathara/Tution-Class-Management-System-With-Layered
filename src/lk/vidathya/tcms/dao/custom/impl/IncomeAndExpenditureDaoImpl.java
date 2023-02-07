package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import lk.vidathya.tcms.dao.custom.IncomeAndExpenditureDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.IncomeAndExpenditure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class IncomeAndExpenditureDaoImpl implements IncomeAndExpenditureDAO {
    private final Connection connection;

    public IncomeAndExpenditureDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(IncomeAndExpenditure incomeAndExpenditure) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO IncomeExpenditure (staffId, type, description, amount, year, month, date ) VALUES(?,?,?,?,?,?,?)",
                incomeAndExpenditure.getStaffId(),
                incomeAndExpenditure.getType(),
                incomeAndExpenditure.getDescription(),
                incomeAndExpenditure.getAmount(),
                incomeAndExpenditure.getYear(),
                incomeAndExpenditure.getMonth(),
                incomeAndExpenditure.getDate()
        );
        return isAdd;
    }

    @Override
    public boolean update(IncomeAndExpenditure entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public IncomeAndExpenditure searchById(Object... args) throws SQLException, ClassNotFoundException {
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
        ResultSet result = CrudUtil.execute("SELECT date,description,amount FROM IncomeExpenditure WHERE (year=?) AND (month=?) AND (type=?)",year, monthName,"Income");
        List<IncomeAndExpenditure> list = FXCollections.observableArrayList();
        while(result.next()){
            IncomeAndExpenditure incomeAndExpenditure = new IncomeAndExpenditure(
                    result.getString("description"),
                    result.getDouble("amount"),
                    result.getString("date")
            );
            list.add(incomeAndExpenditure);
        }
        return list;
    }

    @Override
    public List getExpenditureDataTable(int year, String monthName) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT date,description,amount FROM IncomeExpenditure WHERE (year=?) AND (month=?) AND (type=?)",year, monthName,"Expenditure");
        List<IncomeAndExpenditure> list = FXCollections.observableArrayList();
        while(result.next()){
            IncomeAndExpenditure incomeAndExpenditure = new IncomeAndExpenditure(
                    result.getString("description"),
                    result.getDouble("amount"),
                    result.getString("date")
            );
            list.add(incomeAndExpenditure);
        }
        return list;
    }
}
