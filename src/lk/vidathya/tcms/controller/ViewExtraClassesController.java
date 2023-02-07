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
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.ExtraClassService;
import lk.vidathya.tcms.service.custom.TutorService;
import lk.vidathya.tcms.tableModel.ExtraClassTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewExtraClassesController implements Initializable {

    public AnchorPane context;
    public ExtraClassService extraClassService;
    public ClassesServices classesServices;
    public TutorService tutorService;
    @FXML
    private TableView<ExtraClassTM> tblEClass;
    @FXML
    private TableColumn<?, ?> colEClassCode;
    @FXML
    private TableColumn<?, ?> colClassCode;
    @FXML
    private TableColumn<?, ?> colSubject;
    @FXML
    private TableColumn<?, ?> colGrade;
    @FXML
    private TableColumn<?, ?> colTutorName;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private Button btnOk;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        extraClassService = ServiceFactory.getInstance().getService(ServiceType.EXTRA_CLASS_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);

        colEClassCode.setCellValueFactory(new PropertyValueFactory<>("eCode"));
        colClassCode.setCellValueFactory(new PropertyValueFactory<>("classCode"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colTutorName.setCellValueFactory(new PropertyValueFactory<>("tutorName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadTableData();
    }

    private void loadTableData() {
        ObservableList<ExtraClassTM> data = FXCollections.observableArrayList();
        try {
            List list = extraClassService.getAll();
            for (int i = 0; i < list.size(); i++) {
                ExtraClassDTO extraClassDTO = (ExtraClassDTO) list.get(i);
                ClassesDTO classesDTO = classesServices.searchById(extraClassDTO.getClassCode());
                TutorDTO tutorDTO = tutorService.searchById(classesDTO.getTutorId());

                data.add(
                        new ExtraClassTM(
                                extraClassDTO.geteClassCode(),
                                extraClassDTO.getClassCode(),
                                classesDTO.getSubject(),
                                classesDTO.getGrade(),
                                tutorDTO.getName(),
                                extraClassDTO.getDate()
                        ));

            }
            tblEClass.setItems(data);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEWINFOCONTEXT, context);
    }


}
