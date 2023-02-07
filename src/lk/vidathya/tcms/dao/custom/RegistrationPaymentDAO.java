package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.RegistrationPayment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RegistrationPaymentDAO extends CrudDAO<RegistrationPayment> {
    List getAllDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException;

}
