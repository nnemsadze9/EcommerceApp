import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    private static Map<String, Product> catalog = new HashMap<>();
    private static List<Order> purchased = new LinkedList<>();
    private static List<Order> sold = new LinkedList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String[] tokens = input.split(" ");
            String command = tokens[0];

            switch (command) {
                case "save_product":
                    String productId = tokens[1];
                    String productName = tokens[2];
                    int productPrice = Integer.parseInt(tokens[3]);
                    saveProduct(productId, productName, productPrice);
                    break;

                case "purchase_product":
                    String purchaseProductId = tokens[1];
                    int purchaseQuantity = Integer.parseInt(tokens[2]);
                    int purchasePrice = Integer.parseInt(tokens[3]);
                    purchaseProduct(purchaseProductId, purchaseQuantity, purchasePrice);
                    break;

                case "order_product":
                    String orderProductId = tokens[1];
                    int orderQuantity = Integer.parseInt(tokens[2]);
                    orderProduct(orderProductId, orderQuantity);
                    break;

                case "get_quantity_of_product":
                    String quantityProductId = tokens[1];
                    int quantity = getQuantityOfProduct(quantityProductId);
                    System.out.println(quantity);
                    break;

                case "get_average_price":
                    String averagePriceProductId = tokens[1];
                    double averagePrice = getAveragePrice(averagePriceProductId);
                    System.out.println(averagePrice);
                    break;

                case "get_product_profit":
                    String profitProductId = tokens[1];
                    double profit = getProductProfit(profitProductId);
                    System.out.println(profit);
                    break;

                case "get_fewest_product":
                    String fewestProduct = getFewestProduct();
                    System.out.println(fewestProduct);
                    break;

                case "get_most_popular_product":
                    String mostPopularProduct = getMostPopularProduct();
                    System.out.println(mostPopularProduct);
                    break;

                case "get_orders_report":
                    System.out.println(generateOrderReport());
                    break;

                case "export_orders_report":
                    exportOrdersReport(tokens[1]);
                    break;

                case "exit":
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid command.");
            }
        }
    }

    private static void saveProduct(String productId, String productName, int productPrice) {
        if (catalog.containsKey(productId)) {
            Product product = catalog.get(productId);
            product.setProductName(productName);
            product.setProductPrice(productPrice);
        } else {
            Product product = new Product(productId, productName, productPrice);
            catalog.put(productId, product);
        }
    }

    private static void purchaseProduct(String productId, int quantity, int price) {
        if (catalog.containsKey(productId)) {
            Product product = catalog.get(productId);
            product.addBalance(quantity);
            purchased.add(new Order(product,price,quantity));
        } else {
            System.out.println("Product does not exist in catalog.");
        }
    }

    private static void orderProduct(String productId, int quantity) {
        if (catalog.containsKey(productId)) {
            Product product = catalog.get(productId);
            boolean success = product.reduceBalance(quantity);
            if (!success) {
                System.out.println("Not enough product in stock.");
            }else{
                sold.add(new Order(product,product.getProductPrice(),quantity));
                product.increaseNumOfOrders(quantity);
            }
        } else {
            System.out.println("Product does not exist in catalog.");
        }
    }

    private static int getQuantityOfProduct(String productId) {
        if (catalog.containsKey(productId)) {
            Product product = catalog.get(productId);
            return product.getQuantity();
        } else {
            System.out.println("Product does not exist in catalog.");
            return 0;
        }
    }
    public static double getAveragePrice(String product_id) {
       return getAverage(product_id,purchased);
        }

    public static double getProductProfit(String product_id){
        double purchasedAverage = getAveragePrice(product_id);
        double soldAverage = getAverage(product_id,sold);
        double profitPerUnit = soldAverage-purchasedAverage;
        int soldQuantity=0;
        for(Order order : sold) {
            if(order.getProduct().getProductId().equals(product_id))
                soldQuantity += order.getQuantity();
            }
        return profitPerUnit*soldQuantity;
    }
    public static String getFewestProduct() {
        String productName = "";
        int minQuantity = Integer.MAX_VALUE;
        for (Product p : catalog.values()) {
            if (p.getQuantity() < minQuantity) {
                minQuantity = p.getQuantity();
                productName = p.getProductName();
            }
        }
        return productName;
    }
    public static String getMostPopularProduct() {
        String mostPopularProduct = "";
        int maxOrders = 0;
        for (Product p : catalog.values()) {
            if (p.getNumOfOrder() > maxOrders) {
                mostPopularProduct = p.getProductName();
                maxOrders = p.getNumOfOrder();
            }
        }
        return mostPopularProduct;
    }
    public static String generateOrderReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Order Report\n");
        reportBuilder.append("Product ID,Product Name,Quantity,Price,COGS,Selling Price\n");

        for (Order order : sold) {
            Product product = order.getProduct();
            double cogs = order.getSellingPrice() * order.getQuantity();
            reportBuilder.append(product.getProductId()).append(",")
                    .append(product.getProductName()).append(",")
                    .append(order.getQuantity()).append(",")
                    .append(product.getProductPrice()).append(",")
                    .append(cogs).append(",")
                    .append(order.getSellingPrice()).append("\n");
        }

        return reportBuilder.toString();
    }


    public static void exportOrdersReport(String path) {
        try {
            File file = new File(path);
            FileWriter writer = new FileWriter(file);
            writer.write(generateOrderReport());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static double getAverage(String product_id,List<Order> orderList){
        int totalAmountPaid = 0;
        int totalQuantity = 0;

        for(Order order : orderList) {
            if(order.getProduct().getProductId().equals(product_id)) {
                totalAmountPaid += order.getSellingPrice() * order.getQuantity();
                totalQuantity += order.getQuantity();
            }
        }

        if(totalQuantity == 0) {
            return 0;
        }
        return totalAmountPaid / totalQuantity;
    }
}