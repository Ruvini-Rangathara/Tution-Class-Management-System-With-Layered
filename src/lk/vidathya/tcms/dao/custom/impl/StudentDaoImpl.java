package lk.vidathya.tcms.dao.custom.impl;

import lk.vidathya.tcms.dao.custom.StudentDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDAO {
    private final Connection connection;

    public StudentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(Student student) throws SQLException, ClassNotFoundException {
        boolean isAdd=CrudUtil.execute("INSERT INTO Student VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",
                student.getStudentId(),
                student.getName(),
                student.getNic(),
                student.getGender(),
                student.getAddress(),
                student.getContactNo(),
                student.getEmail(),
                student.getDob(),
                student.getAge(),
                student.getGrade(),
                student.getDate(),
                student.getGuardianNic()
        );
        return isAdd;
    }

    @Override
    public boolean update(Student student) throws SQLException, ClassNotFoundException {
        boolean isUpdate = CrudUtil.execute("UPDATE Student SET name=?, nic=?, gender=?, address=?, contactNo=?, email=?, dob=?, age=?, grade=?, guardianNic=? WHERE studentId=?",
                student.getName(),
                student.getNic(),
                student.getGender(),
                student.getAddress(),
                student.getContactNo(),
                student.getEmail(),
                student.getDob(),
                student.getAge(),
                student.getGrade(),
                student.getGuardianNic(),
                student.getStudentId()
        );
        return isUpdate;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Student searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Student WHERE studentId=?",args[0]);
        if(result.next()){
            Student student = new Student(
                    result.getString("studentId"),
                    result.getString("name"),
                    result.getString("nic"),
                    result.getString("gender"),
                    result.getString("address"),
                    result.getString("contactNo"),
                    result.getString("email"),
                    result.getString("dob"),
                    result.getInt("age"),
                    result.getString("grade"),
                    result.getString("date"),
                    result.getString("guardianNic")
            );
            return student;
        }
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
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT studentId FROM Student ORDER BY studentId DESC LIMIT 1");
        if(result.next()){
            return result.getString("studentId");
        }
        return null;
    }

    @Override
    public int getStudentCount() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT COUNT(studentId) AS StudentCount FROM Student");
        if(result.next()){
            return result.getInt("StudentCount");
        }
        return 0;
    }

    @Override
    public String getStudentMailAddress(String studentId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT email FROM Student WHERE studentId=?", studentId);
        if(result.next()){
            return result.getString("email");
        }
        return null;
    }

    @Override
    public List<String> getAllEmailAddress() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT email FROM Student");
        List<String> allMailAddress = new ArrayList<>();
        while(result.next()){
            allMailAddress.add(result.getString("email"));
        }
        return allMailAddress;
    }

    @Override
    public String getGuardianNic(String studentId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT guardianNic FROM Student WHERE studentId=?",studentId);
        if(resultSet.next()){
            return resultSet.getString("guardianNic");
        }
        return null;
    }

    @Override
    public boolean isExistImage(String studentId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT studentId FROM StudentImage");
        while (result.next()){
            if(result.getString("studentId").equals(studentId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ResultSet getImage(String studentId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT image FROM StudentImage Where studentId=?",studentId);
        return result;
    }
}
