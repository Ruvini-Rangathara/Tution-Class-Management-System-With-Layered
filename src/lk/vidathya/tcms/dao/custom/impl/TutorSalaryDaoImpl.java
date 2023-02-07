package lk.vidathya.tcms.dao.custom.impl;

import lk.vidathya.tcms.dao.custom.TutorSalaryDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.TutorSalary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TutorSalaryDaoImpl implements TutorSalaryDAO {
    private final Connection connection;

    public TutorSalaryDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(TutorSalary tutorSalary) throws SQLException, ClassNotFoundException {
        boolean isAddTutorSalary = CrudUtil.execute("INSERT INTO TutorSalary VALUES (?,?,?,?,?,?,?,?)",
                tutorSalary.getPaymentCode(),
                tutorSalary.getClassCode(),
                tutorSalary.getTutorId(),
                tutorSalary.getYear(),
                tutorSalary.getMonth(),
                tutorSalary.getSalary(),
                tutorSalary.getDate(),
                tutorSalary.getPayerId()
        );

        return isAddTutorSalary;
    }

    @Override
    public boolean update(TutorSalary entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public TutorSalary searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT paymentCode FROM TutorSalary ORDER BY paymentCode DESC LIMIT 1");
        if(result.next()){
            return result.getString("paymentCode");
        }
        return null;
    }

    @Override
    public List getAllByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM TutorSalary WHERE (year=?) AND (month=?)",year, monthName);
        List<TutorSalary> list = new ArrayList<>();
        while (result.next()){
            TutorSalary tutorSalary = new TutorSalary(
                    result.getString("paymentCode"),
                    result.getString("classCode"),
                    result.getString("tutorId"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getDouble("salary"),
                    result.getString("date"),
                    result.getString("payerId")
            );
            list.add(tutorSalary);
        }
        return list;
    }
}
