package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.IncomeAndExpenditureDAO;
import lk.vidathya.tcms.dao.custom.NotPaidStaffSalaryDAO;
import lk.vidathya.tcms.dao.custom.StaffSalaryDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.StaffSalaryDTO;
import lk.vidathya.tcms.entity.IncomeAndExpenditure;
import lk.vidathya.tcms.entity.NotPaidStaffSalary;
import lk.vidathya.tcms.service.custom.StaffSalaryService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StaffSalaryServiceImpl implements StaffSalaryService {
    private final StaffSalaryDAO staffSalaryDAO;
    private final NotPaidStaffSalaryDAO notPaidStaffSalaryDAO;
    private final IncomeAndExpenditureDAO incomeAndExpenditureDAO;
    private final Convertor convertor;
    private final Connection connection;

    public StaffSalaryServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        staffSalaryDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.STAFF_SALARY_DAO_IMPL);
        notPaidStaffSalaryDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.NOT_PAID_STAFF_SALARY_DAO_IMPL);
        incomeAndExpenditureDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.INCOME_AND_EXPENDITURE_DAO_IMPL);
        convertor = new Convertor();
    }

    @Override
    public boolean add(StaffSalaryDTO staffSalaryDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);
            boolean isAddPayment = staffSalaryDAO.add(convertor.toStaffSalary(staffSalaryDTO));
            boolean isUpdate = false;
            if (isAddPayment) {
                int year = staffSalaryDTO.getYear();
                if (staffSalaryDTO.getMonth().equals("December")) {
                    year++;
                }
                String month = null;
                if (staffSalaryDTO.getMonth().equals("January")) {
                    month = "February";
                }
                if (staffSalaryDTO.getMonth().equals("February")) {
                    month = "March";
                }
                if (staffSalaryDTO.getMonth().equals("March")) {
                    month = "April";
                }
                if (staffSalaryDTO.getMonth().equals("April")) {
                    month = "May";
                }
                if (staffSalaryDTO.getMonth().equals("May")) {
                    month = "June";
                }
                if (staffSalaryDTO.getMonth().equals("June")) {
                    month = "July";
                }
                if (staffSalaryDTO.getMonth().equals("July")) {
                    month = "August";
                }
                if (staffSalaryDTO.getMonth().equals("August")) {
                    month = "September";
                }
                if (staffSalaryDTO.getMonth().equals("September")) {
                    month = "October";
                }
                if (staffSalaryDTO.getMonth().equals("October")) {
                    month = "November";
                }
                if (staffSalaryDTO.getMonth().equals("November")) {
                    month = "December";
                }
                if (staffSalaryDTO.getMonth().equals("December")) {
                    month = "January";
                }

                NotPaidStaffSalary notPaidStaffSalary = new NotPaidStaffSalary(
                        staffSalaryDTO.getStaffId(),
                        year,
                        month,
                        staffSalaryDTO.getSalary()
                );

                isUpdate = notPaidStaffSalaryDAO.update(notPaidStaffSalary);
            }
            if (isUpdate) {
                IncomeAndExpenditure budget = new IncomeAndExpenditure(
                        staffSalaryDTO.getPayerId(),
                        "Expenditure",
                        "Staff Salary : " + staffSalaryDTO.getStaffId(),
                        staffSalaryDTO.getSalary(),
                        staffSalaryDTO.getYear(),
                        staffSalaryDTO.getMonth(),
                        staffSalaryDTO.getDate()
                );
                boolean isAddExpenditure = incomeAndExpenditureDAO.add(budget);
                if (isAddExpenditure) {
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
    public boolean update(StaffSalaryDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StaffSalaryDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
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
    public String getNewId() throws SQLException, ClassNotFoundException {
        String lastPaymentCode = staffSalaryDAO.getLastPaymentCode();
        if (lastPaymentCode == null) {
            return "EP0001";
        } else {
            String[] split = lastPaymentCode.split("[E][P]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newPaymentCode = String.format("EP%04d", lastDigits);
            return newPaymentCode;
        }
    }

    @Override
    public String getLastPaymentCode() throws SQLException, ClassNotFoundException {
        return staffSalaryDAO.getLastPaymentCode();
    }

    @Override
    public List getSalaryData(int year, String monthName) throws SQLException, ClassNotFoundException {
        return staffSalaryDAO.getSalaryData(year, monthName);
    }

    @Override
    public List getSalaryData(int year) throws SQLException, ClassNotFoundException {
        return staffSalaryDAO.getSalaryData(year);
    }

    @Override
    public List getSalaryData(String month) throws SQLException, ClassNotFoundException {
        return staffSalaryDAO.getSalaryData(month);
    }

    @Override
    public List getAllDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException {
        return staffSalaryDAO.getAllDataByMonthYear(monthName, year);
    }


}
