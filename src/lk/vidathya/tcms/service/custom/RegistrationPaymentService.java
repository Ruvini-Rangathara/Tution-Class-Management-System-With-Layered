package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.RegistrationPaymentDTO;
import lk.vidathya.tcms.entity.RegistrationPayment;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface RegistrationPaymentService   extends SuperService<RegistrationPaymentDTO> {
    List getAllDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException;
}
