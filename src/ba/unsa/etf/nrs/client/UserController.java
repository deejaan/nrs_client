package ba.unsa.etf.nrs.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField usernameTextField;
    public TextField emailTextField;
    public TextField addressTextField;
    public PasswordField passwordField;
    public ChoiceBox<Role> roleChoiceBox;
    private User user;

    UserController(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Role> roles = FXCollections.observableArrayList(Role.values());
        roleChoiceBox.setItems(roles);
        roleChoiceBox.getSelectionModel().selectFirst();

        if (user != null) {
            firstNameTextField.setText(user.getFirstName());
            lastNameTextField.setText(user.getLastName());
            addressTextField.setText(user.getAddress());
            emailTextField.setText(user.getEmail());
            usernameTextField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            roleChoiceBox.getSelectionModel().select(user.getRole());
        }
    }

    public void cancel() {
        user = null;
        Stage stage = (Stage) firstNameTextField.getScene().getWindow();
        stage.close();
    }

    public void confirm() {
        if (firstNameTextField.getText().isEmpty()) return;
        if (lastNameTextField.getText().isEmpty()) return;
        if (usernameTextField.getText().isEmpty()) return;
        if (emailTextField.getText().isEmpty()) return;
        if (addressTextField.getText().isEmpty()) return;
        if (passwordField.getText().isEmpty()) return;
        if (user == null) user = new User();
        user.setFirstName(firstNameTextField.getText());
        user.setLastName(lastNameTextField.getText());
        user.setUsername(usernameTextField.getText());
        user.setEmail(emailTextField.getText());
        user.setPassword(passwordField.getText());
        user.setAddress(addressTextField.getText());
        user.setRole(roleChoiceBox.getValue());

        Stage stage = (Stage) firstNameTextField.getScene().getWindow();
        stage.close();
    }


}
