package lk.vidathya.tcms.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.ExtraClassDTO;
import lk.vidathya.tcms.dto.ExtraClassHallReservationDTO;
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.*;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.RegExPatterns;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddExtraClassController implements Initializable {

    public AnchorPane context;

    public TableView tblExtraClassHallReservation;

    public TableColumn colHallNo;
    public TableColumn colDate;
    public TableColumn colClassCode;
    public TableColumn colStartTime;
    public TableColumn colEndTime;
    public Label lblEClassHallReservationNo;
    public HallService hallService;
    public ClassesServices classesServices;
    public ExtraClassService extraClassService;
    public ExtraClassHallReservationService extraClassHallReservationService;
    public TutorService tutorService;
    @FXML
    private Label lblEClassCode;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblInvalidStarTime;
    @FXML
    private Label lblInvalidEndTime;
    @FXML
    private Button btnAddEClass;
    @FXML
    private TextField txtTutorName;
    @FXML
    private TextField txtStartTime;
    @FXML
    private TextField txtEndTime;
    @FXML
    private ComboBox<String> cmbHallNo;
    @FXML
    private ComboBox<String> cmbClassCode;
    @FXML
    private TextField txtClassCode;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextField txtGrade;
    @FXML
    private DatePicker dteDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        extraClassHallReservationService = ServiceFactory.getInstance().getService(ServiceType.EXTRA_CLASS_HALL_RESERVATION_SERVICE_IMPL);
        extraClassService = ServiceFactory.getInstance().getService(ServiceType.EXTRA_CLASS_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        hallService = ServiceFactory.getInstance().getService(ServiceType.HALL_SERVICE_IMPL);
        lblDate.setText(String.valueOf(LocalDate.now()));
        lblInvalidEndTime.setVisible(false);
        lblInvalidStarTime.setVisible(false);
        loadClassCode();
        loadHallNo();
        setExtraClassCode();
        setExtraClassHallReservationId();
    }

    private void loadClassCode() {
        try {
            ObservableList<String> classCode = (ObservableList<String>) classesServices.loadIdToComboBox();
            cmbClassCode.setItems(classCode);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadHallNo() {
        try {
            ObservableList<String> hallNo = (ObservableList<String>) hallService.loadIdsToComboBox();
            cmbHallNo.setItems(hallNo);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setExtraClassCode() {
        try {
            lblEClassCode.setText(extraClassService.getNewId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setExtraClassHallReservationId() {
        try {
            lblEClassHallReservationNo.setText(extraClassHallReservationService.getNewId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnAddEClassOnAction(ActionEvent event) {

        lblInvalidEndTime.setVisible(false);
        lblInvalidStarTime.setVisible(false);

        boolean isStartTimeMatched = RegExPatterns.getTimePattern().matcher(txtStartTime.getText()).matches();
        boolean isEndTimeMatched = RegExPatterns.getTimePattern().matcher(txtEndTime.getText()).matches();

        if (isStartTimeMatched) {
            if (isEndTimeMatched) {


                ExtraClassDTO extraClassDTO = new ExtraClassDTO(lblEClassCode.getText(), String.valueOf(cmbClassCode.getValue()), lblDate.getText(), txtStartTime.getText(), txtEndTime.getText(), String.valueOf(cmbHallNo.getValue()));

                ExtraClassHallReservationDTO extraClassHallReservationDTO = new ExtraClassHallReservationDTO(lblEClassHallReservationNo.getText(), String.valueOf(cmbHallNo.getValue()), lblEClassCode.getText(), lblDate.getText(), txtStartTime.getText(), txtEndTime.getText());

                try {
                    boolean isAdd = extraClassService.add(extraClassDTO, extraClassHallReservationDTO);
                    if (isAdd) {

                        Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Extra Class Added Successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                        if (choose.get() == ButtonType.OK) {
                            try {
                                Navigation.navigate(Routes.ADDEXTRACLASS, context);
                            } catch (IOException e) {
                                new Alert(Alert.AlertType.ERROR, e + "").show();
                            }
                        }

                    }
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e + "").show();
                }


            } else {
                lblInvalidEndTime.setVisible(true);
                txtEndTime.requestFocus();
            }
        } else {
            lblInvalidStarTime.setVisible(true);
            txtStartTime.requestFocus();
        }


    }

    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }

    @FXML
    void cmbHallNoOnAction(ActionEvent event) {

    }

    @FXML
    void dteDateOnAction(ActionEvent event) {

    }

    @FXML
    void txtEndTimeOnAction(ActionEvent event) {

    }

    @FXML
    void txtGradeOnAction(ActionEvent event) {

    }

    @FXML
    void txtStartTimeOnAction(ActionEvent event) {

    }

    @FXML
    void txtSubjectOnAction(ActionEvent event) {

    }

    @FXML
    void txtTutorNameOnAction(ActionEvent event) {

    }

    public void cmbClassCodeOnAction(ActionEvent actionEvent) {
        try {
            ClassesDTO classesDTO = classesServices.searchById(String.valueOf(cmbClassCode.getValue()));
            txtGrade.setText(classesDTO.getGrade());
            txtSubject.setText(classesDTO.getSubject());

            TutorDTO tutorDTO = tutorService.searchById(classesDTO.getTutorId());
            txtTutorName.setText(tutorDTO.getName());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


}
