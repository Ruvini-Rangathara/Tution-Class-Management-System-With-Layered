package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.StaffDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.StaffService;
import lk.vidathya.tcms.tableModel.StaffInfoTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewStaffInfoController implements Initializable {

    public AnchorPane context;
    public StaffService staffService;
    @FXML
    private TableView<StaffInfoTM> tblStaffInfo;
    @FXML
    private TableColumn<?, ?> colStaffId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colJob;
    @FXML
    private TableColumn<?, ?> colNicNo;
    @FXML
    private TableColumn<?, ?> colContactNo;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);

        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("job"));
        colNicNo.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadTableData();
    }

    private void loadTableData() {
        ObservableList<StaffInfoTM> data = FXCollections.observableArrayList();
        try {
            List list = staffService.getAll();
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                StaffDTO staffDTO = (StaffDTO) list.get(i);
                count++;
                data.add(
                        new StaffInfoTM(
                                staffDTO.getStaffId(),
                                staffDTO.getName(),
                                staffDTO.getJob(),
                                staffDTO.getNic(),
                                staffDTO.getContactNo(),
                                staffDTO.getEmail()
                        ));
            }

            tblStaffInfo.setItems(data);
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
