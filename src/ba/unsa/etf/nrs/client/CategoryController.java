package ba.unsa.etf.nrs.client;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {
    public TextField nameTextField;
    public TextArea descriptionTextArea;
    Category category;

    public CategoryController(Category category) {
        this.category = category;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (category != null) {
            nameTextField.setText(category.getName());
            descriptionTextArea.setText(category.getDescription());
        }
    }

    public Category getCategory() {
        return category;
    }

    public void cancel() {
        category = null;
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    public void confirm() {
        if (category == null) category = new Category();
        category.setName(nameTextField.getText());
        category.setDescription(descriptionTextArea.getText());
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }
}
