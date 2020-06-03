package ba.unsa.etf.nrs.client;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ChoiceBox<String> filterOrdersChoiceBox;
    public Button orderDetailsButton;
    public Button addOrderButton;
    public Button editOrderButton;
    public Button completeOrderButton;
    public TableView<Order> ordersTable;
    public TableColumn<Order, Integer> orderIdColumn;
    public TableColumn<Order, LocalDateTime> orderDateColumn;
    public TableColumn<Order, User> orderUserColumn;
    public TableColumn<Order, Coupon> orderCouponColumn;
    public TableColumn<Order, String> orderStatusColumn;
    public ChoiceBox<String> filterProductsChoiceBox;
    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> productIdColumn;
    public TableColumn<Product, String> productNameColumn;
    public TableColumn<Product, String> productDescriptionColumn;
    public TableColumn<Product, Category> productCategoryColumn;
    public TableColumn<Product, Double> productPriceColumn;
    public ChoiceBox<String> filterUsersChoiceBox;
    public TableView<User> usersTable;
    public TableColumn<User, Integer> userIdColumn;
    public TableColumn<User, String> userFirstNameColumn;
    public TableColumn<User, String> userLastNameColumn;
    public TableColumn<User, String> userEmailColumn;
    public TableColumn<User, String> userUsernameColumn;
    public TableColumn<User, String> userAddressColumn;
    public TableColumn<User, String> userRoleColumn;
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
    public Tab usersTab;
    public TabPane mainTabPane;
    private ObservableList<Product> products = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Order> orders = FXCollections.observableArrayList(new ArrayList<>());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productCategoryColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCategory()));
        productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        productDescriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userRoleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole().toString()));
        userFirstNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));
        userLastNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
        userEmailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        userUsernameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        userAddressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderCouponColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCoupon()));
        orderUserColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUser()));
        orderDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrderDate()));
        orderStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus().toString()));

        ObservableList<String> roleFilters = FXCollections.observableArrayList(new ArrayList<>());
        roleFilters.add("All");
        for (Role role : Role.values()) {
            roleFilters.addAll(role.toString());
        }
        filterUsersChoiceBox.setItems(roleFilters);
        filterUsersChoiceBox.getSelectionModel().selectFirst();

        ObservableList<String> statusFilters = FXCollections.observableArrayList(new ArrayList<>());
        statusFilters.add("All");
        for (Status status : Status.values()) {
            statusFilters.addAll(status.toString());
        }
        filterOrdersChoiceBox.setItems(statusFilters);
        filterOrdersChoiceBox.getSelectionModel().selectFirst();

        ObservableList<String> categoryFilters = FXCollections.observableArrayList(new ArrayList<>());
        categoryFilters.add("All");
        for (Category category : categories) {
            categoryFilters.add(category.toString());
        }
        filterProductsChoiceBox.setItems(categoryFilters);
        filterProductsChoiceBox.getSelectionModel().selectFirst();


        refreshData();

        //mainTabPane.getTabs().removeAll(usersTab);

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
                    ObservableList<String> categoryFilters = FXCollections.observableArrayList(new ArrayList<>());
                    categoryFilters.add("All");
                    for (Category category : categories) {
                        categoryFilters.add(category.toString());
                    }
                    filterProductsChoiceBox.setItems(categoryFilters);
                    filterProductsChoiceBox.getSelectionModel().selectFirst();
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

    public void deleteUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Deleting user \"" + selectedUser.toString() + "\"!");
        alert.setContentText("Are you sure you want to delete user \"" + selectedUser.toString() + "\" ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new Thread(() -> {
                try {
                    RestService restService = new RestService();
                    restService.deleteUser(selectedUser);
                    refreshData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void deleteCategory() {
        Category selectedCategory = categoriesListView.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Deleting category \"" + selectedCategory.toString() + "\"!");
        alert.setContentText("Are you sure you want to delete category \"" + selectedCategory.toString() + "\" ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new Thread(() -> {
                try {
                    RestService restService = new RestService();
                    restService.deleteCategory(selectedCategory);
                    refreshData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void deleteProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Deleting product \"" + selectedProduct.toString() + "\"!");
        alert.setContentText("Are you sure you want to delete product \"" + selectedProduct.toString() + "\" ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new Thread(() -> {
                try {
                    RestService restService = new RestService();
                    restService.deleteProduct(selectedProduct);
                    refreshData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void deleteOrder() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Deleting order \"" + selectedOrder.toString());
        alert.setContentText("Are you sure you want to delete order \"" + selectedOrder.toString() + "\" ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new Thread(() -> {
                try {
                    RestService restService = new RestService();
                    restService.deleteOrder(selectedOrder);
                    refreshData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void refreshData() {
        getUsers();
        getCategories();
        getProducts();
        getOrders();
    }

    public void addCategory() {
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));
            CategoryController categoryController = new CategoryController(null);
            loader.setController(categoryController);
            root = loader.load();
            stage.setTitle("Category");
            stage.setScene(new Scene(root, 300, 400));
            stage.setMinWidth(300);
            stage.setMaxWidth(400);
            stage.setMinHeight(300);
            stage.setMaxHeight(400);
            stage.initOwner(
                    (categoriesListView.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                Category category = categoryController.getCategory();
                if (category != null) {
                    new Thread(() -> {
                        try {
                            RestService restService = new RestService();
                            restService.addCategory(category);
                            refreshData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editCategory() {
        Category selectedCategory = categoriesListView.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) return;
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));
            CategoryController categoryController = new CategoryController(selectedCategory);
            loader.setController(categoryController);
            root = loader.load();
            stage.setTitle("Category");
            stage.setScene(new Scene(root, 300, 400));
            stage.setMinWidth(300);
            stage.setMaxWidth(400);
            stage.setMinHeight(300);
            stage.setMaxHeight(400);
            stage.initOwner(
                    (categoriesListView.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                Category category = categoryController.getCategory();
                if (category != null) {
                    new Thread(() -> {
                        try {
                            RestService restService = new RestService();
                            restService.editCategory(category);
                            refreshData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct() {
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"));
            ProductController productController = new ProductController(null);
            loader.setController(productController);
            root = loader.load();
            stage.setTitle("Product");
            stage.setScene(new Scene(root, 300, 400));
            stage.setMinWidth(300);
            stage.setMaxWidth(400);
            stage.setMinHeight(300);
            stage.setMaxHeight(400);
            stage.initOwner(
                    (productsTable.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                Product product = productController.getProduct();
                if (product != null) {
                    new Thread(() -> {
                        try {
                            RestService restService = new RestService();
                            restService.addProduct(product);
                            refreshData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) return;
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"));
            ProductController productController = new ProductController(selectedProduct);
            loader.setController(productController);
            root = loader.load();
            stage.setTitle("Product");
            stage.setScene(new Scene(root, 300, 400));
            stage.setMinWidth(300);
            stage.setMaxWidth(400);
            stage.setMinHeight(300);
            stage.setMaxHeight(400);
            stage.initOwner(
                    (productsTable.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                Product product = productController.getProduct();
                if (product != null) {
                    new Thread(() -> {
                        try {
                            RestService restService = new RestService();
                            restService.editProduct(product);
                            refreshData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser() {
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
            UserController userController = new UserController(null);
            loader.setController(userController);
            root = loader.load();
            stage.setTitle("User");
            stage.setScene(new Scene(root, 300, 400));
            stage.setMinWidth(300);
            stage.setMaxWidth(400);
            stage.setMinHeight(300);
            stage.setMaxHeight(400);
            stage.initOwner(
                    (usersTable.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                User user = userController.getUser();
                if (user != null) {
                    new Thread(() -> {
                        try {
                            RestService restService = new RestService();
                            restService.addUser(user);
                            refreshData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) return;
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
            UserController userController = new UserController(selectedUser);
            loader.setController(userController);
            root = loader.load();
            stage.setTitle("User");
            stage.setScene(new Scene(root, 300, 400));
            stage.setMinWidth(300);
            stage.setMaxWidth(400);
            stage.setMinHeight(300);
            stage.setMaxHeight(400);
            stage.initOwner(
                    (usersTable.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                User user = userController.getUser();
                if (user != null) {
                    new Thread(() -> {
                        try {
                            RestService restService = new RestService();
                            restService.editUser(user);
                            refreshData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrder() {
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/order.fxml"));
            OrderController orderController = new OrderController(null);
            loader.setController(orderController);
            root = loader.load();
            stage.setTitle("Order");
            stage.setScene(new Scene(root, 600, 400));
            stage.setMinWidth(600);
            stage.setMaxWidth(400);
            stage.setMinHeight(600);
            stage.setMaxHeight(400);
            stage.initOwner(
                    (ordersTable.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                Order order = orderController.getOrder();
                if (order != null) {
                    new Thread(() -> {
                        try {
                            RestService restService = new RestService();
                            restService.addOrder(order);
                            refreshData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editOrder() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) return;
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/order.fxml"));
            OrderController orderController = new OrderController(selectedOrder);
            loader.setController(orderController);
            root = loader.load();
            stage.setTitle("Order");
            stage.setScene(new Scene(root, 600, 400));
            stage.setMinWidth(600);
            stage.setMaxWidth(400);
            stage.setMinHeight(600);
            stage.setMaxHeight(400);
            stage.initOwner(
                    (ordersTable.getScene().getWindow()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(event -> {
                Order order = orderController.getOrder();
                if (order != null) {
                    new Thread(() -> {
                        try {
                            RestService restService = new RestService();
                            restService.editOrder(order);
                            refreshData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
