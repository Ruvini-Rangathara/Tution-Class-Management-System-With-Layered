package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import lk.vidathya.tcms.dao.custom.RegistrationPaymentDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.RegistrationPayment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RegistrationPaymentDaoImpl implements RegistrationPaymentDAO {
    private final Connection connection;

    public RegistrationPaymentDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(RegistrationPayment registrationPayment) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO RegistrationPayment (fee, studentId, year, month, date, staffId ) VALUES(?,?,?,?,?,?)",
                registrationPayment.getFee(),
                registrationPayment.getStudentId(),
                registrationPayment.getYear(),
                registrationPayment.getMonth(),
                registrationPayment.getDate(),
                registrationPayment.getStaffId()
        );
        return isAdd;
    }

    @Override
    public boolean update(RegistrationPayment entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public RegistrationPayment searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
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
    public List getAllDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM RegistrationPayment WHERE (year=?) AND (month=?)", year, monthName);
        List<RegistrationPayment> list = FXCollections.observableArrayList();
        while(result.next()){
            RegistrationPayment registrationPayment = new RegistrationPayment(
                    result.getInt("code"),
                    result.getDouble("fee"),
                    result.getString("studentId"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getString("date"),
                    result.getString("staffId")
            );
            list.add(registrationPayment);
        }
        return list;
    }
}
