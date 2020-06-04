package ba.unsa.etf.nrs.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderItemController implements Initializable {
    public ChoiceBox<Product> productChoiceBox;
    public TextField quantityTextField;
    private OrderItem orderItem;
    private ObservableList<Product> products = FXCollections.observableArrayList(new ArrayList<>());

    OrderItemController(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getProducts();
        if (orderItem != null) {
            productChoiceBox.getSelectionModel().select(orderItem.getProduct());
            quantityTextField.setText(String.valueOf(orderItem.getQuantity()));
        } else {
            productChoiceBox.getSelectionModel().selectFirst();
            quantityTextField.setText(String.valueOf(1));
        }
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void cancel() {
        orderItem = null;
        Stage stage = (Stage) quantityTextField.getScene().getWindow();
        stage.close();
    }

    public void confirm() {
        if (orderItem == null) orderItem = new OrderItem();
        orderItem.setProduct(productChoiceBox.getValue());
        try {
            orderItem.setQuantity(Integer.parseInt(quantityTextField.getText()));
        } catch (Exception e) {
            orderItem.setQuantity(1);
        }
        Stage stage = (Stage) quantityTextField.getScene().getWindow();
        stage.close();
    }

    public void getProducts() {
        new Thread(() -> {
            try {
                RestService restService = new RestService();
                this.products = FXCollections.observableArrayList(restService.getProducts());
                Platform.runLater(() -> {
                    productChoiceBox.setItems(products);
                    productChoiceBox.getSelectionModel().selectFirst();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
