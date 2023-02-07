package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.*;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.*;
import lk.vidathya.tcms.tableModel.AttendanceTM;
import lk.vidathya.tcms.util.MonthName;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFormController {

    public AnchorPane context;
    public AnchorPane testContext;

    public TableColumn colPaymentStatus;

    public TableColumn colFee;


    public Button btnPayClassFee;
    public Button btnMark;
    public RadioButton btnPresent;
    public RadioButton btnAbsent;
    public ToggleGroup ap;
    public TextField txtStudentName;
    public TextField txtStudentId;
    public ClassesServices classesServices;
    public TutorService tutorService;
    public StudentClassService studentClassService;
    public AttendanceService attendanceService;
    public StudentService studentService;
    public PaymentService paymentService;
    @FXML
    private TextField txtGrade;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextField txtTutorName;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblMonth;
    @FXML
    private TableView<AttendanceTM> tblAttendance;
    @FXML
    private TableColumn<?, ?> colStudentId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private ComboBox<String> cmbClassCode;

    public void initialize() {
        paymentService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        attendanceService = ServiceFactory.getInstance().getService(ServiceType.ATTENDANCE_SERVICE_IMPL);
        studentClassService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_CLASS_SERVICE_IMPL);
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        lblDate.setText(String.valueOf(LocalDate.now()));
        String month = MonthName.getMonthName(String.valueOf(LocalDate.now()));
        lblMonth.setText(month);
        loadClassCode();

        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        btnAbsent.setSelected(true);
    }

    private void loadClassCode() {
        try {
            ObservableList<String> classCode = (ObservableList<String>) classesServices.loadIdToComboBox();
            cmbClassCode.setItems(classCode);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbClassCodeOnAction(ActionEvent event) {
        try {
            ClassesDTO classesDTO = classesServices.searchById(cmbClassCode.getValue());
            txtGrade.setText(classesDTO.getGrade());
            txtSubject.setText(classesDTO.getSubject());

            TutorDTO tutorDTO = tutorService.searchById(classesDTO.getTutorId());
            txtTutorName.setText(tutorDTO.getName());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        loadStudents(String.valueOf(cmbClassCode.getValue()));
    }

    private void loadStudents(String classCode) {
        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        ObservableList<AttendanceTM> data = FXCollections.observableArrayList();
        try {
            List list = studentClassService.getAllStudentsOfAClass(classCode);
            ArrayList<String> classesStudentsId = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                StudentClassDTO studentClassDTO = (StudentClassDTO) list.get(i);
                classesStudentsId.add(studentClassDTO.getStudentId());
            }
            List<String> attendanceExistStudent = attendanceService.getStudentIds(classCode, lblDate.getText());

            ArrayList<String> notMarkAttendanceIds = new ArrayList<>();
            for (int i = 0; i < classesStudentsId.size(); i++) {
                int count = 0;
                for (int j = 0; j < attendanceExistStudent.size(); j++) {
                    if (classesStudentsId.get(i).equals(attendanceExistStudent.get(j))) {
                        count++;
                    }
                }

                if (count == 0) {
                    notMarkAttendanceIds.add(classesStudentsId.get(i));
                }
            }

            for (int i = 0; i < notMarkAttendanceIds.size(); i++) {
                StudentDTO studentDTO = studentService.searchById(notMarkAttendanceIds.get(i));
                ClassesDTO classesDTO = classesServices.searchById(classCode);

                String paymentStatus = paymentService.getPaymentStatus(
                        notMarkAttendanceIds.get(i),
                        classCode,
                        year,
                        MonthName.getMonthName(String.valueOf(LocalDate.now()))
                );

                data.add(
                        new AttendanceTM(
                                notMarkAttendanceIds.get(i),
                                studentDTO.getName(),
                                paymentStatus,
                                classesDTO.getClassFee()
                        ));
            }
            tblAttendance.setItems(data);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void txtGradeOnAction(ActionEvent event) {

    }

    @FXML
    void txtSubjectOnAction(ActionEvent event) {

    }

    @FXML
    void txtTutorNameOnAction(ActionEvent event) {

    }

    public void backImageOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }

    public void btnPayClassFeeOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PAYMENTS, context);
    }

    public void btnMarkOnAction(ActionEvent actionEvent) {
        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);

        String status = null;
        if (btnAbsent.isSelected()) {
            status = "Absent";
        }
        if (btnPresent.isSelected()) {
            status = "Present";
        }

        AttendanceDTO attendanceDTO = new AttendanceDTO(
                txtStudentId.getText(),
                String.valueOf(cmbClassCode.getValue()),
                status,
                lblDate.getText(),
                year,
                MonthName.getMonthName(String.valueOf(LocalDate.now()))
        );

        try {
            boolean isAddAttendance = attendanceService.add(attendanceDTO);

            if (isAddAttendance) {
                txtStudentId.setText(null);
                txtStudentName.setText(null);
                btnAbsent.setSelected(true);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadStudents(String.valueOf(cmbClassCode.getValue()));

    }

    public void txtStudentNameOnAction(ActionEvent actionEvent) {
    }

    public void txtStudentIdOnAction(ActionEvent actionEvent) {
    }

    public void tblAttendanceOnMouseClicked(MouseEvent mouseEvent) {
        if (tblAttendance.getSelectionModel().getSelectedItem() != null) {
            txtStudentId.setText(tblAttendance.getSelectionModel().getSelectedItem().getStudentId());
            txtStudentName.setText(tblAttendance.getSelectionModel().getSelectedItem().getName());
        }

    }
}
