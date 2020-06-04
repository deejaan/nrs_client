package ba.unsa.etf.nrs.client;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public PasswordField passwordTextFieldLogin;
    public TextField usernameTextFieldLogin;
    public Label errorLabelLogin;

    public TextField fnameTextReg;
    public TextField lnameTextReg;
    public TextField usernameTextFieldReg;
    public TextField addressTextFieldRed;
    public TextField emailTextFieldRed;
    public PasswordField passwordTextFieldReg;
    public Label errorLabelReg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void clickLogin(ActionEvent actionEvent) {

    }

    public void clickRegistration(ActionEvent actionEvent) {
    }
}
