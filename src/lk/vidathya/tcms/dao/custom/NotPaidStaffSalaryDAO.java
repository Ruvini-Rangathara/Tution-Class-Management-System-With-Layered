package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.NotPaidStaffSalary;

import java.sql.SQLException;
import java.util.List;

public interface NotPaidStaffSalaryDAO extends CrudDAO<NotPaidStaffSalary> {
    List getAllByMonthYear(String month, int year) throws SQLException, ClassNotFoundException;
}
