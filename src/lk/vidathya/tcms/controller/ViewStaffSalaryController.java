package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.StaffDTO;
import lk.vidathya.tcms.dto.StaffSalaryDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.StaffSalaryService;
import lk.vidathya.tcms.service.custom.StaffService;
import lk.vidathya.tcms.tableModel.StaffSalaryTM;
import lk.vidathya.tcms.util.MonthName;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ViewStaffSalaryController implements Initializable {

    public AnchorPane context;
    public TableColumn colPaymentCode;
    public StaffService staffService;
    public StaffSalaryService staffSalaryService;
    @FXML
    private TableView<StaffSalaryTM> tblStaffSalary;
    @FXML
    private TableColumn<?, ?> colStaffId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colJob;
    @FXML
    private TableColumn<?, ?> colSalary;
    @FXML
    private TableColumn<?, ?> colMonth;
    @FXML
    private TableColumn<?, ?> colPaymentDate;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtYear;
    @FXML
    private ComboBox<String> cmbMonth;
    @FXML
    private Button btnRefresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);
        staffSalaryService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SALARY_SERVICE_IMPL);
        loadMonths();

        colPaymentCode.setCellValueFactory(new PropertyValueFactory<>("paymentCode"));
        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("job"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));


        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        loadStaffSalaryData(year, MonthName.getMonthName(String.valueOf(LocalDate.now())));
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

    private void loadStaffSalaryData(int year, String monthName) {
        ObservableList<StaffSalaryTM> data = FXCollections.observableArrayList();
        try {
            List list = staffSalaryService.getSalaryData(year, monthName);
            for (int i = 0; i < list.size(); i++) {
                StaffSalaryDTO staffSalaryDTO = (StaffSalaryDTO) list.get(i);
                StaffDTO staffDTO = staffService.searchById(staffSalaryDTO.getStaffId());
                data.add(
                        new StaffSalaryTM(
                                staffSalaryDTO.getPaymentCode(),
                                staffSalaryDTO.getStaffId(),
                                staffDTO.getName(),
                                staffDTO.getJob(),
                                staffSalaryDTO.getSalary(),
                                staffSalaryDTO.getMonth(),
                                staffSalaryDTO.getDate()
                        ));
            }
            tblStaffSalary.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    private void loadStaffSalaryData(int year) {
        ObservableList<StaffSalaryTM> data = FXCollections.observableArrayList();
        try {
            List list = staffSalaryService.getSalaryData(year);
            for (int i = 0; i < list.size(); i++) {
                StaffSalaryDTO staffSalaryDTO = (StaffSalaryDTO) list.get(i);
                StaffDTO staffDTO = staffService.searchById(staffSalaryDTO.getStaffId());
                data.add(
                        new StaffSalaryTM(
                                staffSalaryDTO.getPaymentCode(),
                                staffSalaryDTO.getStaffId(),
                                staffDTO.getName(),
                                staffDTO.getJob(),
                                staffSalaryDTO.getSalary(),
                                staffSalaryDTO.getMonth(),
                                staffSalaryDTO.getDate()
                        ));
            }
            tblStaffSalary.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    private void loadStaffSalaryData(String month) {
        ObservableList<StaffSalaryTM> data = FXCollections.observableArrayList();
        try {
            List list = staffSalaryService.getSalaryData(month);
            for (int i = 0; i < list.size(); i++) {
                StaffSalaryDTO staffSalaryDTO = (StaffSalaryDTO) list.get(i);
                StaffDTO staffDTO = staffService.searchById(staffSalaryDTO.getStaffId());
                data.add(
                        new StaffSalaryTM(
                                staffSalaryDTO.getPaymentCode(),
                                staffSalaryDTO.getStaffId(),
                                staffDTO.getName(),
                                staffDTO.getJob(),
                                staffSalaryDTO.getSalary(),
                                staffSalaryDTO.getMonth(),
                                staffSalaryDTO.getDate()
                        ));
            }
            tblStaffSalary.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEWINFOCONTEXT, context);
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {

    }

    @FXML
    void cmbMonthOnAction(ActionEvent event) {
        if (txtYear.getText() == null) {
            loadStaffSalaryData(String.valueOf(cmbMonth.getValue()));
        } else {
            loadStaffSalaryData(Integer.parseInt(txtYear.getText()), String.valueOf(cmbMonth.getValue()));
        }
    }


    @FXML
    void txtYearOnAction(ActionEvent event) {
        if (cmbMonth.getValue() == null) {
            loadStaffSalaryData(Integer.parseInt(txtYear.getText()));
        } else {
            loadStaffSalaryData(Integer.parseInt(txtYear.getText()), String.valueOf(cmbMonth.getValue()));
        }

    }


}
