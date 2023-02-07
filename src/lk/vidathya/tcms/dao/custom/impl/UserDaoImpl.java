package lk.vidathya.tcms.dao.custom.impl;

import lk.vidathya.tcms.dao.custom.UserDAO;
import lk.vidathya.tcms.dao.util.CrudUtil;
import lk.vidathya.tcms.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDAO {
        private final Connection connection;

        public UserDaoImpl(Connection connection) {
                this.connection = connection;
        }

        @Override
        public boolean add(User user) throws SQLException, ClassNotFoundException {
                boolean isAdd = CrudUtil.execute("INSERT INTO User VALUES(?,?,?,?)",
                        user.getStaffId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getPasswordHint()
                );
                return isAdd;
        }

        @Override
        public boolean update(User user) throws SQLException, ClassNotFoundException {
                boolean isUpdate = CrudUtil.execute("UPDATE User SET username=?, password=?, passwordHint=? WHERE staffId=?",
                        user.getUsername(),
                        user.getPassword(),
                        user.getPasswordHint(),
                        user.getStaffId()
                );
                return isUpdate;
        }

        @Override
        public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
                return false;
        }

        @Override
        public User searchById(Object... args) throws SQLException, ClassNotFoundException {
                ResultSet result = CrudUtil.execute("SELECT * FROM User WHERE staffId=?",args[0]);
                if(result.next()){
                        User user = new User(
                                result.getString("staffId"),
                                result.getString("username"),
                                result.getString("password"),
                                result.getString("passwordHint")

                        );
                        return user;
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
        public User searchByPassword(String password) throws SQLException, ClassNotFoundException {
                ResultSet result = CrudUtil.execute("SELECT * FROM User WHERE password=?",password);
                if(result.next()){
                        User user = new User(
                                result.getString("staffId"),
                                result.getString("username"),
                                result.getString("password"),
                                result.getString("passwordHint")

                        );
                        return user;
                }
                return null;
        }

        @Override
        public boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
                ResultSet result = CrudUtil.execute("SELECT username FROM User");
                ArrayList<String> usernameArray = new ArrayList<>();
                while (result.next()){
                        usernameArray.add(result.getString("username"));
                }
                for(String i : usernameArray){
                        if(i.equals(username)){
                                return true;
                        }
                }
                return false;
        }

        @Override
        public boolean checkPassword(String username, String uncheckedPassword) throws SQLException, ClassNotFoundException {
                ResultSet result = CrudUtil.execute("SELECT password FROM User WHERE username=?", username);
                if(result.next()){
                        if(uncheckedPassword.equals(result.getString("password"))){
                                return true;
                        }
                }
                return false;
        }

        @Override
        public String getPasswordHint(String username) throws SQLException, ClassNotFoundException {
                ResultSet result = CrudUtil.execute("SELECT passwordHint FROM User WHERE username=?",username );
                if(result.next()){
                        return result.getString("passwordHint");
                }
                return null;
        }
}
