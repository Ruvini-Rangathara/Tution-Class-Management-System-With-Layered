package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.HallReservationDTO;
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.HallReservationService;
import lk.vidathya.tcms.service.custom.HallService;
import lk.vidathya.tcms.service.custom.TutorService;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.RegExPatterns;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class AddClassFormController {
    public AnchorPane context;
    public TextField txtGrade;

    public ComboBox cmbSubject;


    public TableColumn colHallNo;
    public TableColumn colDay;
    public TableColumn colClassCode;
    public TableColumn colStartTime;
    public TableColumn colEndTime;
    public Label lblHallReservationNo;
    public ClassesServices classesServices;
    public HallReservationService hallReservationService;
    public TutorService tutorService;
    public HallService hallService;
    @FXML
    private Label lblClassCode;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblInvalidStarTime;
    @FXML
    private Label lblInvalidEndTime;
    @FXML
    private Button btnAddClass;
    @FXML
    private Label lblInvalidClassFee;
    @FXML
    private TextField txtTutorName;
    @FXML
    private TextField txtStartTime;
    @FXML
    private TextField txtEndTime;
    @FXML
    private ComboBox<String> cmbTutorId;
    @FXML
    private ComboBox<String> cmbDay;
    @FXML
    private ComboBox<String> cmbHallNo;
    @FXML
    private TextField txtClassFee;

    public void initialize() {
        hallService = ServiceFactory.getInstance().getService(ServiceType.HALL_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        hallReservationService = ServiceFactory.getInstance().getService(ServiceType.HALL_RESERVATION_SERVICE_IMPL);
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        setHallReservationId();
        lblDate.setText(String.valueOf(LocalDate.now()));
        setClassCode();

        lblInvalidClassFee.setVisible(false);
        lblInvalidEndTime.setVisible(false);
        lblInvalidStarTime.setVisible(false);
        loadDays();
        loadHallNo();
        loadTutorId();
        loadSubject();
    }

    private void loadSubject() {
        try {
            ObservableList<String> subject = (ObservableList<String>) tutorService.loadSubjectToComboBox();
            cmbSubject.setItems(subject);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTutorId() {
        try {
            ObservableList<String> tutorId = (ObservableList<String>) tutorService.loadTutorIdToComboBox();
            cmbTutorId.setItems(tutorId);
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

    private void loadDays() {
        ObservableList<String> days = FXCollections.observableArrayList();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");
        cmbDay.setItems(days);
    }

    private void setHallReservationId() {
        try {
            lblHallReservationNo.setText(hallReservationService.getNewId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setClassCode() {
        try {
            lblClassCode.setText(classesServices.getNewId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddClassOnAction(ActionEvent event) {

        lblInvalidClassFee.setVisible(false);
        lblInvalidEndTime.setVisible(false);
        lblInvalidStarTime.setVisible(false);

        boolean isStartTimeMatched = RegExPatterns.getTimePattern().matcher(txtStartTime.getText()).matches();
        boolean isEndTimeMatched = RegExPatterns.getTimePattern().matcher(txtEndTime.getText()).matches();
        boolean isClassFeeMatched = RegExPatterns.getSalaryPattern().matcher(txtStartTime.getText()).matches();

        if (isStartTimeMatched) {
            if (isEndTimeMatched) {
                // if(isClassFeeMatched) {
                System.out.println("In add");
                ClassesDTO classes = new ClassesDTO(lblClassCode.getText(), txtGrade.getText(), String.valueOf(cmbSubject.getValue()), cmbTutorId.getValue(), String.valueOf(cmbDay.getValue()), txtStartTime.getText(), txtEndTime.getText(), String.valueOf(cmbHallNo.getValue()), Double.parseDouble(txtClassFee.getText()), String.valueOf(LocalDate.now()));

                HallReservationDTO hallReservationDTO = new HallReservationDTO(lblHallReservationNo.getText(), String.valueOf(cmbHallNo.getValue()), lblClassCode.getText(), String.valueOf(cmbDay.getValue()), txtStartTime.getText(), txtEndTime.getText());

                try {
                    boolean isAdd = classesServices.add(classes, hallReservationDTO);
                    System.out.println("final is add class" + isAdd);
                    if (isAdd) {
                        Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Class Added Successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                        if (choose.get() == ButtonType.OK) {
                            try {
                                Navigation.navigate(Routes.ADDCLASS, context);
                            } catch (IOException e) {
                                new Alert(Alert.AlertType.ERROR, e + "").show();
                            }
                        }

                    }
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e + "").show();
                }

//                }else{
//                    lblInvalidClassFee.setVisible(true);
//                    txtClassFee.requestFocus();
//                }
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
    void cmbDayOnAction(ActionEvent event) {

    }

    @FXML
    void cmbHallNoOnAction(ActionEvent event) {

    }

    @FXML
    void cmbTutorIdOnAction(ActionEvent event) {
        try {
            //Tutor tutor = TutorModel.getTutorDetails(cmbTutorId.getValue());
            TutorDTO tutorDTO = tutorService.searchById(cmbTutorId.getValue());
            txtTutorName.setText(tutorDTO.getName());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    @FXML
    void txtClassFeeOnAction(ActionEvent event) {

    }

    @FXML
    void txtEndTimeOnAction(ActionEvent event) {

    }

    @FXML
    void txtStartTimeOnAction(ActionEvent event) {

    }

    @FXML
    void txtTutorNameOnAction(ActionEvent event) {

    }


    public void txtGradeOnAction(ActionEvent actionEvent) {

    }

    public void txtSubjectOnAction(ActionEvent actionEvent) {

    }

    public void cmbSubjectOnAction(ActionEvent actionEvent) {
    }
}
