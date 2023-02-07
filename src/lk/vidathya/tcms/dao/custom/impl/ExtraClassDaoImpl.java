package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.vidathya.tcms.dao.custom.ExtraClassDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.ExtraClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ExtraClassDaoImpl implements ExtraClassDAO {
    private final Connection connection;

    public ExtraClassDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(ExtraClass extraClass) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO ExtraClass Values(?,?,?,?,?,?)",
                extraClass.geteClassCode(),
                extraClass.getClassCode(),
                extraClass.getDate(),
                extraClass.getStartTime(),
                extraClass.getEndTime(),
                extraClass.getHallNo()
        );
        return isAdd;
    }

    @Override
    public boolean update(ExtraClass extraClass) throws SQLException, ClassNotFoundException {
        boolean isUpdate = CrudUtil.execute("UPDATE ExtraClass SET date=?, startTime=?, endTime=?, hallNo=? WHERE eClassCode=?",
                extraClass.getDate(),
                extraClass.getStartTime(),
                extraClass.getEndTime(),
                extraClass.getHallNo(),
                extraClass.geteClassCode()
        );
        return isUpdate;
    }

    @Override
    public boolean delete(Object... args) {
        return false;
    }

    @Override
    public ExtraClass searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM ExtraClass WHERE eClassCode=?",args[0]);
        if(result.next()){
            ExtraClass extraClass = new ExtraClass(
                    result.getString("eClassCode"),
                    result.getString("classCode"),
                    result.getString("date"),
                    result.getString("startTime"),
                    result.getString("endTime"),
                    result.getString("hallNo")
            );
            return extraClass;
        }
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM ExtraClass ORDER BY date DESC");
        List<ExtraClass> list = FXCollections.observableArrayList();
        while (result.next()){
            ExtraClass extraClass = new ExtraClass(
                    result.getString("eClassCode"),
                    result.getString("classCode"),
                    result.getString("date"),
                    result.getString("startTime"),
                    result.getString("endTime"),
                    result.getString("hallNo")
            );
            list.add(extraClass);
        }
        return list;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT eClassCode FROM ExtraClass ORDER BY eClassCode DESC LIMIT 1");
        if(result.next()){
            return result.getString("eClassCode");
        }
        return null;
    }

    @Override
    public List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT eClassCode FROM ExtraClass ORDER BY eClassCode DESC");
        List<String> options = FXCollections.observableArrayList();
        while (result.next()) {
            options.add(result.getString("eClassCode"));
        }
        return options;
    }

    @Override
    public List<ExtraClass> getDataToScheduleTable(String date) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM ExtraClass WHERE date=?", date);
        List<ExtraClass> list = FXCollections.observableArrayList();
        while (result.next()){
            ExtraClass extraClass = new ExtraClass(
                    result.getString("eClassCode"),
                    result.getString("classCode"),
                    result.getString("date"),
                    result.getString("startTime"),
                    result.getString("endTime"),
                    result.getString("hallNo")
            );
            list.add(extraClass);
        }
        return list;
    }
}
