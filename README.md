# Inventory and Order Management System

## Overview

This project is a command-line-based Inventory and Order Management System that handles basic inventory operations like adding new products, updating stock, and removing products, as well as order management operations like creating new orders, modifying existing ones, and finalizing orders.

## Project Structure

The project is structured into several Java classes:

1. **Inventory Management** (`InventoryManager.java`, `Product.java`):
   - Manages the list of products in the inventory.
   - Allows adding new products, removing existing ones, and updating product stock.
   - Loads inventory from `inventory.txt` and saves changes to the file.
   - A Product object represents a product in the inventory with attributes such as ID, name, price, and stock quantity.
   - Products can be updated, added, or removed based on inventory operations.


2. **Order Management** (`OrderManager.java`, `Order.java`):
   - Manages orders, which can contain multiple products.
   - Supports creating new orders, modifying products in pending orders, and finalizing orders.
   - An Order object represents an order with attributes such as status, ID, products, and total price. 
   - Orders are stored in `orders.txt` and are loaded and saved to this file during application use.

3. **User Interface** (`UserInterface.java`):
   - Provides the command-line interface for user interaction.
   - Users can manage inventory, create and modify orders, and save their changes.
   - Handles input validation and ensures data integrity.

4. **Data Files**:
   - `orders.txt`: Stores the list of orders with their details.
   - `inventory.txt`: Stores the list of products in the inventory.

## Features

### Inventory Management

- **Add a New Product**:
  - Users can add a new product to the inventory by specifying the product's name, price, and stock quantity.
  - Example interaction:
    ```sh
    OPTIONS:
    (1) Add a new product
    (2) Remove a product
    (3) Add stock
    (4) Remove stock
    (5) Back to main menu

    Choose an option: 1

    Add a new product:

    Enter Product Name: City's Best Grandpa Mug
    Enter Product Price: 8
    Enter Stock Quantity: 20
    Product added successfully with ID: 6
    ```

- **Remove a Product**:
  - Users can remove a product from the inventory by specifying the product ID.
  - Example interaction:
    ```sh
    OPTIONS:
    (1) Add a new product
    (2) Remove a product
    (3) Add stock
    (4) Remove stock
    (5) Back to main menu

    Choose an option: 2

    Remove a product:

    Enter Product ID to remove: 7
    Product removed successfully.
    ```

- **Add Stock**:
  - Users can increase the stock quantity of an existing product by specifying the product ID and the amount to add.
  - Example interaction:
    ```sh
    OPTIONS:
    (1) Add a new product
    (2) Remove a product
    (3) Add stock
    (4) Remove stock
    (5) Back to main menu

    Choose an option: 3

    Add stock:

    Enter Product ID to update: 3
    Enter quantity to add: 10
    Stock updated successfully.
    ```

- **Remove Stock**:
  - Users can decrease the stock quantity of an existing product by specifying the product ID and the amount to remove.
  - Example interaction:
    ```sh
    OPTIONS:
    (1) Add a new product
    (2) Remove a product
    (3) Add stock
    (4) Remove stock
    (5) Back to main menu

    Choose an option: 4

    Remove stock:

    Enter Product ID to update: 3
    Enter quantity to remove: 2
    Stock updated successfully.
    ```

- **View Current Inventory**:
  - The current inventory, including all products, their prices, and stock quantities, is displayed to the user whenever they perform an inventory-related action.
  - Example output:
    ```sh
    CURRENT INVENTORY (remember to save and quit!):
    Product ID: 2, Name: Balloons, Price: $5.00, Stock Quantity: 20
    Product ID: 3, Name: Pencil Case, Price: $7.00, Stock Quantity: 6
    Product ID: 4, Name: Hairbrush, Price: $12.00, Stock Quantity: 15
    Product ID: 5, Name: World's Best Grandpa Mug, Price: $14.00, Stock Quantity: 10
    Product ID: 6, Name: City's Best Grandpa Mug, Price: $8.00, Stock Quantity: 20
    ```

### Order Management

- **Create a New Order**:
  - Users can create a new order by selecting products from the inventory and specifying quantities.
  - Example interaction:
    ```sh
    CURRENT ORDERS (remember to save and quit!):
    Order ID: 1, Total Price: $58.00, Status: Pending
    Order ID: 2, Total Price: $20.00, Status: Pending
    Order ID: 3, Total Price: $22.00, Status: Finalized
    
    OPTIONS:
    (1) Create a new order
    (2) Finalize an order
    (3) Modify an existing order
    (4) Back to main menu

    Choose an option: 1

    Create a new order:

    CURRENT INVENTORY (remember to save and quit!):
    Product ID: 2, Name: Balloons, Price: $5.00, Stock Quantity: 20
    Product ID: 3, Name: Pencil Case, Price: $7.00, Stock Quantity: 6
    Product ID: 4, Name: Hairbrush, Price: $12.00, Stock Quantity: 15

    Enter Product ID: 2
    Enter quantity: 10
    Product added to order.
    ```

- **Modify an Existing Order**:
  - Users can modify a pending order by adding or removing products or updating quantities. Orders can only be modified if they are still marked as "Pending."
  - Example interaction:
    ```sh
    OPTIONS:
    (1) Create a new order
    (2) Finalize an order
    (3) Modify an existing order
    (4) Back to main menu

    Choose an option: 3

    Modify an existing order:

    Enter Order ID to modify: 1

    Order ID: 1
    Status: Pending
    Products in Order:
    Product ID: 2, Quantity: 10
    Total Price: $50.00
    -----------------------------
    MODIFY ORDER:
    (1) Add a product
    (2) Remove a product
    (3) Change product quantity
    (4) Finish modifying
    ```

- **Finalize an Order**:
  - Users can finalize an order, which updates the inventory based on the products in the order and marks the order as "Finalized."
  - Example interaction:
    ```sh
    OPTIONS:
    (1) Create a new order
    (2) Finalize an order
    (3) Modify an existing order
    (4) Back to main menu

    Choose an option: 2

    Finalize an order:

    Enter Order ID to finalize: 3

    Order finalized successfully.
    ```

- **View Current Orders**:
  - The current list of orders, including their IDs, total prices, and statuses, is displayed to the user whenever they perform an order-related action.
  - Example output:
    ```sh
    CURRENT ORDERS (remember to save and quit!):
    Order ID: 1, Total Price: $58.00, Status: Pending
    Order ID: 2, Total Price: $20.00, Status: Pending
    Order ID: 3, Total Price: $22.00, Status: Finalized
    ```

## Setup and Usage

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A text editor or Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or VS Code.

### Running the Application

1. **Compile the Project**:
   - Open a terminal and navigate to the project directory.
   - Compile the Java files:
     ```sh
     javac *.java
     ```

2. **Run the Application**:
   - Execute the main class:
     ```sh
     java UserInterface
     ```

3. **Interact with the Application**:
   - Follow the on-screen prompts to manage inventory and orders.

### File Formats

- **orders.txt**:
  - Stores orders in the format: `orderId,totalPrice,status,(productId-quantity;productId-quantity;...)`
  - Example: `1,58.0,Pending,(2-10;6-1)`

- **inventory.txt**:
  - Stores inventory in the format: `productId,name,price,stockQuantity`
  - Example: `2,Balloons,5.0,20`

## Error Handling

- The system is designed to handle invalid inputs gracefully, prompting the user to re-enter values where necessary.
- If the system encounters a problem reading from or writing to a file, appropriate error messages will be displayed.

## Future Enhancements

- Adding more detailed logging for operations.
- Implementing a graphical user interface (GUI) for enhanced user experience.
- Enhancing data persistence mechanisms.
