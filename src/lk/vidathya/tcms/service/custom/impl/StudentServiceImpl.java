package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.GuardianDAO;
import lk.vidathya.tcms.dao.custom.StudentDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.GuardianDTO;
import lk.vidathya.tcms.dto.StudentDTO;
import lk.vidathya.tcms.service.custom.StudentService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentDAO studentDAO;
    private final GuardianDAO guardianDAO;
    private final Convertor convertor;
    private final Connection connection;

    public StudentServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        guardianDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.GUARDIAN_DAO_IMPL);
        studentDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.STUDENTS_DAO_IMPL);
        convertor = new Convertor();
    }

    @Override
    public boolean add(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return studentDAO.add(convertor.toStudent(studentDTO));
    }

    @Override
    public boolean add(StudentDTO studentDTO, GuardianDTO guardianDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);
            boolean isAddStudent = studentDAO.add(convertor.toStudent(studentDTO));
            if (isAddStudent) {
                boolean isAddGuardian = guardianDAO.add(convertor.toGuardian(guardianDTO));
                if (isAddGuardian) {
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
    public boolean update(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return studentDAO.update(convertor.toStudent(studentDTO));
    }

    @Override
    public boolean update(StudentDTO studentDTO, GuardianDTO guardianDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);
            boolean isUpdateStudent = studentDAO.update(convertor.toStudent(studentDTO));
            if (isUpdateStudent) {
                boolean isUpdateGuardian = guardianDAO.update(convertor.toGuardian(guardianDTO));
                if (isUpdateGuardian) {
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
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StudentDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return convertor.toStudentDTO(studentDAO.searchById(args));
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
        String lastStudentId = studentDAO.getLastId();
        if (lastStudentId == null) {
            return "S0001";
        } else {
            String[] split = lastStudentId.split("[S]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newStudentId = String.format("S%04d", lastDigits);
            return newStudentId;
        }
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return studentDAO.getLastId();
    }

    @Override
    public int getStudentCount() throws SQLException, ClassNotFoundException {
        return studentDAO.getStudentCount();
    }

    @Override
    public String getStudentMailAddress(String studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.getStudentMailAddress(studentId);
    }

    @Override
    public List<String> getAllEmailAddress() throws SQLException, ClassNotFoundException {
        return studentDAO.getAllEmailAddress();
    }

    @Override
    public String getGuardianNic(String studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.getGuardianNic(studentId);
    }

    @Override
    public boolean isExistImage(String studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.isExistImage(studentId);
    }

    @Override
    public ResultSet getImage(String studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.getImage(studentId);
    }
}
