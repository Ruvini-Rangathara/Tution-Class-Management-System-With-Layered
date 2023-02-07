package lk.vidathya.tcms.service.util;

import lk.vidathya.tcms.dto.*;
import lk.vidathya.tcms.entity.*;

public class Convertor {

    public AttendanceDTO toAttendanceDTO(Attendance attendance){
        return new AttendanceDTO(
                attendance.getStudentId(),
                attendance.getClassCode(),
                attendance.getPresentOrAbsent(),
                attendance.getDate(),
                attendance.getYear(),
                attendance.getMonth()
        );
    }
    public Attendance toAttendance(AttendanceDTO attendanceDTO){
        return new Attendance(
                attendanceDTO.getStudentId(),
                attendanceDTO.getClassCode(),
                attendanceDTO.getPresentOrAbsent(),
                attendanceDTO.getDate(),
                attendanceDTO.getYear(),
                attendanceDTO.getMonth()
        );
    }
    public Classes toClasses(ClassesDTO classesDTO){
        return new Classes(
                classesDTO.getClassCode(),
                classesDTO.getGrade(),
                classesDTO.getSubject(),
                classesDTO.getTutorId(),
                classesDTO.getDay(),
                classesDTO.getStartTime(),
                classesDTO.getEndTime(),
                classesDTO.getHallNo(),
                classesDTO.getClassFee(),
                classesDTO.getDate()
        );
    }
    public ClassesDTO toClassesDTO(Classes classes){
        return new ClassesDTO(
                classes.getClassCode(),
                classes.getGrade(),
                classes.getSubject(),
                classes.getTutorId(),
                classes.getDay(),
                classes.getStartTime(),
                classes.getEndTime(),
                classes.getHallNo(),
                classes.getClassFee(),
                classes.getDate()
        );
    }
    public ExtraClass toExtraClass(ExtraClassDTO extraClassDTO){
        return new ExtraClass(
                extraClassDTO.geteClassCode(),
                extraClassDTO.getClassCode(),
                extraClassDTO.getDate(),
                extraClassDTO.getStartTime(),
                extraClassDTO.getEndTime(),
                extraClassDTO.getHallNo()
        );
    }
    public ExtraClassDTO toExtraClassDTO(ExtraClass extraClass){
        return new ExtraClassDTO(
                extraClass.geteClassCode(),
                extraClass.getClassCode(),
                extraClass.getDate(),
                extraClass.getStartTime(),
                extraClass.getEndTime(),
                extraClass.getHallNo()
        );
    }
    public ExtraClassHallReservationDTO toExtraClassHallReservationDTO(ExtraClassHallReservation extraClassHallReservation){
        return new ExtraClassHallReservationDTO(
                extraClassHallReservation.getHallReservationNo(),
                extraClassHallReservation.getHallNo(),
                extraClassHallReservation.geteClassCode(),
                extraClassHallReservation.getDate(),
                extraClassHallReservation.getStartTime(),
                extraClassHallReservation.getEndTime()
        );
    }
    public ExtraClassHallReservation toExtraClassHallReservation(ExtraClassHallReservationDTO extraClassHallReservationDTO){
        return new ExtraClassHallReservation(
                extraClassHallReservationDTO.getHallReservationNo(),
                extraClassHallReservationDTO.getHallNo(),
                extraClassHallReservationDTO.geteClassCode(),
                extraClassHallReservationDTO.getDate(),
                extraClassHallReservationDTO.getStartTime(),
                extraClassHallReservationDTO.getEndTime()
        );
    }
    public GuardianDTO toGuardianDTO(Guardian guardian){
        return new GuardianDTO(
                guardian.getGuardianNic(),
                guardian.getName(),
                guardian.getContactNo(),
                guardian.getEmail(),
                guardian.getOccupation()
        );
    }
    public Guardian toGuardian(GuardianDTO guardianDTO){
        return new Guardian(
                guardianDTO.getGuardianNic(),
                guardianDTO.getName(),
                guardianDTO.getContactNo(),
                guardianDTO.getEmail(),
                guardianDTO.getOccupation()
        );
    }
    public HallDTO toHallDTO(Hall hall){
        return new HallDTO(
                hall.getHallNo(),
                hall.getCapacity(),
                hall.getAvailableFacilities()
        );
    }
    public Hall toHall(HallDTO hallDTO){
        return new Hall(
                hallDTO.getHallNo(),
                hallDTO.getCapacity(),
                hallDTO.getAvailableFacilities()
        );
    }
    public HallReservation toHallReservation(HallReservationDTO hallReservationDTO){
        return new HallReservation(
                hallReservationDTO.getHallReservationNo(),
                hallReservationDTO.getHallNo(),
                hallReservationDTO.getClassCode(),
                hallReservationDTO.getDay(),
                hallReservationDTO.getStartTime(),
                hallReservationDTO.getEndTime()
        );
    }
    public HallReservationDTO toHallReservationDTO(HallReservation hallReservation){
        return new HallReservationDTO(
                hallReservation.getHallReservationNo(),
                hallReservation.getHallNo(),
                hallReservation.getClassCode(),
                hallReservation.getDay(),
                hallReservation.getStartTime(),
                hallReservation.getEndTime()
        );
    }
    public IncomeAndExpenditureDTO toIncomeExpenditureDTO(IncomeAndExpenditure incomeAndExpenditure){
        return new IncomeAndExpenditureDTO(
                incomeAndExpenditure.getStaffId(),
                incomeAndExpenditure.getType(),
                incomeAndExpenditure.getDescription(),
                incomeAndExpenditure.getAmount(),
                incomeAndExpenditure.getYear(),
                incomeAndExpenditure.getMonth(),
                incomeAndExpenditure.getDate()
        );
    }
    public IncomeAndExpenditure toIncomeExpenditure(IncomeAndExpenditureDTO incomeAndExpenditureDTO){
        return new IncomeAndExpenditure(
                incomeAndExpenditureDTO.getStaffId(),
                incomeAndExpenditureDTO.getType(),
                incomeAndExpenditureDTO.getDescription(),
                incomeAndExpenditureDTO.getAmount(),
                incomeAndExpenditureDTO.getYear(),
                incomeAndExpenditureDTO.getMonth(),
                incomeAndExpenditureDTO.getDate()
        );
    }
    public NotPaidStaffSalary toNotPaidStaffSalary(NotPaidStaffSalaryDTO notPaidStaffSalaryDTO){
        return new NotPaidStaffSalary(
                notPaidStaffSalaryDTO.getStaffId(),
                notPaidStaffSalaryDTO.getYear(),
                notPaidStaffSalaryDTO.getMonth(),
                notPaidStaffSalaryDTO.getSalary()
        );
    }
    public NotPaidStaffSalaryDTO toNotPaidStaffSalaryDTO(NotPaidStaffSalary notPaidStaffSalary){
        return new NotPaidStaffSalaryDTO(
                notPaidStaffSalary.getStaffId(),
                notPaidStaffSalary.getYear(),
                notPaidStaffSalary.getMonth(),
                notPaidStaffSalary.getSalary()
        );
    }
    public NotPaidTutorSalary toNotPaidTutorSalary(NotPaidTutorSalaryDTO notPaidTutorSalaryDTO){
        return new NotPaidTutorSalary(
                notPaidTutorSalaryDTO.getTutorId(),
                notPaidTutorSalaryDTO.getClassCode(),
                notPaidTutorSalaryDTO.getYear(),
                notPaidTutorSalaryDTO.getMonth(),
                notPaidTutorSalaryDTO.getSalary()
        );
    }
    public NotPaidTutorSalaryDTO toNotPaidTutorSalaryDTO(NotPaidTutorSalary notPaidTutorSalary){
        return new NotPaidTutorSalaryDTO(
                notPaidTutorSalary.getTutorId(),
                notPaidTutorSalary.getClassCode(),
                notPaidTutorSalary.getYear(),
                notPaidTutorSalary.getMonth(),
                notPaidTutorSalary.getSalary()
        );
    }
    public Payment toPayment(PaymentDTO paymentDTO){
        return new Payment(
                paymentDTO.getPaymentCode(),
                paymentDTO.getDescription(),
                paymentDTO.getClassCode(),
                paymentDTO.getFee(),
                paymentDTO.getStudentId(),
                paymentDTO.getYear(),
                paymentDTO.getMonth(),
                paymentDTO.getDate(),
                paymentDTO.getStaffId()
        );
    }
    public PaymentDTO toPaymentDTO(Payment payment){
        return new PaymentDTO(
                payment.getPaymentCode(),
                payment.getDescription(),
                payment.getClassCode(),
                payment.getFee(),
                payment.getStudentId(),
                payment.getYear(),
                payment.getMonth(),
                payment.getDate(),
                payment.getStaffId()
        );
    }
    public Refund toRefund(RefundDTO refundDTO){
        return new Refund(
                refundDTO.getPaymentCode(),
                refundDTO.getStudentId(),
                refundDTO.getDescription(),
                refundDTO.getAmount(),
                refundDTO.getDate(),
                refundDTO.getStaffId(),
                refundDTO.getMonth()
        );
    }
    public RefundDTO toRefundDTO(Refund refund){
        return new RefundDTO(
                refund.getPaymentCode(),
                refund.getStudentId(),
                refund.getDescription(),
                refund.getAmount(),
                refund.getDate(),
                refund.getStaffId(),
                refund.getMonth()
        );
    }
    public RegistrationPayment toRegistrationPayment(RegistrationPaymentDTO registrationPaymentDTO){
        return new RegistrationPayment(
                registrationPaymentDTO.getCode(),
                registrationPaymentDTO.getFee(),
                registrationPaymentDTO.getStudentId(),
                registrationPaymentDTO.getYear(),
                registrationPaymentDTO.getMonth(),
                registrationPaymentDTO.getDate(),
                registrationPaymentDTO.getStaffId()
        );
    }
    public RegistrationPaymentDTO toRegistrationPaymentDTO(RegistrationPayment registrationPayment){
        return new RegistrationPaymentDTO(
                registrationPayment.getCode(),
                registrationPayment.getFee(),
                registrationPayment.getStudentId(),
                registrationPayment.getYear(),
                registrationPayment.getMonth(),
                registrationPayment.getDate(),
                registrationPayment.getStaffId()
        );
    }
    public Staff toStaff(StaffDTO staffDTO){
        return new Staff(
                staffDTO.getStaffId(),
                staffDTO.getName(),
                staffDTO.getJob(),
                staffDTO.getNic(),
                staffDTO.getGender(),
                staffDTO.getAddress(),
                staffDTO.getContactNo(),
                staffDTO.getEmail(),
                staffDTO.getDob(),
                staffDTO.getSalary(),
                staffDTO.getDate()
        );
    }
    public StaffDTO toStaffDTO(Staff staff){
        return new StaffDTO(
                staff.getStaffId(),
                staff.getName(),
                staff.getJob(),
                staff.getNic(),
                staff.getGender(),
                staff.getAddress(),
                staff.getContactNo(),
                staff.getEmail(),
                staff.getDob(),
                staff.getSalary(),
                staff.getDate()
        );
    }
    public StaffSalary toStaffSalary(StaffSalaryDTO staffSalaryDTO){
        return new StaffSalary(
                staffSalaryDTO.getPaymentCode(),
                staffSalaryDTO.getStaffId(),
                staffSalaryDTO.getYear(),
                staffSalaryDTO.getMonth(),
                staffSalaryDTO.getSalary(),
                staffSalaryDTO.getDate(),
                staffSalaryDTO.getPayerId()
        );
    }
    public StaffSalaryDTO toStaffSalaryDTO(StaffSalary staffSalary){
        return new StaffSalaryDTO(
                staffSalary.getPaymentCode(),
                staffSalary.getStaffId(),
                staffSalary.getYear(),
                staffSalary.getMonth(),
                staffSalary.getSalary(),
                staffSalary.getDate(),
                staffSalary.getPayerId()
        );
    }
    public Student toStudent(StudentDTO studentDTO){
        return new Student(
                studentDTO.getStudentId(),
                studentDTO.getName(),
                studentDTO.getNic(),
                studentDTO.getGender(),
                studentDTO.getAddress(),
                studentDTO.getContactNo(),
                studentDTO.getEmail(),
                studentDTO.getDob(),
                studentDTO.getAge(),
                studentDTO.getGrade(),
                studentDTO.getDate(),
                studentDTO.getGuardianNic()
        );
    }
    public StudentDTO toStudentDTO(Student student){
        return new StudentDTO(
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
    }
    public StudentClass toStudentClass(StudentClassDTO studentClassDTO){
        return new StudentClass(
                studentClassDTO.getStudentId(),
                studentClassDTO.getClassCode(),
                studentClassDTO.getGuardianNic(),
                studentClassDTO.getDate()
        );
    }
    public StudentClassDTO toStudentClassDTO(StudentClass studentClass){
        return new StudentClassDTO(
                studentClass.getStudentId(),
                studentClass.getClassCode(),
                studentClass.getGuardianNic(),
                studentClass.getDate()
        );
    }
    public Tutor toTutor(TutorDTO tutorDTO){
        return new Tutor(
                tutorDTO.getTutorId(),
                tutorDTO.getName(),
                tutorDTO.getNic(),
                tutorDTO.getGender(),
                tutorDTO.getAddress(),
                tutorDTO.getContactNo(),
                tutorDTO.getEmail(),
                tutorDTO.getDob(),
                tutorDTO.getSubject(),
                tutorDTO.getDate()
        );
    }
    public TutorDTO toTutorDTO(Tutor tutor){
        return new TutorDTO(
                tutor.getTutorId(),
                tutor.getName(),
                tutor.getNic(),
                tutor.getGender(),
                tutor.getAddress(),
                tutor.getContactNo(),
                tutor.getEmail(),
                tutor.getDob(),
                tutor.getSubject(),
                tutor.getDate()
        );
    }
    public TutorSalary toTutorSalary(TutorSalaryDTO tutorSalaryDTO){
        return new TutorSalary(
                tutorSalaryDTO.getPaymentCode(),
                tutorSalaryDTO.getClassCode(),
                tutorSalaryDTO.getTutorId(),
                tutorSalaryDTO.getYear(),
                tutorSalaryDTO.getMonth(),
                tutorSalaryDTO.getSalary(),
                tutorSalaryDTO.getDate(),
                tutorSalaryDTO.getPayerId()
        );
    }
    public TutorSalaryDTO toTutorSalaryDTO(TutorSalary tutorSalary){
        return new TutorSalaryDTO(
                tutorSalary.getPaymentCode(),
                tutorSalary.getClassCode(),
                tutorSalary.getTutorId(),
                tutorSalary.getYear(),
                tutorSalary.getMonth(),
                tutorSalary.getSalary(),
                tutorSalary.getDate(),
                tutorSalary.getPayerId()
        );
    }
    public User toUser(UserDTO userDTO){
        return new User(
                userDTO.getStaffId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getPasswordHint()
        );
    }
    public UserDTO toUserDTO(User user){
        return new UserDTO(
                user.getStaffId(),
                user.getUsername(),
                user.getPassword(),
                user.getPasswordHint()
        );
    }


}
