package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.StaffSalary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface StaffSalaryDAO extends CrudDAO<StaffSalary> {

    String getLastPaymentCode() throws SQLException, ClassNotFoundException;
    List getSalaryData(int year, String monthName) throws SQLException, ClassNotFoundException;
    List getSalaryData(int year) throws SQLException, ClassNotFoundException;
    List getSalaryData(String month) throws SQLException, ClassNotFoundException;
    List getAllDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException;

}
