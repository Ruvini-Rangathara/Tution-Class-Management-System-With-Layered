package lk.vidathya.tcms.util;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import lk.vidathya.tcms.dto.StaffDTO;
import lk.vidathya.tcms.dto.UserDTO;
import lk.vidathya.tcms.service.ServiceFactory;
import lk.vidathya.tcms.service.ServiceType;
import lk.vidathya.tcms.service.custom.StaffService;
import lk.vidathya.tcms.service.custom.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginCredentials implements Initializable {

    public static UserService userService = ServiceFactory.getInstance().getService(ServiceType.USER_SERVICE_IMPL);
    public static StaffService staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);
    private static String name = "Sachini Nayanathara";
    private static String currentUser = "E0002";

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String password) {
        try {
            UserDTO userDTO = userService.searchByPassword(password);
            currentUser = userDTO.getStaffId();

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }
    }

    public static String getName() {
        return name;
    }

    public static void setName(String password) {
        try {
            UserDTO userDTO = userService.searchByPassword(password);
            String staffId = userDTO.getStaffId();

            StaffDTO staffDTO = staffService.searchById(staffId);
            LoginCredentials.name = staffDTO.getName();


        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffService = ServiceFactory.getInstance().getService(ServiceType.STAFF_SERVICE_IMPL);
        userService = ServiceFactory.getInstance().getService(ServiceType.USER_SERVICE_IMPL);
    }

}
