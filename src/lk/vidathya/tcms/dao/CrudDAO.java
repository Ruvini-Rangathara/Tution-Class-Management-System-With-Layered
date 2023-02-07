package lk.vidathya.tcms.dao;

import lk.vidathya.tcms.entity.SuperEntity;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T extends SuperEntity> extends SuperDAO {

    boolean add(T entity) throws SQLException, ClassNotFoundException;
    boolean update(T entity) throws SQLException, ClassNotFoundException;
    boolean delete(Object... args) throws SQLException, ClassNotFoundException;
    T searchById(Object... args) throws SQLException, ClassNotFoundException;
    List getAll() throws SQLException, ClassNotFoundException;
    boolean existByPk(Object... args) throws SQLException, ClassNotFoundException;

}
