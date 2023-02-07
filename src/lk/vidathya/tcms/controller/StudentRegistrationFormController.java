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
import javafx.stage.Window;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.GuardianDTO;
import lk.vidathya.tcms.dto.StudentDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.GuardianService;
import lk.vidathya.tcms.service.custom.StudentService;
import lk.vidathya.tcms.util.*;

import javax.mail.MessagingException;
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

public class StudentRegistrationFormController extends Window implements Initializable {

    public AnchorPane context;

    public CheckBox checkBoxPaid;
    public ImageView studentImage;
    public Button btnBrowseImage;
    public StudentService studentService;
    public ClassesServices classesServices;
    public GuardianService guardianService;
    @FXML
    private Button btnRegister;
    @FXML
    private Label lblStudentId;
    @FXML
    private Label lblDate;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblInvalidStudentName;
    @FXML
    private Label lblInvalidStudentNic;
    @FXML
    private Label lblInvalidStudentContactNo;
    @FXML
    private Label lblInvalidStudentEmail;
    @FXML
    private Label lblInvalidStudentDob;
    @FXML
    private Label lblInvalidGuardianName;
    @FXML
    private Label lblInvalidGuardianNic;
    @FXML
    private Label lblInvalidGuardianContactNo;
    @FXML
    private Label lblInvalidGuardianEmail;
    @FXML
    private Label lblRegistrationFee;
    @FXML
    private ComboBox<String> cmbStudentGender;
    @FXML
    private TextField txtStudentName;
    @FXML
    private TextField txtStudentAddress;
    @FXML
    private TextField txtStudentNic;
    @FXML
    private TextField txtStudentContactNo;
    @FXML
    private TextField txtStudentEmail;
    @FXML
    private TextField txtStudentDob;
    @FXML
    private TextField txtStudentAge;
    @FXML
    private ComboBox<String> cmbGrade;
    @FXML
    private TextField txtGuardianName;
    @FXML
    private TextField txtGuardianNic;
    @FXML
    private TextField txtGuardianContactNo;
    @FXML
    private TextField txtGuardianEmail;
    @FXML
    private TextField txtOccupation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        guardianService = ServiceFactory.getInstance().getService(ServiceType.GUARDIAN_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);

        setStudentId();
        loadGrade();
        loadGenderOptions();

        lblDate.setText(String.valueOf(LocalDate.now()));
        lblRegistrationFee.setText(String.valueOf(InstituteData.getRegistrationFee()));

        setLabelVisibility();
    }

    private void loadGenderOptions() {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("Male");
        options.add("Female");
        cmbStudentGender.setItems(options);
    }

    private void loadGrade() {
        try {
            ObservableList<String> grade = (ObservableList<String>) classesServices.loadGradeToComboBox();
            cmbGrade.setItems(grade);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLabelVisibility() {
        lblInvalidStudentName.setVisible(false);
        lblInvalidStudentNic.setVisible(false);
        lblInvalidStudentContactNo.setVisible(false);
        lblInvalidStudentEmail.setVisible(false);
        lblInvalidStudentDob.setVisible(false);

        lblInvalidGuardianName.setVisible(false);
        lblInvalidGuardianNic.setVisible(false);
        lblInvalidGuardianContactNo.setVisible(false);
        lblInvalidGuardianEmail.setVisible(false);
    }


    private void setStudentId() {
        try {
            lblStudentId.setText(studentService.getNewId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws RuntimeException {
        setLabelVisibility();

        boolean isStudentNameMatched = RegExPatterns.getNamePattern().matcher(txtStudentName.getText()).matches();
        boolean isStudentNicMatched = RegExPatterns.getIdPattern().matcher(txtStudentNic.getText()).matches();
        boolean isStudentOldNicMatched = RegExPatterns.getOldIDPattern().matcher(txtStudentNic.getText()).matches();
        boolean isStudentContactNoMatched = RegExPatterns.getMobilePattern().matcher(txtStudentContactNo.getText()).matches();
        boolean isStudentEmailMatched = RegExPatterns.getEmailPattern().matcher(txtStudentEmail.getText()).matches();
        boolean isStudentDobMatched = RegExPatterns.getDobPattern().matcher(txtStudentDob.getText()).matches();

        boolean isGuardianNameMatched = RegExPatterns.getNamePattern().matcher(txtGuardianName.getText()).matches();
        boolean isGuardianNicMatched = RegExPatterns.getIdPattern().matcher(txtGuardianNic.getText()).matches();
        boolean isGuardianOldNicMatched = RegExPatterns.getOldIDPattern().matcher(txtGuardianNic.getText()).matches();
        boolean isGuardianContactNoMatched = RegExPatterns.getMobilePattern().matcher(txtGuardianContactNo.getText()).matches();
        boolean isGuardianEmailMatched = RegExPatterns.getEmailPattern().matcher(txtGuardianEmail.getText()).matches();

        if (isStudentNameMatched) {
            if (isStudentNicMatched || isStudentOldNicMatched) {
                if (isStudentContactNoMatched) {
                    if (isStudentEmailMatched) {
                        if (isStudentDobMatched) {
                            if (isGuardianNameMatched) {
                                if (isGuardianNicMatched || isGuardianOldNicMatched) {
                                    if (isGuardianContactNoMatched) {
                                        if (isGuardianEmailMatched) {


                                            if (checkBoxPaid.isSelected()) {
                                                StudentDTO studentDTO = new StudentDTO(
                                                        lblStudentId.getText(),
                                                        txtStudentName.getText(),
                                                        txtStudentNic.getText(),
                                                        String.valueOf(cmbStudentGender.getValue()),
                                                        txtStudentAddress.getText(),
                                                        txtStudentContactNo.getText(),
                                                        txtStudentEmail.getText(),
                                                        txtStudentDob.getText(),
                                                        Integer.parseInt(txtStudentAge.getText()),
                                                        String.valueOf(cmbGrade.getValue()),
                                                        lblDate.getText(),
                                                        txtGuardianNic.getText()
                                                );

                                                GuardianDTO guardianDTO = new GuardianDTO(
                                                        txtGuardianNic.getText(),
                                                        txtGuardianName.getText(),
                                                        txtGuardianContactNo.getText(),
                                                        txtGuardianEmail.getText(),
                                                        txtOccupation.getText()
                                                );

                                                try {
                                                    boolean isAdd = studentService.add(studentDTO, guardianDTO);
                                                    if (isAdd) {
                                                        InstituteData.setStudentCount();
                                                        Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Student Registration Successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();

                                                        String guardianEmail = txtGuardianEmail.getText();
                                                        String studentEmail = txtStudentEmail.getText();

                                                        String subject = "Registration of Vidathya Higher Education Centre is successful!";
                                                        String text = "Dear " + txtStudentName.getText() + ",\nWelcome To Vidathya Higher Education Centre.\n\nYour Registration No is  : " + lblStudentId.getText() + " \n\nPlease note that : \n(01) Fees must be paid before 15th day of each month.\n(02) Classes will not run on public holidays. \n\n    Please Reply to this email by attaching your photo.\n\n\n    Thank You!\n\n\nContact us :\nEmail : " + InstituteData.getEmail() + "\nTel : " + InstituteData.getContactNo() + "\nWhatsApp : " + InstituteData.getWhatsAppContactNo();

                                                        if (guardianEmail != null) {
                                                            Mail.sendMail(guardianEmail, subject, text);
                                                        }
                                                        if (studentEmail != null) {
                                                            Mail.sendMail(studentEmail, subject, text);
                                                        }

                                                        if (choose.get() == ButtonType.OK) {
                                                            Navigation.navigate(Routes.ADDSTUDENTTOCLASSES, context);
                                                        }
                                                    }

                                                } catch (SQLException | ClassNotFoundException | IOException e) {
                                                    new Alert(Alert.AlertType.ERROR, e + "").show();
                                                } catch (MessagingException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            } else {
                                                new Alert(Alert.AlertType.WARNING, "Verify that the Registration fees have been paid.").show();
                                            }


                                        } else {
                                            lblInvalidGuardianEmail.setVisible(true);
                                            txtGuardianEmail.requestFocus();
                                        }
                                    } else {
                                        lblInvalidGuardianContactNo.setVisible(true);
                                        txtGuardianContactNo.requestFocus();
                                    }
                                } else {
                                    lblInvalidGuardianNic.setVisible(true);
                                    txtGuardianNic.requestFocus();
                                }
                            } else {
                                lblInvalidGuardianName.setVisible(true);
                                txtGuardianName.requestFocus();
                            }
                        } else {
                            lblInvalidStudentDob.setVisible(true);
                            txtStudentDob.requestFocus();
                        }
                    } else {
                        lblInvalidStudentEmail.setVisible(true);
                        txtStudentEmail.requestFocus();
                    }
                } else {
                    lblInvalidStudentContactNo.setVisible(true);
                    txtStudentContactNo.requestFocus();
                }
            } else {
                lblInvalidStudentNic.setVisible(true);
                txtStudentNic.requestFocus();
            }
        } else {
            lblInvalidStudentName.setVisible(true);
            txtStudentName.requestFocus();
        }

    }


    @FXML
    void cmbGradeOnAction(ActionEvent event) {

    }

    @FXML
    void cmbStudentGenderOnAction(ActionEvent event) {

    }

    @FXML
    void txtGuardianContactNoOnAction(ActionEvent event) {

    }

    @FXML
    void txtGuardianEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtGuardianNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtGuardianNicOnAction(ActionEvent event) {
        try {
            GuardianDTO guardianDTO = guardianService.searchById(txtGuardianNic.getText());
            if (guardianDTO != null) {
                txtGuardianName.setText(guardianDTO.getName());
                txtGuardianContactNo.setText(guardianDTO.getContactNo());
                txtGuardianEmail.setText(guardianDTO.getEmail());
                txtOccupation.setText(guardianDTO.getOccupation());
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void txtOccupationOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentAddressOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentAgeOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentContactNoOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentDobOnAction(ActionEvent event) {
        String date = String.valueOf(LocalDate.now());
        int nowYear = Integer.parseInt(date.split("-")[0]);

        String dob = txtStudentDob.getText();
        int birthYear = Integer.parseInt(dob.split("-")[0]);

        int age = nowYear - birthYear;
        txtStudentAge.setText(String.valueOf(age));

    }

    @FXML
    void txtStudentEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentNicOnAction(ActionEvent event) {

    }

    public void checkBoxPaidOnAction(ActionEvent actionEvent) {

    }


    public void btnBrowseImageOnAction(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("..\\StudentImages"));
        try {

            boolean isExist = studentService.isExistImage(lblStudentId.getText());
            if (!isExist) {
                String sql = "INSERT INTO StudentImage VALUES (?, ?)";
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

                File file = fileChooser.showOpenDialog(new Stage());

                if (file != null) {
                    FileInputStream fis = new FileInputStream(file);
                    pstm.setString(1, lblStudentId.getText());
                    pstm.setBinaryStream(2, fis, fis.available());
                    int x = pstm.executeUpdate();

                    if (x > 0) {
                        JOptionPane.showMessageDialog(null, "Image SuccessFully Added!");

                        ResultSet resultSet = studentService.getImage(lblStudentId.getText());
                        if (resultSet.next()) {
                            Image img = new Image(resultSet.getBinaryStream("image"));
                            studentImage.setImage(img);
                            studentImage.setPreserveRatio(false);
                        }
                    }

                }

            } else {
                String sql = "UPDATE StudentImage SET image=? WHERE studentId=? ";
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

                fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(this.getScene().getWindow());

                if (file != null) {
                    FileInputStream fis = new FileInputStream(file);
                    pstm.setString(2, lblStudentId.getText());
                    pstm.setBinaryStream(1, fis, fis.available());
                    int x = pstm.executeUpdate();

                    if (x > 0) {
                        JOptionPane.showMessageDialog(null, "Image Added SuccessFully!");

                        ResultSet resultSet = studentService.getImage(lblStudentId.getText());
                        if (resultSet.next()) {
                            Image img = new Image(resultSet.getBinaryStream("image"));
                            studentImage.setImage(img);
                            studentImage.setPreserveRatio(false);
                        }
                    }

                }
            }

        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
