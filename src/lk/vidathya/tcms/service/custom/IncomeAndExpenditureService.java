package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.IncomeAndExpenditureDTO;
import lk.vidathya.tcms.entity.IncomeAndExpenditure;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface IncomeAndExpenditureService   extends SuperService<IncomeAndExpenditureDTO> {
    List getIncomeDataTable(int year, String monthName) throws SQLException, ClassNotFoundException;
    List getExpenditureDataTable(int year,String monthName) throws SQLException, ClassNotFoundException;
}
