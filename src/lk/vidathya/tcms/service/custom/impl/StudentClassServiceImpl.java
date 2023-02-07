package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.PaymentDAO;
import lk.vidathya.tcms.dao.custom.StudentClassDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.StudentClassDTO;
import lk.vidathya.tcms.dto.SuperDTO;
import lk.vidathya.tcms.entity.StudentClass;
import lk.vidathya.tcms.service.custom.StudentClassService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentClassServiceImpl implements StudentClassService {
    private final StudentClassDAO studentClassDAO;
    private final Convertor convertor;
    private final Connection connection;

    public StudentClassServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        studentClassDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.STUDENTS_CLASS_DAO_IMPL);
        convertor=new Convertor();
    }

    @Override
    public boolean add(StudentClassDTO studentClassDTO) throws SQLException, ClassNotFoundException {
        return studentClassDAO.add(convertor.toStudentClass(studentClassDTO));
    }

    @Override
    public boolean update(StudentClassDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StudentClassDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public List<String> getStudentIds(String classCode) throws SQLException, ClassNotFoundException {
        return studentClassDAO.getStudentIds(classCode);
    }

    @Override
    public List<String> getGuardianNic(String classCodes) throws SQLException, ClassNotFoundException {
        return studentClassDAO.getGuardianNic(classCodes);
    }

    @Override
    public List getClassesOfAStudent(String studentId) throws SQLException, ClassNotFoundException {
        return studentClassDAO.getClassesOfAStudent(studentId);
    }

    @Override
    public boolean existStudent(String classCode, String studentId) throws SQLException, ClassNotFoundException {
        return studentClassDAO.existStudent(classCode,studentId);
    }

    @Override
    public List<String> getStudentsOfAClass(String classCode) throws SQLException, ClassNotFoundException {
        return studentClassDAO.getStudentsOfAClass(classCode);
    }

    @Override
    public boolean isExistStudentInAClass(String studentId, String classCode) throws SQLException, ClassNotFoundException {
        return studentClassDAO.isExistStudentInAClass(studentId, classCode);
    }

    @Override
    public List getAllStudentsOfAClass(String classCode) throws SQLException, ClassNotFoundException {
        return studentClassDAO.getAllStudentsOfAClass(classCode);
    }
}
