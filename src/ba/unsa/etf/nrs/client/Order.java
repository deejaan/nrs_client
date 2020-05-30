package ba.unsa.etf.nrs.client;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    int id;
    ArrayList<OrderItem> orderItems;
    User user;
    Coupon coupon;
    LocalDateTime orderDate;
    boolean completed;

    public Order() {
    }

    public Order(int id, ArrayList<OrderItem> orderItems, User user, Coupon coupon, LocalDateTime orderDate, boolean completed) {
        this.id = id;
        this.orderItems = orderItems;
        this.user = user;
        this.coupon = coupon;
        this.orderDate = orderDate;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "order#" + getId();
    }
}
