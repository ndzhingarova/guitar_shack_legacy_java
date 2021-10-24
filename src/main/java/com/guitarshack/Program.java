package com.guitarshack;

public class Program {

    private static StockMonitor monitor;

    static {
        final Service<SalesTotal> salesTotalService = new Service<>("https://gjtvhjg8e9.execute-api.us-east-2.amazonaws.com/default/sales", SalesTotal.class);
        Service<Product> productService = new Service<>("https://6hr1390c1j.execute-api.us-east-2.amazonaws.com/default/product", Product.class);
        SalesHistory salesHistory = new SalesHistory(salesTotalService);
        Today today = new Today();
        monitor = new StockMonitor(product -> {
            // We are faking this for now
            System.out.println(
                    "You need to reorder product " + product.getId() +
                            ". Only " + product.getStock() + " remaining in stock");
        },
                new Warehouse(productService), new ReorderLevelCalculator(salesHistory, today));
    }

    public static void main(String[] args) {
        int productId = Integer.parseInt(args[0]);
        int quantity = Integer.parseInt(args[1]);

        monitor.productSold(productId, quantity);
    }
}
