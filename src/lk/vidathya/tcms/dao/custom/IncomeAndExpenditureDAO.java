package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.IncomeAndExpenditure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IncomeAndExpenditureDAO extends CrudDAO<IncomeAndExpenditure> {

    List getIncomeDataTable(int year, String monthName) throws SQLException, ClassNotFoundException;
    List getExpenditureDataTable(int year,String monthName) throws SQLException, ClassNotFoundException;

}
