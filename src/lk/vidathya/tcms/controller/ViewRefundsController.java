package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.PaymentDTO;
import lk.vidathya.tcms.dto.RefundDTO;
import lk.vidathya.tcms.dto.StudentDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.PaymentService;
import lk.vidathya.tcms.service.custom.RefundService;
import lk.vidathya.tcms.service.custom.StudentService;
import lk.vidathya.tcms.tableModel.RefundsTM;
import lk.vidathya.tcms.util.MonthName;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ViewRefundsController implements Initializable {

    public AnchorPane context;
    public TableColumn colPayerId;
    public RefundService refundService;
    public StudentService studentService;
    public PaymentService paymentService;
    @FXML
    private TableView<RefundsTM> tblRefunds;
    @FXML
    private TableColumn<?, ?> colPaymentCode;
    @FXML
    private TableColumn<?, ?> colPaymentDescription;
    @FXML
    private TableColumn<?, ?> colStudentId;
    @FXML
    private TableColumn<?, ?> colStudentName;
    @FXML
    private TableColumn<?, ?> colPaymentDate;
    @FXML
    private TableColumn<?, ?> colRefundDate;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnRefresh;
    @FXML
    private ComboBox<String> cmbMonth;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refundService = ServiceFactory.getInstance().getService(ServiceType.REFUND_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        paymentService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_SERVICE_IMPL);

        loadMonths();

        colPaymentCode.setCellValueFactory(new PropertyValueFactory<>("paymentCode"));
        colPaymentDescription.setCellValueFactory(new PropertyValueFactory<>("paymentDescription"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colRefundDate.setCellValueFactory(new PropertyValueFactory<>("refundDate"));
        colPayerId.setCellValueFactory(new PropertyValueFactory<>("payerId"));

        loadRefundData(MonthName.getMonthName(String.valueOf(LocalDate.now())));
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

    private void loadRefundData(String monthName) {
        ObservableList<RefundsTM> data = FXCollections.observableArrayList();
        try {
            List list = refundService.getAllByMonth(monthName);
            for (int i = 0; i < list.size(); i++) {
                RefundDTO refundDTO = (RefundDTO) list.get(i);
                StudentDTO studentDTO = studentService.searchById(refundDTO.getStudentId());
                PaymentDTO paymentDTO = paymentService.searchById(refundDTO.getPaymentCode());

                data.add(new RefundsTM(refundDTO.getPaymentCode(), refundDTO.getDescription(), refundDTO.getStudentId(), studentDTO.getName(), paymentDTO.getDate(), refundDTO.getDate(), refundDTO.getStaffId()));
            }
            tblRefunds.setItems(data);
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
        loadRefundData(String.valueOf(cmbMonth.getValue()));
    }

    @FXML
    void cmbMonthOnAction(ActionEvent event) {

    }


}
