import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    private static final String FILE_NAME = "orders.txt";
    private static volatile OrderManager instance; // for thread-safe lazy initialization
    private final Map<Integer, Order> orders; // Encapsulated with final keyword
    private int nextOrderId; // Encapsulated

    // Private constructor to prevent instantiation from outside the class
    private OrderManager() {
        this.orders = new HashMap<>();
        this.nextOrderId = 1;
        loadOrdersFromFile();
    }

    // Double-checked locking for thread-safe lazy initialization
    public static OrderManager getInstance() {
        if (instance == null) {
            synchronized (OrderManager.class) {
                if (instance == null) {
                    instance = new OrderManager();
                }
            }
        }
        return instance;
    }

    private void loadOrdersFromFile() {
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
                    InventoryManager manager = InventoryManager.getInstance();
                    String[] parts = line.split(",");
                    int orderId = Integer.parseInt(parts[0]);
                    double totalPrice = Double.parseDouble(parts[1]);
                    String status = parts[2];
                    Order order = new Order(orderId);
                    String[] productStrings = parts[3].substring(1, parts[3].length() - 2).split(",");

                    // add products to order
                    for (String productString : productStrings) {
                        String[] subParts = productString.split("-");
                        int productInt = Integer.parseInt(subParts[0]);
                        int quantity = Integer.parseInt(subParts[1]);
                        Product product = manager.findProductById(productInt);
                        order.addProduct(product, quantity);
                    }

                    // Assuming Order class has methods to set its total price and status
                    order.setTotalPrice(totalPrice);
                    order.setStatus(status);

                    if ("Finalized".equals(status)) {
                        order.finalizeOrder(InventoryManager.getInstance().getProducts());
                    }

                    orders.put(orderId, order);
                    System.out.println(order);
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
                // iterate through product ids (keys in getProducts dict), create string
                String productsString = "(";
                Map<Integer, Integer> products = order.getProducts();
                for (int id : products.keySet()) {
                    int quantity = products.get(id);
                    productsString += id + "-" + quantity + ",";
                }
                if (productsString.length() > 1) {
                    productsString = productsString.substring(0, productsString.length() - 1) + ")";
                }
                else {
                    productsString += ")";
                }
                bw.write(order.getOrderId() + "," + order.getTotalPrice() + "," + order.getStatus() + "," + productsString);
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
        return new HashMap<>(orders); // Returning copy to prevent external modifications
    }
}

