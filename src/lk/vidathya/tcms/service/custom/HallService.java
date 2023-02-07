package lk.vidathya.tcms.service.custom;


import lk.vidathya.tcms.dto.HallDTO;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface HallService  extends SuperService<HallDTO> {

    String getNewId() throws SQLException, ClassNotFoundException;
    String getLastId() throws SQLException, ClassNotFoundException;
    List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException;
    int getAllCount() throws SQLException, ClassNotFoundException;
}
