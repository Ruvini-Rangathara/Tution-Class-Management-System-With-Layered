package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.GuardianDTO;
import lk.vidathya.tcms.entity.Guardian;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface GuardianService  extends SuperService<GuardianDTO> {
    List<String> getAllEmailAddress() throws SQLException, ClassNotFoundException;
}
