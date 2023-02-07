package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ExtraClassHallReservationDAO;
import lk.vidathya.tcms.dao.custom.HallDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.HallDTO;
import lk.vidathya.tcms.dto.SuperDTO;
import lk.vidathya.tcms.entity.Hall;
import lk.vidathya.tcms.service.custom.HallService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HallServiceImpl implements HallService {
    private final HallDAO hallDAO;
    private final Convertor convertor;
    private final Connection connection;

    public HallServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        hallDAO= DaoFactory.getInstance().getDao(connection, DaoTypes.HALL_DAO_IMPL );
        convertor=new Convertor();
    }

    @Override
    public boolean add(HallDTO hallDTO) throws SQLException, ClassNotFoundException {
        return hallDAO.add(convertor.toHall(hallDTO));
    }

    @Override
    public boolean update(HallDTO hallDTO) throws SQLException, ClassNotFoundException {
        return hallDAO.update(convertor.toHall(hallDTO));
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return hallDAO.delete(args);
    }

    @Override
    public HallDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return convertor.toHallDTO(hallDAO.searchById(args));
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        List<Hall> list=hallDAO.getAll();
        List<HallDTO> list2 = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            list2.add(convertor.toHallDTO(list.get(i)));
        }
        return list2;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        String lastHallNo= hallDAO.getLastId();
        if(lastHallNo==null){
            return "H0001";
        }else{
            String[] split=lastHallNo.split("[H]");
            int lastDigits=Integer.parseInt(split[1]);
            lastDigits++;
            String newHall=String.format("H%04d", lastDigits);
            return newHall;
        }
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return hallDAO.getLastId();
    }

    @Override
    public List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException {
        return hallDAO.loadIdsToComboBox();
    }

    @Override
    public int getAllCount() throws SQLException, ClassNotFoundException {
        return hallDAO.getAllCount();
    }
}
