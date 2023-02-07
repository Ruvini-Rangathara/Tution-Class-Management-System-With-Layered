package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.Refund;

import java.sql.SQLException;
import java.util.List;

public interface RefundDAO extends CrudDAO<Refund> {
    List getAllByMonth(String month) throws SQLException, ClassNotFoundException;
}
