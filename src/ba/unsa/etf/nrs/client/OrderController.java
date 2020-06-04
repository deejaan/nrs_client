package ba.unsa.etf.nrs.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
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
    private ObservableList<Coupon> coupons = FXCollections.observableArrayList(new ArrayList<>());

    OrderController(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUsers();
        getCoupons();
        ObservableList<Status> status = FXCollections.observableArrayList(Status.values());
        statusChoiceBox.setItems(status);
        if (order != null) {
            dateLabel.setText(String.valueOf(order.getOrderDate()));
            userChoiceBox.getSelectionModel().select(findUserId());
            couponChoiceBox.getSelectionModel().select(findCouponId());
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


    private int findUserId() {
        int id = 0;
        try {
            for (User user : users) {
                if (user.getId() == order.getUser().getId()) id = users.indexOf(user);
            }
        } catch (Exception ignored) {
        }
        return id;
    }

    private int findCouponId() {
        int id = 0;
        try {
            for (Coupon coupon : coupons) {
                if (coupon.getId() == order.getCoupon().getId()) id = coupons.indexOf(coupon);
            }
        } catch (Exception ignored) {
        }
        return id;
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
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item.fxml"));
            OrderItemController orderItemController = new OrderItemController(null);
            loader.setController(orderItemController);
            root = loader.load();
            stage.setTitle("Category");
            stage.setScene(new Scene(root, 250, 200));
            stage.setMinWidth(250);
            stage.setMaxWidth(250);
            stage.setMinHeight(200);
            stage.setMaxHeight(200);
            stage.initOwner(
                    (statusChoiceBox.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                OrderItem orderItem = orderItemController.getOrderItem();
                if (orderItem != null) {
                    orderItemsListView.getItems().add(orderItem);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editItem() {
        OrderItem selectedOrderItem = orderItemsListView.getSelectionModel().getSelectedItem();
        if (selectedOrderItem == null) return;
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item.fxml"));
            OrderItemController orderItemController = new OrderItemController(selectedOrderItem);
            loader.setController(orderItemController);
            root = loader.load();
            stage.setTitle("Category");
            stage.setScene(new Scene(root, 250, 200));
            stage.setMinWidth(250);
            stage.setMaxWidth(250);
            stage.setMinHeight(200);
            stage.setMaxHeight(200);
            stage.initOwner(
                    (statusChoiceBox.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                OrderItem orderItem = orderItemController.getOrderItem();
                if (orderItem != null) {
                    orderItemsListView.getSelectionModel().getSelectedItem().setQuantity(orderItem.getQuantity());
                    orderItemsListView.getSelectionModel().getSelectedItem().setProduct(orderItem.getProduct());
                    orderItemsListView.refresh();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem() {
        OrderItem selectedOrderItem = orderItemsListView.getSelectionModel().getSelectedItem();
        if (selectedOrderItem == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Deleting order item \"" + selectedOrderItem.toString());
        alert.setContentText("Are you sure you want to delete order item \"" + selectedOrderItem.toString() + "\" ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            orderItemsListView.getItems().remove(selectedOrderItem);
        }
    }

    public void getUsers() {
        new Thread(() -> {
            try {
                RestService restService = new RestService();
                this.users = FXCollections.observableArrayList(restService.getUsers());
                Platform.runLater(() -> {
                    userChoiceBox.setItems(users);
                    if (order != null) userChoiceBox.getSelectionModel().select(findUserId());
                    else userChoiceBox.getSelectionModel().selectFirst();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void getCoupons() {
        new Thread(() -> {
            try {
                RestService restService = new RestService();
                this.coupons = FXCollections.observableArrayList(restService.getCoupons());
                Platform.runLater(() -> {
                    couponChoiceBox.setItems(coupons);
                    if (order != null) couponChoiceBox.getSelectionModel().select(findCouponId());
                    else couponChoiceBox.getSelectionModel().selectFirst();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
