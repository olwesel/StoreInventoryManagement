public class Product {
    private final int productId; // Marked as final since it should not change
    private String name;
    private double price;
    private int stockQuantity;

    public Product(int productId, String name, double price, int stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getProductInfo() {
        return String.format("Product ID: %d, Name: %s, Price: $%.2f, Stock Quantity: %d",
                productId, name, price, stockQuantity);
    }

    public boolean updateProductStock(int quantity) {
        int newQuantity = this.stockQuantity + quantity;
        if (newQuantity < 0) {
            return false;
        }
        this.stockQuantity = newQuantity;
        return true;
    }

    // Getters and Setters for encapsulation
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    // No setter for productId, as it should be immutable once set
}
