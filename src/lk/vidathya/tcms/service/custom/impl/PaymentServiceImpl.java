package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ClassesDAO;
import lk.vidathya.tcms.dao.custom.IncomeAndExpenditureDAO;
import lk.vidathya.tcms.dao.custom.NotPaidTutorSalaryDAO;
import lk.vidathya.tcms.dao.custom.PaymentDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.PaymentDTO;
import lk.vidathya.tcms.entity.Classes;
import lk.vidathya.tcms.entity.IncomeAndExpenditure;
import lk.vidathya.tcms.entity.NotPaidTutorSalary;
import lk.vidathya.tcms.service.custom.PaymentService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentDAO paymentDAO;
    private final ClassesDAO classesDAO;
    private final NotPaidTutorSalaryDAO notPaidTutorSalaryDAO;
    private final IncomeAndExpenditureDAO incomeAndExpenditureDAO;
    private final Convertor convertor;
    private final Connection connection;

    public PaymentServiceImpl() {
        connection= DBConnection.getInstance().getConnection();
        paymentDAO= DaoFactory.getInstance().getDao(connection, DaoTypes.PAYMENT_DAO_IMPL );
        classesDAO= DaoFactory.getInstance().getDao(connection,DaoTypes.CLASS_DAO_IMPL);
        incomeAndExpenditureDAO= DaoFactory.getInstance().getDao(connection,DaoTypes.INCOME_AND_EXPENDITURE_DAO_IMPL);
        notPaidTutorSalaryDAO= DaoFactory.getInstance().getDao(connection,DaoTypes.NOT_PAID_TUTOR_SALARY_DAO_IMPL);
        convertor=new Convertor();
    }


    @Override
    public boolean add(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        try{
            connection.setAutoCommit(false);
            boolean isAddPayment = paymentDAO.add(convertor.toPayment(paymentDTO));

            if(isAddPayment){
                Classes classes = classesDAO.searchById(paymentDTO.getClassCode());
                IncomeAndExpenditure budget = new IncomeAndExpenditure(
                        paymentDTO.getStaffId(),
                        "Income",
                        paymentDTO.getDescription(),
                        paymentDTO.getFee(),
                        paymentDTO.getYear(),
                        paymentDTO.getMonth(),
                        paymentDTO.getDate()
                );
                boolean isAddIncome = incomeAndExpenditureDAO.add(budget);
                if(isAddIncome){
                    double existSalary = notPaidTutorSalaryDAO.getExistSalary(classes.getTutorId());
                    NotPaidTutorSalary notPaidTutorSalary = new NotPaidTutorSalary(
                            classes.getTutorId(),
                            classes.getClassCode(),
                            paymentDTO.getYear(),
                            paymentDTO.getMonth(),
                            paymentDTO.getFee()+existSalary
                    );

                    boolean isUpdate = notPaidTutorSalaryDAO.update(notPaidTutorSalary);
                    if(isUpdate){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(PaymentDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public PaymentDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return convertor.toPaymentDTO(paymentDAO.searchById(args));
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
        String lastPaymentCode= paymentDAO.getLastId();
        if(lastPaymentCode==null){
            return "P0001";
        }else{
            String[] split=lastPaymentCode.split("[P]");
            int lastDigits=Integer.parseInt(split[1]);
            lastDigits++;
            String newPaymentCode=String.format("P%04d", lastDigits);
            return newPaymentCode;
        }
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getLastId();
    }

    @Override
    public List getPaidData(String classCode, int year, String month) throws SQLException, ClassNotFoundException {
        return paymentDAO.getPaidData(classCode,year,month);
    }

    @Override
    public String getPaymentStatus(String studentId, String classCode, int year, String monthName) throws SQLException, ClassNotFoundException {
        return paymentDAO.getPaymentStatus(studentId, classCode, year, monthName);
    }
}
