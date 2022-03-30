import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlDbStore {
    // Connection constants
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/test";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWD = "testpasswd";

    Connection dbConnection = null;

    public SqlDbStore() throws SQLException {
        this.dbConnection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWD);
    }

    public SqlDbStore(String DB_URL, String DB_USERNAME, String DB_PASSWD) throws SQLException {
        this.dbConnection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWD);
    }

    public Customer getCustomerById(Integer id) {
        final String query = "SELECT * FROM customers WHERE id = ?;";

        try(PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if(!resultSet.next()) {
                throw new RuntimeException((String.format("No results returned for the customer with id %d", id)));
            }
            else {
                Customer result =  new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("postalCode"),
                        resultSet.getString("country")
                );
                if(resultSet.next())
                    throw new RuntimeException((String.format("Multiple occurrences for the customer with id %d", id)));
                else return result;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException((String.format("There was a problem fetching the customer with id %d,\n%s", id, e.toString())));
        }
    }

    public List<Customer> getAllCustomers() {
        final String query = "SELECT * FROM customers;";
        List<Customer> customerList = new ArrayList<>();
        try(PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Customer result = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("postalCode"),
                        resultSet.getString("country")
                );
                customerList.add(result);
            }
            return customerList;
        }
        catch (SQLException e) {
            throw new RuntimeException((String.format("There was a problem fetching the customer list,\n%s", e.toString())));
        }
    }

    public Integer insertCustomer(Customer customer) {
        final String query = "INSERT INTO customers (username, first_name, last_name, phone, address, city, postalCode, country) VALUES (?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getCity());
            stmt.setString(7, customer.getPostalCode());
            stmt.setString(8, customer.getCountry());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }

        } catch (SQLException e) {
            throw new RuntimeException((String.format("There was a problem adding the customer to the database,\n%s", e.toString())));
        }
    }

    public void updateCustomer(Customer customer) {
        String params = "";
        List<String> paramValues = new ArrayList<>();

        if(customer.getUsername() != null) {
            params += "username = ?, ";
            paramValues.add(customer.getUsername());
        }
        if(customer.getFirstName() != null) {
            params += "first_name = ?, ";
            paramValues.add(customer.getFirstName());
        }
        if(customer.getLastName() != null) {
            params += "last_name = ?, ";
            paramValues.add(customer.getUsername());
        }
        if(customer.getPhone() != null) {
            params += "phone = ?, ";
            paramValues.add(customer.getPhone());
        }
        if(customer.getAddress() != null) {
            params += "address = ?, ";
            paramValues.add(customer.getAddress());
        }
        if(customer.getCity() != null) {
            params += "city = ?, ";
            paramValues.add(customer.getCity());
        }
        if(customer.getPostalCode() != null) {
            params += "postalCode = ?, ";
            paramValues.add(customer.getPostalCode());
        }

        if(params.length() > 0)
            params = params.substring(0, params.length() - 2);
        final String query = "UPDATE customers SET " + params + " WHERE id = ?;";
        System.out.println(query);
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(query);
            int i;
            for(i = 1; i <= paramValues.size(); i++) {
                stmt.setString(i, paramValues.get(i - 1));
            }
            stmt.setInt(i, customer.getId());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating customer failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException((String.format("There was a problem updating the customer in the database,\n%s", e.toString())));
        }
    }

    public void deleteCustomerById(Integer id) {
        final String query = "DELETE FROM customers WHERE id = ?";
        try(PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeQuery();
        }
        catch (SQLException e) {
            throw new RuntimeException(String.format("There was a problem deleting customer with id %d from the database,\n %s", id, e.toString()));
        }
    }

    //place order and update stock

    public Integer insertOrder(Order order, List<OrderDetails> orderDetails) throws InterruptedException{

        final String query = "INSERT INTO orders (customer_id, order_date, shipped_date, status, comments) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println("Cid: " + order.getCustomerId());
            stmt.setInt(1, order.getCustomerId());
            stmt.setDate(2, order.getOrderDate());
            stmt.setDate(3, order.getShippingDate());
            stmt.setString(4, order.getStatus());
            stmt.setString(5, order.getComment());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer id = generatedKeys.getInt(1);
                for(OrderDetails details: orderDetails) {
                    details.setId(id);
                    insertOrderDetails(details);
                }
                return id;
            }
            else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }

        } catch (SQLException e) {
            throw new RuntimeException((String.format("There was a problem adding the order to the database,\n%s", e.toString())));
        }
    }

    public void insertOrderDetails(OrderDetails details) {
        final String query = "INSERT INTO orderdetails(id, product_code, quantity) VALUES (?,?,?);";
        final String updateStockQuery = "UPDATE products SET stock = stock - ? WHERE code = ?";
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement updateStockStmt = dbConnection.prepareStatement(updateStockQuery);
            stmt.setInt(1, details.getId());
            stmt.setString(2, details.getProductCode());
            stmt.setInt(3, details.getQuantity());
            updateStockStmt.setInt(1, details.getQuantity());
            updateStockStmt.setString(2, details.getProductCode());
            updateStockStmt.executeQuery();
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order details failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException((String.format("There was a problem adding the order details to the database,\n%s", e.toString())));
        }
    }

    public List<Order> getAllOrdersByCustomerId(Integer customerId) {
        final String query = "SELECT * FROM orders WHERE customer_id = ?;";
        List<Order> orderList = new ArrayList<>();
        try(PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Order result = new Order(
                        resultSet.getDate("order_date"),
                        resultSet.getDate("shipped_date"),
                        resultSet.getString("status"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("comments")
                );
                orderList.add(result);
            }
            return orderList;
        }
        catch (SQLException e) {
            throw new RuntimeException((String.format("There was a problem fetching the order list,\n%s", e.toString())));
        }
    }

    public void updateOrderStatus(Integer orderId, String status) {
        final String query = "UPDATE orders SET status = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(query);
            int i;
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating order failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException((String.format("There was a problem updating the order in the database,\n%s", e.toString())));
        }
    }

    public void updateOrderComment(Integer orderId, String comment) {
        final String query = "UPDATE orders SET comments = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(query);
            int i;
            stmt.setString(1, comment);
            stmt.setInt(2, orderId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating order failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException((String.format("There was a problem updating the order in the database,\n%s", e.toString())));
        }
    }

}
