package lk.vidathya.tcms.dao.custom.impl;

import lk.vidathya.tcms.dao.custom.NotPaidTutorSalaryDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.NotPaidTutorSalary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotPaidTutorSalaryDaoImpl implements NotPaidTutorSalaryDAO {
    private final Connection connection;

    public NotPaidTutorSalaryDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(NotPaidTutorSalary notPaidTutorSalary) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO NotPaidTutorSalary VALUES (?,?,?,?,?)",
                notPaidTutorSalary.getTutorId(),
                notPaidTutorSalary.getClassCode(),
                notPaidTutorSalary.getYear(),
                notPaidTutorSalary.getMonth(),
                notPaidTutorSalary.getSalary()
        );
        return  isAdd;
    }

    @Override
    public boolean update(NotPaidTutorSalary notPaidTutorSalary) throws SQLException, ClassNotFoundException {
        boolean isUpdate = CrudUtil.execute("UPDATE NotPaidTutorSalary SET year=?, month=?, salary=? WHERE (tutorId=?) AND (classCode=?)",
                notPaidTutorSalary.getYear(),
                notPaidTutorSalary.getMonth(),
                notPaidTutorSalary.getSalary(),
                notPaidTutorSalary.getTutorId(),
                notPaidTutorSalary.getClassCode()
        );
        return  isUpdate;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public NotPaidTutorSalary searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public double getExistSalary(String tutorId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT salary FROM NotPaidTutorSalary WHERE tutorId=?", tutorId);
        if(result.next()){
            return result.getDouble("salary");
        }
        return 0;
    }

    @Override
    public List getAllNotPaidDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM NotPaidTutorSalary WHERE (year=?) AND (month=?)",year, monthName);
        List<NotPaidTutorSalary> list = new ArrayList<>();
        while (result.next()){
            NotPaidTutorSalary notPaidTutorSalary = new NotPaidTutorSalary(
                    result.getString("tutorId"),
                    result.getString("classCode"),
                    result.getInt("year"),
                    result.getString("month"),
                    result.getDouble("salary")
            );
            list.add(notPaidTutorSalary);
        }
        return list;
    }

    @Override
    public double getNotPaidTutorSalaryOfATutor(String tutorId, String classCode) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT salary FROM NotPaidTutorSalary WHERE (tutorId=?) AND (classCode=?)", tutorId, classCode);
        if(result.next()){
            return result.getDouble("salary");
        }
        return 0;
    }

    @Override
    public boolean updateNotPaidTutorSalaryAfter(String tutorId, String classCode) throws SQLException, ClassNotFoundException {
        boolean update = CrudUtil.execute("UPDATE NotPaidTutorSalary SET salary=? WHERE (tutorId=?) AND (classCode=?)", 0,tutorId,classCode);
        return update;
    }
}
