package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.ExtraClassDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.custom.ExtraClassService;
import lk.vidathya.tcms.service.custom.StaffService;
import lk.vidathya.tcms.tableModel.ClassScheduleTM;
import lk.vidathya.tcms.tableModel.ExtraClassScheduleTM;
import lk.vidathya.tcms.util.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DashboardAssistantController {

    public Button btnStudentsPayments;

    public Button btnManageClass;

    public Button btnAddNewClass;
    public Button btnExit;
    public AnchorPane dashboardAssistantContext;

    public TableView tblEClassSchedule;

    public TableColumn colEClassSubject;
    public TableColumn colEClassGrade;
    public TableColumn colEClassHall;
    public TableColumn colEClassStartTime;
    public TableColumn colEClassEndTime;
    public TableColumn colHallNo;

    public ImageView assistantImage;
    public Label lblDashboardContactNo;
    public Label lblDashboardEmail;
    public ClassesServices classesServices;
    public StaffService staffService;
    public ExtraClassService extraClassService;
    @FXML
    private Label lblFormName;
    @FXML
    private Label lblUser;
    @FXML
    private Button btnStudents;
    @FXML
    private Button btnStudentAttendance;
    @FXML
    private Button btnStudentsFees;
    @FXML
    private Button btnClass;
    @FXML
    private Button btnExtraClass;
    @FXML
    private Button btnInfo;
    @FXML
    private Button btnSendMails;
    @FXML
    private Button btnRefunds;
    @FXML
    private Button btnIncomeExpenditure;
    @FXML
    private Button btnReports;
    @FXML
    private AnchorPane dashboardContext;
    @FXML
    private Button btnAddNewStudent;
    @FXML
    private Button btnManageStudent;
    @FXML
    private Button btnAddStudentsToClasses;
    @FXML
    private TableView<ClassScheduleTM> tblSchedule;
    @FXML
    private TableColumn<?, ?> colSubject;
    @FXML
    private TableColumn<?, ?> colGrade;
    @FXML
    private TableColumn<?, ?> colStartTime;
    @FXML
    private TableColumn<?, ?> colEndTime;
    @FXML
    private Label lblExit;
    @FXML
    private Button btnAddNewExtraClass;
    @FXML
    private Button btnManageExtraClass;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblDate;

    public void initialize() {
        extraClassService = ServiceFactory.getInstance().getService(ServiceType.EXTRA_CLASS_SERVICE_IMPL);
        classesServices = ServiceFactory.getInstance().getService(ServiceType.CLASS_SERVICE_IMPL);
        staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);

        lblDashboardEmail.setText(InstituteData.getEmail());
        lblDashboardContactNo.setText(InstituteData.getContactNo());

        setUserImage();

        lblUser.setText(LoginCredentials.getName());
        DateTime.localTime(lblTime);

        String date = String.valueOf(LocalDate.now());
        lblDate.setText(DateTime.setDate(date));

        btnAddNewStudent.setVisible(false);
        btnManageStudent.setVisible(false);
        btnAddStudentsToClasses.setVisible(false);

        btnAddNewClass.setVisible(false);
        btnManageClass.setVisible(false);

        btnAddNewExtraClass.setVisible(false);
        btnManageExtraClass.setVisible(false);

        lblExit.setVisible(false);

        dashboardContext.getChildren().clear();
        try {
            dashboardContext.getChildren().add(FXMLLoader.load(getClass().getResource("/DashboardView.fxml")));
        } catch (IOException e) {
            System.out.println(e);
        }
        colEClassSubject.setCellValueFactory(new PropertyValueFactory<>("extraClassSubject"));
        colEClassGrade.setCellValueFactory(new PropertyValueFactory<>("extraClassGrade"));
        colEClassHall.setCellValueFactory(new PropertyValueFactory<>("extraClassHallNo"));
        colEClassStartTime.setCellValueFactory(new PropertyValueFactory<>("extraClassStartTime"));
        colEClassEndTime.setCellValueFactory(new PropertyValueFactory<>("extraClassEndTime"));

        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colHallNo.setCellValueFactory(new PropertyValueFactory<>("hall"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        loadScheduleTableData();

        loadExtraClassScheduleTableData();

    }

    private void setUserImage() {

        try {
            ResultSet resultSet = staffService.getImage(LoginCredentials.getCurrentUser());

            if (resultSet.next()) {
                Image img = new Image(resultSet.getBinaryStream("image"));
                assistantImage.setImage(img);
                assistantImage.setPreserveRatio(false);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadExtraClassScheduleTableData() {

        ObservableList<ExtraClassScheduleTM> extraClassData = FXCollections.observableArrayList();
        String date = String.valueOf(LocalDate.now());
        try {

            List list = extraClassService.getDataToScheduleTable(date);
            for (int i = 0; i < list.size(); i++) {
                ExtraClassDTO extraClassDTO = (ExtraClassDTO) list.get(i);
                ClassesDTO classesDTO = classesServices.searchById(extraClassDTO.getClassCode());
                extraClassData.add(new ExtraClassScheduleTM(
                        classesDTO.getSubject(),
                        classesDTO.getGrade(),
                        extraClassDTO.getHallNo(),
                        extraClassDTO.getStartTime(),
                        extraClassDTO.getEndTime()
                ));
            }
            tblEClassSchedule.setItems(extraClassData);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadScheduleTableData() {

        ObservableList<ClassScheduleTM> data = FXCollections.observableArrayList();

        String date = String.valueOf(LocalDate.now());
        String dayName = DayName.getDayName(date);

        try {
            List list = classesServices.getDataToScheduleTable(dayName);
            for (int i = 0; i < list.size(); i++) {
                ClassesDTO classesDTO = (ClassesDTO) list.get(i);
                data.add(new ClassScheduleTM(
                        classesDTO.getSubject(),
                        classesDTO.getGrade(),
                        classesDTO.getHallNo(),
                        classesDTO.getStartTime(),
                        classesDTO.getEndTime()
                ));
            }
            tblSchedule.setItems(data);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

    }


    @FXML
    void btnAddNewExtraClassOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("Add New Extra Class");
        Navigation.navigate(Routes.ADDEXTRACLASS, dashboardContext);
    }

    @FXML
    void btnAddNewStudentOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("Student Registration");
        Navigation.navigate(Routes.STUDENTREGISTRATION, dashboardContext);
    }

    @FXML
    void btnAddStudentsToClassesOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("Add Students To Classes");
        Navigation.navigate(Routes.ADDSTUDENTTOCLASSES, dashboardContext);
    }

    @FXML
    void btnClassOnAction(ActionEvent event) {
        btnAddNewClass.setVisible(true);
        btnManageClass.setVisible(true);
    }

    @FXML
    void btnExtraClassOnAction(ActionEvent event) {
        btnAddNewExtraClass.setVisible(true);
        btnManageExtraClass.setVisible(true);
    }

    @FXML
    void btnIncomeExpenditureOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("Income & Expenditure");
        Navigation.navigate(Routes.INCOMEANDEXPENDITURE, dashboardContext);
    }

    @FXML
    void btnInfoOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("View Information");
        Navigation.navigate(Routes.VIEWINFORMATION, dashboardContext);
    }

    @FXML
    void btnManageExtraClassOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("Manage Extra Classes");
        Navigation.navigate(Routes.MANAGEEXTRACLASSES, dashboardContext);
    }

    @FXML
    void btnManageStudentOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("Manage Students");
        Navigation.navigate(Routes.STUDENTMANAGE, dashboardContext);
    }

    @FXML
    void btnRefundsOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("Refunds");
        Navigation.navigate(Routes.REFUNDS, dashboardContext);
    }

    @FXML
    void btnReportsOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.REPORTS, dashboardContext);
    }

    @FXML
    void btnSendMailsOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("New Message");
        Navigation.navigate(Routes.MAIL, dashboardContext);
    }

    @FXML
    void btnStudentAttendanceOnAction(ActionEvent event) throws IOException {
        //lblFormName.setText("Students Attendance");
        Navigation.navigate(Routes.ATTENDANCE, dashboardContext);
    }

    @FXML
    void btnStudentsOnAction(ActionEvent event) {
        btnAddNewStudent.setVisible(true);
        btnManageStudent.setVisible(true);
        btnAddStudentsToClasses.setVisible(true);
    }

    public void btnStudentsPaymentsOnAction(ActionEvent actionEvent) throws IOException {
        //lblFormName.setText("Students Payments");
        Navigation.navigate(Routes.PAYMENTS, dashboardContext);
    }

    public void btnAddNewClassOnAction(ActionEvent actionEvent) throws IOException {
        //lblFormName.setText("Add New Class");
        Navigation.navigate(Routes.ADDCLASS, dashboardContext);
    }

    public void btnManageClassOnAction(ActionEvent actionEvent) throws IOException {
        //lblFormName.setText("Manage Classes");
        Navigation.navigate(Routes.MANAGECLASSES, dashboardContext);
    }

    public void btnAddNewStudentOnMouseExited(MouseEvent mouseEvent) {
        btnAddNewStudent.setVisible(false);
        btnManageStudent.setVisible(false);
        btnAddStudentsToClasses.setVisible(false);
    }

    public void btnAddNewStudentOnMouseMoved(MouseEvent mouseEvent) {
        btnAddNewStudent.setVisible(true);
        btnManageStudent.setVisible(true);
        btnAddStudentsToClasses.setVisible(true);
    }

    public void btnManageStudentsOnMouseExited(MouseEvent mouseEvent) {
        btnAddNewStudent.setVisible(false);
        btnManageStudent.setVisible(false);
        btnAddStudentsToClasses.setVisible(false);
    }

    public void btnManageStudentsOnMouseMoved(MouseEvent mouseEvent) {
        btnAddNewStudent.setVisible(true);
        btnManageStudent.setVisible(true);
        btnAddStudentsToClasses.setVisible(true);
    }

    public void btnAddStudentsToClassesOnMouseExited(MouseEvent mouseEvent) {
        btnAddNewStudent.setVisible(false);
        btnManageStudent.setVisible(false);
        btnAddStudentsToClasses.setVisible(false);
    }

    public void btnAddStudentsToClassesOnMouseMoved(MouseEvent mouseEvent) {
        btnAddNewStudent.setVisible(true);
        btnManageStudent.setVisible(true);
        btnAddStudentsToClasses.setVisible(true);
    }


    public void btnAddNewClassOnMouseExited(MouseEvent mouseEvent) {
        btnAddNewClass.setVisible(false);
        btnManageClass.setVisible(false);
    }

    public void btnAddNewClassOnMouseMoved(MouseEvent mouseEvent) {
        btnAddNewClass.setVisible(true);
        btnManageClass.setVisible(true);
    }

    public void btnManageClassOnMouseExited(MouseEvent mouseEvent) {
        btnAddNewClass.setVisible(false);
        btnManageClass.setVisible(false);
    }

    public void btnManageClassOnMouseMoved(MouseEvent mouseEvent) {
        btnAddNewClass.setVisible(true);
        btnManageClass.setVisible(true);
    }


    public void btnAddNewExtraClassOnMouseExited(MouseEvent mouseEvent) {
        btnAddNewExtraClass.setVisible(false);
        btnManageExtraClass.setVisible(false);
    }

    public void btnAddNewExtraClassOnMouseMoved(MouseEvent mouseEvent) {
        btnAddNewExtraClass.setVisible(true);
        btnManageExtraClass.setVisible(true);
    }

    public void btnManageExtraClassesOnMouseExited(MouseEvent mouseEvent) {
        btnAddNewExtraClass.setVisible(false);
        btnManageExtraClass.setVisible(false);
    }

    public void btnManageExtraClassesOnMouseMoved(MouseEvent mouseEvent) {
        btnAddNewExtraClass.setVisible(true);
        btnManageExtraClass.setVisible(true);
    }

    public void btnExitOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN, dashboardAssistantContext);
    }

    public void btnExitOnMouseExited(MouseEvent mouseEvent) {
        lblExit.setVisible(false);
    }

    public void btnExitOnMouseMoved(MouseEvent mouseEvent) {
        lblExit.setVisible(true);
    }
}
