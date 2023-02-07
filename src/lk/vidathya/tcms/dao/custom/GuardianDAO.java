package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.Guardian;

import java.sql.SQLException;
import java.util.List;

public interface GuardianDAO  extends CrudDAO<Guardian> {
    List<String> getAllEmailAddress() throws SQLException, ClassNotFoundException;
}
