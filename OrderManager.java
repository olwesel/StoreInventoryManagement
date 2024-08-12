import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    private static final String FILE_NAME = "orders.txt";
    private static OrderManager instance; // Singleton instance
    private Map<Integer, Order> orders = new HashMap<>();
    private int nextOrderId = 1;

    // Private constructor to prevent instantiation from outside the class
    private OrderManager() {
        loadOrdersFromFile();
    }

    // Public method to provide access to the singleton instance
    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public  void loadOrdersFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("No existing order file found. A new file has been created.");
                }
            } catch (IOException e) {
                System.err.println("Error creating new file: " + e.getMessage());
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    int orderId = Integer.parseInt(parts[0]);
                    double totalPrice = Double.parseDouble(parts[1]);
                    String status = parts[2];
                    Order order = new Order(orderId);
                    
                    // Assuming Order class has a method to set its total price and status
                    order.setTotalPrice(totalPrice);
                    order.setStatus(status);

                    if ("Finalized".equals(status)) {
                        order.finalizeOrder(InventoryManager.getInstance().getProducts());
                    }

                    orders.put(orderId, order);
                    nextOrderId = Math.max(nextOrderId, orderId + 1);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error parsing order data: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveOrdersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Order order : orders.values()) {
                bw.write(order.getOrderId() + "," + order.getTotalPrice() + "," + order.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public Order createOrder() {
        Order newOrder = new Order(nextOrderId++);
        orders.put(newOrder.getOrderId(), newOrder);
        return newOrder;
    }

    public Order findOrderById(int orderId) {
        return orders.get(orderId);
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }
}
