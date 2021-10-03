package com.guitarshack;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockMonitor {
    private final Alert alert;
    private final Service<Product> productService;
    private final SalesHistory salesHistory;
    private final Today today;

    public StockMonitor(Alert alert, Service<Product> productService, SalesHistory salesHistory, Today today) {
        this.alert = alert;
        this.productService = productService;
        this.salesHistory = salesHistory;
        this.today = today;
    }

    public void productSold(int productId, int quantity) {
        Product product = getProduct(productId);

        Calendar calendar = today.getCurrentCalendar();
        Date endDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -1);
        Date startDate = calendar.getTime();

        SalesTotal total = salesHistory.getSalesTotal(product, startDate, endDate);

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
