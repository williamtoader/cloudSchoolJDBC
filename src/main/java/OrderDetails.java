public class OrderDetails {
    Integer id = null;
    String productCode = null;
    Integer quantity = null;

    public OrderDetails(Integer id, String productCode, Integer quantity) {
        this.id = id;
        this.productCode = productCode;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
