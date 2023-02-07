package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.dto.TutorSalaryDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.TutorSalaryService;
import lk.vidathya.tcms.service.custom.TutorService;
import lk.vidathya.tcms.tableModel.TutorSalaryInfoTM;
import lk.vidathya.tcms.util.MonthName;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ViewTutorsSalaryController {

    public AnchorPane context;
    public TableColumn colPayerId;
    public TutorService tutorService;
    public TutorSalaryService tutorSalaryService;
    @FXML
    private TableView<TutorSalaryInfoTM> tblTutorSalary;
    @FXML
    private TableColumn<?, ?> colTutorId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colSubject;
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

    public void initialize() {
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        tutorSalaryService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SALARY_SERVICE_IMPL);
        loadMonths();

        colTutorId.setCellValueFactory(new PropertyValueFactory<>("tutorId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colPayerId.setCellValueFactory(new PropertyValueFactory<>("payerId"));

        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        loadSalaryData(MonthName.getMonthName(String.valueOf(LocalDate.now())), year);
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

    private void loadSalaryData(String monthName, int year) {

        ObservableList<TutorSalaryInfoTM> data = FXCollections.observableArrayList();
        try {
            List list = tutorSalaryService.getAllByMonthYear(monthName, year);
            for (int i = 0; i < list.size(); i++) {
                TutorSalaryDTO tutorSalaryDTO = (TutorSalaryDTO) list.get(i);
                TutorDTO tutorDTO = tutorService.searchById(tutorSalaryDTO.getTutorId());
                data.add(new TutorSalaryInfoTM(tutorSalaryDTO.getTutorId(), tutorDTO.getName(), tutorDTO.getSubject(), tutorSalaryDTO.getSalary(), tutorSalaryDTO.getMonth(), tutorSalaryDTO.getDate(), tutorSalaryDTO.getPayerId()));
            }
            tblTutorSalary.setItems(data);
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
        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        if (txtYear.getText() == null && cmbMonth.getValue() == null) {
            loadSalaryData(MonthName.getMonthName(String.valueOf(LocalDate.now())), year);
        } else if (txtYear.getText() != null && cmbMonth.getValue() == null) {
            loadSalaryData(MonthName.getMonthName(String.valueOf(LocalDate.now())), Integer.parseInt(txtYear.getText()));
        } else if (txtYear.getText() == null && cmbMonth.getValue() != null) {
            loadSalaryData(String.valueOf(cmbMonth.getValue()), year);
        } else {
            loadSalaryData(String.valueOf(cmbMonth.getValue()), Integer.parseInt(txtYear.getText()));
        }

    }

    @FXML
    void cmbMonthOnAction(ActionEvent event) {

    }

    @FXML
    void txtYearOnAction(ActionEvent event) {

    }


}
