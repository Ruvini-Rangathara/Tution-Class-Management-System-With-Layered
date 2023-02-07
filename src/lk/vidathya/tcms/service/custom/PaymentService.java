package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.PaymentDTO;
import lk.vidathya.tcms.entity.Payment;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface PaymentService  extends SuperService<PaymentDTO> {
    String getNewId() throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
    List getPaidData(String classCode, int year, String month) throws SQLException, ClassNotFoundException;
    String getPaymentStatus(String studentId, String classCode, int year, String monthName) throws SQLException, ClassNotFoundException;
}
