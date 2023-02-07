package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ExtraClassHallReservationDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.ExtraClassHallReservationDTO;
import lk.vidathya.tcms.dto.HallDTO;
import lk.vidathya.tcms.entity.ExtraClassHallReservation;
import lk.vidathya.tcms.entity.Hall;
import lk.vidathya.tcms.service.custom.ExtraClassHallReservationService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExtraClassHallReservationServiceImpl implements ExtraClassHallReservationService {
    private final ExtraClassHallReservationDAO extraClassHallReservationDAO;
    private final Convertor convertor;
    private final Connection connection;

    public ExtraClassHallReservationServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        extraClassHallReservationDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.EXTRA_CLASS_HALL_RESERVATION_DAO_IMPL);
        convertor = new Convertor();
    }


    @Override
    public boolean add(ExtraClassHallReservationDTO extraClassHallReservationDTO) throws SQLException, ClassNotFoundException {
        return extraClassHallReservationDAO.add(convertor.toExtraClassHallReservation(extraClassHallReservationDTO));
    }

    @Override
    public boolean update(ExtraClassHallReservationDTO extraClassHallReservationDTO) throws SQLException, ClassNotFoundException {
        return extraClassHallReservationDAO.update(convertor.toExtraClassHallReservation(extraClassHallReservationDTO));
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return extraClassHallReservationDAO.delete(args);
    }

    @Override
    public ExtraClassHallReservationDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public String getNewId() throws SQLException, ClassNotFoundException {
        String lastHallReservationNo = extraClassHallReservationDAO.getLastHallReservationNo();
        if (lastHallReservationNo == null) {
            return "HR0001";
        } else {
            String[] split = lastHallReservationNo.split("[H][R]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newHallReservationNo = String.format("HR%04d", lastDigits);
            return newHallReservationNo;
        }
    }

    @Override
    public List getDataToViewTable() throws SQLException, ClassNotFoundException {
        List<ExtraClassHallReservation> list=extraClassHallReservationDAO.getDataToViewTable();
        List<ExtraClassHallReservationDTO> list2 = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            list2.add(convertor.toExtraClassHallReservationDTO(list.get(i)));
        }
        return list2;
    }

    @Override
    public String getHallReservationNo(String eClassCode) throws SQLException, ClassNotFoundException {
        return extraClassHallReservationDAO.getHallReservationNo(eClassCode);
    }

    @Override
    public String getLastHallReservationNo() throws SQLException, ClassNotFoundException {
        return extraClassHallReservationDAO.getLastHallReservationNo();
    }
}