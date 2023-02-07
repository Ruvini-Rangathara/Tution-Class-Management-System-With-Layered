package lk.vidathya.tcms.dao.custom.impl;

import javafx.collections.FXCollections;
import lk.vidathya.tcms.dao.custom.ExtraClassHallReservationDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.ExtraClassHallReservation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ExtraClassHallReservationDaoImpl implements ExtraClassHallReservationDAO {
    private final Connection connection;

    public ExtraClassHallReservationDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(ExtraClassHallReservation extraClassHallReservation) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO ExtraClassHallReservation VALUES (?,?,?,?,?,?)",
                extraClassHallReservation.getHallReservationNo(),
                extraClassHallReservation.getHallNo(),
                extraClassHallReservation.geteClassCode(),
                extraClassHallReservation.getDate(),
                extraClassHallReservation.getStartTime(),
                extraClassHallReservation.getEndTime()
        );
        return isAdd;
    }

    @Override
    public boolean update(ExtraClassHallReservation extraClassHallReservation) throws SQLException, ClassNotFoundException {
        boolean isUpdate = CrudUtil.execute("UPDATE ExtraClassHallReservation SET hallNo=?, eClassCode=?, date=?, startTime=?, endTime=? WHERE hallReservationNo=?",
                extraClassHallReservation.getHallNo(),
                extraClassHallReservation.geteClassCode(),
                extraClassHallReservation.getDate(),
                extraClassHallReservation.getStartTime(),
                extraClassHallReservation.getEndTime(),
                extraClassHallReservation.getHallReservationNo()
        );
        return isUpdate;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        boolean isDelete = CrudUtil.execute("DELETE FROM ExtraClassHallReservation WHERE hallReservationNo=?",
                args[0]
        );
        return isDelete;
    }

    @Override
    public ExtraClassHallReservation searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public List getDataToViewTable() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM ExtraClassHallReservation ORDER BY date DESC");
        List<ExtraClassHallReservation> list = FXCollections.observableArrayList();
        while (result.next()){
            ExtraClassHallReservation ob = new ExtraClassHallReservation(
                    result.getString("hallReservationNo"),
                    result.getString("hallNo"),
                    result.getString("eClassCode"),
                    result.getString("date"),
                    result.getString("startTime"),
                    result.getString("endTime")

            );
            list.add(ob);
        }
        return list;

    }

    @Override
    public String getHallReservationNo(String eClassCode) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT hallReservationNo FROM ExtraClassHallReservation WHERE eClassCode =? ",eClassCode);
        if(result.next()){
            return result.getString("hallReservationNo");
        }
        return null;
    }

    @Override
    public String getLastHallReservationNo() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT hallReservationNo FROM ExtraClassHallReservation ORDER BY hallReservationNo DESC LIMIT 1");
        if(result.next()){
            return result.getString("hallReservationNo");
        }
        return null;
    }
}
