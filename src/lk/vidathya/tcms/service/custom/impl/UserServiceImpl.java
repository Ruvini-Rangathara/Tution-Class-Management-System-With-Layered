package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.PaymentDAO;
import lk.vidathya.tcms.dao.custom.UserDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.UserDTO;
import lk.vidathya.tcms.entity.User;
import lk.vidathya.tcms.service.custom.UserService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final Convertor convertor;
    private final Connection connection;

    public UserServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        userDAO= DaoFactory.getInstance().getDao(connection, DaoTypes.USER_DAO_IMPL );
        convertor=new Convertor();
    }

    @Override
    public boolean add(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDAO.add(convertor.toUser(userDTO));
    }

    @Override
    public boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDAO.update(convertor.toUser(userDTO));
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public UserDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return convertor.toUserDTO(userDAO.searchById(args));
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public UserDTO searchByPassword(String password) throws SQLException, ClassNotFoundException {
        return convertor.toUserDTO(userDAO.searchByPassword(password));
    }

    @Override
    public boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
        return userDAO.checkUsername(username);
    }

    @Override
    public boolean checkPassword(String username, String uncheckedPassword) throws SQLException, ClassNotFoundException {
        return userDAO.checkPassword(username,uncheckedPassword);
    }

    @Override
    public String getPasswordHint(String username) throws SQLException, ClassNotFoundException {
        return userDAO.getPasswordHint(username);
    }
}
