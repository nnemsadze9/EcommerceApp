public class Order {
    private Product product;
    private int sellingPrice;
    private int quantity;
public Order(Product product,int sellingPrice,int quantity){
    this.product = product;
    this.sellingPrice = sellingPrice;
    this.quantity = quantity;
}
    public Product getProduct() {
        return product;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "product=" + product +
                ", sellingPrice=" + sellingPrice +
                ", quantity=" + quantity +
                '}';
    }
}
