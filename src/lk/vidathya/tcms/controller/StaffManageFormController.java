package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.StaffDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.StaffService;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.RegExPatterns;
import lk.vidathya.tcms.util.Routes;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class  StaffManageFormController implements Initializable {

    public AnchorPane context;
    public Button btnBack;
    public ImageView staffImage;
    public Button btnBrowseImage;

    @FXML
    private Label lblDate;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lblInvalidStaffName;

    @FXML
    private Label lblInvalidStaffNic;

    @FXML
    private Label lblInvalidStaffContactNo;

    @FXML
    private Label lblInvalidStaffEmail;

    @FXML
    private Label lblInvalidStaffDob;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblInvalidStaffSalary;

    @FXML
    private ComboBox<String> cmbStaffId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtJob;

    @FXML
    private TextField txtNic;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtDob;

    @FXML
    private TextField txtSalary;

    public StaffService staffService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffService= ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);
        lblDate.setText(String.valueOf(LocalDate.now()));

        loadStaffId();
        loadGenderOptions();

        setLabelVisibility();
    }

    public void loadGenderOptions() {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("Male");
        options.add("Female");
        cmbGender.setItems(options);
    }

    private void loadStaffId(){
        try {
            ObservableList<String> staffIds = (ObservableList<String>) staffService.loadIdsToComboBox();
            cmbStaffId.setItems(staffIds);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLabelVisibility(){
        lblInvalidStaffName.setVisible(false);
        lblInvalidStaffNic.setVisible(false);
        lblInvalidStaffContactNo.setVisible(false);
        lblInvalidStaffEmail.setVisible(false);
        lblInvalidStaffDob.setVisible(false);
        lblInvalidStaffSalary.setVisible(false);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        setLabelVisibility();

        boolean isNameMatched = RegExPatterns.getNamePattern().matcher(txtName.getText()).matches();
        boolean isNicMatched = RegExPatterns.getIdPattern().matcher(txtNic.getText()).matches();
        boolean isOldNicMatched = RegExPatterns.getOldIDPattern().matcher(txtNic.getText()).matches();
        boolean isContactNoMatched = RegExPatterns.getMobilePattern().matcher(txtContactNo.getText()).matches();
        boolean isEmailMatched = RegExPatterns.getEmailPattern().matcher(txtEmail.getText()).matches();
        boolean isDobMatched = RegExPatterns.getDobPattern().matcher(txtDob.getText()).matches();
        boolean isSalaryMatched = RegExPatterns.getSalaryPattern().matcher(txtSalary.getText()).matches();

        if(isNameMatched){
            if(isNicMatched || isOldNicMatched){
                if(isContactNoMatched){
                    if(isEmailMatched){
                        if(isDobMatched){
                            if(isSalaryMatched){


                                String gender = null;
                                if(cmbGender.getValue()==null){
                                    gender=cmbGender.getPromptText();
                                }else{
                                    gender=String.valueOf(cmbGender.getValue());
                                }

                                StaffDTO staffDTO = new StaffDTO(
                                        String.valueOf(cmbStaffId.getValue()),
                                        txtName.getText(),
                                        txtJob.getText(),
                                        txtNic.getText(),
                                        gender,
                                        txtAddress.getText(),
                                        txtContactNo.getText(),
                                        txtEmail.getText(),
                                        txtDob.getText(),
                                        Double.parseDouble(txtSalary.getText()),
                                        String.valueOf(LocalDate.now())
                                );

                                try {

                                    boolean isUpdate =  staffService.update(staffDTO);
                                    if(isUpdate){

                                        Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION,"Staff Member Updated Successfully!",ButtonType.OK,ButtonType.CANCEL).showAndWait();
                                        if(choose.get()==ButtonType.OK){
                                            try {
                                                Navigation.navigate(Routes.STAFFMANAGE,context);
                                            } catch (IOException e) {
                                                new Alert(Alert.AlertType.ERROR,e+"").show();
                                            }
                                        }

                                    }


                                } catch (SQLException | ClassNotFoundException e) {
                                    new Alert(Alert.AlertType.ERROR,e+"").show();
                                }


                            }else{
                                lblInvalidStaffSalary.setVisible(true);
                                txtSalary.requestFocus();
                            }
                        }else{
                            lblInvalidStaffDob.setVisible(true);
                            txtDob.requestFocus();
                        }
                    }else{
                        lblInvalidStaffEmail.setVisible(true);
                        txtEmail.requestFocus();
                    }
                }else{
                    lblInvalidStaffContactNo.setVisible(true);
                    txtContactNo.requestFocus();
                }
            }else{
                lblInvalidStaffNic.setVisible(true);
                txtNic.requestFocus();
            }
        }else{
            lblInvalidStaffName.setVisible(true);
            txtName.requestFocus();
        }

    }

    @FXML
    void cmbGenderOnAction(ActionEvent event) {

    }

    @FXML
    void cmbStaffIdOnAction(ActionEvent event) {
        try {
            StaffDTO staffDTO = staffService.searchById(String.valueOf(cmbStaffId.getValue()));

            txtName.setText(staffDTO.getName());
            txtJob.setText(staffDTO.getJob());
            txtNic.setText(staffDTO.getNic());
            txtAddress.setText(staffDTO.getAddress());
            txtContactNo.setText(staffDTO.getContactNo());
            txtEmail.setText(staffDTO.getEmail());
            txtDob.setText(staffDTO.getDob());
            txtSalary.setText(String.valueOf(staffDTO.getSalary()));
            cmbGender.setPromptText(staffDTO.getGender());

            ResultSet resultSet = staffService.getImage(String.valueOf(cmbStaffId.getValue()));
            if(resultSet.next()){
                Image img = new Image(resultSet.getBinaryStream("image"));
                staffImage.setImage(img);
                staffImage.setPreserveRatio(true);
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e+"").show();
        }
    }


    @FXML
    void txtAddressOnAction(ActionEvent event) {

    }

    @FXML
    void txtContactNoOnAction(ActionEvent event) {

    }

    @FXML
    void txtDobOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtJobOnAction(ActionEvent event) {

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtNicOnAction(ActionEvent event) {

    }

    @FXML
    void txtSalaryOnAction(ActionEvent event) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW,context);
    }

    public void btnBrowseImageOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("..\\StaffImages"));
        try {

            boolean isExist = staffService.isExistImage(String.valueOf(cmbStaffId.getValue()));
            if(!isExist){
                String sql = "INSERT INTO StaffImage VALUES (?, ?)";
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

                File file = fileChooser.showOpenDialog(new Stage());

                if(file!=null){
                    FileInputStream fis = new FileInputStream(file);
                    pstm.setString(1,String.valueOf(cmbStaffId.getValue()));
                    pstm.setBinaryStream(2,fis,fis.available());
                    int x = pstm.executeUpdate();

                    if(x>0){
                        JOptionPane.showMessageDialog(null, "Image SuccessFully Added!");

                        ResultSet resultSet = staffService.getImage(String.valueOf(cmbStaffId.getValue()));
                        if(resultSet.next()){
                            Image img = new Image(resultSet.getBinaryStream("image"));
                            staffImage.setImage(img);
                            staffImage.setPreserveRatio(true);
                        }
                    }

                }

            }else{
                String sql = "UPDATE StaffImage SET image=? WHERE staffId=? ";
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

                fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(new Stage());

                if(file!=null){
                    FileInputStream fis = new FileInputStream(file);
                    pstm.setString(2,String.valueOf(cmbStaffId.getValue()));
                    pstm.setBinaryStream(1,fis,fis.available());
                    int x = pstm.executeUpdate();

                    if(x>0){
                        JOptionPane.showMessageDialog(null, "Image Added SuccessFully!");

                        ResultSet resultSet = staffService.getImage(String.valueOf(cmbStaffId.getValue()));
                        if(resultSet.next()){
                            Image img = new Image(resultSet.getBinaryStream("image"));
                            staffImage.setImage(img);
                            staffImage.setPreserveRatio(false);
                        }
                    }

                }
            }

        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
