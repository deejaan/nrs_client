package ba.unsa.etf.nrs.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;


public class RestService {
    public static final String ROOT = "http://localhost:8080";

    private static String getJSON(String url) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(Objects.requireNonNull(getJSON(ROOT + "/api/users")));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            User user = new User();
            user.setId(object.getInt("id"));
            user.setLastName(object.getString("lastName"));
            user.setFirstName(object.getString("name"));
            user.setEmail(object.getString("email"));
            user.setRole(object.getInt("role"));
            user.setUsername(object.getString("username"));
            user.setAddress(object.getString("address"));
            users.add(user);
        }
        return users;
    }

    public User getUser(int id) {
        try {
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(getJSON(ROOT + "/api/user/" + id)));
            User user = new User();
            user.setId(jsonObject.getInt("id"));
            user.setLastName(jsonObject.getString("lastName"));
            user.setFirstName(jsonObject.getString("name"));
            user.setEmail(jsonObject.getString("email"));
            user.setRole(jsonObject.getInt("role"));
            user.setUsername(jsonObject.getString("username"));
            user.setAddress(jsonObject.getString("address"));
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(Objects.requireNonNull(getJSON(ROOT + "/api/categories")));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Category category = new Category();
            category.setId(object.getInt("id"));
            category.setDescription(object.getString("description"));
            category.setName(object.getString("name"));
            categories.add(category);
        }
        return categories;
    }

    public Category getCategory(int id) {
        try {
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(getJSON(ROOT + "/api/category/" + id)));
            Category category = new Category();
            category.setId(jsonObject.getInt("id"));
            category.setDescription(jsonObject.getString("description"));
            category.setName(jsonObject.getString("name"));
            return category;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(Objects.requireNonNull(getJSON(ROOT + "/api/products")));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Product product = new Product();
            product.setId(object.getInt("id"));
            product.setDescription(object.getString("description"));
            product.setName(object.getString("name"));
            product.setPrice(object.getDouble("price"));
            try {
                product.setCategory(getCategory(object.getInt("categoryId")));
            } catch (Exception e) {
                product.setCategory(null);
            }
            products.add(product);
        }
        return products;
    }

    public Product getProduct(int id) {
        try {
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(getJSON(ROOT + "/api/product/" + id)));
            Product product = new Product();
            product.setId(jsonObject.getInt("id"));
            product.setDescription(jsonObject.getString("description"));
            product.setName(jsonObject.getString("name"));
            product.setPrice(jsonObject.getDouble("price"));
            try {
                product.setCategory(getCategory(jsonObject.getInt("categoryId")));
            } catch (Exception e) {
                product.setCategory(null);
            }
            return product;
        } catch (Exception e) {
            return null;
        }
    }

    public Coupon getCoupon(int id) {
        try {
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(getJSON(ROOT + "/api/coupon/" + id)));
            Coupon coupon = new Coupon();
            coupon.setId(jsonObject.getInt("id"));
            coupon.setCode(jsonObject.getString("code"));
            coupon.setDiscount(jsonObject.getDouble("discount"));
            coupon.setUsed(jsonObject.getBoolean("used"));
            coupon.setExpiryDate(LocalDateTime.parse(jsonObject.getString("expiryDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
            return coupon;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<OrderItem> getOrderItems(int id) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(Objects.requireNonNull(getJSON(ROOT + "/api/orderitems/" + id)));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setId(object.getInt("id"));
            orderItem.setQuantity(object.getInt("quantity"));
            try {
                orderItem.setProduct(getProduct(object.getJSONObject("product").getInt("id")));
            } catch (Exception e) {
                orderItem.setProduct(null);
            }
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(Objects.requireNonNull(getJSON(ROOT + "/api/orders")));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Order order = new Order();
            order.setId(object.getInt("id"));
            order.setCompleted(object.getBoolean("completed"));
            order.setOrderDate(LocalDateTime.parse(object.getString("orderDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
            order.setOrderItems(getOrderItems(object.getInt("id")));
            order.setCoupon(getCoupon(object.getJSONObject("coupon").getInt("id")));
            try {
                order.setUser(getUser(object.getJSONObject("user").getInt("id")));
            } catch (Exception e) {
                order.setUser(null);
            }
            orders.add(order);
        }
        return orders;
    }

    public void deleteUser(User user) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/user/" + user.getId());
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("DELETE");
            con.connect();
            con.getResponseCode();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public void deleteCategory(Category category) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/category/" + category.getId());
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("DELETE");
            con.connect();
            con.getResponseCode();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void deleteProduct(Product product) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/product/" + product.getId());
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("DELETE");
            con.connect();
            con.getResponseCode();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void deleteOrder(Order order) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/order/" + order.getId());
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("DELETE");
            con.connect();
            con.getResponseCode();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
