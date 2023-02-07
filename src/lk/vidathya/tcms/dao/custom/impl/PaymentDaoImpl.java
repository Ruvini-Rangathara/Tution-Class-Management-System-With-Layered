package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import lk.vidathya.tcms.dao.custom.PaymentDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.Payment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PaymentDaoImpl implements PaymentDAO {
    private final Connection connection;

    public PaymentDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(Payment payment) throws SQLException, ClassNotFoundException {
        boolean isAddPayment = CrudUtil.execute("INSERT INTO StudentPayment VALUES(?,?,?,?,?,?,?,?,?)",
                payment.getPaymentCode(),
                payment.getDescription(),
                payment.getClassCode(),
                payment.getFee(),
                payment.getStudentId(),
                payment.getYear(),
                payment.getMonth(),
                payment.getDate(),
                payment.getStaffId()
        );
        return isAddPayment;
    }

    @Override
    public boolean update(Payment entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Payment searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM StudentPayment WHERE paymentCode=?",args[0]);
        if(result.next()){
            Payment payment = new Payment(
                    result.getString("paymentCode"),
                    result.getString("description"),
                    result.getString("classCode"),
                    result.getDouble("fee"),
                    result.getString("studentId"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getString("date"),
                    result.getString("staffId")
            );
            return payment;
        }
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
        ResultSet result = CrudUtil.execute("SELECT paymentCode FROM StudentPayment ORDER BY paymentCode DESC LIMIT 1");
        if(result.next()){
            return result.getString("paymentCode");
        }
        return null;
    }

    @Override
    public List getPaidData(String classCode, int year, String month) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM StudentPayment WHERE (classCode=?) AND (year=?) AND (month=?) ORDER BY paymentCode DESC",
                classCode,
                year,
                month
        );
        List<Payment> list = FXCollections.observableArrayList();
        while(result.next()){
            Payment payment = new Payment(
                    result.getString("paymentCode"),
                    result.getString("description"),
                    result.getString("classCode"),
                    result.getDouble("fee"),
                    result.getString("studentId"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getString("date"),
                    result.getString("staffId")
            );
            list.add(payment);
        }
        return list;
    }

    @Override
    public String getPaymentStatus(String studentId, String classCode, int year, String monthName) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("select * from studentPayment where studentId=? and classCode=? and year=? and month=?",
                studentId,
                classCode,
                year,
                monthName
        );

        if(result.next()){
            return "paid";
        }
        return "Not paid";
    }
}
