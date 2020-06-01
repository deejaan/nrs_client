package ba.unsa.etf.nrs.client;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ChoiceBox<String> filterOrdersChoiceBox;
    public Button orderDetailsButton;
    public Button addOrderButton;
    public Button editOrderButton;
    public Button deleteOrderButton;
    public Button completeOrderButton;
    public TableView<Order> ordersTable;
    public TableColumn<Order, Integer> orderNumberColumn;
    public TableColumn<Order, Integer> orderIdColumn;
    public TableColumn<Order, LocalDateTime> orderDateColumn;
    public TableColumn<Order, User> orderUserColumn;
    public TableColumn<Order, Coupon> orderCouponColumn;
    public TableColumn<Order, Boolean> orderStatusColumn;
    public ChoiceBox<Category> filterProductsChoiceBox;
    public Button addProductButton;
    public Button editProductButton;
    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> productNumberColumn;
    public TableColumn<Product, Integer> productIdColumn;
    public TableColumn<Product, String> productNameColumn;
    public TableColumn<Product, String> productDescriptionColumn;
    public TableColumn<Product, Category> productCategoryColumn;
    public TableColumn<Product, Double> productPriceColumn;
    public Button editCategoryButton;
    public Button deleteCategoryButton;
    public Button addCategoryButton;
    public ChoiceBox<String> filterUsersChoiceBox;
    public Button addUserButton;
    public Button editUserButton;
    public Button deleteUserButton;
    public TableView<User> usersTable;
    public TableColumn<User, Integer> userNumberColumn;
    public TableColumn<User, Integer> userIdColumn;
    public TableColumn<User, String> userFirstNameColumn;
    public TableColumn<User, String> userLastNameColumn;
    public TableColumn<User, String> userEmailColumn;
    public TableColumn<User, String> userUsernameColumn;
    public TableColumn<User, String> userAddressColumn;
    public TableColumn<User, Integer> userRoleColumn;
    public ListView<Category> categoriesListView;
    public Button generateUserReportButton;
    public Button generateProductReportButton;
    public Button generateOrderReportButton;
    public CheckBox employeeCheckBox;
    public CheckBox clientCheckBox;
    public CheckBox managerCheckBox;
    public RadioButton salesYesRadio;
    public RadioButton salesNoRadio;
    public RadioButton ordersYesRadio;
    public RadioButton ordersNoRadio;
    public ObservableList<User> users = FXCollections.observableArrayList(new ArrayList<>());
    public ObservableList<Category> categories = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Product> products = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Order> orders = FXCollections.observableArrayList(new ArrayList<>());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productCategoryColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCategory()));
        productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        productDescriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        userNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        userFirstNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));
        userLastNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
        userEmailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        userUsernameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        userAddressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));

        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderCouponColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCoupon()));
        orderUserColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUser()));
        orderDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrderDate()));
        orderStatusColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().isCompleted()));

        getUsers();
        getCategories();
        getProducts();
        getOrders();
    }

    public void getUsers() {
        new Thread(() -> {
            try {
                RestService restService = new RestService();
                this.users = FXCollections.observableArrayList(restService.getUsers());
                Platform.runLater(() -> {
                    usersTable.setItems(users);
                    usersTable.refresh();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void getCategories() {
        new Thread(() -> {
            try {
                RestService restService = new RestService();
                this.categories = FXCollections.observableArrayList(restService.getCategories());
                Platform.runLater(() -> {
                    categoriesListView.setItems(categories);
                    categoriesListView.refresh();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void getProducts() {
        new Thread(() -> {
            try {
                RestService restService = new RestService();
                this.products = FXCollections.observableArrayList(restService.getProducts());
                Platform.runLater(() -> {
                    productsTable.setItems(products);
                    productsTable.refresh();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void getOrders() {
        new Thread(() -> {
            try {
                RestService restService = new RestService();
                this.orders = FXCollections.observableArrayList(restService.getOrders());
                Platform.runLater(() -> {
                    ordersTable.setItems(orders);
                    ordersTable.refresh();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
