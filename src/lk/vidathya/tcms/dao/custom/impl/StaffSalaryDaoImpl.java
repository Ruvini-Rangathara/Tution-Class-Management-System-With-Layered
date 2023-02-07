package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import lk.vidathya.tcms.dao.custom.StaffSalaryDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.StaffSalary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StaffSalaryDaoImpl implements StaffSalaryDAO {
    private final Connection connection;

    public StaffSalaryDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(StaffSalary staffSalary) throws SQLException, ClassNotFoundException {
        boolean isAddPayment = CrudUtil.execute("INSERT INTO StaffSalary VALUES(?,?,?,?,?,?,?)",
                staffSalary.getPaymentCode(),
                staffSalary.getStaffId(),
                staffSalary.getYear(),
                staffSalary.getMonth(),
                staffSalary.getSalary(),
                staffSalary.getDate(),
                staffSalary.getPayerId()
        );
        return isAddPayment;
    }

    @Override
    public boolean update(StaffSalary entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StaffSalary searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public String getLastPaymentCode() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT paymentCode FROM StaffSalary ORDER BY paymentCode DESC LIMIT 1");
        if(result.next()){
            return result.getString("paymentCode");
        }
        return null;
    }

    @Override
    public List getSalaryData(int year, String monthName) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM StaffSalary WHERE (year=?) AND (month=?) ORDER BY paymentCode ASC",year,monthName);
        List<StaffSalary> list = FXCollections.observableArrayList();
        while(result.next()){
            StaffSalary staffSalary = new StaffSalary(
                    result.getString("paymentCode"),
                    result.getString("staffId"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getDouble("salary"),
                    result.getString("date"),
                    result.getString("payerId")
            );
            list.add(staffSalary);
        }
        return list;
    }

    @Override
    public List getSalaryData(int year) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM StaffSalary WHERE year=? ORDER BY paymentCode ASC",year);
        List<StaffSalary> list = FXCollections.observableArrayList();
        while(result.next()){
            StaffSalary staffSalary = new StaffSalary(
                    result.getString("paymentCode"),
                    result.getString("staffId"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getDouble("salary"),
                    result.getString("date"),
                    result.getString("payerId")
            );
            list.add(staffSalary);
        }
        return list;
    }

    @Override
    public List getSalaryData(String month) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM StaffSalary WHERE month=? ORDER BY paymentCode ASC",month);
        List<StaffSalary> list = FXCollections.observableArrayList();
        while(result.next()){
            StaffSalary staffSalary = new StaffSalary(
                    result.getString("paymentCode"),
                    result.getString("staffId"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getDouble("salary"),
                    result.getString("date"),
                    result.getString("payerId")
            );
            list.add(staffSalary);
        }
        return list;
    }

    @Override
    public List getAllDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM StaffSalary WHERE month=? AND year=? ORDER BY paymentCode ASC",monthName, year);
        List<StaffSalary> list = FXCollections.observableArrayList();
        while(result.next()){
            StaffSalary staffSalary = new StaffSalary(
                    result.getString("paymentCode"),
                    result.getString("staffId"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getDouble("salary"),
                    result.getString("date"),
                    result.getString("payerId")
            );
            list.add(staffSalary);
           Object ob = new Object [10];
        }
        return list;
    }

}
