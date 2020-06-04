package ba.unsa.etf.nrs.client;

public class OrderItem {
    int id, quantity;
    Product product;

    public OrderItem() {
        this.id = 0;
        this.quantity = 0;
        this.product = null;
    }

    public OrderItem(int id, int quantity, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return getProduct() + " " + getQuantity();
    }
}
