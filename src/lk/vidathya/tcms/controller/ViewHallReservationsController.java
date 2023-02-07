package lk.vidathya.tcms.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.ExtraClassDTO;
import lk.vidathya.tcms.dto.ExtraClassHallReservationDTO;
import lk.vidathya.tcms.dto.HallReservationDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.ExtraClassHallReservationService;
import lk.vidathya.tcms.service.custom.ExtraClassService;
import lk.vidathya.tcms.service.custom.HallReservationService;
import lk.vidathya.tcms.tableModel.EClassHallReservationTM;
import lk.vidathya.tcms.tableModel.HallReservationTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewHallReservationsController implements Initializable {

    public ClassesServices classesServices;
    public ExtraClassService extraClassService;
    public ExtraClassHallReservationService extraClassHallReservationService;
    public HallReservationService hallReservationService;
    @FXML
    private AnchorPane context;
    @FXML
    private TableView<EClassHallReservationTM> tblExtraClassHallReservation;
    @FXML
    private TableColumn<?, ?> colEClassHallNo;
    @FXML
    private TableColumn<?, ?> colEClassCode;
    @FXML
    private TableColumn<?, ?> colEClassGrade;
    @FXML
    private TableColumn<?, ?> colEClassSubject;
    @FXML
    private TableColumn<?, ?> colEClassDate;
    @FXML
    private TableColumn<?, ?> colEClassStartTime;
    @FXML
    private TableColumn<?, ?> colEClassEndTime;
    @FXML
    private TableView<HallReservationTM> tblHallReservation;
    @FXML
    private TableColumn<?, ?> colHallNo;
    @FXML
    private TableColumn<?, ?> colClassCode;
    @FXML
    private TableColumn<?, ?> colGrade;
    @FXML
    private TableColumn<?, ?> colSubject;
    @FXML
    private TableColumn<?, ?> colDay;
    @FXML
    private TableColumn<?, ?> colStartTime;
    @FXML
    private TableColumn<?, ?> colEndTime;
    @FXML
    private Button btnOk;

    @FXML
    void btnOkOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEWINFOCONTEXT, context);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hallReservationService = ServiceFactory.getInstance().getService(ServiceType.HALL_RESERVATION_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        extraClassService = ServiceFactory.getInstance().getService(ServiceType.EXTRA_CLASS_SERVICE_IMPL);
        extraClassHallReservationService = ServiceFactory.getInstance().getService(ServiceType.EXTRA_CLASS_HALL_RESERVATION_SERVICE_IMPL);


        colHallNo.setCellValueFactory(new PropertyValueFactory<>("hallNo"));
        colClassCode.setCellValueFactory(new PropertyValueFactory<>("classCode"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        colEClassHallNo.setCellValueFactory(new PropertyValueFactory<>("extraClassHallNo"));
        colEClassCode.setCellValueFactory(new PropertyValueFactory<>("extraClassCode"));
        colEClassGrade.setCellValueFactory(new PropertyValueFactory<>("extraClassGrade"));
        colEClassSubject.setCellValueFactory(new PropertyValueFactory<>("extraClassSubject"));
        colEClassDate.setCellValueFactory(new PropertyValueFactory<>("extraClassDate"));
        colEClassStartTime.setCellValueFactory(new PropertyValueFactory<>("extraClassStartTime"));
        colEClassEndTime.setCellValueFactory(new PropertyValueFactory<>("extraClassEndTime"));

        loadDataToExtraClassHallReservation();
        loadDataToHallReservation();

    }

    private void loadDataToExtraClassHallReservation() {
        ObservableList<EClassHallReservationTM> extraClassHallData = FXCollections.observableArrayList();
        try {
            List list = extraClassHallReservationService.getDataToViewTable();
            for (int i = 0; i < list.size(); i++) {
                ExtraClassHallReservationDTO extraClassHallReservationDTO = (ExtraClassHallReservationDTO) list.get(i);
                ExtraClassDTO extraClassDTO = extraClassService.searchById(extraClassHallReservationDTO.geteClassCode());
                ClassesDTO classesDTO = classesServices.searchById(extraClassDTO.getClassCode());

                extraClassHallData.add(
                        new EClassHallReservationTM(
                                extraClassHallReservationDTO.getHallNo(),
                                extraClassHallReservationDTO.geteClassCode(),
                                classesDTO.getGrade(),
                                classesDTO.getSubject(),
                                extraClassHallReservationDTO.getDate(),
                                extraClassHallReservationDTO.getStartTime(),
                                extraClassHallReservationDTO.getEndTime()
                        ));
            }

            tblExtraClassHallReservation.setItems(extraClassHallData);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    private void loadDataToHallReservation() {
        ObservableList<HallReservationTM> data = FXCollections.observableArrayList();
        try {
            List list = hallReservationService.getDataToViewTable();
            for (int i = 0; i < list.size(); i++) {
                HallReservationDTO hallReservationDTO = (HallReservationDTO) list.get(i);
                ClassesDTO classesDTO = classesServices.searchById(hallReservationDTO.getClassCode());

                data.add(
                        new HallReservationTM(
                                hallReservationDTO.getHallNo(),
                                hallReservationDTO.getClassCode(),
                                classesDTO.getGrade(),
                                classesDTO.getSubject(),
                                hallReservationDTO.getDay(),
                                hallReservationDTO.getStartTime(),
                                hallReservationDTO.getEndTime()
                        ));
            }

            tblHallReservation.setItems(data);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }
}
