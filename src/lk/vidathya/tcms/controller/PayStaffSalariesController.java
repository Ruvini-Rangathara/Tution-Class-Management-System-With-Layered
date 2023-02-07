package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.NotPaidStaffSalaryDTO;
import lk.vidathya.tcms.dto.StaffDTO;
import lk.vidathya.tcms.dto.StaffSalaryDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.NotPaidStaffSalaryService;
import lk.vidathya.tcms.service.custom.StaffSalaryService;
import lk.vidathya.tcms.service.custom.StaffService;
import lk.vidathya.tcms.tableModel.NotPaidStaffSalaryTM;
import lk.vidathya.tcms.tableModel.PaidStaffSalaryTM;
import lk.vidathya.tcms.util.LoginCredentials;
import lk.vidathya.tcms.util.MonthName;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PayStaffSalariesController {

    public TableView tblStaffSalaryNotPaid;
    public Button btnRefresh;
    public StaffService staffService;
    public StaffSalaryService staffSalaryService;
    public NotPaidStaffSalaryService notPaidStaffSalaryService;
    @FXML
    private AnchorPane context;
    @FXML
    private TableView<PaidStaffSalaryTM> tblStaffSalary;
    @FXML
    private TableColumn<?, ?> colPaymentId;
    @FXML
    private TableColumn<?, ?> colStaffId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colJob;
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
    private Label lblDate;
    @FXML
    private TableColumn<?, ?> colNotPaiStaffId;
    @FXML
    private TableColumn<?, ?> colNotPaidName;
    @FXML
    private TableColumn<?, ?> colNotPaidJob;
    @FXML
    private TableColumn<?, ?> colNotPaidEmail;
    @FXML
    private TableColumn<?, ?> colNotPaidSalary;
    @FXML
    private ComboBox<String> cmbStaffId;
    @FXML
    private Button btnPay;
    @FXML
    private Label lblPaymentCode;

    public void initialize() {
        notPaidStaffSalaryService = ServiceFactory.getInstance().getService(ServiceType.NOT_PAID_STAFF_SALARY_SERVICE_IMPL);
        staffSalaryService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SALARY_SERVICE_IMPL);
        staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);
        lblDate.setText(String.valueOf(LocalDate.now()));
        loadMonths();
        setPaymentCode();
        loadStaffId();

        colNotPaiStaffId.setCellValueFactory(new PropertyValueFactory<>("notPaidStaffId"));
        colNotPaidName.setCellValueFactory(new PropertyValueFactory<>("notPaidName"));
        colNotPaidJob.setCellValueFactory(new PropertyValueFactory<>("notPaidJob"));
        colNotPaidEmail.setCellValueFactory(new PropertyValueFactory<>("notPaidEmail"));
        colNotPaidSalary.setCellValueFactory(new PropertyValueFactory<>("notPaidSalary"));


        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentCode"));
        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("job"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colPayerId.setCellValueFactory(new PropertyValueFactory<>("payerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        loadPaidData(MonthName.getMonthName(String.valueOf(LocalDate.now())), year);

        loadNotPaidData(MonthName.getMonthName(String.valueOf(LocalDate.now())), year);

    }

    private void loadStaffId() {
        try {
            ObservableList<String> staffIds = (ObservableList<String>) staffService.loadIdsToComboBox();
            cmbStaffId.setItems(staffIds);
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

    private void loadNotPaidData(String monthName, int year) {
        ObservableList<NotPaidStaffSalaryTM> data = FXCollections.observableArrayList();
        try {
            List list = notPaidStaffSalaryService.getAllByMonthYear(monthName, year);
            for (int i = 0; i < list.size(); i++) {
                NotPaidStaffSalaryDTO notPaidStaffSalaryDTO = (NotPaidStaffSalaryDTO) list.get(i);
                StaffDTO staffDTO = staffService.searchById(notPaidStaffSalaryDTO.getStaffId());
                data.add(
                        new NotPaidStaffSalaryTM(
                                notPaidStaffSalaryDTO.getStaffId(),
                                staffDTO.getName(),
                                staffDTO.getJob(),
                                staffDTO.getEmail(),
                                notPaidStaffSalaryDTO.getSalary()
                        ));

            }
            tblStaffSalaryNotPaid.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadPaidData(String monthName, int year) {
        //db table = StaffSalary
        ObservableList<PaidStaffSalaryTM> data = FXCollections.observableArrayList();
        try {
            List list = staffSalaryService.getAllDataByMonthYear(monthName, year);
            for (int i = 0; i < list.size(); i++) {
                StaffSalaryDTO staffSalaryDTO = (StaffSalaryDTO) list.get(i);
                StaffDTO staffDTO = staffService.searchById(staffSalaryDTO.getStaffId());
                data.add(
                        new PaidStaffSalaryTM(
                                staffSalaryDTO.getPaymentCode(),
                                staffSalaryDTO.getStaffId(),
                                staffDTO.getName(),
                                staffDTO.getJob(),
                                staffSalaryDTO.getSalary(),
                                LoginCredentials.getCurrentUser(),
                                lblDate.getText()
                        ));

            }

            tblStaffSalary.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

    }

    private void setPaymentCode() {
        try {
            lblPaymentCode.setText(staffSalaryService.getNewId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnPayOnAction(ActionEvent event) {

        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        String month = MonthName.getMonthName(String.valueOf(LocalDate.now()));

        if (!(String.valueOf(cmbStaffId.getValue()).equals(null))) {
            Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Confirm?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (choose.get() == ButtonType.YES) {

                try {
                    StaffDTO staffDTO = staffService.searchById(String.valueOf(cmbStaffId.getValue()));

                    StaffSalaryDTO staffSalaryDTO = new StaffSalaryDTO(
                            lblPaymentCode.getText(),
                            String.valueOf(cmbStaffId.getValue()),
                            year,
                            month,
                            staffDTO.getSalary(),
                            lblDate.getText(),
                            LoginCredentials.getCurrentUser()
                    );

                    boolean isAdd = staffSalaryService.add(staffSalaryDTO);
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
}
