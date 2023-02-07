package lk.vidathya.tcms.service.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.dto.UserDTO;
import lk.vidathya.tcms.entity.User;
import lk.vidathya.tcms.service.SuperService;

import java.sql.SQLException;

public interface UserService  extends SuperService<UserDTO> {

    UserDTO searchByPassword(String password) throws SQLException, ClassNotFoundException;
    boolean checkUsername(String username) throws SQLException, ClassNotFoundException;
    boolean checkPassword(String username, String uncheckedPassword) throws SQLException, ClassNotFoundException;
    String getPasswordHint(String username) throws SQLException, ClassNotFoundException;
}
