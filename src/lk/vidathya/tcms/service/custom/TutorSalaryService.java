package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.TutorSalaryDTO;
import lk.vidathya.tcms.entity.TutorSalary;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface TutorSalaryService  extends SuperService<TutorSalaryDTO> {
    String getNewId() throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
    List getAllByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException;
}
