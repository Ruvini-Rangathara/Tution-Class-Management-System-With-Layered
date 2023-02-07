package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.IncomeAndExpenditureDAO;
import lk.vidathya.tcms.dao.custom.RegistrationPaymentDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.HallReservationDTO;
import lk.vidathya.tcms.dto.RegistrationPaymentDTO;
import lk.vidathya.tcms.entity.HallReservation;
import lk.vidathya.tcms.entity.IncomeAndExpenditure;
import lk.vidathya.tcms.entity.RegistrationPayment;
import lk.vidathya.tcms.service.custom.RegistrationPaymentService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationPaymentServiceImpl implements RegistrationPaymentService {
    private final RegistrationPaymentDAO registrationPaymentDAO;
    private final IncomeAndExpenditureDAO incomeAndExpenditureDAO;
    private final Convertor convertor;
    private final Connection connection;

    public RegistrationPaymentServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        incomeAndExpenditureDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.INCOME_AND_EXPENDITURE_DAO_IMPL);
        registrationPaymentDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.REGISTRATION_PAYMENT_DAO_IMPL);
        convertor = new Convertor();
    }


    @Override
    public boolean add(RegistrationPaymentDTO registrationPaymentDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);
            boolean isAdd = registrationPaymentDAO.add(convertor.toRegistrationPayment(registrationPaymentDTO));

            if (isAdd) {
                IncomeAndExpenditure budget = new IncomeAndExpenditure(
                        registrationPaymentDTO.getStaffId(),
                        "Income",
                        "Registration Payment",
                        registrationPaymentDTO.getFee(),
                        registrationPaymentDTO.getYear(),
                        registrationPaymentDTO.getMonth(),
                        registrationPaymentDTO.getDate()
                );
                boolean isAddIncome = incomeAndExpenditureDAO.add(budget);
                if (isAddIncome) {
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
    public boolean update(RegistrationPaymentDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public RegistrationPaymentDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return registrationPaymentDAO.getAll();
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public List getAllDataByMonthYear(String monthName, int year) throws SQLException, ClassNotFoundException {
        List<RegistrationPayment> list = registrationPaymentDAO.getAllDataByMonthYear(monthName, year);
        List<RegistrationPaymentDTO> list2 = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            list2.add(convertor.toRegistrationPaymentDTO(list.get(i)));
        }
        return list2;
    }
}
