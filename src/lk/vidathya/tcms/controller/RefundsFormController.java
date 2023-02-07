package lk.vidathya.tcms.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.PaymentDTO;
import lk.vidathya.tcms.dto.RefundDTO;
import lk.vidathya.tcms.dto.StudentDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.PaymentService;
import lk.vidathya.tcms.service.custom.RefundService;
import lk.vidathya.tcms.service.custom.StudentService;
import lk.vidathya.tcms.util.LoginCredentials;
import lk.vidathya.tcms.util.MonthName;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class RefundsFormController {

    public AnchorPane context;
    public Label lblUserId;
    public PaymentService paymentService;
    public StudentService studentService;
    public RefundService refundService;
    @FXML
    private Label lblDate;
    @FXML
    private Button btnPayBack;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtPaymentDate;
    @FXML
    private TextField txtStudentId;
    @FXML
    private TextField txtStudentName;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtPaymentDescription;
    @FXML
    private TextField txtPaymentCode;

    public void initialize() {
        paymentService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        refundService = ServiceFactory.getInstance().getService(ServiceType.REFUND_SERVICE_IMPL);

        lblDate.setText(String.valueOf(LocalDate.now()));
        lblUserId.setText(LoginCredentials.getCurrentUser());

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }

    @FXML
    void btnPayBackOnAction(ActionEvent event) {
        RefundDTO refundDTO = new RefundDTO(
                txtPaymentCode.getText(),
                txtStudentId.getText(),
                txtPaymentDescription.getText(),
                Double.parseDouble(txtAmount.getText()),
                lblDate.getText(),
                LoginCredentials.getCurrentUser(),
                MonthName.getMonthName(String.valueOf(LocalDate.now()))
        );

        try {
            boolean isDone = refundService.add(refundDTO);
            if (isDone) {
                Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Refund Successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                if (choose.get() == ButtonType.OK) {
                    try {
                        Navigation.navigate(Routes.REFUNDS, context);
                    } catch (IOException e) {
                        new Alert(Alert.AlertType.ERROR, e + "").show();
                    }
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    @FXML
    void txtAmountOnAction(ActionEvent event) {

    }

    @FXML
    void txtPaymentCodeOnAction(ActionEvent event) {
        try {
            PaymentDTO paymentDTO = paymentService.searchById(txtPaymentCode.getText());
            txtStudentId.setText(paymentDTO.getStudentId());
            txtPaymentDescription.setText(paymentDTO.getDescription());
            txtAmount.setText(String.valueOf(paymentDTO.getFee()));
            txtPaymentDate.setText(paymentDTO.getDate());

            StudentDTO studentDTO = studentService.searchById(txtStudentId.getText());
            txtStudentName.setText(studentDTO.getName());

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    @FXML
    void txtPaymentDateOnAction(ActionEvent event) {

    }

    @FXML
    void txtPaymentDescriptionOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentNameOnAction(ActionEvent event) {

    }
}
