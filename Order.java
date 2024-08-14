import java.util.HashMap;
import java.util.Map;

public class Order {
    private final int orderId; // Final to prevent changes after creation
    private final Map<Integer, Integer> products; // Maps productId to quantity
    private double totalPrice;
    private String status; // "Pending" or "Finalized"

    public Order(int orderId) {
        this.orderId = orderId;
        this.products = new HashMap<>();
        this.totalPrice = 0.0;
        this.status = "Pending";
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<Integer, Integer> getProducts() {
        return new HashMap<>(products); // Return a copy to prevent external modification
    }

    // Methods
    public void addProduct(Product product, int quantity) {
        int currentQuantity = products.getOrDefault(product.getProductId(), 0);
        products.put(product.getProductId(), currentQuantity + quantity);
        totalPrice += product.getPrice() * quantity;
    }

    public void removeProduct(Product product) {
        if (products.containsKey(product.getProductId())) {
            int quantity = products.remove(product.getProductId());
            totalPrice -= product.getPrice() * quantity;
            System.out.println("Product removed from the order.");
        } else {
            System.out.println("Product not found in the order.");
        }
    }

    public void modifyProductQuantity(Product product, int newQuantity) {
        if (products.containsKey(product.getProductId())) {
            int currentQuantity = products.get(product.getProductId());
            products.put(product.getProductId(), newQuantity);
            totalPrice += product.getPrice() * (newQuantity - currentQuantity);
            System.out.println("Product quantity updated.");
        } else {
            System.out.println("Product not found in the order.");
        }
    }

    public boolean finalizeOrder(Map<Integer, Product> inventory) {
        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            Product product = inventory.get(entry.getKey());
            if (product == null) {
                System.out.println("The product with key: " + entry.getKey() + " no longer exists in inventory.");
                return false;
            }
            if (product.getStockQuantity() < entry.getValue()) {
                System.out.println("Not enough stock for product: " + product.getName() + ".");
                return false;
            }
        }

        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            Product product = inventory.get(entry.getKey());
            product.updateProductStock(-entry.getValue());
        }

        this.status = "Finalized";
        return true;
    }

    // Override toString() method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Products in Order:\n");

        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            sb.append("Product ID: ").append(entry.getKey())
              .append(", Quantity: ").append(entry.getValue()).append("\n");
        }

        sb.append("Total Price: $").append(String.format("%.2f", totalPrice)).append("\n");
        sb.append("-----------------------------");

        return sb.toString();
    }
}

