package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.RefundDTO;
import lk.vidathya.tcms.entity.Refund;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface RefundService  extends SuperService<RefundDTO> {
    List getAllByMonth(String month) throws SQLException, ClassNotFoundException;
}
