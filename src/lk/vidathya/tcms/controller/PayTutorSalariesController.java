package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.NotPaidTutorSalaryDTO;
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.dto.TutorSalaryDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.NotPaidTutorSalaryService;
import lk.vidathya.tcms.service.custom.TutorSalaryService;
import lk.vidathya.tcms.service.custom.TutorService;
import lk.vidathya.tcms.tableModel.NotPaidTutorSalaryTM;
import lk.vidathya.tcms.tableModel.PaidTutorSalaryTM;
import lk.vidathya.tcms.util.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PayTutorSalariesController {
    public ComboBox cmbTutorId;
    public TableView tblTutorSalaryNotPaid;
    public TableView tblTutorSalary;
    public TableColumn colTutorId;
    public TableColumn colNotPaidTutorId;
    public TableColumn colNotPaidGrade;
    public TableColumn colClassCode;
    public Button btnRefresh;
    public TextField txtSalary;
    public TextField txtNetSalary;
    public ComboBox cmbClassCode;
    public TableColumn colNotPaidClassCode;
    public ClassesServices classesServices;
    public TutorService tutorService;
    public TutorSalaryService tutorSalaryService;
    public NotPaidTutorSalaryService notPaidTutorSalaryService;
    @FXML
    private AnchorPane context;
    @FXML
    private Label lblDate;
    @FXML
    private TableColumn<?, ?> colPaymentId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colSalary;
    @FXML
    private TableColumn<?, ?> colPayerId;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private ComboBox<String> cmbMonth;
    @FXML
    private TextField txtYear;
    @FXML
    private TableColumn<?, ?> colNotPaidName;
    @FXML
    private TableColumn<?, ?> colNotPaidSubject;
    @FXML
    private TableColumn<?, ?> colNotPaidSalary;
    @FXML
    private Button btnPay;
    @FXML
    private Label lblPaymentCode;

    public void initialize() {
        notPaidTutorSalaryService = ServiceFactory.getInstance().getService(ServiceType.NOT_PAID_TUTOR_SALARY_SERVICE_IMPL);
        tutorSalaryService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SALARY_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);

        lblDate.setText(String.valueOf(LocalDate.now()));
        loadMonths();
        setPaymentCode();

        loadTutorId();
        loadClassCode();

        colNotPaidTutorId.setCellValueFactory(new PropertyValueFactory<>("notPaidTutorId"));
        colNotPaidName.setCellValueFactory(new PropertyValueFactory<>("notPaidName"));
        colNotPaidSubject.setCellValueFactory(new PropertyValueFactory<>("notPaidSubject"));
        colNotPaidGrade.setCellValueFactory(new PropertyValueFactory<>("notPaidGrade"));
        colNotPaidClassCode.setCellValueFactory(new PropertyValueFactory<>("notPaidClassCode"));
        colNotPaidSalary.setCellValueFactory(new PropertyValueFactory<>("notPaidSalary"));


        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentCode"));
        colTutorId.setCellValueFactory(new PropertyValueFactory<>("tutorId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colClassCode.setCellValueFactory(new PropertyValueFactory<>("classCode"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colPayerId.setCellValueFactory(new PropertyValueFactory<>("payerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        loadPaidData(MonthName.getMonthName(String.valueOf(LocalDate.now())), year);

        loadNotPaidData(MonthName.getMonthName(String.valueOf(LocalDate.now())), year);

    }

    private void loadMonths() {
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

    private void loadTutorId() {
        try {
            ObservableList<String> tutorId = (ObservableList<String>) tutorService.loadTutorIdToComboBox();
            cmbTutorId.setItems(tutorId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadClassCode() {
        try {
            ObservableList<String> classCode = (ObservableList<String>) classesServices.loadIdToComboBox();
            cmbClassCode.setItems(classCode);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNotPaidData(String monthName, int year) {

        ObservableList<NotPaidTutorSalaryTM> data = FXCollections.observableArrayList();
        try {
            List list = notPaidTutorSalaryService.getAllNotPaidDataByMonthYear(monthName, year);
            for (int i = 0; i < list.size(); i++) {
                NotPaidTutorSalaryDTO notPaidTutorSalaryDTO = (NotPaidTutorSalaryDTO) list.get(i);
                ClassesDTO classesDTO = classesServices.searchById(notPaidTutorSalaryDTO.getClassCode());
                TutorDTO tutorDTO = tutorService.searchById(notPaidTutorSalaryDTO.getTutorId());
                data.add(
                        new NotPaidTutorSalaryTM(
                                notPaidTutorSalaryDTO.getTutorId(),
                                tutorDTO.getName(),
                                classesDTO.getSubject(),
                                classesDTO.getGrade(),
                                notPaidTutorSalaryDTO.getClassCode(),
                                notPaidTutorSalaryDTO.getSalary()
                        ));

            }

            tblTutorSalaryNotPaid.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPaidData(String monthName, int year) {

        ObservableList<PaidTutorSalaryTM> data = FXCollections.observableArrayList();
        try {
            List list = tutorSalaryService.getAllByMonthYear(monthName, year);
            for (int i = 0; i < list.size(); i++) {
                TutorSalaryDTO tutorSalaryDTO = (TutorSalaryDTO) list.get(i);
                TutorDTO tutorDTO = tutorService.searchById(tutorSalaryDTO.getTutorId());
                data.add(
                        new PaidTutorSalaryTM(
                                tutorSalaryDTO.getPaymentCode(),
                                tutorSalaryDTO.getTutorId(),
                                tutorDTO.getName(),
                                tutorSalaryDTO.getClassCode(),
                                tutorSalaryDTO.getSalary(),
                                LoginCredentials.getCurrentUser(),
                                lblDate.getText()
                        ));
            }
            tblTutorSalary.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    private void setPaymentCode() {
        try {
            lblPaymentCode.setText(tutorSalaryService.getNewId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPayOnAction(ActionEvent event) {
        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        String month = MonthName.getMonthName(String.valueOf(LocalDate.now()));

        if (!(String.valueOf(cmbTutorId.getValue()).equals(null))) {
            Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Confirm?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (choose.get() == ButtonType.YES) {

                try {
                    TutorDTO tutorDTO = tutorService.searchById(String.valueOf(cmbTutorId.getValue()));

                    TutorSalaryDTO tutorSalaryDTO = new TutorSalaryDTO(
                            lblPaymentCode.getText(),
                            String.valueOf(cmbClassCode.getValue()),
                            String.valueOf(cmbTutorId.getValue()),
                            year,
                            month,
                            Double.parseDouble(txtNetSalary.getText()),
                            lblDate.getText(),
                            LoginCredentials.getCurrentUser()

                    );

                    boolean isAdd = tutorSalaryService.add(tutorSalaryDTO);
                    if (isAdd) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Pay Successfully!").show();
                        loadPaidData(month, year);
                        loadNotPaidData(month, year);

                    }


                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e + "").show();
                }


            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please Select the Staff member.").show();
        }

    }

    @FXML
    void cmbMonthOnAction(ActionEvent event) {

    }

    @FXML
    void txtYearOnAction(ActionEvent event) {

    }

    public void cmbTutorIdOnAction(ActionEvent actionEvent) {
        if (cmbClassCode.getValue() != null) {
            try {
                double salary = notPaidTutorSalaryService.getNotPaidTutorSalaryOfATutor(String.valueOf(cmbTutorId.getValue()), String.valueOf(cmbClassCode.getValue()));
                txtSalary.setText(String.valueOf(salary));

                double netSalary = salary - (((salary * InstituteData.getSalaryRate())) / 100);
                txtNetSalary.setText(String.valueOf(netSalary));

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void backImageOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.FINANCEMANAGE, context);
    }

    public void btnRefreshOnAction(ActionEvent actionEvent) {

        int year = Integer.parseInt(txtYear.getText());
        if (year == 0) {
            year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        }

        String month = String.valueOf(cmbMonth.getValue());
        if (month.equals(null)) {
            month = MonthName.getMonthName(String.valueOf(LocalDate.now()));
        }

        loadPaidData(month, year);
        loadNotPaidData(month, year);
    }

    public void cmbClassCodeOnAction(ActionEvent actionEvent) {
        if (cmbTutorId.getValue() != null) {
            try {
                double salary = notPaidTutorSalaryService.getNotPaidTutorSalaryOfATutor(String.valueOf(cmbTutorId.getValue()), String.valueOf(cmbClassCode.getValue()));
                txtSalary.setText(String.valueOf(salary));

                double netSalary = salary - (((salary * InstituteData.getSalaryRate())) / 100);
                txtNetSalary.setText(String.valueOf(netSalary));

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
