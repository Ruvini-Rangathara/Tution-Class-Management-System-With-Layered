package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.IncomeAndExpenditureDAO;
import lk.vidathya.tcms.dao.custom.NotPaidTutorSalaryDAO;
import lk.vidathya.tcms.dao.custom.TutorSalaryDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.TutorSalaryDTO;
import lk.vidathya.tcms.entity.IncomeAndExpenditure;
import lk.vidathya.tcms.service.custom.TutorSalaryService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TutorSalaryServiceImpl implements TutorSalaryService {
    private final TutorSalaryDAO tutorSalaryDAO;
    private final IncomeAndExpenditureDAO incomeAndExpenditureDAO;
    private final NotPaidTutorSalaryDAO notPaidTutorSalaryDAO;
    private final Convertor convertor;
    private final Connection connection;

    public TutorSalaryServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        incomeAndExpenditureDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.INCOME_AND_EXPENDITURE_DAO_IMPL);
        notPaidTutorSalaryDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.NOT_PAID_TUTOR_SALARY_DAO_IMPL);
        tutorSalaryDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.TUTOR_SALARY_DAO_IMPL);
        convertor = new Convertor();
    }

    @Override
    public boolean add(TutorSalaryDTO tutorSalaryDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);
            boolean isAddTutorSalary = tutorSalaryDAO.add(convertor.toTutorSalary(tutorSalaryDTO));
            if (isAddTutorSalary) {
                boolean isUpdateNotPaidTutorSalary = notPaidTutorSalaryDAO.updateNotPaidTutorSalaryAfter(tutorSalaryDTO.getTutorId(), tutorSalaryDTO.getClassCode());
                if (isUpdateNotPaidTutorSalary) {
                    IncomeAndExpenditure budget = new IncomeAndExpenditure(
                            tutorSalaryDTO.getPayerId(),
                            "Expenditure",
                            "Tutor Salary : " + tutorSalaryDTO.getTutorId(),
                            tutorSalaryDTO.getSalary(),
                            tutorSalaryDTO.getYear(),
                            tutorSalaryDTO.getMonth(),
                            tutorSalaryDTO.getDate()
                    );
                    boolean isAddExpenditure = incomeAndExpenditureDAO.add(budget);
                    if (isAddExpenditure) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(TutorSalaryDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public TutorSalaryDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
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
        String lastPaymentCode = tutorSalaryDAO.getLastId();
        if (lastPaymentCode == null) {
            return "TP0001";
        } else {
            String[] split = lastPaymentCode.split("[T][P]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newPaymentCode = String.format("TP%04d", lastDigits);
            return newPaymentCode;
        }
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return tutorSalaryDAO.getLastId();
    }

    @Override
    public List getAllByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException {
        return tutorSalaryDAO.getAllByMonthYear(monthName, year);
    }
}
