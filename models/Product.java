package models;

public class Product {
    private int id;
    private String sku;
    private String name;
    private double price;
    private int stock;
    private String description;

    public Product(String name, double price, int stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getDescription() { return description; }

    public void setId(int id) { this.id = id; }
    public void setSku(String sku) { this.sku = sku; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setDescription(String description) { this.name = description; }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nSKU: " + sku +
                "\nNombre: " + name +
                "\nPrecio: $" + price +
                "\nStock: " + stock +
                "\nDescripción: " + description;

    }
}