package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import lk.vidathya.tcms.dao.custom.ClassesDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.HallReservationDTO;
import lk.vidathya.tcms.entity.Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClassesDaoImpl implements ClassesDAO {
    private final Connection connection;

    public ClassesDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(Classes classes) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO Classes VALUES (?,?,?,?,?,?,?,?,?,?)",
                classes.getClassCode(),
                classes.getGrade(),
                classes.getSubject(),
                classes.getTutorId(),
                classes.getDay(),
                classes.getStartTime(),
                classes.getEndTime(),
                classes.getHallNo(),
                classes.getClassFee(),
                classes.getDate()
        );
        return isAdd;
    }

    @Override
    public boolean update(Classes classes) throws SQLException, ClassNotFoundException {
        boolean isUpdate = CrudUtil.execute("UPDATE Classes SET day=?, startTime=?, endTime=?, hallNo=? , classFee=? WHERE classCode=?",
                classes.getDay(),
                classes.getStartTime(),
                classes.getEndTime(),
                classes.getHallNo(),
                classes.getClassFee(),
                classes.getClassCode()
        );
        return isUpdate;
    }

    @Override
    public boolean delete(Object... args) {
        return false;
    }

    @Override
    public Classes searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Classes WHERE classCode=?",args[0]);
        if(result.next()){
            Classes classes = new Classes(
                    result.getString("classCode"),
                    result.getString("grade"),
                    result.getString("subject"),
                    result.getString("tutorId"),
                    result.getString("day"),
                    result.getString("startTime"),
                    result.getString("endTime"),
                    result.getString("hallNo"),
                    result.getDouble("classFee"),
                    result.getString("date")
            );
            return classes;
        }
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Classes ORDER  BY classCode ASC");
        List<Classes> list = FXCollections.observableArrayList();
        while (result.next()){
            Classes classes = new Classes(
                    result.getString("classCode"),
                    result.getString("grade"),
                    result.getString("subject"),
                    result.getString("tutorId"),
                    result.getString("day"),
                    result.getString("startTime"),
                    result.getString("endTime"),
                    result.getString("hallNo"),
                    result.getDouble("classFee"),
                    result.getString("date")
            );
            list.add(classes);
        }
        return list;
    }


    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT classCode FROM Classes ORDER BY classCode DESC LIMIT 1");
        if(result.next()){
            return result.getString("classCode");
        }
        return null;
    }

    @Override
    public List<String> loadIdToComboBox() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT classCode FROM Classes ORDER BY classCode ASC");
        List<String> options = FXCollections.observableArrayList();
        while (result.next()) {
            options.add(result.getString("classCode"));
        }
        return options;
    }

    @Override
    public List<String> loadGradeToComboBox() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT DISTINCT grade FROM Classes");
        List<String> options = FXCollections.observableArrayList();
        while (result.next()) {
            options.add(result.getString("grade"));
        }
        return options;
    }

    @Override
    public List<Classes> getDataToScheduleTable(String dayName) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT subject,grade,hallNo,startTime,endTime FROM Classes WHERE day=?",dayName);
        List<Classes> list = FXCollections.observableArrayList();
        while (result.next()) {
            Classes classes = new Classes(
                    result.getString("grade"),
                    result.getString("subject"),
                    result.getString("startTime"),
                    result.getString("endTime"),
                    result.getString("hallNo")
            );
            list.add(classes);
        }
        return list;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }
}
