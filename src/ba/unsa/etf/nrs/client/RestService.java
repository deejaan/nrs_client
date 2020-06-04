package ba.unsa.etf.nrs.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;


public class RestService {
    public static final String ROOT = "http://tim1-nrs-backend.herokuapp.com";

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
            user.setPassword(object.getString("password"));
            int role = object.getInt("role");
            if (role == 3) {
                user.setRole(Role.CLIENT);
            } else if (role == 2) {
                user.setRole(Role.EMPLOYEE);
            } else user.setRole(Role.MANAGER);
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
            user.setPassword(jsonObject.getString("password"));
            int role = jsonObject.getInt("role");
            if (role == 3) {
                user.setRole(Role.CLIENT);
            } else if (role == 2) {
                user.setRole(Role.EMPLOYEE);
            } else user.setRole(Role.MANAGER);
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

    public ArrayList<Coupon> getCoupons() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(Objects.requireNonNull(getJSON(ROOT + "/api/coupons/")));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Coupon coupon = new Coupon();
            coupon.setId(object.getInt("id"));
            coupon.setCode(object.getString("code"));
            coupon.setDiscount(object.getDouble("discount"));
            try {
                coupon.setUsed(object.getBoolean("used"));
            } catch (Exception e) {
                int used = object.getInt("used");
                coupon.setUsed(used != 0);
            }
            coupon.setExpiryDate(LocalDateTime.parse(object.getString("expiryDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
            coupons.add(coupon);
        }
        return coupons;
    }

    public Coupon getCoupon(int id) {
        try {
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(getJSON(ROOT + "/api/coupon/" + id)));
            Coupon coupon = new Coupon();
            coupon.setId(jsonObject.getInt("id"));
            coupon.setCode(jsonObject.getString("code"));
            coupon.setDiscount(jsonObject.getDouble("discount"));
            try {
                coupon.setUsed(jsonObject.getBoolean("used"));
            } catch (Exception e) {
                int used = jsonObject.getInt("used");
                coupon.setUsed(used != 0);
            }
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
            boolean completed = object.getBoolean("completed");
            if (completed) order.setStatus(Status.COMPLETED);
            else order.setStatus(Status.ACTIVE);
            order.setOrderDate(LocalDateTime.parse(object.getString("orderDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
            order.setOrderItems(getOrderItems(object.getInt("id")));
            try {
                order.setCoupon(getCoupon(object.getJSONObject("coupon").getInt("id")));
            } catch (Exception e) {
                order.setCoupon(null);
            }
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

    public void addCategory(Category category) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/category/");
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", category.getName());
            jsonParam.put("description", category.getDescription());
            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            os.writeBytes(jsonParam.toString());

            con.getResponseMessage();
            os.flush();
            os.close();
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

    public void editCategory(Category category) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/category/" + category.getId());
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", category.getName());
            jsonParam.put("description", category.getDescription());
            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            os.writeBytes(jsonParam.toString());

            System.out.println(con.getResponseMessage());
            os.flush();
            os.close();
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

    public void addProduct(Product product) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/product/");
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", product.getName());
            jsonParam.put("description", product.getDescription());
            jsonParam.put("price", product.getPrice());
            jsonParam.put("categoryId", product.getCategory().getId());
            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            os.writeBytes(jsonParam.toString());

            con.getResponseMessage();
            os.flush();
            os.close();
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

    public void editProduct(Product product) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/product/" + product.getId());
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", product.getName());
            jsonParam.put("description", product.getDescription());
            jsonParam.put("price", product.getPrice());
            jsonParam.put("categoryId", product.getCategory().getId());
            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            os.writeBytes(jsonParam.toString());

            con.getResponseMessage();
            os.flush();
            os.close();
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


    public void addUser(User user) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/user/");
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", user.getFirstName());
            jsonParam.put("lastName", user.getLastName());
            jsonParam.put("address", user.getAddress());
            jsonParam.put("username", user.getUsername());
            jsonParam.put("password", user.getPassword());
            jsonParam.put("email", user.getEmail());
            if (user.getRole().equals(Role.MANAGER)) jsonParam.put("role", 1);
            else if (user.getRole().equals(Role.EMPLOYEE)) jsonParam.put("role", 2);
            else jsonParam.put("role", 3);
            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            os.writeBytes(jsonParam.toString());

            System.out.println(con.getResponseMessage());
            os.flush();
            os.close();
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

    public void editUser(User user) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/user/" + user.getId());
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", user.getFirstName());
            jsonParam.put("lastName", user.getLastName());
            jsonParam.put("address", user.getAddress());
            jsonParam.put("username", user.getUsername());
            jsonParam.put("password", user.getPassword());
            jsonParam.put("email", user.getEmail());
            if (user.getRole().equals(Role.MANAGER)) jsonParam.put("role", 1);
            else if (user.getRole().equals(Role.EMPLOYEE)) jsonParam.put("role", 2);
            else jsonParam.put("role", 3);

            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            os.writeBytes(jsonParam.toString());

            System.out.println(con.getResponseMessage());
            os.flush();
            os.close();
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

    public void addOrder(Order order) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/order/");
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("orderDate", order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
            jsonParam.put("cuponId", order.getCoupon().getId());
            jsonParam.put("userId", order.getUser().getId());

            JSONArray jsonArray = new JSONArray();

            System.out.println(order.getCoupon());

            for (OrderItem orderItem : order.getOrderItems()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("quantity", orderItem.getQuantity());
                jsonObject.put("productId", orderItem.getProduct().getId());
                jsonArray.put(jsonObject);
            }

            jsonParam.put("orderItems", jsonArray);

            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            os.writeBytes(jsonParam.toString());

            System.out.println(con.getResponseMessage());
            os.flush();
            os.close();
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

    public void editOrder(Order order) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(ROOT + "/api/order/" + order.getId());
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("orderDate", order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
            jsonParam.put("couponId", order.getCoupon().getId());
            jsonParam.put("userId", order.getUser().getId());
            jsonParam.put("completed", !order.getStatus().equals(Status.ACTIVE));

            JSONArray jsonArray = new JSONArray();

            for (OrderItem orderItem : order.getOrderItems()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("quantity", orderItem.getQuantity());
                jsonObject.put("productId", orderItem.getProduct().getId());
                jsonArray.put(jsonObject);
            }

            jsonParam.put("orderItems", jsonArray);

            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            os.writeBytes(jsonParam.toString());

            System.out.println(con.getResponseMessage());
            os.flush();
            os.close();
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
