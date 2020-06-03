package ba.unsa.etf.nrs.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    public ListView<OrderItem> orderItemsListView;
    public Label dateLabel;
    public Label totalLabel;
    public ChoiceBox<User> userChoiceBox;
    public ChoiceBox<Coupon> couponChoiceBox;
    public ChoiceBox<Status> statusChoiceBox;
    public Order order;
    public ObservableList<User> users = FXCollections.observableArrayList(new ArrayList<>());

    OrderController(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUsers();
        ObservableList<Status> status = FXCollections.observableArrayList(Status.values());
        statusChoiceBox.setItems(status);
        if (order != null) {
            dateLabel.setText(String.valueOf(order.getOrderDate()));
            userChoiceBox.getSelectionModel().select(order.getUser());
            couponChoiceBox.getSelectionModel().select(order.getCoupon());
            statusChoiceBox.getSelectionModel().select(order.getStatus());
            double total = 0;
            for (OrderItem orderItem : order.getOrderItems()) {
                total += (orderItem.getQuantity() * orderItem.getProduct().getPrice());
            }
            orderItemsListView.setItems(FXCollections.observableArrayList(order.getOrderItems()));
            totalLabel.setText(String.valueOf(total));
        } else {
            dateLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
            userChoiceBox.getSelectionModel().selectFirst();
            couponChoiceBox.getSelectionModel().selectFirst();
            statusChoiceBox.getSelectionModel().selectFirst();
            totalLabel.setText(String.valueOf(0));
        }


    }

    public void cancel() {
        order = null;
        Stage stage = (Stage) dateLabel.getScene().getWindow();
        stage.close();
    }

    public void confirm() {
        if (order == null) order = new Order();
        order.setStatus(statusChoiceBox.getValue());
        order.setUser(userChoiceBox.getValue());
        order.setCoupon(couponChoiceBox.getValue());
        order.setOrderDate(LocalDateTime.parse(dateLabel.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
        order.setOrderItems(new ArrayList<>(orderItemsListView.getItems()));
        if (orderItemsListView.getItems().size() == 0) return;
        Stage stage = (Stage) dateLabel.getScene().getWindow();
        stage.close();
    }

    public void addItem() {
    }

    public void editItem() {
    }

    public void deleteItem() {
    }

    public void getUsers() {
        new Thread(() -> {
            try {
                RestService restService = new RestService();
                this.users = FXCollections.observableArrayList(restService.getUsers());
                Platform.runLater(() -> {
                    userChoiceBox.setItems(users);
                    userChoiceBox.getSelectionModel().selectFirst();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
