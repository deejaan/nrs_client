package ba.unsa.etf.nrs.client;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Coupon {
    int id;
    SimpleDoubleProperty discount;
    SimpleStringProperty code;
    LocalDateTime expiryDate;
    boolean used;

    public Coupon() {
    }

    public Coupon(int id, double discount, String code, LocalDateTime expiryDate, boolean used) {
        this.id = id;
        this.discount = new SimpleDoubleProperty(discount);
        this.code = new SimpleStringProperty(code);
        this.expiryDate = expiryDate;
        this.used = used;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDiscount() {
        return discount.get();
    }

    public void setDiscount(double discount) {
        this.discount.set(discount);
    }

    public SimpleDoubleProperty discountProperty() {
        return discount;
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return getCode();
    }
}
