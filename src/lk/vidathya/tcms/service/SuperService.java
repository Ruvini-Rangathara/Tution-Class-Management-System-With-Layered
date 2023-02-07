package lk.vidathya.tcms.service;

import lk.vidathya.tcms.dto.SuperDTO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface SuperService <T extends SuperDTO> extends Serializable {
    boolean add(T entity) throws SQLException, ClassNotFoundException;
    boolean update(T entity) throws SQLException, ClassNotFoundException;
    boolean delete(Object... args) throws SQLException, ClassNotFoundException;
    T searchById(Object... args) throws SQLException, ClassNotFoundException;
    List getAll() throws SQLException, ClassNotFoundException;
    boolean existByPk(Object... args);
}
