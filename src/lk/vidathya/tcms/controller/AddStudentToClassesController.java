package lk.vidathya.tcms.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class AddStudentToClassesController {

    public AnchorPane context;
    public Button btnBack;
    public ComboBox cmbClassCode;
    public ClassesServices classesServices;
    public TutorService tutorService;
    public StudentService studentService;
    public StudentClassService studentClassService;
    @FXML
    private Label lblDate;
    @FXML
    private Button btnAddStudent;
    @FXML
    private TextField txtClassCode;
    @FXML
    private TextField txtStudentId;
    @FXML
    private TextField txtStudentName;
    @FXML
    private TextField txtStudentGrade;
    @FXML
    private TextField txtClassGrade;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextField txtTutorName;

    public void initialize() {
        studentClassService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_CLASS_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        lblDate.setText(String.valueOf(LocalDate.now()));
        loadClassCode();
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
    void btnAddStudentOnAction(ActionEvent event) {
        String guardianNic = null;
        try {
            StudentDTO studentDTO = studentService.searchById(txtStudentId.getText());
            guardianNic = studentDTO.getGuardianNic();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

        StudentClassDTO studentClassDTO = new StudentClassDTO(txtStudentId.getText(), String.valueOf(cmbClassCode.getValue()), guardianNic, lblDate.getText());


        try {
            boolean isExists = studentClassService.isExistStudentInAClass(txtStudentId.getText(), String.valueOf(cmbClassCode.getValue()));
            if (isExists) {
                new Alert(Alert.AlertType.WARNING, "Student Already Exists").show();
            } else {
                boolean isAdd = studentClassService.add(studentClassDTO);
                if (isAdd) {
                    Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Student Added Successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                    if (choose.get() == ButtonType.OK) {
                        try {
                            Navigation.navigate(Routes.ADDSTUDENTTOCLASSES, context);
                        } catch (IOException e) {
                            new Alert(Alert.AlertType.ERROR, e + "").show();
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtClassGradeOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentGradeOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentIdOnAction(ActionEvent event) {
        try {
            StudentDTO studentDTO = studentService.searchById(txtStudentId.getText());
            txtStudentName.setText(studentDTO.getName());
            txtStudentGrade.setText(studentDTO.getGrade());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    @FXML
    void txtStudentNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtSubjectOnAction(ActionEvent event) {

    }

    @FXML
    void txtTutorNameOnAction(ActionEvent event) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }

    public void cmbClassCodeOnAction(ActionEvent actionEvent) {
        setClassDetails();
    }


    private void setClassDetails() {
        try {
            ClassesDTO classesDTO = classesServices.searchById(cmbClassCode.getValue());
            txtClassGrade.setText(classesDTO.getGrade());
            txtSubject.setText(classesDTO.getSubject());

            TutorDTO tutorDTO = tutorService.searchById(classesDTO.getTutorId());
            txtTutorName.setText(tutorDTO.getName());

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

}
