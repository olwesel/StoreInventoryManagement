import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    private static final String FILE_NAME = "orders.txt";
    private static Map<Integer, Order> orders = new HashMap<>();
    private static int nextOrderId = 1;

    public static Map<Integer, Order> loadOrdersFromFile() {
        File file = new File(FILE_NAME);
        Map<Integer, Product> inventory = new HashMap<>();
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

                    if ("Finalized".equals(status)) {
                        order.finalizeOrder(inventory); // Pass the inventory map
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
        return orders;
    }

    public static void saveOrdersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Order order : orders.values()) {
                bw.write(order.getOrderId() + "," + order.getTotalPrice() + "," + order.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static Order createOrder() {
        Order newOrder = new Order(nextOrderId++);
        orders.put(newOrder.getOrderId(), newOrder);
        return newOrder;
    }

    public static Order findOrderById(int orderId) {
        return orders.get(orderId);
    }

    public static Map<Integer, Order> getOrders() {
        return orders;
    }
}
