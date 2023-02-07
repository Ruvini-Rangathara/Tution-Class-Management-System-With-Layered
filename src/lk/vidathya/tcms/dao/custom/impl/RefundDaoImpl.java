package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import lk.vidathya.tcms.dao.custom.RefundDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.Refund;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RefundDaoImpl implements RefundDAO {
    private final Connection connection;

    public RefundDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(Refund refund) throws SQLException, ClassNotFoundException {
        boolean isAddRefund = CrudUtil.execute("INSERT INTO Refund VALUES (?,?,?,?,?,?,?)",
                refund.getPaymentCode(),
                refund.getStudentId(),
                refund.getDescription(),
                refund.getAmount(),
                refund.getDate(),
                refund.getStaffId(),
                refund.getMonth()
        );
        return isAddRefund;
    }

    @Override
    public boolean update(Refund entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Refund searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAllByMonth(String month) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Refund WHERE month=? ORDER BY paymentCode DESC", month);
        List<Refund> list = FXCollections.observableArrayList();
        while(result.next()){
            Refund refund = new Refund(

            );
            list.add(refund);
        }
        return list;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }
}
