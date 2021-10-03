package com.guitarshack;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockMonitor {
    private final Alert alert;
    private final Service<Product> productService;
    private final Service<SalesTotal> salesTotalService;
    private final SalesHistory salesHistory;

    public StockMonitor(Alert alert, Service<Product> productService, Service<SalesTotal> salesTotalService, SalesHistory salesHistory) {
        this.alert = alert;
        this.productService = productService;
        this.salesTotalService = salesTotalService;
        this.salesHistory = salesHistory;
    }

    public void productSold(int productId, int quantity) {
        Product product = getProduct(productId);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Calendar.getInstance().getTime());
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DATE, -30);
        Date startDate = calendar.getTime();

        SalesTotal total = salesHistory.getSalesTotal(product, endDate, startDate);

        if (product.getStock() - quantity <= (int) ((double) (total.getTotal() / 30) * product.getLeadTime()))
            alert.send(product);
    }

    private Product getProduct(int productId) {
        Map<String, Object> params = new HashMap<>() {{
            put("id", productId);
        }};
        String paramString = "?";

        for (String key : params.keySet()) {
            paramString += key + "=" + params.get(key).toString() + "&";
        }
        Product product = productService.getObject(paramString);
        return product;
    }

}
