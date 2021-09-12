package com.guitarshack;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Test;
public class StockMonitorTest {

    @Test
    public void alertSentWhenProductReachesReorderLevel() {
        Alert alert = mock(Alert.class);
        Product product = new Product(811, 27, 14);
        Service<Product> productService = mock(Service.class);
        when(productService.getObject(anyString())).thenReturn(product);
        Service<SalesTotal> salesTotalService = mock(Service.class);
        when(salesTotalService.getObject(anyString())).thenReturn(new SalesTotal());
        StockMonitor stockMonitor = new StockMonitor(alert, productService, salesTotalService);

        stockMonitor.productSold(811, 27);

        verify(alert).send(product);
    }
}