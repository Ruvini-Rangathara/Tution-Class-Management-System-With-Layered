package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.*;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.*;
import lk.vidathya.tcms.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

public class PaymentsFormController {


    public AnchorPane context;
    public Label lblPaymentCode;
    public Label lblUserId;
    public TextField txtYear;
    public Button btnBackToAttendance;
    public Button btnPrintBil;
    public StudentService studentService;
    public StaffService staffService;
    public ClassesServices classesServices;
    public PaymentService paymentService;
    public StudentClassService studentClassService;
    public RegistrationPaymentService registrationPaymentService;
    @FXML
    private Label lblDate;
    @FXML
    private Button btnPay;
    @FXML
    private Button btnCancel;
    @FXML
    private ComboBox<String> cmbClassCode;
    @FXML
    private TextField txtGrade;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextField txtFee;
    @FXML
    private TextField txtStudentName;
    @FXML
    private TextField txtStudentId;
    @FXML
    private ComboBox<String> cmbMonth;
    @FXML
    private ComboBox<String> cmbDescription;

    public void initialize() {
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        paymentService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_SERVICE_IMPL);
        studentClassService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_CLASS_SERVICE_IMPL);
        registrationPaymentService = ServiceFactory.getInstance().getService(ServiceType.REGISTRATION_PAYMENT_SERVICE_IMPL);

        setPaymentCode();
        lblDate.setText(String.valueOf(LocalDate.now()));

        loadClassCode();
        loadPaymentDescription();
        loadMonths();

        lblUserId.setText(LoginCredentials.getCurrentUser());
        txtYear.setText(String.valueOf(LocalDate.now()).split("-")[0]);
    }

    public void loadPaymentDescription() {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("Class Fee");
        options.add("Registration Fee");

        cmbDescription.setItems(options);
    }

    private void loadClassCode() {
        try {
            ObservableList<String> classCode = (ObservableList<String>) classesServices.loadIdToComboBox();
            cmbClassCode.setItems(classCode);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPaymentCode() {
        try {
            lblPaymentCode.setText(paymentService.getNewId());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
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

    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }

    @FXML
    void btnPayOnAction(ActionEvent event) {

        String description = null;
        if (String.valueOf(cmbDescription.getValue()) == null) {
            description = String.valueOf(cmbDescription.getPromptText());
        } else {
            description = String.valueOf(cmbDescription.getValue());
        }

        String classCode = null;
        if (String.valueOf(cmbClassCode.getValue()) == null) {
            classCode = String.valueOf(cmbClassCode.getPromptText());
        } else {
            classCode = String.valueOf(cmbClassCode.getValue());
        }

        Optional<ButtonType> choose = new Alert(Alert.AlertType.WARNING, "Confirm?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (choose.get() == ButtonType.YES) {

            try {

                if (description.equals("Class Fee")) {

                    boolean isExistStudent = studentClassService.existStudent(classCode, txtStudentId.getText());
                    if (isExistStudent) {

                        PaymentDTO paymentDTO = new PaymentDTO(
                                lblPaymentCode.getText(),
                                description,
                                classCode,
                                Double.parseDouble(txtFee.getText()),
                                txtStudentId.getText(),
                                Integer.parseInt(txtYear.getText()),
                                String.valueOf(cmbMonth.getValue()),
                                lblDate.getText(),
                                lblUserId.getText()
                        );
//                        System.out.print(lblPaymentCode.getText()+"     ");
//                        System.out.print(description+"     ");
//                        System.out.print(classCode+"     ");
//                        System.out.print(Double.parseDouble(txtFee.getText())+"     ");
//                        System.out.print(txtStudentId.getText()+"     ");
//                        System.out.print(Integer.parseInt(txtYear.getText())+"     ");
//                        System.out.print(String.valueOf(cmbMonth.getValue())+"     ");
//                        System.out.print(lblDate.getText()+"     ");
//                        System.out.print(lblUserId.getText()+"     ");

                        boolean isAdd = paymentService.add(paymentDTO);

                        if (isAdd) {
                            StudentDTO studentDTO = studentService.searchById(txtStudentId.getText());
                            String subject = "Class Fee Payment Accepted.";
                            String text = "\n\n\nStudent ID : " + txtStudentId.getText() + "\nName : " + txtStudentName.getText() + "\n\nPayment Code : " + lblPaymentCode.getText() + "\nClass Code  : " + cmbClassCode.getValue() + "\nGrade : " + txtGrade.getText() + "\nSubject : " + txtSubject.getText() + "\nMonth : " + cmbMonth.getValue() + "\n\n\n       Thank You!";

                            Mail.sendMail(studentDTO.getEmail(), subject, text);
                            new Alert(Alert.AlertType.CONFIRMATION, "Success!").show();

                            Navigation.navigate(Routes.PAYMENTS, context);
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Check this student is in class").show();
                    }

                }

                if (description.equals("Registration Fee")) {

                    RegistrationPaymentDTO registrationPaymentDTO = new RegistrationPaymentDTO(
                            0,
                            Double.parseDouble(txtFee.getText()),
                            txtStudentId.getText(),
                            Integer.parseInt(txtYear.getText()),
                            String.valueOf(cmbMonth.getValue()),
                            lblDate.getText(),
                            lblUserId.getText()
                    );
                    boolean isAdd = registrationPaymentService.add(registrationPaymentDTO);

                    if (isAdd) {
                        Navigation.navigate(Routes.PAYMENTS, context);
                        new Alert(Alert.AlertType.CONFIRMATION, "Success!").show();
                    }
                }

            } catch (IOException | SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e + "").show();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }


    }


    @FXML
    void cmbClassCodeOnAction(ActionEvent event) {
        try {
            ClassesDTO classesDTO = classesServices.searchById(cmbClassCode.getValue());
            txtGrade.setText(classesDTO.getGrade());
            txtSubject.setText(classesDTO.getSubject());
            txtFee.setText(String.valueOf(classesDTO.getClassFee()));

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    @FXML
    void cmbDescriptionOnAction(ActionEvent event) {
        if (cmbDescription.getValue().equals("Registration Fee")) {
            cmbClassCode.setDisable(true);
            txtFee.setText(String.valueOf(InstituteData.getRegistrationFee()));
            txtGrade.setText(null);
            txtSubject.setText(null);
            cmbClassCode.setPromptText(null);
        }
    }

    @FXML
    void cmbMonthOnAction(ActionEvent event) {

    }

    @FXML
    void txtFeeOnAction(ActionEvent event) {

    }

    @FXML
    void txtGradeOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentIdOnAction(ActionEvent event) {
        try {
            StudentDTO studentDTO = studentService.searchById(txtStudentId.getText());
            txtStudentName.setText(studentDTO.getName());

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    @FXML
    void txtStudentNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtSubjectOnAction(ActionEvent event) {

    }

    public void txtYearOnAction(ActionEvent actionEvent) {
    }

    public void btnBackToAttendanceOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ATTENDANCE, context);
    }

    public void btnPrintBilOnAction(ActionEvent actionEvent) {
        try {
            StaffDTO staffDTO = staffService.searchById(LoginCredentials.getCurrentUser());
            StudentDTO studentDTO = studentService.searchById(txtStudentId.getText());

            HashMap<String, Object> hm = new HashMap<>();
            hm.put("address", InstituteData.getAddress());
            hm.put("contactNo", InstituteData.getContactNo());
            hm.put("email", InstituteData.getEmail());
            hm.put("paymentCode", lblPaymentCode.getText());
            hm.put("date", lblDate.getText());
            hm.put("studentId", txtStudentId.getText());
            hm.put("name", studentDTO.getName());
            hm.put("description", String.valueOf(cmbDescription.getValue()));
            hm.put("amount", txtFee.getText());
            hm.put("staffName", staffDTO.getName());

            getBill(hm);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getBill(HashMap<String, Object> hm) {
        try {
            //JasperDesign inputStream = JRXmlLoader.load(getClass().getResourceAsStream("lk/vidathya/tcms/report/ReceiptNew.jrxml"));
            //InputStream inputStream = this.getClass().getResourceAsStream("../report/ReceiptNew.jrxml");
            InputStream inputStream = this.getClass().getResourceAsStream("/ReceiptNew.jrxml");

            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, hm, new JREmptyDataSource());
            //JasperPrintManager.printReport(jasperPrint,true);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            System.out.println(e);

        }
    }
}
