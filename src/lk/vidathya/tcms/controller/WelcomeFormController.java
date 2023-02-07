package lk.vidathya.tcms.controller;

import animatefx.animation.FadeInDown;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeFormController {

    public AnchorPane welcomeContext;
    public JFXButton btnClick;

    public void btnClickOnAction(ActionEvent actionEvent) throws IOException {
        //Navigation.navigate(Routes.LOGIN, welcomeContext);
        welcomeContext.getChildren().clear();
        Stage window = (Stage) welcomeContext.getScene().getWindow();

        new FadeInDown(welcomeContext).play();
        welcomeContext.getChildren().add(FXMLLoader.load(this.getClass().getResource("/lk/vidathya/tcms/view/LoginForm.fxml")));

    }
}
