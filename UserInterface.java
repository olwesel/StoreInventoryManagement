import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize singleton instances
        InventoryManager inventoryManager = InventoryManager.getInstance();
        OrderManager orderManager = OrderManager.getInstance();

        // Main interaction loop
        mainScreen();
    }

    private static void mainScreen() {
        boolean exit = false;

        while (!exit) {
            try {
                System.out.println();
                System.out.println("Inventory and Order Management System:");
                System.out.println();
                System.out.println("(1) Manage inventory");
                System.out.println("(2) Manage orders");
                System.out.println("(3) Save and quit");
                System.out.println();
                System.out.print("Choose an option: ");

                int choice = getIntInput(scanner);

                switch (choice) {
                    case 1:
                        manageInventory();
                        break;
                    case 2:
                        manageOrders();
                        break;
                    case 3:
                        saveToFile();
                        exit = true;
                        System.out.println("Exiting... Data has been saved.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear scanner buffer
            }
        }
    }

    private static void saveToFile() {
        InventoryManager.getInstance().saveProductsToFile();
        OrderManager.getInstance().saveOrdersToFile();
    }

    private static void manageInventory() {
        try {
            System.out.println();
            printInventory();

            System.out.println();
            System.out.println("OPTIONS:");
            System.out.println("(1) Add a new product");
            System.out.println("(2) Remove a product");
            System.out.println("(3) Add stock");
            System.out.println("(4) Remove stock");
            System.out.println("(5) Back to main menu");
            System.out.println();
            System.out.print("Choose an option: ");

            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    addNewProduct();
                    manageInventory();
                    break;
                case 2:
                    removeProduct();
                    manageInventory();
                    break;
                case 3:
                    addStock();
                    manageInventory();
                    break;
                case 4:
                    removeStock();
                    manageInventory();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    manageInventory();
            }
        } catch (Exception e) {
            System.err.println("An error occurred while managing inventory: " + e.getMessage());
            scanner.nextLine(); // Clear scanner buffer
        }
    }

    private static void manageOrders() {
        try {
            System.out.println();
            printOrders();
            System.out.println();
            System.out.println("OPTIONS:");
            System.out.println("(1) Create a new order");
            System.out.println("(2) Finalize an order");
            System.out.println("(3) Modify an existing order");
            System.out.println("(4) Back to main menu");
            System.out.println();
            System.out.print("Choose an option: ");

            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    createNewOrder();
                    manageOrders();
                    break;
                case 2:
                    finalizeOrder();
                    manageOrders();
                    break;
                case 3:
                    modifyOrder();
                    manageOrders();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    manageOrders();
            }
        } catch (Exception e) {
            System.err.println("An error occurred while managing orders: " + e.getMessage());
            scanner.nextLine(); // Clear scanner buffer
        }
    }

    private static void addNewProduct() {
        try {
            System.out.println();
            System.out.println("Add a new product:");
            System.out.println();
            System.out.print("Enter Product Name: ");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.print("Enter Product Price: ");
            double price = getDoubleInput(scanner);

            System.out.print("Enter Stock Quantity: ");
            int stockQuantity = getIntInput(scanner);

            InventoryManager.getInstance().addProduct(name, price, stockQuantity);
            System.out.println("Product added successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred while adding a new product: " + e.getMessage());
        }
    }

    private static void removeProduct() {
        try {
            System.out.println();
            System.out.println("Remove a product:");
            System.out.println();
            System.out.print("Enter Product ID to remove: ");
            int productId = getIntInput(scanner);
            if (OrderManager.getInstance().checkForProduct(productId)) {
                System.out.println("Cannot remove product, it is part of a pending order.");
                return;
            } // checks if product is in any orders, returns boolean
            InventoryManager.getInstance().removeProduct(productId);
        } catch (Exception e) {
            System.err.println("An error occurred while removing the product: " + e.getMessage());
        }
    }

    private static void addStock() {
        try {
            System.out.println();
            System.out.println("Add stock:");
            System.out.println();
            System.out.print("Enter Product ID to add stock: ");
            int productId = getIntInput(scanner);
            Product product = InventoryManager.getInstance().findProductById(productId);

            if (product != null) {
                System.out.print("Enter quantity to add: ");
                int quantity = getIntInput(scanner);
                if (product.updateProductStock(quantity)) {
                    System.out.println("Stock added successfully.");
                } else {
                    System.out.println("Error adding stock.");
                }
            } else {
                System.out.println("Product not found.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding stock: " + e.getMessage());
        }
    }

    private static void removeStock() {
        try {
            System.out.println();
            System.out.println("Remove stock:");
            System.out.println();
            System.out.print("Enter Product ID to remove stock: ");
            int productId = getIntInput(scanner);
            Product product = InventoryManager.getInstance().findProductById(productId);

            if (product != null) {
                System.out.print("Enter quantity to remove: ");
                int quantity = getIntInput(scanner);
                if (product.updateProductStock(-quantity)) {
                    System.out.println("Stock removed successfully.");
                } else {
                    System.out.println("UNSUCCESSFUL: Not enough stock to remove that quantity.");
                }
            } else {
                System.out.println("Product not found.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while removing stock: " + e.getMessage());
        }
    }

    private static void createNewOrder() {
        try {
            System.out.println();
            System.out.println("Note: You may create a new pending order with stock quantity that is not in inventory, however to finalize it, the inventory must be available.");
            System.out.println("Create a new order:");
            System.out.println();
            Order newOrder = OrderManager.getInstance().createOrder();
            addProductsToOrder(newOrder);
        } catch (Exception e) {
            System.err.println("An error occurred while creating a new order: " + e.getMessage());
        }
    }

    private static void modifyOrder() {
        try {
            System.out.println();
            System.out.println("Modify an existing order:");
            System.out.println();
            System.out.print("Enter Order ID to modify: ");
            int orderId = getIntInput(scanner);
            System.out.println();
            Order order = OrderManager.getInstance().findOrderById(orderId);
            System.out.println(order);
            if (order != null && "Pending".equals(order.getStatus())) {
                modifyOrderOptions(order);
            } else if (order == null) {
                System.out.println("Order not found.");
            } else {
                System.out.println("Order already finalized.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while modifying the order: " + e.getMessage());
        }
    }

    private static void modifyOrderOptions(Order order) {
        boolean modifyingOrder = true;

        while (modifyingOrder) {
            System.out.println("MODIFY ORDER:");
            System.out.println("(1) Add a product");
            System.out.println("(2) Remove a product");
            System.out.println("(3) Change product quantity");
            System.out.println("(4) Finish modifying");

            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    addProductsToOrder(order);
                    break;
                case 2:
                    removeProductFromOrder(order);
                    break;
                case 3:
                    changeProductQuantity(order);
                    break;
                case 4:
                    modifyingOrder = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addProductsToOrder(Order order) {
        boolean addingProducts = true;
        printInventory();
    
        while (addingProducts) {
            System.out.println();
            System.out.println("Add a product to the order:");
            System.out.println();
            System.out.print("Enter Product ID: ");
            int productId = getIntInput(scanner);
            Product product = InventoryManager.getInstance().findProductById(productId);
    
            if (product != null) {
                System.out.print("Enter quantity: ");
                int quantity = getIntInput(scanner);
                order.addProduct(product, quantity);
                System.out.println("Product added to order.");
            } else {
                System.out.println("Product not found. Please try again.");
            }
    
            System.out.print("Add more products? (press Enter to finish or type 'yes' to continue): ");
            scanner.nextLine(); // Consume newline left-over
            String more = scanner.nextLine().trim(); // Read user input
    
            if (!more.equalsIgnoreCase("yes") && !more.equalsIgnoreCase("y")) {
                addingProducts = false; // Exit loop if input is empty or anything other than "yes" or "y"
            }
        }
    }
    

    private static void removeProductFromOrder(Order order) {
        try {
            System.out.println();
            System.out.println("Remove product from order:");
            System.out.println();
            System.out.print("Enter Product ID to remove from order: ");
            int productId = getIntInput(scanner);
            Product product = InventoryManager.getInstance().findProductById(productId);

            if (product != null) {
                order.removeProduct(product);
            } else {
                System.out.println("Product not found in the order.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while removing the product from the order: " + e.getMessage());
        }
    }

    private static void changeProductQuantity(Order order) {
        try {
            System.out.println();
            System.out.println("Change product quantity:");
            System.out.println();
            System.out.print("Enter Product ID to change quantity: ");
            int productId = getIntInput(scanner);
            Product product = InventoryManager.getInstance().findProductById(productId);

            if (product != null) {
                System.out.print("Enter new quantity: ");
                int newQuantity = getIntInput(scanner);
                order.modifyProductQuantity(product, newQuantity);
            } else {
                System.out.println("Product not found in the order.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while changing the product quantity: " + e.getMessage());
        }
    }

    private static void finalizeOrder() {
        try {
            System.out.println();
            System.out.println("Finalize an order:");
            System.out.println();
            System.out.print("Enter Order ID to finalize: ");
            int orderId = getIntInput(scanner);
            System.out.println();
            Order order = OrderManager.getInstance().findOrderById(orderId);
            System.out.println(order);
            if (order != null && "Pending".equals(order.getStatus())) {
                boolean finalized = order.finalizeOrder(InventoryManager.getInstance().getProducts());
                if (finalized) {
                    System.out.println("Order finalized successfully.");
                } else {
                    System.out.println("ORDER REMAINS PENDING DUE TO INSUFFICIENT STOCK.");
                }
            } else if (order == null) {
                System.out.println("Order not found.");
            } else {
                System.out.println("Order already finalized.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while finalizing the order: " + e.getMessage());
        }
    }

    private static void printInventory() {
        try {
            System.out.println("CURRENT INVENTORY (remember to save and quit!):");
            Map<Integer, Product> products = InventoryManager.getInstance().getProducts();
            if (products.isEmpty()) {
                System.out.println("No products available.");
            } else {
                for (Product product : products.values()) {
                    System.out.println(product.getProductInfo());
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while printing the inventory: " + e.getMessage());
        }
    }

    private static void printOrders() {
        try {
            System.out.println("CURRENT ORDERS (remember to save and quit!):");
            Map<Integer, Order> orders = OrderManager.getInstance().getOrders();
            if (orders.isEmpty()) {
                System.out.println("No orders available.");
            } else {
                for (Order order : orders.values()) {
                    System.out.println("Order ID: " + order.getOrderId() + ", Total Price: $" + order.getTotalPrice() + ", Status: " + order.getStatus());
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while printing the orders: " + e.getMessage());
        }
    }

    // Utility method to get a valid integer input from the user
    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Clear scanner buffer
            }
        }
    }

    // Utility method to get a valid double input from the user
    private static double getDoubleInput(Scanner scanner) {
        while (true) {
            try {
                double doub = scanner.nextDouble();
                if (doub < 0) {
                    throw new InputMismatchException("Negative numbers are not allowed.");
                }
                return doub;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a non-negative number.");
                scanner.nextLine(); // Clear scanner buffer
            }
        }
    }
}
