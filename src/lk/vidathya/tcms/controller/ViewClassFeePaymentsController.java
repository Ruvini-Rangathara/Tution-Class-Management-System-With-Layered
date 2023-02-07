package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.*;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.*;
import lk.vidathya.tcms.tableModel.ClassFeeNotPaidTM;
import lk.vidathya.tcms.tableModel.ClassFeePaymentTM;
import lk.vidathya.tcms.util.Mail;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewClassFeePaymentsController implements Initializable {

    public AnchorPane context;
    public ComboBox cmbClassCode;
    public TextField txtGrade;
    public TextField txtSubject;
    public TextField txtYear;
    public Button btnSendMail;
    public Button btnBack;
    public TableColumn colNotPaidMail;
    public TableColumn colNotPaidEmail;
    public TableColumn colNotPaidAmount;
    public TableColumn colAmount;
    public TableView tblClassFeePaid;
    public ClassesServices classesServices;
    public PaymentService paymentService;
    public StudentService studentService;
    public StaffService staffService;
    public StudentClassService studentClassService;
    public TutorService tutorService;
    @FXML
    private TableColumn<?, ?> colPaymentCode;
    @FXML
    private TableColumn<?, ?> colStudentId;
    @FXML
    private TableColumn<?, ?> colStudentName;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colStaffId;
    @FXML
    private TableColumn<?, ?> colStaffName;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnRefresh;
    @FXML
    private ComboBox<String> cmbMonth;
    @FXML
    private TableView<ClassFeeNotPaidTM> tblNotPaid;
    @FXML
    private TableColumn<?, ?> colNotPaidStudentId;
    @FXML
    private TableColumn<?, ?> colNotPaidStudentName;
    @FXML
    private TableColumn<?, ?> colNotPaidContactNo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        studentClassService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_CLASS_SERVICE_IMPL);
        paymentService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        loadMonths();
        loadClassCode();

        colPaymentCode.setCellValueFactory(new PropertyValueFactory<>("paymentCode"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colStaffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        colNotPaidStudentId.setCellValueFactory(new PropertyValueFactory<>("notPaidStudentId"));
        colNotPaidStudentName.setCellValueFactory(new PropertyValueFactory<>("notPaidStudentName"));
        colNotPaidContactNo.setCellValueFactory(new PropertyValueFactory<>("notPaidContactNo"));
        colNotPaidEmail.setCellValueFactory(new PropertyValueFactory<>("notPaidEmail"));
        colNotPaidAmount.setCellValueFactory(new PropertyValueFactory<>("notPaidAmount"));
    }

    private void loadClassCode() {
        try {
            ObservableList<String> classCode = (ObservableList<String>) classesServices.loadIdToComboBox();
            cmbClassCode.setItems(classCode);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMonths() {
        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        cmbMonth.setItems(months);
    }

    private void loadPaidData(String classCode, String month, int year) {
        ObservableList<ClassFeePaymentTM> data = FXCollections.observableArrayList();
        try {
            List list = paymentService.getPaidData(classCode, year, month);
            for (int i = 0; i < list.size(); i++) {
                PaymentDTO paymentDTO = (PaymentDTO) list.get(i);
                StudentDTO studentDTO = studentService.searchById(paymentDTO.getStudentId());
                StaffDTO staffDTO = staffService.searchById(paymentDTO.getStaffId());
                data.add(new ClassFeePaymentTM(paymentDTO.getPaymentCode(), paymentDTO.getStudentId(), studentDTO.getName(), paymentDTO.getDate(), paymentDTO.getFee(), paymentDTO.getStaffId(), staffDTO.getName()));
            }
            tblClassFeePaid.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    private void loadNotPaidData(String classCode, String month, int year) {
        ObservableList<ClassFeeNotPaidTM> data = FXCollections.observableArrayList();
        try {
            List list = paymentService.getPaidData(classCode, year, month);

            ArrayList<String> paidArrayList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                PaymentDTO paymentDTO = (PaymentDTO) list.get(i);
                paidArrayList.add(paymentDTO.getStudentId());
            }

            List list2 = studentClassService.getAllStudentsOfAClass(classCode);

            ArrayList<String> allStudentArrayList = new ArrayList<>();
            for (int i = 0; i < list2.size(); i++) {
                StudentClassDTO studentClassDTO = (StudentClassDTO) list2.get(i);
                allStudentArrayList.add(studentClassDTO.getStudentId());
            }

            ArrayList<String> notPaidArrayList = new ArrayList<>();

            for (int i = 0; i < allStudentArrayList.size(); i++) {
                for (int j = 0; j < paidArrayList.size(); j++) {
                    if (!(allStudentArrayList.get(i).equals(paidArrayList.get(j)))) {
                        notPaidArrayList.add(allStudentArrayList.get(i));
                        break;
                    }
                }
            }

            for (String notPaidStudent : notPaidArrayList) {
                StudentDTO studentDTO = studentService.searchById(notPaidStudent);
                ClassesDTO classesDTO = classesServices.searchById(classCode);
                data.add(new ClassFeeNotPaidTM(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getContactNo(), studentDTO.getEmail(), classesDTO.getClassFee()));
            }
            tblNotPaid.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

    }


    @FXML
    void btnRefreshOnAction(ActionEvent event) {

        if ((cmbClassCode.getValue() != null) && (txtYear.getText() != null) && (cmbMonth.getValue() != null)) {
            loadPaidData(String.valueOf(cmbClassCode.getValue()), String.valueOf(cmbMonth.getValue()), Integer.parseInt(txtYear.getText()));
            loadNotPaidData(String.valueOf(cmbClassCode.getValue()), String.valueOf(cmbMonth.getValue()), Integer.parseInt(txtYear.getText()));

        }
    }

    @FXML
    void cmbMonthOnAction(ActionEvent event) {

    }

    public void cmbClassCodeOnAction(ActionEvent actionEvent) {
        try {
            ClassesDTO classesDTO = classesServices.searchById(cmbClassCode.getValue());
            txtGrade.setText(classesDTO.getGrade());
            txtSubject.setPromptText(classesDTO.getSubject());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    public void txtGradeOnAction(ActionEvent actionEvent) {
    }

    public void txtSubjectOnAction(ActionEvent actionEvent) {
    }

    public void txtYearOnAction(ActionEvent actionEvent) {

    }

    public void btnSendMailOnAction(ActionEvent actionEvent) {
        String classCode = String.valueOf(cmbClassCode.getValue());
        int year = Integer.parseInt(txtYear.getText());
        String month = String.valueOf(cmbMonth.getValue());

        try {
            List list = paymentService.getPaidData(classCode, year, month);
            ArrayList<String> paidArrayList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                PaymentDTO paymentDTO = (PaymentDTO) list.get(i);
                paidArrayList.add(paymentDTO.getStudentId());
            }

            List list2 = studentClassService.getAllStudentsOfAClass(classCode);
            ArrayList<String> allStudentArrayList = new ArrayList<>();
            for (int i = 0; i < list2.size(); i++) {
                StudentClassDTO studentClassDTO = (StudentClassDTO) list2.get(i);
                allStudentArrayList.add(studentClassDTO.getStudentId());
            }

            ArrayList<String> notPaidArrayList = new ArrayList<>();

            for (int i = 0; i < allStudentArrayList.size(); i++) {
                for (int j = 0; j < paidArrayList.size(); j++) {
                    if (!(allStudentArrayList.get(i).equals(paidArrayList.get(j)))) {
                        notPaidArrayList.add(allStudentArrayList.get(i));
                        break;
                    }
                }
            }

            String subject = "Notification of non-payment of class fees";
            for (String notPaidStudent : notPaidArrayList) {
                StudentDTO studentDTO = studentService.searchById(notPaidStudent);
                ClassesDTO classesDTO = classesServices.searchById(classCode);
                TutorDTO tutorDTO = tutorService.searchById(classesDTO.getTutorId());

                String text = "Grade    :  " + classesDTO.getGrade() + "\nSubject     :  " + classesDTO.getSubject() + "\nTutor    :  " + tutorDTO.getName() + "\n\nKindly pay the class fees.";
                if (studentDTO.getEmail() != null) {
                    Mail.sendMail(studentDTO.getEmail(), subject, text);
                }
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Send Mails SuccessFully!").show();

        } catch (SQLException | ClassNotFoundException | MessagingException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.VIEWINFOCONTEXT, context);
    }


}
