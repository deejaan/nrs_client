package ba.unsa.etf.nrs.client;

import javafx.beans.property.SimpleStringProperty;

public class Category {
    int id;
    SimpleStringProperty name, description;

    public Category() {
        this.id = 0;
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
    }

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    @Override
    public String toString() {
        return getName();
    }
}
