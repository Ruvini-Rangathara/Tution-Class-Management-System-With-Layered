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
import lk.vidathya.tcms.dto.GuardianDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.GuardianService;
import lk.vidathya.tcms.service.custom.StudentClassService;
import lk.vidathya.tcms.tableModel.GuardianTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewGuardianInfoController implements Initializable {

    public AnchorPane context;
    public ClassesServices classesServices;
    public StudentClassService studentClassService;
    public GuardianService guardianService;
    @FXML
    private ComboBox<String> cmbClassCode;
    @FXML
    private Button btnRefresh;
    @FXML
    private TextField txtGrade;
    @FXML
    private TextField txtSubject;
    @FXML
    private TableView<GuardianTM> tblGuardian;
    @FXML
    private TableColumn<?, ?> colGuardianNic;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colContactNo;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colOccupation;
    @FXML
    private Button btnOk;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        studentClassService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_CLASS_SERVICE_IMPL);
        guardianService = ServiceFactory.getInstance().getService(ServiceType.GUARDIAN_SERVICE_IMPL);

        loadClassCode();

        colGuardianNic.setCellValueFactory(new PropertyValueFactory<>("guardianNic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colOccupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));
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
        ObservableList<GuardianTM> data = FXCollections.observableArrayList();
        if (cmbClassCode.getValue() != null) {
            try {
                List list = studentClassService.getGuardianNic(String.valueOf(cmbClassCode.getValue()));
                for (int i = 0; i < list.size(); i++) {
                    GuardianDTO guardianDTO = guardianService.searchById(list.get(i));
                    data.add(new GuardianTM(guardianDTO.getGuardianNic(), guardianDTO.getName(), guardianDTO.getContactNo(), guardianDTO.getEmail(), guardianDTO.getOccupation()));
                }
                tblGuardian.setItems(data);
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e + "").show();
            }
        }
    }

    @FXML
    void cmbClassCodeOnAction(ActionEvent event) {
        try {
            ClassesDTO classesDTO = classesServices.searchById(cmbClassCode.getValue());
            txtGrade.setText(classesDTO.getGrade());
            txtSubject.setText(classesDTO.getSubject());

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }


}
