package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.vidathya.tcms.dao.custom.StaffDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.Staff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDaoImpl implements StaffDAO {
    private final Connection connection;

    public StaffDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(Staff staff) throws SQLException, ClassNotFoundException {
        boolean isAdd=CrudUtil.execute("INSERT INTO Staff VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                staff.getStaffId(),
                staff.getName(),
                staff.getJob(),
                staff.getNic(),
                staff.getGender(),
                staff.getAddress(),
                staff.getContactNo(),
                staff.getEmail(),
                staff.getDob(),
                staff.getSalary(),
                staff.getDate()
        );
        return isAdd;
    }

    @Override
    public boolean update(Staff staff) throws SQLException, ClassNotFoundException {
        boolean isUpdated = CrudUtil.execute("UPDATE Staff SET name=?, job=?, nic=?, gender=?, address=?, contactNo=?, email=?, dob=?, salary=? WHERE staffId=?",
                staff.getName(),
                staff.getJob(),
                staff.getNic(),
                staff.getGender(),
                staff.getAddress(),
                staff.getContactNo(),
                staff.getEmail(),
                staff.getDob(),
                staff.getSalary(),
                staff.getStaffId()
        );
        return isUpdated;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Staff searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Staff WHERE staffId=?",args[0]);
        if(result.next()){
            Staff staff = new Staff(
                    result.getString("staffId"),
                    result.getString("name"),
                    result.getString("job"),
                    result.getString("nic"),
                    result.getString("gender"),
                    result.getString("address"),
                    result.getString("contactNo"),
                    result.getString("email"),
                    result.getString("dob"),
                    result.getDouble("salary"),
                    result.getString("date")
            );
            return staff;
        }
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Staff ORDER BY StaffId");
        List<Staff> list = new ArrayList<>();
        while (result.next()){
            Staff staff = new Staff(
                    result.getString("staffId"),
                    result.getString("name"),
                    result.getString("job"),
                    result.getString("nic"),
                    result.getString("gender"),
                    result.getString("address"),
                    result.getString("contactNo"),
                    result.getString("email"),
                    result.getString("dob"),
                    result.getDouble("salary"),
                    result.getString("date")
            );
            list.add(staff);
        }
        return list;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT staffId FROM Staff ORDER BY staffId DESC LIMIT 1");
        if(result.next()){
            return result.getString("staffId");
        }
        return null;
    }

    @Override
    public List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT staffId FROM Staff ORDER BY staffId ASC");
        List<String> options = FXCollections.observableArrayList();
        while (result.next()) {
            options.add(result.getString("staffId"));
        }
        return options;
    }

    @Override
    public List<String> getStaffEmailAddress() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT email FROM Staff");
        List<String> mailAddress = new ArrayList<>();
        while(result.next()){
            mailAddress.add(result.getString("email"));
        }
        return mailAddress;
    }

    @Override
    public ResultSet getImage(String staffId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT image FROM StaffImage Where staffId=?",staffId);
        return result;
    }

    @Override
    public boolean isExistImage(String staffId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT staffId FROM StaffImage");
        while (result.next()){
            if(result.getString("staffId").equals(staffId)){
                return true;
            }
        }
        return false;
    }
}
