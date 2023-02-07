package lk.vidathya.tcms.dao.custom.impl;

import lk.vidathya.tcms.dao.custom.GuardianDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.Guardian;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuardianDaoImpl implements GuardianDAO {
    private final Connection connection;

    public GuardianDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean add(Guardian guardian) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT guardianNic FROM Guardian");
        if(!result.next()){
            boolean isAdd = CrudUtil.execute("INSERT INTO Guardian VALUES(?,?,?,?,?)",
                    guardian.getGuardianNic(),
                    guardian.getName(),
                    guardian.getContactNo(),
                    guardian.getEmail(),
                    guardian.getOccupation()
            );
            return isAdd;
        }
        return true;
    }

    @Override
    public boolean update(Guardian guardian) throws SQLException, ClassNotFoundException {
        boolean isUpdateGuardian = CrudUtil.execute("UPDATE Guardian SET name=?, contactNo=?, email=?, occupation=? WHERE  guardianNic=?",
                guardian.getName(),
                guardian.getContactNo(),
                guardian.getEmail(),
                guardian.getOccupation(),
                guardian.getGuardianNic()
        );
        return isUpdateGuardian;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Guardian searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Guardian WHERE guardianNic=?",args[0]);
        if(result.next()){
            Guardian guardian = new Guardian(
                    result.getString("guardianNic"),
                    result.getString("name"),
                    result.getString("contactNo"),
                    result.getString("email"),
                    result.getString("occupation")
            );
            return guardian;
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
    public List<String> getAllEmailAddress() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT email FROM Guardian");
        List<String> allMails = new ArrayList<>();
        while(result.next()){
            allMails.add(result.getString("email"));
        }
        return allMails;
    }
}
