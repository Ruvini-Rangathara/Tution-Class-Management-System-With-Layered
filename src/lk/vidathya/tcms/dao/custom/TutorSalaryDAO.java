package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.TutorSalary;

import java.sql.SQLException;
import java.util.List;

public interface TutorSalaryDAO extends CrudDAO<TutorSalary> {
    String getLastId() throws SQLException, ClassNotFoundException;
    List getAllByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException;
}
