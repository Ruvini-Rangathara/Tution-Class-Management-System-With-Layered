package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.NotPaidTutorSalaryDTO;
import lk.vidathya.tcms.entity.NotPaidTutorSalary;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface NotPaidTutorSalaryService  extends SuperService<NotPaidTutorSalaryDTO> {
    double getExistSalary(String tutorId) throws SQLException, ClassNotFoundException;

    List getAllNotPaidDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException;
    double getNotPaidTutorSalaryOfATutor(String tutorId, String classCode) throws SQLException, ClassNotFoundException;
    boolean updateNotPaidTutorSalaryAfter(String tutorId, String classCode) throws SQLException, ClassNotFoundException;
}
