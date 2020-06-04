package ba.unsa.etf.nrs.client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public PasswordField passwordLoginTextField;
    public TextField emailLoginTextField;
    public Label loginLabel;
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField usernameTextField;
    public TextField addressTextField;
    public TextField emailTextField;
    public PasswordField passwordTextField;
    public Label registerLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login() {
        new Thread(() -> {
            RestService restService = new RestService();
            User user = restService.login(emailLoginTextField.getText(), passwordLoginTextField.getText());
            Platform.runLater(() -> {
                if (user == null) loginLabel.setText("Username and password not valid!");
                else {
                    Stage s = (Stage) loginLabel.getScene().getWindow();
                    s.hide();
                    loginLabel.setText("");
                    Stage stage = new Stage();
                    Parent root;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
                        Controller controller = new Controller(user);
                        loader.setController(controller);
                        root = loader.load();
                        stage.setTitle("Nrs Client");
                        stage.setScene(new Scene(root));
                        stage.initOwner(
                                (emailLoginTextField.getScene().getWindow()));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();
                        stage.setOnHiding((event) -> {
                            passwordLoginTextField.setText("");
                            s.show();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }).start();
    }

    public void register() {
        User user = new User();

    }
}
