public class Product {
    private int productId;
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

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
