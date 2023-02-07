package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.NotPaidStaffSalaryDTO;
import lk.vidathya.tcms.entity.NotPaidStaffSalary;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface NotPaidStaffSalaryService  extends SuperService<NotPaidStaffSalaryDTO> {
    List getAllByMonthYear(String month, int year) throws SQLException, ClassNotFoundException;
}
