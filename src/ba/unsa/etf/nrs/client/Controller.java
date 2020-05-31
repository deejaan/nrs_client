package ba.unsa.etf.nrs.client;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        User user1 = new User(1, 1, "First Name 1", "Last Name 1", "Username 1", "Email 1", "Address 1");
//        User user2 = new User(2, 1, "First Name 2", "Last Name 2", "Username 2", "Email 2", "Address 2");
//        User user3 = new User(3, 2, "First Name 3", "Last Name 3", "Username 3", "Email 3", "Address 3");
//        ArrayList<User> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//        ObservableList<User> userObs = FXCollections.observableArrayList(users);
//        Category category1 = new Category(1, "Category 1", "Description 1");
//        Category category2 = new Category(2, "Category 2", "Description 2");
//        Category category3 = new Category(3, "Category 3", "Description 3");
//        ArrayList<Category> categories = new ArrayList<>();
//        categories.add(category1);
//        categories.add(category2);
//        categories.add(category3);
//        ObservableList<Category> categoryObs = FXCollections.observableArrayList(categories);
//        categoriesListView.setItems(categoryObs);
//
//        filterProductsChoiceBox.setItems(categoryObs);
//
//        filterProductsChoiceBox.getSelectionModel().select(0);
//
//        Product product1 = new Product(1, category1, "Product 1", "Description 1", 10.0);
//        Product product2 = new Product(1, category2, "Product 2", "Description 2", 20.0);
//        Product product3 = new Product(1, category3, "Product 3", "Description 3", 30.0);
//
//        ArrayList<Product> products = new ArrayList<>();
//        products.add(product1);
//        products.add(product2);
//        products.add(product3);
//
//        ObservableList<Product> productObs = FXCollections.observableArrayList(products);
//
//        productsTable.setItems(productObs);
//        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        productNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        productCategoryColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCategory()));
//        productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
//        productDescriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
//        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        usersTable.setItems(userObs);
//        userNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
//        userFirstNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));
//        userLastNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
//        userEmailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
//        userUsernameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
//        userAddressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));
//
//
//        OrderItem orderItem1 = new OrderItem(1, 1, product1);
//        OrderItem orderItem2 = new OrderItem(2, 2, product2);
//        OrderItem orderItem3 = new OrderItem(3, 3, product3);
//
//        Coupon coupon1 = new Coupon(1, 10, "Coupon 1", LocalDateTime.now(), false);
//        Coupon coupon2 = new Coupon(2, 20, "Coupon 2", LocalDateTime.now(), false);
//        Coupon coupon3 = new Coupon(3, 30, "Coupon 3", LocalDateTime.now(), true);
//
//        ArrayList<OrderItem> orderItems = new ArrayList<>();
//        orderItems.add(orderItem1);
//        orderItems.add(orderItem2);
//        orderItems.add(orderItem3);
//
//        Order order1 = new Order(1, orderItems, user1, coupon1, LocalDateTime.now(), false);
//        Order order2 = new Order(2, orderItems, user2, coupon2, LocalDateTime.now(), false);
//        Order order3 = new Order(3, orderItems, user3, coupon3, LocalDateTime.now(), true);
//
//
//        ArrayList<Order> orders = new ArrayList<>();
//        orders.add(order1);
//        orders.add(order2);
//        orders.add(order3);
//        ObservableList<Order> ordersObs = FXCollections.observableArrayList(orders);
//        ordersTable.setItems(ordersObs);
//        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        orderCouponColumn.setCellValueFactory(data-> new SimpleObjectProperty<>(data.getValue().getCoupon()));
//        orderUserColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUser()));
//        orderDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrderDate()));
//        orderStatusColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().isCompleted()));

    }
}
