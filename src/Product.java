public class Product {
    private String productId;
    private String productName;
    private int productPrice;
    private int balance;
    private int numOfOrder;

    public Product(String productId, String productName, int productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        numOfOrder=0;
    }
    public void addBalance(int quantity) {
        this.balance +=quantity;
    }
    public boolean reduceBalance(int quantity) {
        if (this.balance >= quantity) {
            this.balance -= quantity;
            return true;  // successfully reduced balance
        } else {
            return false;  // insufficient stock
        }
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return balance;
    }


    public int getNumOfOrder() {
        return numOfOrder;
    }

    public void increaseNumOfOrders(int numOfOrder) {
        this.numOfOrder += numOfOrder;
    }
    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", balance=" + balance +
                '}';
    }
}
