package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.vidathya.tcms.dao.custom.HallDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.Hall;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HallDaoImpl implements HallDAO {
    private final Connection connection;

    public HallDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(Hall hall) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO Hall VALUES (?,?,?)",
                hall.getHallNo(),
                hall.getCapacity(),
                hall.getAvailableFacilities()
        );
        return isAdd;
    }

    @Override
    public boolean update(Hall hall) throws SQLException, ClassNotFoundException {
        boolean isUpdate = CrudUtil.execute("UPDATE Hall SET capacity=?, availableFacilities=? WHERE hallNo=?",
                hall.getCapacity(),
                hall.getAvailableFacilities(),
                hall.getHallNo()
        );
        return isUpdate;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Hall searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Hall WHERE hallNo=?", args[0]);
        if(result.next()){
            Hall hall = new Hall(
                    result.getString("hallNo"),
                    result.getInt("capacity"),
                    result.getString("availableFacilities")
            );
            return hall;
        }
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Hall");
        List<Hall> list = FXCollections.observableArrayList();
        while(result.next()) {
            Hall hall = new Hall(
                    result.getString("hallNo"),
                    result.getInt("capacity"),
                    result.getString("availableFacilities")
            );
            list.add(hall);
        }
        return list;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT hallNo FROM Hall ORDER BY hallNo DESC LIMIT 1");
        if(result.next()){
            return result.getString("hallNo");
        }
        return null;
    }

    @Override
    public List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT hallNo FROM Hall ORDER BY hallNo ASC");
        List<String> options = FXCollections.observableArrayList();
        while (result.next()) {
            options.add(result.getString("hallNo"));
        }
        return options;
    }

    @Override
    public int getAllCount() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT COUNT(hallNo) AS HallCount FROM Hall");
        if(result.next()){
            return result.getInt("HallCount");
        }
        return 0;
    }
}
