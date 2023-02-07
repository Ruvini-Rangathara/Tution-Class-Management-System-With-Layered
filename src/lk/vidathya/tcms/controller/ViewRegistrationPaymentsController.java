package lk.vidathya.tcms.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.RegistrationPaymentDTO;
import lk.vidathya.tcms.dto.StaffDTO;
import lk.vidathya.tcms.dto.StudentDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.RegistrationPaymentService;
import lk.vidathya.tcms.service.custom.StaffService;
import lk.vidathya.tcms.service.custom.StudentService;
import lk.vidathya.tcms.tableModel.RegistrationPaymentTM;
import lk.vidathya.tcms.util.MonthName;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ViewRegistrationPaymentsController {

    public AnchorPane context;
    public TableColumn colAmount;
    public TextField txtYear;
    public RegistrationPaymentService registrationPaymentService;
    public StudentService studentService;
    public StaffService staffService;
    @FXML
    private TableView<RegistrationPaymentTM> tblRegistrationPayments;
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
    private ComboBox<String> cmbMonth;

    public void initialize() {
        registrationPaymentService = ServiceFactory.getInstance().getService(ServiceType.REGISTRATION_PAYMENT_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);

        loadMonths();

        colPaymentCode.setCellValueFactory(new PropertyValueFactory<>("paymentCode"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colStaffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("fee"));

        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        loadPaymentData(MonthName.getMonthName(String.valueOf(LocalDate.now())), year);
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

    private void loadPaymentData(String monthName, int year) {
        ObservableList<RegistrationPaymentTM> data = FXCollections.observableArrayList();
        try {
            List list = registrationPaymentService.getAllDataByMonthYear(monthName, year);
            for (int i = 0; i < list.size(); i++) {
                RegistrationPaymentDTO registrationPaymentDTO = (RegistrationPaymentDTO) list.get(i);
                StudentDTO studentDTO = studentService.searchById(registrationPaymentDTO.getStudentId());
                StaffDTO staffDTO = staffService.searchById(registrationPaymentDTO.getStaffId());
                data.add(
                        new RegistrationPaymentTM(
                                registrationPaymentDTO.getCode(),
                                registrationPaymentDTO.getStudentId(),
                                studentDTO.getName(),
                                registrationPaymentDTO.getDate(),
                                registrationPaymentDTO.getStaffId(),
                                staffDTO.getName(),
                                registrationPaymentDTO.getFee()
                        ));

            }
            tblRegistrationPayments.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEWINFOCONTEXT, context);
    }


    @FXML
    void cmbMonthOnAction(ActionEvent event) {
        if (txtYear.getText() == null) {
            loadPaymentData(String.valueOf(cmbMonth.getValue()), Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]));
        } else {
            loadPaymentData(String.valueOf(cmbMonth.getValue()), Integer.parseInt(txtYear.getText()));
        }
    }

    public void txtYearOnAction(ActionEvent actionEvent) {
        if (cmbMonth.getValue() == null) {
            loadPaymentData(MonthName.getMonthName(String.valueOf(LocalDate.now())), Integer.parseInt(txtYear.getText()));
        } else {
            loadPaymentData(String.valueOf(cmbMonth.getValue()), Integer.parseInt(txtYear.getText()));
        }
    }
}
