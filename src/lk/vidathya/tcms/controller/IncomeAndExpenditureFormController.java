package lk.vidathya.tcms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.vidathya.tcms.dto.IncomeAndExpenditureDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.IncomeAndExpenditureService;
import lk.vidathya.tcms.tableModel.IncomeExpenditureTM;
import lk.vidathya.tcms.util.LoginCredentials;
import lk.vidathya.tcms.util.MonthName;
import lk.vidathya.tcms.util.Navigation;
import lk.vidathya.tcms.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class IncomeAndExpenditureFormController implements Initializable {

    public AnchorPane context;
    public Button btnBack;
    public ComboBox cmbMonth;
    public TextField txtYear;
    public IncomeAndExpenditureService incomeAndExpenditureService;
    @FXML
    private TableView<IncomeExpenditureTM> tblIncome;
    @FXML
    private TableColumn<?, ?> colIncomeDate;
    @FXML
    private TableColumn<?, ?> colIncomeDescription;
    @FXML
    private TableColumn<?, ?> colIncomeAmount;
    @FXML
    private TableView<IncomeExpenditureTM> tblExpenditure;
    @FXML
    private TableColumn<?, ?> colExpenditureDate;
    @FXML
    private TableColumn<?, ?> colExpenditureDescription;
    @FXML
    private TableColumn<?, ?> colExpenditureAmount;
    @FXML
    private TextField txtIncomeTotal;
    @FXML
    private TextField txtExpenditureTotal;
    @FXML
    private TextField txtNetProfit;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtDescription;
    @FXML
    private DatePicker dteDate;
    @FXML
    private ComboBox<String> cmbIncomeExpenditure;
    @FXML
    private Button btnAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        incomeAndExpenditureService = ServiceFactory.getInstance().getService(ServiceType.INCOME_AND_EXPENDITURE_SERVICE_IMPL);
        loadIncomeAndExpenditureToComboBox();
        dteDate.setPromptText(String.valueOf(LocalDate.now()));
        loadMonths();
        String monthName = MonthName.getMonthName(String.valueOf(LocalDate.now()));


        colIncomeDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colIncomeDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colIncomeAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        colExpenditureDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colExpenditureDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colExpenditureAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        int year = Integer.parseInt(String.valueOf(LocalDate.now()).split("-")[0]);
        loadBudgetData(year, monthName);

    }

    private void loadMonths() {
        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        cmbMonth.setItems(months);
    }

    private void loadIncomeAndExpenditureToComboBox() {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("Income");
        options.add("Expenditure");
        cmbIncomeExpenditure.setItems(options);
    }

    public void loadBudgetData(int year, String month) {
        ObservableList<IncomeExpenditureTM> incomeData = FXCollections.observableArrayList();
        try {
            List list = incomeAndExpenditureService.getIncomeDataTable(year, month);
            for (int i = 0; i < list.size(); i++) {
                IncomeAndExpenditureDTO incomeAndExpenditureDTO = (IncomeAndExpenditureDTO) list.get(i);
                incomeData.add(new IncomeExpenditureTM(incomeAndExpenditureDTO.getDate(), incomeAndExpenditureDTO.getDescription(), incomeAndExpenditureDTO.getAmount()));
            }

            tblIncome.setItems(incomeData);
            calculateTotal(incomeData, txtIncomeTotal);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

        ObservableList<IncomeExpenditureTM> expenditureData = FXCollections.observableArrayList();
        try {
            if (month == null) {
                month = cmbMonth.getPromptText();
            }

            List list = incomeAndExpenditureService.getExpenditureDataTable(year, month);
            for (int i = 0; i < list.size(); i++) {
                IncomeAndExpenditureDTO incomeAndExpenditureDTO = (IncomeAndExpenditureDTO) list.get(i);
                expenditureData.add(new IncomeExpenditureTM(incomeAndExpenditureDTO.getDate(), incomeAndExpenditureDTO.getDescription(), incomeAndExpenditureDTO.getAmount()));
            }

            tblExpenditure.setItems(expenditureData);
            calculateTotal(expenditureData, txtExpenditureTotal);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

        double netProfit = Double.parseDouble(txtIncomeTotal.getText()) - Double.parseDouble(txtExpenditureTotal.getText());
        txtNetProfit.setText(String.valueOf(netProfit));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String date = String.valueOf(dteDate.getValue());
        String month = MonthName.getMonthName(date);
        int year = Integer.parseInt(date.split("-")[0]);

        IncomeAndExpenditureDTO incomeAndExpenditureDTO = new IncomeAndExpenditureDTO(LoginCredentials.getCurrentUser(), String.valueOf(cmbIncomeExpenditure.getValue()), txtDescription.getText(), Double.parseDouble(txtAmount.getText()), year, month, date);

        try {
            boolean isAdd = incomeAndExpenditureService.add(incomeAndExpenditureDTO);
            if (isAdd) {
                cmbMonth.setPromptText("");
                txtDescription.setText("");
                dteDate.setPromptText("");
                txtAmount.setText("");
                cmbIncomeExpenditure.setPromptText("");

            } else {
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong.").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }


    private void calculateTotal(ObservableList<IncomeExpenditureTM> data, TextField txt) {
        double total = 0;
        for (IncomeExpenditureTM i : data) {
            total += i.getAmount();
        }
        txt.setText(String.valueOf(total));
    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.DASHBOARDVIEW, context);
    }

    @FXML
    void cmbIncomeExpenditureOnAction(ActionEvent event) {

    }

    @FXML
    void dteDateOnAction(ActionEvent event) {

    }

    @FXML
    void txtAmountOnAction(ActionEvent event) {

    }

    @FXML
    void txtDescriptionOnAction(ActionEvent event) {

    }

    @FXML
    void txtExpenditureTotalOnAction(ActionEvent event) {

    }

    @FXML
    void txtIncomeTotalOnAction(ActionEvent event) {

    }

    @FXML
    void txtNetProfitOnAction(ActionEvent event) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.FINANCEMANAGE, context);
    }


    public void cmbMonthOnAction(ActionEvent actionEvent) {

    }

    public void txtYearOnAction(ActionEvent actionEvent) {
    }
}
