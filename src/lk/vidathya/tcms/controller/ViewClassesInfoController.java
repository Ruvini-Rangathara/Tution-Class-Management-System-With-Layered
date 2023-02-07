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
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.TutorService;
import lk.vidathya.tcms.tableModel.ClassesInfoTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewClassesInfoController implements Initializable {

    public AnchorPane context;
    public ClassesServices classesServices;
    public TutorService tutorService;
    @FXML
    private TableView<ClassesInfoTM> tblClass;
    @FXML
    private TableColumn<?, ?> colClassCode;
    @FXML
    private TableColumn<?, ?> colSubject;
    @FXML
    private TableColumn<?, ?> colGrade;
    @FXML
    private TableColumn<?, ?> colTutorName;
    @FXML
    private TableColumn<?, ?> colDay;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);

        colClassCode.setCellValueFactory(new PropertyValueFactory<>("classCode"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colTutorName.setCellValueFactory(new PropertyValueFactory<>("tutorName"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("day"));

        loadClassesDetails();
    }

    private void loadClassesDetails() {
        ObservableList<ClassesInfoTM> data = FXCollections.observableArrayList();
        try {
            int count = 0;
            List list = classesServices.getAll();
            for (int i = 0; i < list.size(); i++) {
                count++;
                ClassesDTO classesDTO = (ClassesDTO) list.get(i);
                TutorDTO tutorDTO = tutorService.searchById(classesDTO.getTutorId());
                data.add(
                        new ClassesInfoTM(
                                classesDTO.getClassCode(),
                                classesDTO.getSubject(),
                                classesDTO.getGrade(),
                                tutorDTO.getName(),
                                classesDTO.getDay()
                        ));
            }

            tblClass.setItems(data);
            txtCount.setText(String.valueOf(count));
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEWINFOCONTEXT, context);
    }

    @FXML
    void txtCountOnAction(ActionEvent event) {

    }


}
