package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.TutorDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.TutorService;
import lk.vidathya.tcms.tableModel.TutorInfoTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewTutorsInfoController implements Initializable {

    public AnchorPane context;
    public TutorService tutorService;
    @FXML
    private TableView<TutorInfoTM> tblTutors;
    @FXML
    private TableColumn<?, ?> colTutorId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colSubject;
    @FXML
    private TableColumn<?, ?> colContactNo;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colViewMore;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);

        colTutorId.setCellValueFactory(new PropertyValueFactory<>("tutorId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadTableData();
    }

    private void loadTableData() {
        ObservableList<TutorInfoTM> data = FXCollections.observableArrayList();
        try {
            int count = 0;
            List list = tutorService.getAll();
            for (int i = 0; i < list.size(); i++) {
                TutorDTO tutorDTO = (TutorDTO) list.get(i);
                count++;
                data.add(
                        new TutorInfoTM(
                                tutorDTO.getTutorId(),
                                tutorDTO.getName(),
                                tutorDTO.getSubject(),
                                tutorDTO.getContactNo(),
                                tutorDTO.getEmail()
                        ));

            }

            tblTutors.setItems(data);
            txtCount.setText(String.valueOf(count));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
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
