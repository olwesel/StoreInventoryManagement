import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private static final String FILE_NAME = "inventory.txt";
    private static Map<Integer, Product> products = new HashMap<>();
    private static int nextProductId = 1;

    public static Map<Integer, Product> loadProductsFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("No existing product file found. A new file has been created.");
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
                    int productId = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int stockQuantity = Integer.parseInt(parts[3]);
                    products.put(productId, new Product(productId, name, price, stockQuantity));
                    nextProductId = Math.max(nextProductId, productId + 1);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error parsing product data: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return products;
    }

    public static void saveProductsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Product product : products.values()) {
                bw.write(product.getProductId() + "," + product.getName() + "," + product.getPrice() + "," + product.getStockQuantity());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void addProduct(String name, double price, int stockQuantity) {
        int productId = nextProductId++;
        products.put(productId, new Product(productId, name, price, stockQuantity));
        System.out.println("Product added successfully with ID: " + productId);
    }

    public static void removeProduct(int productId) {
        if (products.containsKey(productId)) {
            products.remove(productId);
            System.out.println("Product removed successfully.");
        } else {
            System.err.println("Product ID not found.");
        }
    }

    public static Product findProductById(int productId) {
        return products.get(productId);
    }

    public static Map<Integer, Product> getProducts() {
        return products;
    }
}
