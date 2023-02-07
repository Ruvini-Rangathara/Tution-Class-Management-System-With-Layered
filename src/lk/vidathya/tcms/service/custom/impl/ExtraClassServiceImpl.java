package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ExtraClassDAO;
import lk.vidathya.tcms.dao.custom.ExtraClassHallReservationDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.ExtraClassDTO;
import lk.vidathya.tcms.dto.ExtraClassHallReservationDTO;
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.entity.ExtraClass;
import lk.vidathya.tcms.entity.Tutor;
import lk.vidathya.tcms.service.custom.ExtraClassService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExtraClassServiceImpl implements ExtraClassService {
    private final ExtraClassDAO extraClassDAO;
    private final ExtraClassHallReservationDAO extraClassHallReservationDAO;
    private final Convertor convertor;
    private final Connection connection;

    public ExtraClassServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        extraClassDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.EXTRA_CLASS_DAO_IMPL);
        extraClassHallReservationDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.EXTRA_CLASS_HALL_RESERVATION_DAO_IMPL);
        convertor = new Convertor();
    }


    @Override
    public boolean add(ExtraClassDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean add(ExtraClassDTO extraClassDTO, ExtraClassHallReservationDTO extraClassHallReservationDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);
            boolean isAdd = extraClassDAO.add(convertor.toExtraClass(extraClassDTO));
            if (isAdd) {
                boolean isAddHallReservation = extraClassHallReservationDAO.add(convertor.toExtraClassHallReservation(extraClassHallReservationDTO));
                if (isAddHallReservation) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(ExtraClassDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(ExtraClassDTO extraClassDTO, ExtraClassHallReservationDTO extraClassHallReservationDTO) throws SQLException {
        try {
            connection.setAutoCommit(false);
            boolean isUpdate = extraClassDAO.update(convertor.toExtraClass(extraClassDTO));
            if (isUpdate) {
                boolean isUpdateHallReservation = extraClassHallReservationDAO.update(convertor.toExtraClassHallReservation(extraClassHallReservationDTO));
                if (isUpdateHallReservation) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ExtraClassDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return convertor.toExtraClassDTO(extraClassDAO.searchById(args));
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        List<ExtraClass> list = extraClassDAO.getAll();
        List<ExtraClassDTO> list2 =new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            list2.add(convertor.toExtraClassDTO(list.get(i)));
        }
        return list2;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        String lastExtraClassCode = extraClassDAO.getLastId();
        if (lastExtraClassCode == null) {
            return "EC0001";
        } else {
            String[] split = lastExtraClassCode.split("[E][C]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newExtraClassCode = String.format("EC%04d", lastDigits);
            return newExtraClassCode;
        }
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return extraClassDAO.getLastId();
    }

    @Override
    public List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException {
        return extraClassDAO.loadIdsToComboBox();
    }

    @Override
    public List<ExtraClass> getDataToScheduleTable(String date) throws SQLException, ClassNotFoundException {
        return extraClassDAO.getDataToScheduleTable(date);
    }
}
