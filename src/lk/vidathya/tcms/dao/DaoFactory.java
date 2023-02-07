package lk.vidathya.tcms.dao;

import lk.vidathya.tcms.dao.custom.impl.*;

import java.sql.Connection;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return daoFactory == null ? daoFactory = new DaoFactory() : daoFactory;
    }

    public <T extends SuperDAO> T getDao(Connection connection, DaoTypes type) {
        switch (type) {
            case ATTENDANCE_DAO_IMPL:
                return (T) new AttendanceDaoImpl(connection);
            case CLASS_DAO_IMPL:
                return (T) new ClassesDaoImpl(connection);
            case EXTRA_CLASS_DAO_IMPL:
                return (T) new ExtraClassDaoImpl(connection);
            case EXTRA_CLASS_HALL_RESERVATION_DAO_IMPL:
                return (T) new ExtraClassHallReservationDaoImpl(connection);
            case GUARDIAN_DAO_IMPL:
                return (T) new GuardianDaoImpl(connection);
            case HALL_DAO_IMPL:
                return (T) new HallDaoImpl(connection);
            case HALL_RESERVATION_DAO_IMPL:
                return (T) new HallReservationDaoImpl(connection);
            case INCOME_AND_EXPENDITURE_DAO_IMPL:
                return (T) new IncomeAndExpenditureDaoImpl(connection);
            case NOT_PAID_STAFF_SALARY_DAO_IMPL:
                return (T) new NotPaidStaffSalaryDaoImpl(connection);
            case NOT_PAID_TUTOR_SALARY_DAO_IMPL:
                return (T) new NotPaidTutorSalaryDaoImpl(connection);
            case PAYMENT_DAO_IMPL:
                return (T) new PaymentDaoImpl(connection);
            case REFUND_DAO_IMPL:
                return (T) new RefundDaoImpl(connection);
            case REGISTRATION_PAYMENT_DAO_IMPL:
                return (T) new RegistrationPaymentDaoImpl(connection);
            case STAFF_DAO_IMPL:
                return (T) new StaffDaoImpl(connection);
            case STAFF_SALARY_DAO_IMPL:
                return (T) new StaffSalaryDaoImpl(connection);
            case STUDENTS_CLASS_DAO_IMPL:
                return (T) new StudentClassDaoImpl(connection);
            case STUDENTS_DAO_IMPL:
                return (T) new StudentDaoImpl(connection);
            case TUTOR_DAO_IMPL:
                return (T) new TutorDaoImpl(connection);
            case TUTOR_SALARY_DAO_IMPL:
                return (T) new TutorSalaryDaoImpl(connection);
            case USER_DAO_IMPL:
                return (T) new UserDaoImpl(connection);
            default:
                return null;
        }
    }
}
