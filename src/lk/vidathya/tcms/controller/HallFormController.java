package lk.vidathya.tcms.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.HallDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.HallService;
import lk.vidathya.tcms.util.InstituteData;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.RegExPatterns;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class HallFormController implements Initializable {

    public AnchorPane context;
    public Button btnBack;
    public Label lblEnterHallNo;
    public HallService hallService;
    @FXML
    private Label lblDate;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblInvalidCapacity;
    @FXML
    private Button btnAddHall;
    @FXML
    private Button btnAddHallOption;
    @FXML
    private Button btnManageHallOption;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField txtHallNo;
    @FXML
    private TextField txtCapacity;
    @FXML
    private TextField txtAvailableFacilities;
    @FXML
    private ComboBox<String> cmbEnterHallNo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddHall.setVisible(false);
        btnCancel.setVisible(false);
        btnUpdate.setVisible(false);

        lblEnterHallNo.setVisible(false);
        cmbEnterHallNo.setVisible(false);

        lblDate.setText(String.valueOf(LocalDate.now()));

        lblInvalidCapacity.setVisible(false);

        btnAddHallOption.setFocusTraversable(false);
        btnManageHallOption.setFocusTraversable(false);

        hallService = ServiceFactory.getInstance().getService(ServiceType.HALL_SERVICE_IMPL);
    }

    private void setHallNo() {
        try {
            txtHallNo.setText(hallService.getNewId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddHallOptionOnAction(ActionEvent event) {

        cmbEnterHallNo.setPromptText(null);
        txtHallNo.setText(null);
        txtCapacity.setText(null);
        txtAvailableFacilities.setText(null);

        setHallNo();

        btnAddHall.setVisible(true);
        btnCancel.setVisible(true);

        btnUpdate.setVisible(false);
    }

    @FXML
    void btnManageHallOptionOnAction(ActionEvent event) {
        cmbEnterHallNo.setPromptText(null);
        txtHallNo.setText(null);
        txtCapacity.setText(null);
        txtAvailableFacilities.setText(null);

        btnUpdate.setVisible(true);

        btnAddHall.setVisible(false);
        btnCancel.setVisible(false);

        lblEnterHallNo.setVisible(true);
        cmbEnterHallNo.setVisible(true);

        txtHallNo.setText(null);

        loadHallNo();
        lblDate.setText(String.valueOf(LocalDate.now()));

    }

    private void loadHallNo() {
        try {
            ObservableList<String> hallNo = (ObservableList<String>) hallService.loadIdsToComboBox();
            cmbEnterHallNo.setItems(hallNo);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnAddHallOnAction(ActionEvent event) {

        lblInvalidCapacity.setVisible(false);
        boolean isCapacityMatched = RegExPatterns.getCapacityPattern().matcher(txtCapacity.getText()).matches();

        if (isCapacityMatched) {

            HallDTO hallDTO = new HallDTO(txtHallNo.getText(), Integer.parseInt(txtCapacity.getText()), txtAvailableFacilities.getText());

            try {
                boolean isAdd = hallService.add(hallDTO);
                if (isAdd) {

                    Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Hall Added Successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                    if (choose.get() == ButtonType.OK) {
                        try {
                            Navigation.navigate(Routes.HALL, context);
                        } catch (IOException e) {
                            new Alert(Alert.AlertType.ERROR, e + "").show();
                        }
                    }

                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e + "").show();
            }

            InstituteData.setHallCount();
        } else {
            lblInvalidCapacity.setVisible(true);
            txtCapacity.requestFocus();
        }


    }

    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        lblInvalidCapacity.setVisible(false);
        boolean isCapacityMatched = RegExPatterns.getCapacityPattern().matcher(txtCapacity.getText()).matches();

        if (isCapacityMatched) {

            HallDTO hallDTO = new HallDTO(String.valueOf(cmbEnterHallNo.getValue()), Integer.parseInt(txtCapacity.getText()), txtAvailableFacilities.getText());

            try {
                boolean isUpdate = hallService.update(hallDTO);
                if (isUpdate) {

                    Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Hall Updated Successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                    if (choose.get() == ButtonType.OK) {
                        try {
                            Navigation.navigate(Routes.HALL, context);
                        } catch (IOException e) {
                            new Alert(Alert.AlertType.ERROR, e + "").show();
                        }
                    }

                }

            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e + "").show();
            }
        } else {
            lblInvalidCapacity.setVisible(true);
            txtCapacity.requestFocus();
        }

    }

    @FXML
    void cmbEnterHallNoOnAction(ActionEvent event) {
        txtHallNo.setText(cmbEnterHallNo.getValue());
        try {
            HallDTO hallDTO = hallService.searchById(String.valueOf(cmbEnterHallNo.getValue()));
            if (hallDTO != null) {
                txtCapacity.setText(String.valueOf(hallDTO.getCapacity()));
                txtAvailableFacilities.setText(hallDTO.getAvailableFacilities());
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

    }

    @FXML
    void txtAvailableFacilitiesOnAction(ActionEvent event) {

    }

    @FXML
    void txtCapacityOnAction(ActionEvent event) {
        txtAvailableFacilities.requestFocus();
    }

    @FXML
    void txtHallNoOnAction(ActionEvent event) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }


}
