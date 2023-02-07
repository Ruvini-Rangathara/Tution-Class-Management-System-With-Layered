package lk.vidathya.tcms.dao.custom;

import lk.vidathya.tcms.dao.CrudDAO;
import lk.vidathya.tcms.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    User searchByPassword(String password) throws SQLException, ClassNotFoundException;
    boolean checkUsername(String username) throws SQLException, ClassNotFoundException;
    boolean checkPassword(String username, String uncheckedPassword) throws SQLException, ClassNotFoundException;
    String getPasswordHint(String username) throws SQLException, ClassNotFoundException;
}
