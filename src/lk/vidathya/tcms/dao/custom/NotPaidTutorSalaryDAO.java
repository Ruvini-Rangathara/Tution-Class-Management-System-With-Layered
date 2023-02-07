package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.NotPaidTutorSalary;

import java.sql.SQLException;
import java.util.List;

public interface NotPaidTutorSalaryDAO  extends CrudDAO<NotPaidTutorSalary> {

    double getExistSalary(String tutorId) throws SQLException, ClassNotFoundException;
    List getAllNotPaidDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException;
    double getNotPaidTutorSalaryOfATutor(String tutorId, String classCode) throws SQLException, ClassNotFoundException;
    boolean updateNotPaidTutorSalaryAfter(String tutorId, String classCode) throws SQLException, ClassNotFoundException;
}
