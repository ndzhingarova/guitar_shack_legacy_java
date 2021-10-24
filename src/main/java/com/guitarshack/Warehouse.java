package com.guitarshack;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final Service<Product> productService;

    public Warehouse(Service<Product> productService) {
        this.productService = productService;
    }

    Product getProduct(int productId) {
        Map<String, Object> params = new HashMap<>() {{
            put("id", productId);
        }};
        String paramString = "?";

        for (String key : params.keySet()) {
            paramString += key + "=" + params.get(key).toString() + "&";
        }

        return productService.getObject(paramString);
    }
}
