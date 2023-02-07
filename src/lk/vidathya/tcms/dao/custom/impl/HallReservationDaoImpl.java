package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import lk.vidathya.tcms.dao.custom.HallReservationDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.HallReservation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HallReservationDaoImpl implements HallReservationDAO {
    private final Connection connection;

    public HallReservationDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(HallReservation hallReservation) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO HallReservation VALUES (?,?,?,?,?,?)",
                hallReservation.getHallReservationNo(),
                hallReservation.getHallNo(),
                hallReservation.getClassCode(),
                hallReservation.getDay(),
                hallReservation.getStartTime(),
                hallReservation.getEndTime()
        );
        return isAdd;
    }

    @Override
    public boolean update(HallReservation hallReservation) throws SQLException, ClassNotFoundException {
        boolean isUpdate = CrudUtil.execute("UPDATE HallReservation SET hallNo=? , day=?, startTime=?, endTime=? WHERE hallReservationNo=?",
                hallReservation.getHallNo(),
                hallReservation.getDay(),
                hallReservation.getStartTime(),
                hallReservation.getEndTime(),
                hallReservation.getHallReservationNo()
        );
        return isUpdate;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        boolean isDelete = CrudUtil.execute("DELETE FROM HallReservation WHERE hallReservationNo=?", args[0]);
        return isDelete;
    }

    @Override
    public HallReservation searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public String getHallReservationNo(String classCode) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT hallReservationNo FROM HallReservation WHERE classCode=? ",classCode);
        if(result.next()){
            return result.getString("hallReservationNo");
        }
        return null;
    }

    @Override
    public List getDataToViewTable() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM HallReservation ORDER BY day ASC");
        List<HallReservation> list = FXCollections.observableArrayList();
        while(result.next()){
            HallReservation hallReservation = new HallReservation(
                    result.getString("hallReservationNo"),
                    result.getString("hallNo"),
                    result.getString("classCode"),
                    result.getString("day"),
                    result.getString("startTime"),
                    result.getString("endTime")
            );
            list.add(hallReservation);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT hallReservationNo FROM HallReservation ORDER BY hallReservationNo DESC LIMIT 1");
        if(result.next()){
            return result.getString("hallReservationNo");
        }
        return null;
    }
}
