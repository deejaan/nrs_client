package ba.unsa.etf.nrs.client;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    int id;
    ArrayList<OrderItem> orderItems;
    User user;
    Coupon coupon;
    LocalDateTime orderDate;
    Status status;

    public Order() {
        this.id = 0;
        this.orderItems = new ArrayList<>();
        this.user = null;
        this.coupon = null;
        this.orderDate = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    public Order(int id, ArrayList<OrderItem> orderItems, User user, Coupon coupon, LocalDateTime orderDate, Status status) {
        this.id = id;
        this.orderItems = orderItems;
        this.user = user;
        this.coupon = coupon;
        this.orderDate = orderDate;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "order#" + getId();
    }
}
