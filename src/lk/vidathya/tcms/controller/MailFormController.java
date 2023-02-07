package lk.vidathya.tcms.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.GuardianDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.*;
import lk.vidathya.tcms.util.Mail;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MailFormController {

    public JFXTextField txtRecipient;
    public JFXTextField txtSubject;
    public TextArea txtText;


    public AnchorPane pane;
    public ComboBox cmbGroupRecipient;
    public AnchorPane context;
    public Button btnSend;
    public Button btnDiscard;
    public ComboBox cmbClassCode;
    public Label lblClassCode;
    public TextField txtGrade;
    public TextField txtClassSubject;
    public ClassesServices classesServices;
    public StaffService staffService;
    public GuardianService guardianService;
    public TutorService tutorService;
    public StudentService studentService;
    public StudentClassService studentClassService;
    private Pattern emailPattern;

    public void initialize() {
        studentClassService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_CLASS_SERVICE_IMPL);
        studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENTS_SERVICE_IMPL);
        tutorService = ServiceFactory.getInstance().getService(ServiceType.TUTOR_SERVICE_IMPL);
        staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);
        guardianService = ServiceFactory.getInstance().getService(ServiceType.GUARDIAN_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        emailPattern = Pattern.compile("^([a-z|0-9]{3,})[@]([a-z]{2,})\\.(com|lk)$");
        loadRecipientGroup();

        loadClassCode();
        cmbClassCode.setDisable(true);
    }

    private void loadRecipientGroup() {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("To Staff");
        options.add("To Tutors");
        options.add("To Students");
        options.add("To Parents");
        cmbGroupRecipient.setItems(options);
    }

    private void loadClassCode() {
        try {
            ObservableList<String> classCode = (ObservableList<String>) classesServices.loadIdToComboBox();
            cmbClassCode.setItems(classCode);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSendOnAction(ActionEvent actionEvent) {

        if (txtRecipient.getText() != null) {
            boolean isEmailMatched = emailPattern.matcher(txtRecipient.getText()).matches();
            if (isEmailMatched) {
                try {
                    Mail.sendMail(txtRecipient.getText(), txtSubject.getText(), txtText.getText());
                    new Alert(Alert.AlertType.INFORMATION, "Mail send successfully!").show();
                } catch (MessagingException e) {
                    new Alert(Alert.AlertType.WARNING, "" + e).show();
                }
            }
        }

        if (cmbGroupRecipient.getValue() != null) {

            String group = (String) cmbGroupRecipient.getValue();
            switch (group) {


                case "To Staff":
                    try {
                        ArrayList<String> mailAddress = (ArrayList<String>) staffService.getStaffEmailAddress();
                        if (mailAddress != null) {
                            for (String recipientAddress : mailAddress) {
                                if (recipientAddress != null) {
                                    sendMail(recipientAddress);
                                }
                            }
                        }
                        new Alert(Alert.AlertType.INFORMATION, "Mail send successfully!").show();
                    } catch (SQLException | ClassNotFoundException | MessagingException e) {
                        new Alert(Alert.AlertType.ERROR, e + "").show();
                    }
                    break;


                case "To Tutors":
                    try {
                        ArrayList<String> mailAddress = (ArrayList<String>) tutorService.getTutorEmailAddress();
                        if (mailAddress != null) {
                            for (String recipientAddress : mailAddress) {
                                if (recipientAddress != null) {
                                    sendMail(recipientAddress);
                                }
                            }
                        }
                        new Alert(Alert.AlertType.INFORMATION, "Mail send successfully!").show();
                    } catch (SQLException | ClassNotFoundException | MessagingException e) {
                        new Alert(Alert.AlertType.ERROR, e + "").show();
                    }
                    break;


                case "To Students":

                    if (cmbClassCode.getValue() == null) {
                        try {
                            ArrayList<String> allMails = (ArrayList<String>) studentService.getAllEmailAddress();
                            for (String mailAddress : allMails) {
                                if (mailAddress != null) {
                                    sendMail(mailAddress);
                                }

                            }
                            new Alert(Alert.AlertType.INFORMATION, "Mail send successfully!").show();
                        } catch (SQLException | ClassNotFoundException | MessagingException e) {
                            new Alert(Alert.AlertType.ERROR, e + "").show();
                        }


                    } else {
                        String classCode = String.valueOf(cmbClassCode.getValue());
                        try {
                            ArrayList<String> studentIdArray = (ArrayList<String>) studentClassService.getStudentIds(classCode);
                            for (String studentId : studentIdArray) {
                                String studentMail = studentService.getStudentMailAddress(studentId);
                                if (studentMail != null) {
                                    sendMail(studentMail);
                                }
                            }
                            new Alert(Alert.AlertType.INFORMATION, "Mail send successfully!").show();
                        } catch (SQLException | ClassNotFoundException | MessagingException e) {
                            new Alert(Alert.AlertType.ERROR, e + "").show();
                        }
                    }


                    break;


                case "To Parents":

                    if (cmbClassCode.getValue() == null) {

                        try {
                            ArrayList<String> allMails = (ArrayList<String>) guardianService.getAllEmailAddress();
                            for (String mailAddress : allMails) {
                                if (mailAddress != null) {
                                    sendMail(mailAddress);
                                }

                            }
                            new Alert(Alert.AlertType.INFORMATION, "Mail send successfully!").show();
                        } catch (SQLException | ClassNotFoundException | MessagingException e) {
                            new Alert(Alert.AlertType.ERROR, e + "").show();
                        }

                    } else {
                        String classCodes = String.valueOf(cmbClassCode.getValue());

                        try {
                            ArrayList<String> guardianNicArray = (ArrayList<String>) studentClassService.getGuardianNic(classCodes);

                            for (String guardianNic : guardianNicArray) {
                                GuardianDTO guardianDTO = guardianService.searchById(guardianNic);
                                String guardianMail = guardianDTO.getEmail();
                                if (guardianMail != null) {
                                    sendMail(guardianMail);
                                }
                            }
                            new Alert(Alert.AlertType.INFORMATION, "Mail send successfully!").show();
                        } catch (SQLException | ClassNotFoundException | MessagingException e) {
                            new Alert(Alert.AlertType.ERROR, e + "").show();
                        }
                    }


                    break;

            }
        }

        if (txtRecipient.getText() == null && cmbGroupRecipient.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please Enter Recipient!").show();
        }

    }

    public void sendMail(String recipientAddress) throws MessagingException {
        Mail.sendMail(recipientAddress, txtSubject.getText(), txtText.getText());
    }

    public void cmbGroupRecipientOnAction(ActionEvent actionEvent) {
        if (cmbGroupRecipient.getValue().equals("To Students") || cmbGroupRecipient.getValue().equals("To Parents")) {
            cmbClassCode.setDisable(false);

        } else {
            cmbClassCode.setDisable(true);
            cmbClassCode.setPromptText("");
        }
    }

    public void txtSubjectOnAction(ActionEvent actionEvent) {
        txtText.setFocusTraversable(true);
    }

    public void btnDiscardOnAction(ActionEvent actionEvent) throws IOException {
        new Alert(Alert.AlertType.WARNING, "Discard Message.").show();
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }

    public void cmbClassCodeOnAction(ActionEvent actionEvent) {

        try {
            ClassesDTO classesDTO = classesServices.searchById(cmbClassCode.getValue());
            txtGrade.setText(classesDTO.getGrade());
            txtClassSubject.setText(classesDTO.getSubject());

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

    }
}
