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

public class ManageExtraClassController implements Initializable {


    public AnchorPane context;
    public Button btnBack;
    public ComboBox cmbClassCode;
    public ComboBox cmbEClassCode;

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
    private Label lblDate;
    @FXML
    private Label lblInvalidStarTime;
    @FXML
    private Label lblInvalidEndTime;
    @FXML
    private TextField txtTutorName;
    @FXML
    private TextField txtStartTime;
    @FXML
    private TextField txtEndTime;
    @FXML
    private ComboBox<String> cmbHallNo;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextField txtGrade;
    @FXML
    private DatePicker dteDate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Label lblExit;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        extraClassHallReservationService = ServiceFactory.getInstance().getService(ServiceType.EXTRA_CLASS_HALL_RESERVATION_SERVICE_IMPL);

        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        extraClassHallReservationService = ServiceFactory.getInstance().getService(ServiceType.EXTRA_CLASS_HALL_RESERVATION_SERVICE_IMPL);
        extraClassService = ServiceFactory.getInstance().getService(ServiceType.EXTRA_CLASS_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        hallService = ServiceFactory.getInstance().getService(ServiceType.HALL_SERVICE_IMPL);

        loadHallNo();
        loadExtraClassCode();
        loadClassCode();

        lblDate.setText(String.valueOf(LocalDate.now()));

        lblInvalidEndTime.setVisible(false);
        lblInvalidStarTime.setVisible(false);
    }

    private void loadExtraClassCode() {
        try {
            ObservableList<String> eClassCode = (ObservableList<String>) extraClassService.loadIdsToComboBox();
            cmbClassCode.setItems(eClassCode);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (cmbEClassCode.getValue() != null) {
            try {
                Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO).showAndWait();
                if (choose.get() == ButtonType.YES) {

                    boolean isDelete = extraClassHallReservationService.delete(lblEClassHallReservationNo.getText());
                    if (isDelete) {
                        new Alert(Alert.AlertType.CONFIRMATION, "The Extra Class Was SuccessFully Deactivated!").show();
                        Navigation.navigate(Routes.MANAGEEXTRACLASSES, context);
                    }
                }
            } catch (SQLException | ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }


    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        lblInvalidEndTime.setVisible(false);
        lblInvalidStarTime.setVisible(false);

        boolean isStartTimeMatched = RegExPatterns.getTimePattern().matcher(txtStartTime.getText()).matches();
        boolean isEndTimeMatched = RegExPatterns.getTimePattern().matcher(txtEndTime.getText()).matches();

        if (isStartTimeMatched) {
            if (isEndTimeMatched) {


                String classCode = null;
                if (cmbClassCode.getValue() == null) {
                    classCode = cmbClassCode.getPromptText();
                } else {
                    classCode = String.valueOf(cmbClassCode.getValue());
                }

                String hallNo = null;
                if (cmbHallNo.getValue() == null) {
                    hallNo = cmbHallNo.getPromptText();
                } else {
                    hallNo = String.valueOf(cmbHallNo.getValue());
                }

                ExtraClassDTO extraClassDTO = new ExtraClassDTO(
                        String.valueOf(cmbEClassCode.getValue()),
                        classCode,
                        lblDate.getText(),
                        txtStartTime.getText(),
                        txtEndTime.getText(),
                        hallNo
                );

                ExtraClassHallReservationDTO extraClassHallReservationDTO = new ExtraClassHallReservationDTO(
                        lblEClassHallReservationNo.getText(),
                        hallNo,
                        String.valueOf(cmbEClassCode.getValue()),
                        lblDate.getText(),
                        txtStartTime.getText(),
                        txtEndTime.getText()
                );

                try {
                    boolean isUpdate = extraClassService.update(extraClassDTO, extraClassHallReservationDTO);
                    if (isUpdate) {

                        Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Extra Class Updated Successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                        if (choose.get() == ButtonType.OK) {
                            try {
                                Navigation.navigate(Routes.MANAGEEXTRACLASSES, context);
                            } catch (IOException e) {
                                new Alert(Alert.AlertType.ERROR, e + "").show();
                            }
                        }

                    }


                } catch (SQLException e) {
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

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
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

    public void cmbEClassCodeOnAction(ActionEvent actionEvent) {
        try {

            ExtraClassDTO extraClassDTO = extraClassService.searchById(cmbEClassCode.getValue());
            cmbClassCode.setPromptText(extraClassDTO.getClassCode());

            ClassesDTO classesDTO = classesServices.searchById(cmbClassCode.getPromptText());
            txtGrade.setText(classesDTO.getGrade());
            txtSubject.setText(classesDTO.getSubject());

            TutorDTO tutorDTO = tutorService.searchById(classesDTO.getTutorId());
            txtTutorName.setText(tutorDTO.getName());

            txtStartTime.setText(extraClassDTO.getStartTime());
            txtEndTime.setText(extraClassDTO.getEndTime());


            cmbHallNo.setPromptText(extraClassDTO.getHallNo());
            dteDate.setPromptText(extraClassDTO.getDate());

            String hallReservationNo = extraClassHallReservationService.getHallReservationNo(String.valueOf(cmbEClassCode.getValue()));
            lblEClassHallReservationNo.setText(hallReservationNo);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


}
