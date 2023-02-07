package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ExtraClassHallReservationDAO;
import lk.vidathya.tcms.dao.custom.HallReservationDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.HallReservationDTO;
import lk.vidathya.tcms.dto.SuperDTO;
import lk.vidathya.tcms.entity.Classes;
import lk.vidathya.tcms.entity.HallReservation;
import lk.vidathya.tcms.service.custom.HallReservationService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HallReservationServiceImpl implements HallReservationService {
    private final HallReservationDAO hallReservationDAO;
    private final Convertor convertor;
    private final Connection connection;

    public HallReservationServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        hallReservationDAO= DaoFactory.getInstance().getDao(connection, DaoTypes.HALL_RESERVATION_DAO_IMPL );
        convertor=new Convertor();
    }


    @Override
    public boolean add(HallReservationDTO hallReservationDTO) throws SQLException, ClassNotFoundException {
        return hallReservationDAO.add(convertor.toHallReservation(hallReservationDTO));
    }

    @Override
    public boolean update(HallReservationDTO hallReservationDTO) throws SQLException, ClassNotFoundException {
        return hallReservationDAO.update(convertor.toHallReservation(hallReservationDTO));
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return hallReservationDAO.delete(args);
    }

    @Override
    public HallReservationDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
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
        return hallReservationDAO.getHallReservationNo(classCode);
    }

    @Override
    public List getDataToViewTable() throws SQLException, ClassNotFoundException {
        List<HallReservation> list = hallReservationDAO.getDataToViewTable();
        List<HallReservationDTO> list2 = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            list2.add(convertor.toHallReservationDTO(list.get(i)));
        }
        return list2;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return hallReservationDAO.getLastId();
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        String lastHallReservationNo= getLastId();
        if(lastHallReservationNo==null){
            return "HR0001";
        }else{
            String[] split=lastHallReservationNo.split("[H][R]");
            int lastDigits=Integer.parseInt(split[1]);
            lastDigits++;
            String newHallReservationNo=String.format("HR%04d", lastDigits);
            return newHallReservationNo;
        }
    }
}
