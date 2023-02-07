package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
    String getLastId() throws SQLException, ClassNotFoundException;
    List getPaidData(String classCode, int year, String month) throws SQLException, ClassNotFoundException;
    String getPaymentStatus(String studentId, String classCode, int year, String monthName) throws SQLException, ClassNotFoundException;
}
