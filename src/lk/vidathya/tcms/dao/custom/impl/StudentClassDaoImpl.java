package lk.vidathya.tcms.dao.custom.impl;

import lk.vidathya.tcms.dao.custom.StudentClassDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.StudentClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentClassDaoImpl implements StudentClassDAO {
    private final Connection connection;

    public StudentClassDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(StudentClass studentClass) throws SQLException, ClassNotFoundException {
        boolean isAdd = CrudUtil.execute("INSERT INTO StudentClass VALUES (?,?,?,?)",
                studentClass.getStudentId(),
                studentClass.getClassCode(),
                studentClass.getGuardianNic(),
                studentClass.getDate()
        );
        return isAdd;
    }

    @Override
    public boolean update(StudentClass entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StudentClass searchById(Object... args) throws SQLException, ClassNotFoundException {
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
        ResultSet result = CrudUtil.execute("SELECT studentId FROM StudentClass WHERE classCode=?",classCode);
        List<String> studentIdArray = new ArrayList<>();
        while(result.next()){
            studentIdArray.add(result.getString("studentId"));
        }
        return studentIdArray;
    }

    @Override
    public List<String> getGuardianNic(String classCodes) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT guardianNic FROM StudentClass WHERE classCode=?",classCodes);
        List<String> guardianNicArray = new ArrayList<>();
        while(result.next()){
            guardianNicArray.add(result.getString("guardianNic"));
        }
        return guardianNicArray;
    }

    @Override
    public List getClassesOfAStudent(String studentId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM StudentClass WHERE studentId=?", studentId);
        List<StudentClass> list = new ArrayList<>();
        while (result.next()){
            StudentClass studentClass = new StudentClass(
                    result.getString("studentId"),
                    result.getString("classCode"),
                    result.getString("guardianNic"),
                    result.getString("date")
            );
            list.add(studentClass);
        }
        return list;
    }

    @Override
    public boolean existStudent(String classCode, String studentId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT studentId FROM StudentClass WHERE classCode=?", classCode);
        ArrayList<String> studentListArray = new ArrayList<>();
        while (result.next()){
            studentListArray.add(result.getString("studentId"));
        }
        for(String id : studentListArray){
            if(studentId.equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getStudentsOfAClass(String classCode) throws SQLException, ClassNotFoundException {
        List<String> studentIdArray = new ArrayList<>();
        ResultSet result = CrudUtil.execute("SELECT studentId FROM StudentClass WHERE classCode=? ORDER BY studentId ASC", classCode);
        while (result.next()){
            studentIdArray.add(result.getString("studentId"));
        }
        return studentIdArray;
    }

    @Override
    public boolean isExistStudentInAClass(String studentId, String classCode) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT classCode FROM StudentClass WHERE studentId=? ",studentId);
        while(result.next()){
            if(classCode.equals(result.getString("classCode"))){
                return true;
            }
        }
        return false;
    }

    @Override
    public List getAllStudentsOfAClass(String classCode) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM StudentClass WHERE classCode=? ORDER BY studentId ASC",classCode);
        List<StudentClass> list = new ArrayList<>();
        while (result.next()){
            StudentClass studentClass = new StudentClass(
                    result.getString("studentId"),
                    result.getString("classCode"),
                    result.getString("guardianNic"),
                    result.getString("date")
            );
            list.add(studentClass);
        }
        return list;
    }
}
