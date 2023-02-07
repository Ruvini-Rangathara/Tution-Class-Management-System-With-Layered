package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ExtraClassHallReservationDAO;
import lk.vidathya.tcms.dao.custom.GuardianDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.GuardianDTO;
import lk.vidathya.tcms.dto.SuperDTO;
import lk.vidathya.tcms.entity.Guardian;
import lk.vidathya.tcms.service.custom.GuardianService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GuardianServiceImpl implements GuardianService {
    private final GuardianDAO guardianDAO;
    private final Convertor convertor;
    private final Connection connection;

    public GuardianServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        guardianDAO= DaoFactory.getInstance().getDao(connection, DaoTypes.GUARDIAN_DAO_IMPL );
        convertor=new Convertor();
    }

    @Override
    public boolean add(GuardianDTO guardianDTO) throws SQLException, ClassNotFoundException {
        return guardianDAO.add(convertor.toGuardian(guardianDTO));
    }

    @Override
    public boolean update(GuardianDTO guardianDTO) throws SQLException, ClassNotFoundException {
        return guardianDAO.update(convertor.toGuardian(guardianDTO));
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public GuardianDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return convertor.toGuardianDTO(guardianDAO.searchById(args));
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
    public List<String> getAllEmailAddress() throws SQLException, ClassNotFoundException {
        return guardianDAO.getAllEmailAddress() ;
    }
}
