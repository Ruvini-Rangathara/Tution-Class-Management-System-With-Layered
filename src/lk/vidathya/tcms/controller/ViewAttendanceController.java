package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.AttendanceDTO;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.StudentDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.AttendanceService;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.StudentService;
import lk.vidathya.tcms.tableModel.ViewAttendanceTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewAttendanceController implements Initializable {

    public AnchorPane context;
    public ComboBox cmbClassCode;
    public TextField txtSubject;
    public ClassesServices classesServices;
    public StudentService studentService;
    public AttendanceService attendanceService;
    @FXML
    private TableView<ViewAttendanceTM> tblAttendance;
    @FXML
    private TableColumn<?, ?> colStudentId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colStartTime;
    @FXML
    private TableColumn<?, ?> colEndTime;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnRefresh;
    @FXML
    private DatePicker dteDate;
    @FXML
    private TextField txtGrade;
    @FXML
    private TextField txtCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        attendanceService = ServiceFactory.getInstance().getService(ServiceType.ATTENDANCE_SERVICE_IMPL);

        loadClassCode();

        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

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
    void btnOkOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEWINFOCONTEXT, context);
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        int count = 0;
        if (String.valueOf(cmbClassCode.getValue()) != null && String.valueOf(dteDate.getValue()) != null) {
            ObservableList<ViewAttendanceTM> data = FXCollections.observableArrayList();
            try {
                List list = attendanceService.getStudentsAttendance(String.valueOf(cmbClassCode.getValue()), String.valueOf(dteDate.getValue()));
                for (int i = 0; i < list.size(); i++) {
                    AttendanceDTO attendanceDTO = (AttendanceDTO) list.get(i);

                    StudentDTO studentDTO = studentService.searchById(attendanceDTO.getStudentId());
                    ClassesDTO classesDTO = classesServices.searchById(String.valueOf(cmbClassCode.getValue()));

                    data.add(new ViewAttendanceTM(attendanceDTO.getStudentId(), studentDTO.getName(), attendanceDTO.getDate(), classesDTO.getStartTime(), classesDTO.getEndTime()));
                    count++;
                }

                tblAttendance.setItems(data);
                txtCount.setText(String.valueOf(count));
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e + "").show();
            }

        }

    }


    @FXML
    void dteDateOnAction(ActionEvent event) {

    }

    @FXML
    void txtCountOnAction(ActionEvent event) {

    }

    @FXML
    void txtGradeOnAction(ActionEvent event) {

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

    public void txtSubjectOnAction(ActionEvent actionEvent) {

    }

}
