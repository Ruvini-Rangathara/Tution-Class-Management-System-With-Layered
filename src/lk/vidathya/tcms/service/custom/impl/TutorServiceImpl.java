package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.TutorDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.entity.Tutor;
import lk.vidathya.tcms.service.custom.TutorService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TutorServiceImpl implements TutorService {
    private final TutorDAO tutorDAO;
    private final Convertor convertor;
    private final Connection connection;

    public TutorServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        tutorDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.TUTOR_DAO_IMPL);
        convertor = new Convertor();
    }

    @Override
    public boolean add(TutorDTO tutorDTO) throws SQLException, ClassNotFoundException {
        return tutorDAO.add(convertor.toTutor(tutorDTO));
    }

    @Override
    public boolean update(TutorDTO tutorDTO) throws SQLException, ClassNotFoundException {
        return tutorDAO.update(convertor.toTutor(tutorDTO));
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public TutorDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return convertor.toTutorDTO(tutorDAO.searchById(args));
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        List<Tutor> list = tutorDAO.getAll();
        List<TutorDTO> list2 =new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            list2.add(convertor.toTutorDTO(list.get(i)));
        }
        return list2;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        String lastTutorId = tutorDAO.getLastId();
        if (lastTutorId == null) {
            return "T0001";
        } else {
            String[] split = lastTutorId.split("[T]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newTutorId = String.format("T%04d", lastDigits);
            return newTutorId;
        }
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return tutorDAO.getLastId();
    }

    @Override
    public List<String> loadTutorIdToComboBox() throws SQLException, ClassNotFoundException {
        return tutorDAO.loadTutorIdToComboBox();
    }

    @Override
    public List<String> loadSubjectToComboBox() throws SQLException, ClassNotFoundException {
        return tutorDAO.loadSubjectToComboBox();
    }

    @Override
    public int getTutorCount() throws SQLException, ClassNotFoundException {
        return tutorDAO.getTutorCount();
    }

    @Override
    public List<String> getTutorEmailAddress() throws SQLException, ClassNotFoundException {
        return tutorDAO.getTutorEmailAddress();
    }

    @Override
    public boolean isExistImage(String tutorId) throws SQLException, ClassNotFoundException {
        return tutorDAO.isExistImage(tutorId);
    }

    @Override
    public ResultSet getImage(String tutorId) throws SQLException, ClassNotFoundException {
        return tutorDAO.getImage(tutorId);
    }
}
