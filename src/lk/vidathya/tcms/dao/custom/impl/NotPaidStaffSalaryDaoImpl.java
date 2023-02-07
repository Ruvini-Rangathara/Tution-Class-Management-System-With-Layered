package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import lk.vidathya.tcms.dao.custom.NotPaidStaffSalaryDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.NotPaidStaffSalary;
import lk.vidathya.tcms.util.MonthName;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class NotPaidStaffSalaryDaoImpl implements NotPaidStaffSalaryDAO {
    private final Connection connection;

    public NotPaidStaffSalaryDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(NotPaidStaffSalary notPaidStaffSalary) throws SQLException, ClassNotFoundException {

        boolean isAdd = CrudUtil.execute("INSERT INTO NotPaidStaffSalary VALUES (?,?,?,?)",
                notPaidStaffSalary.getStaffId(),
                notPaidStaffSalary.getYear(),
                notPaidStaffSalary.getMonth(),
                notPaidStaffSalary.getSalary()
        );
        return isAdd;
    }

    @Override
    public boolean update(NotPaidStaffSalary entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public NotPaidStaffSalary searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public List getAllByMonthYear(String month, int year) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM NotPaidStaffSalary WHERE month=? AND year=? ORDER BY staffId ASC",month, year);
        List<NotPaidStaffSalary> list = FXCollections.observableArrayList();
        while(result.next()){
            NotPaidStaffSalary notPaidStaffSalary = new NotPaidStaffSalary(
                    result.getString("staffId"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getDouble("salary")
            );
            list.add(notPaidStaffSalary);
        }
        return list;
    }

}
