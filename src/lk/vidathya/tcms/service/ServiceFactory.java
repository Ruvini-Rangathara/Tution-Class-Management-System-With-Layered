package lk.vidathya.tcms.service;

import lk.vidathya.tcms.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

   public static ServiceFactory getInstance() {
        return serviceFactory == null ? serviceFactory = new ServiceFactory() : serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceType type) {
        switch (type) {
            case ATTENDANCE_SERVICE_IMPL:
                return (T) new AttendanceServiceImpl();
            case CLASS_SERVICE_IMPL:
                return (T) new ClassesServiceImpl();
            case EXTRA_CLASS_SERVICE_IMPL:
                return (T) new ExtraClassServiceImpl();
            case EXTRA_CLASS_HALL_RESERVATION_SERVICE_IMPL:
                return (T) new ExtraClassHallReservationServiceImpl();
            case GUARDIAN_SERVICE_IMPL:
                return (T) new GuardianServiceImpl();
            case HALL_SERVICE_IMPL:
                return (T) new HallServiceImpl();
            case HALL_RESERVATION_SERVICE_IMPL:
                return (T) new HallReservationServiceImpl();
            case INCOME_AND_EXPENDITURE_SERVICE_IMPL:
                return (T) new IncomeAndExpenditureServiceImpl();
            case NOT_PAID_STAFF_SALARY_SERVICE_IMPL:
                return (T) new NotPaidStaffSalaryServiceImpl();
            case NOT_PAID_TUTOR_SALARY_SERVICE_IMPL:
                return (T) new NotPaidTutorSalaryServiceImpl();
            case PAYMENT_SERVICE_IMPL:
                return (T) new PaymentServiceImpl();
            case REFUND_SERVICE_IMPL:
                return (T) new RefundServiceImpl();
            case REGISTRATION_PAYMENT_SERVICE_IMPL:
                return (T) new RegistrationPaymentServiceImpl();
            case STAFF_SERVICE_IMPL:
                return (T) new StaffServiceImpl();
            case STAFF_SALARY_SERVICE_IMPL:
                return (T) new StaffSalaryServiceImpl();
            case STUDENTS_CLASS_SERVICE_IMPL:
                return (T) new StudentClassServiceImpl();
            case STUDENTS_SERVICE_IMPL:
                return (T) new StudentServiceImpl();
            case TUTOR_SERVICE_IMPL:
                return (T) new TutorServiceImpl();
            case TUTOR_SALARY_SERVICE_IMPL:
                return (T) new TutorSalaryServiceImpl();
            case USER_SERVICE_IMPL:
                return (T) new UserServiceImpl();
            default:
                return null;
        }
    }
}
