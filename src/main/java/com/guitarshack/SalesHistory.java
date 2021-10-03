package com.guitarshack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SalesHistory {
    private final Service<SalesTotal> salesTotalService;

    public SalesHistory(Service<SalesTotal> salesTotalService) {
        this.salesTotalService = salesTotalService;
    }

    SalesTotal getSalesTotal(Product product, Date endDate, Date startDate) {
        DateFormat format = new SimpleDateFormat("M/d/yyyy");
        Map<String, Object> params1 = new HashMap<>() {{
            put("productId", product.getId());
            put("startDate", format.format(startDate));
            put("endDate", format.format(endDate));
            put("action", "total");
        }};
        String paramString1 = "?";

        for (String key : params1.keySet()) {
            paramString1 += key + "=" + params1.get(key).toString() + "&";
        }

        return salesTotalService.getObject(paramString1);
    }
}
