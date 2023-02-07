package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.HallDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.HallService;
import lk.vidathya.tcms.tableModel.HallTM;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewHallInfoController implements Initializable {

    public AnchorPane context;
    public HallService hallService;
    @FXML
    private TableView<HallTM> tblHall;
    @FXML
    private TableColumn<?, ?> colHallNo;
    @FXML
    private TableColumn<?, ?> colCapacity;
    @FXML
    private TableColumn<?, ?> colAvailableFacilities;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hallService = ServiceFactory.getInstance().getService(ServiceType.HALL_SERVICE_IMPL);

        colHallNo.setCellValueFactory(new PropertyValueFactory<>("hallNo"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colAvailableFacilities.setCellValueFactory(new PropertyValueFactory<>("availableFacilities"));

        loadHallData();
        txtCount.setText(String.valueOf(getHallCount()));
    }

    private void loadHallData() {
        ObservableList<HallTM> data = FXCollections.observableArrayList();
        try {
            List list = hallService.getAll();
            for (int i = 0; i < list.size(); i++) {
                HallDTO hallDTO = (HallDTO) list.get(i);

                data.add(new HallTM(hallDTO.getHallNo(), hallDTO.getCapacity(), hallDTO.getAvailableFacilities()));
            }

            tblHall.setItems(data);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    private int getHallCount() {
        try {
            return hallService.getAllCount();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

        return 0;
    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEWINFOCONTEXT, context);
    }

    @FXML
    void txtCountOnAction(ActionEvent event) {

    }


}
