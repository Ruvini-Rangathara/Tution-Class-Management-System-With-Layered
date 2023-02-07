package lk.vidathya.tcms.util;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.HallService;
import lk.vidathya.tcms.service.custom.StudentService;
import lk.vidathya.tcms.service.custom.TutorService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InstituteData implements Initializable {

    public static StudentService studentService  = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
    public static HallService hallService = ServiceFactory.getInstance().getService(ServiceType.HALL_SERVICE_IMPL);
    public static TutorService tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);

    private static double registrationFee = 1000.00;
    private static double salaryRate = 20.00;
    private static String email = "vidathyaInstitute@gmail.com";
    private static int studentCount = 0;
    private static int tutorCount = 0;
    private static int hallCount = 0;
    private static String contactNo = "0705811994";
    private static String whatsAppContactNo = "0786628489";
    private static String address = "No:48,Galle Road,Panadura";

    public InstituteData() {
    }

    public static double getRegistrationFee() {
        return registrationFee;
    }

    public static void setRegistrationFee(double registrationFee) {
        InstituteData.registrationFee = registrationFee;
    }

    public static double getSalaryRate() {
        return salaryRate;
    }

    public static void setSalaryRate(double salaryRate) {
        InstituteData.salaryRate = salaryRate;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        InstituteData.email = email;
    }

    public static int getStudentCount() {
        return studentCount;
    }

    public static void setStudentCount() {
        try {
            studentCount = studentService.getStudentCount();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    public static int getTutorCount() {
        return tutorCount;
    }

    public static void setTutorCount() {
        try {
            tutorCount = tutorService.getTutorCount();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    public static int getHallCount() {
        return hallCount;
    }

    public static void setHallCount() {
        try {
            hallCount = hallService.getAllCount();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    public static String getContactNo() {
        return contactNo;
    }

    public static void setContactNo(String contactNo) {
        InstituteData.contactNo = contactNo;
    }

    public static String getWhatsAppContactNo() {
        return whatsAppContactNo;
    }

    public static void setWhatsAppContactNo(String whatsAppContactNo) {
        InstituteData.whatsAppContactNo = whatsAppContactNo;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        InstituteData.address = address;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        hallService = ServiceFactory.getInstance().getService(ServiceType.HALL_SERVICE_IMPL);
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
    }

}
