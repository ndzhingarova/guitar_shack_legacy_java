package com.guitarshack;

public class StockMonitor {
    private final Alert alert;
    private final Warehouse warehouse;
    private final ReorderLevelCalculator reorderLevelCalculator;

    public StockMonitor(Alert alert, Warehouse warehouse, ReorderLevelCalculator reorderLevelCalculator) {
        this.alert = alert;
        this.warehouse = warehouse;
        this.reorderLevelCalculator = reorderLevelCalculator;
    }

    public void productSold(int productId, int quantity) {
        Product product = warehouse.getProduct(productId);

        int reorderLevel = reorderLevelCalculator.getReorderLevel(product);
        if (product.getStock() - quantity <= reorderLevel)
            alert.send(product);
    }

}
