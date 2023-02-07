package lk.vidathya.tcms.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.StudentClassDTO;
import lk.vidathya.tcms.dto.StudentDTO;
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.StudentClassService;
import lk.vidathya.tcms.service.custom.StudentService;
import lk.vidathya.tcms.service.custom.TutorService;
import lk.vidathya.tcms.tableModel.ClassStudentTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewClassStudentController implements Initializable {

    public AnchorPane context;
    public ComboBox cmbClassCode;
    public ClassesServices classesServices;
    public StudentService studentService;
    public StudentClassService studentClassService;
    public TutorService tutorService;
    @FXML
    private TableView<ClassStudentTM> tblClassStudent;
    @FXML
    private TableColumn<?, ?> colStudentId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colContactNo;
    @FXML
    private TableColumn<?, ?> colJoinDate;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnRefresh;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextField txtClassCode;
    @FXML
    private TextField txtGrade;
    @FXML
    private TextField txtTutorName;
    @FXML
    private TextField txtCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        studentClassService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_CLASS_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        loadClassCode();

        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colJoinDate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

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
        if (cmbClassCode.getValue() != null) {
            ObservableList<ClassStudentTM> data = FXCollections.observableArrayList();
            try {
                List list = studentClassService.getAllStudentsOfAClass(String.valueOf(cmbClassCode.getValue()));
                for (int i = 0; i < list.size(); i++) {
                    StudentClassDTO studentClassDTO = (StudentClassDTO) list.get(i);
                    StudentDTO studentDTO = studentService.searchById(studentClassDTO.getStudentId());
                    data.add(new ClassStudentTM(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getEmail(), studentDTO.getContactNo(), studentDTO.getDate()));
                }
                tblClassStudent.setItems(data);
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e + "").show();
            }
        }
    }

    @FXML
    void txtCountOnAction(ActionEvent event) {

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

    public void cmbClassCodeOnAction(ActionEvent actionEvent) {
        try {
            ClassesDTO classesDTO = classesServices.searchById(cmbClassCode.getValue());
            txtGrade.setText(classesDTO.getGrade());
            txtSubject.setText(classesDTO.getSubject());

            TutorDTO tutorDTO = tutorService.searchById(classesDTO.getTutorId());
            txtTutorName.setText(tutorDTO.getName());

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }


}
