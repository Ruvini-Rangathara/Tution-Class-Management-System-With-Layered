package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.StaffSalaryDTO;
import lk.vidathya.tcms.entity.StaffSalary;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface StaffSalaryService  extends SuperService<StaffSalaryDTO> {

    String getNewId() throws SQLException, ClassNotFoundException;
    String getLastPaymentCode() throws SQLException, ClassNotFoundException;
    List getSalaryData(int year, String monthName) throws SQLException, ClassNotFoundException;
    List getSalaryData(int year) throws SQLException, ClassNotFoundException;
    List getSalaryData(String month) throws SQLException, ClassNotFoundException;
    List getAllDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException;

}
