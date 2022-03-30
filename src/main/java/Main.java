import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        SqlDbStore store = new SqlDbStore();
        System.out.println(store.getCustomerById(1).toString());
        System.out.println(store.getAllCustomers());
        System.out.println(store.insertCustomer(new Customer("Anotha one","Anotha one","Anotha one",null,"Anotha one","Anotha one","Anotha one","Anotha one")));
        store.updateCustomer(new Customer(1, "madnerd3", null, null, null, null, null, null, null));
        store.deleteCustomerById(5);
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(new OrderDetails(null, "PROD-3", 1));

        store.insertOrder(new Order(1), orderDetailsList);
        System.out.println(store.getAllOrdersByCustomerId(1));
        store.updateOrderComment(4, "Hello");
        store.updateOrderStatus(4, "SHIPPED");
    }
}
