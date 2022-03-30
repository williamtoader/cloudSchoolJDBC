import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class Order {
    Date orderDate = Date.valueOf(LocalDate.now());
    Date shippingDate = Date.valueOf(LocalDate.now().plus(Period.ofDays(3)));
    String status = "PLACED";
    Integer customerId = null;
    String comment = "";

    public Order(Integer customerId) {
        this.customerId = customerId;
    }

    public Order(Date orderDate, Date shippingDate, String status, Integer customerId, String comment) {
        this.orderDate = orderDate;
        this.shippingDate = shippingDate;
        this.status = status;
        this.customerId = customerId;
        this.comment = comment;
    }

    public Order(String status, Integer customerId, String comment) {
        this.status = status;
        this.customerId = customerId;
        this.comment = comment;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderDate=" + orderDate +
                ", shippingDate=" + shippingDate +
                ", status='" + status + '\'' +
                ", customerId=" + customerId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
