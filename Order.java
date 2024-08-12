import java.util.HashMap;
import java.util.Map;

public class Order {
    private int orderId;
    private Map<Integer, Integer> products; // Maps productId to quantity
    private double totalPrice;
    private String status; // "Pending" or "Finalized"

    public Order(int orderId) {
        this.orderId = orderId;
        this.products = new HashMap<>();
        this.totalPrice = 0.0;
        this.status = "Pending";
    }
        // Setters
        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }
    
        public void setStatus(String status) {
            this.status = status;
        }

    public int getOrderId() {
        return orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void addProduct(Product product, int quantity) {
        int currentQuantity = products.getOrDefault(product.getProductId(), 0);
        products.put(product.getProductId(), currentQuantity + quantity);
        totalPrice += product.getPrice() * quantity;
    }

    public void removeProduct(Product product) {
        if (products.containsKey(product.getProductId())) {
            int quantity = products.get(product.getProductId());
            totalPrice -= product.getPrice() * quantity;
            products.remove(product.getProductId());
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
            if (product == null || product.getStockQuantity() < entry.getValue()) {
                System.out.println("Not enough stock for product: " + product.getName() + ". Order remains pending.");
                return false;
            }
        }

        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            Product product = inventory.get(entry.getKey());
            product.updateProductStock(-entry.getValue());
        }

        this.status = "Finalized";
        System.out.println("Order finalized successfully.");
        return true;
    }


}
