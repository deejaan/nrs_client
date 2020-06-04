package ba.unsa.etf.nrs.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    public TextField nameTextField;
    public TextField priceTextField;
    public ChoiceBox<Category> categoryChoiceBox;
    public TextArea descriptionTextArea;
    public ObservableList<Category> categories = FXCollections.observableArrayList(new ArrayList<>());
    private Product product;

    ProductController(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCategories();
        if (product != null) {
            nameTextField.setText(product.getName());
            priceTextField.setText(String.valueOf(product.getPrice()));
            descriptionTextArea.setText(product.getDescription());
        }
    }

    public Product getProduct() {
        return product;
    }

    private int findId() {
        int id = 0;
        try {
            for (Category category : categories) {
                if (category.getId() == product.getCategory().getId()) id = categories.indexOf(category);
            }
        } catch (Exception ignored) {
        }
        return id;
    }

    public void cancel() {
        product = null;
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    public void confirm() {
        if (nameTextField.getText().isEmpty()) return;
        if (descriptionTextArea.getText().isEmpty()) return;
        if (product == null) product = new Product();
        product.setName(nameTextField.getText());
        product.setDescription(descriptionTextArea.getText());
        try {
            product.setPrice(Double.parseDouble(priceTextField.getText()));
        } catch (Exception e) {
            product.setPrice(0);
            return;
        }
        product.setCategory(categoryChoiceBox.getValue());
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    public void getCategories() {
        new Thread(() -> {
            try {
                RestService restService = new RestService();
                this.categories = FXCollections.observableArrayList(restService.getCategories());
                Platform.runLater(() -> {
                    categoryChoiceBox.setItems(categories);
                    if (product != null) categoryChoiceBox.getSelectionModel().select(findId());
                    else categoryChoiceBox.getSelectionModel().selectFirst();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
