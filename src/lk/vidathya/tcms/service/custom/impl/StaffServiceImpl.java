package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.NotPaidStaffSalaryDAO;
import lk.vidathya.tcms.dao.custom.StaffDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.StaffDTO;
import lk.vidathya.tcms.entity.NotPaidStaffSalary;
import lk.vidathya.tcms.entity.Staff;
import lk.vidathya.tcms.service.custom.StaffService;
import lk.vidathya.tcms.service.util.Convertor;
import lk.vidathya.tcms.util.MonthName;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffServiceImpl implements StaffService {
    private final StaffDAO staffDAO;
    private final NotPaidStaffSalaryDAO notPaidStaffSalaryDAO;
    private final Convertor convertor;
    private final Connection connection;

    public StaffServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        staffDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.STAFF_DAO_IMPL);
        notPaidStaffSalaryDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.NOT_PAID_STAFF_SALARY_DAO_IMPL);
        convertor = new Convertor();
    }


    @Override
    public boolean add(StaffDTO staffDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);
            boolean isAdd = staffDAO.add(convertor.toStaff(staffDTO));

            if (isAdd) {
                String date = String.valueOf(LocalDate.now());
                String month = MonthName.getMonthName(date);
                int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
                NotPaidStaffSalary notPaidStaffSalary = new NotPaidStaffSalary(
                        staffDTO.getStaffId(),
                        year,
                        month,
                        staffDTO.getSalary()
                );
                boolean isAddNotPaidStaffSalary = notPaidStaffSalaryDAO.add(notPaidStaffSalary);
                if (isAddNotPaidStaffSalary) {
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
    public boolean update(StaffDTO staffDTO) throws SQLException, ClassNotFoundException {
        return staffDAO.update(convertor.toStaff(staffDTO));
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StaffDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return convertor.toStaffDTO(staffDAO.searchById(args));
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        List<Staff> list =  staffDAO.getAll();
        List<StaffDTO> list2 = new ArrayList<>();
        for(int i=0; i< list.size(); i++){
            list2.add(
                    convertor.toStaffDTO(list.get(i))
            );
        }
        return list2;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        String lastStaffId = staffDAO.getLastId();
        if (lastStaffId == null) {
            return "E0001";
        } else {
            String[] split = lastStaffId.split("[E]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newStaffId = String.format("E%04d", lastDigits);
            return newStaffId;
        }
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return staffDAO.getLastId();
    }

    @Override
    public List<String> loadIdsToComboBox() throws SQLException, ClassNotFoundException {
        return staffDAO.loadIdsToComboBox();
    }

    @Override
    public List<String> getStaffEmailAddress() throws SQLException, ClassNotFoundException {
        return staffDAO.getStaffEmailAddress();
    }

    @Override
    public ResultSet getImage(String staffId) throws SQLException, ClassNotFoundException {
        return staffDAO.getImage(staffId);
    }

    @Override
    public boolean isExistImage(String staffId) throws SQLException, ClassNotFoundException {
        return staffDAO.isExistImage(staffId);
    }
}
