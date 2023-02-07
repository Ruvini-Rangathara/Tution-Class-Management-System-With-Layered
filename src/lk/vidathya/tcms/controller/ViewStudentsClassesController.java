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
import lk.vidathya.tcms.tableModel.StudentClassTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewStudentsClassesController implements Initializable {

    public AnchorPane context;
    public TableColumn colTutorName;
    public StudentService studentService;
    public StudentClassService studentClassService;
    public TutorService tutorService;
    public ClassesServices classesServices;
    @FXML
    private TableView<StudentClassTM> tblStudentClasses;
    @FXML
    private TableColumn<?, ?> colClassCode;
    @FXML
    private TableColumn<?, ?> colGrade;
    @FXML
    private TableColumn<?, ?> colSubject;
    @FXML
    private TableColumn<?, ?> colJoinDate;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnRefresh;
    @FXML
    private TextField txtStudentId;
    @FXML
    private TextField txtName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        studentClassService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_CLASS_SERVICE_IMPL);
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);

        colClassCode.setCellValueFactory(new PropertyValueFactory<>("classCode"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colTutorName.setCellValueFactory(new PropertyValueFactory<>("tutorName"));
        colJoinDate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEWINFOCONTEXT, context);
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentIdOnAction(ActionEvent event) {
        try {
            StudentDTO studentDTO = studentService.searchById(txtStudentId.getText());
            txtName.setText(studentDTO.getName());

            ObservableList<StudentClassTM> data = FXCollections.observableArrayList();
            List list = studentClassService.getClassesOfAStudent(txtStudentId.getText());
            for (int i = 0; i < list.size(); i++) {
                StudentClassDTO studentClassDTO = (StudentClassDTO) list.get(i);
                ClassesDTO classesDTO = classesServices.searchById(studentClassDTO.getClassCode());
                TutorDTO tutorDTO = tutorService.searchById(classesDTO.getTutorId());
                data.add(
                        new StudentClassTM(
                                studentClassDTO.getClassCode(),
                                classesDTO.getGrade(),
                                classesDTO.getSubject(),
                                tutorDTO.getName(),
                                studentClassDTO.getDate()
                        ));
            }
            tblStudentClasses.setItems(data);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }


}
