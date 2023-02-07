package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.vidathya.tcms.dao.custom.TutorDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.Tutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TutorDaoImpl implements TutorDAO {
    private final Connection connection;

    public TutorDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(Tutor tutor) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO Tutor VALUES(?,?,?,?,?,?,?,?,?,?)",
                tutor.getTutorId(),
                tutor.getName(),
                tutor.getNic(),
                tutor.getGender(),
                tutor.getAddress(),
                tutor.getContactNo(),
                tutor.getEmail(),
                tutor.getDob(),
                tutor.getSubject(),
                tutor.getDate()
        );

        return isAdd;
    }

    @Override
    public boolean update(Tutor tutor) throws SQLException, ClassNotFoundException {
        boolean isUpdate = CrudUtil.execute("UPDATE Tutor SET name=?, nic=?, gender=?, address=?, contactNo=?, email=?, dob=?, subject=? WHERE tutorId=?",
                tutor.getName(),
                tutor.getNic(),
                tutor.getGender(),
                tutor.getAddress(),
                tutor.getContactNo(),
                tutor.getEmail(),
                tutor.getDob(),
                tutor.getSubject(),
                tutor.getTutorId()
        );
        return isUpdate;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Tutor searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Tutor WHERE tutorId=?",args[0]);
        if(result.next()){
            Tutor tutor = new Tutor(
                    result.getString("tutorId"),
                    result.getString("name"),
                    result.getString("nic"),
                    result.getString("gender"),
                    result.getString("address"),
                    result.getString("contactNo"),
                    result.getString("email"),
                    result.getString("dob"),
                    result.getString("subject"),
                    result.getString("date")
            );
            return tutor;
        }
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Tutor");
        List<Tutor> list = new ArrayList<>();
        while (result.next()){
            Tutor tutor = new Tutor(
                    result.getString("tutorId"),
                    result.getString("name"),
                    result.getString("nic"),
                    result.getString("gender"),
                    result.getString("address"),
                    result.getString("contactNo"),
                    result.getString("email"),
                    result.getString("dob"),
                    result.getString("subject"),
                    result.getString("date")
            );
            list.add(tutor);
        }
        return list;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT tutorId FROM Tutor ORDER BY tutorId DESC LIMIT 1");
        if(result.next()){
            return result.getString("tutorId");
        }
        return null;
    }

    @Override
    public List<String> loadTutorIdToComboBox() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT tutorId FROM Tutor ORDER BY tutorId ASC");

        List<String> options = FXCollections.observableArrayList();
        while (result.next()) {
            options.add(result.getString("tutorId"));
        }
        return options;
    }

    @Override
    public List<String> loadSubjectToComboBox() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT DISTINCT subject FROM Tutor");
        List<String> options = FXCollections.observableArrayList();
        while (result.next()) {
            options.add(result.getString("subject"));
        }
        return options;
    }

    @Override
    public int getTutorCount() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT COUNT(tutorId) AS tutorCount FROM Tutor");
        if(result.next()){
            return result.getInt("tutorCount");
        }
        return 0;
    }

    @Override
    public List<String> getTutorEmailAddress() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT email FROM Tutor");
        ArrayList<String> mailAddress = new ArrayList<>();
        while(result.next()){
            mailAddress.add(result.getString("email"));
        }
        return mailAddress;
    }

    @Override
    public boolean isExistImage(String tutorId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT tutorId FROM TutorImage");

        while (result.next()){
            if(result.getString("tutorId").equals(tutorId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ResultSet getImage(String tutorId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT image FROM TutorImage Where tutorId=?",tutorId);
        return result;
    }
}
