package ba.unsa.etf.nrs.client;

import javafx.beans.property.SimpleStringProperty;

public class User {
    int id;
    Role role;
    SimpleStringProperty firstName, lastName, username, email, address;
    String password;

    public User() {
        this.id = 0;
        this.role = Role.CLIENT;
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("lastName");
        this.username = new SimpleStringProperty("username");
        this.email = new SimpleStringProperty("email");
        this.address = new SimpleStringProperty("address");
    }

    public User(int id, Role role, String firstName, String lastName, String username, String email, String address, String password) {
        this.id = id;
        this.role = role;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
