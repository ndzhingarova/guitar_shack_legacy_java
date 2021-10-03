package com.guitarshack;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class StockMonitorTest {

    @Test
    public void alertSentWhenProductReachesReorderLevel() {
        Alert alert = mock(Alert.class);
        Product product = new Product(811, 27, 14);
        Service<Product> productService = mock(Service.class);
        when(productService.getObject(anyString())).thenReturn(product);
        Service<SalesTotal> salesTotalService = mock(Service.class);
        when(salesTotalService.getObject(anyString())).thenReturn(new SalesTotal());
        StockMonitor stockMonitor = new StockMonitor(alert, productService, new SalesHistory(salesTotalService), new Today());

        stockMonitor.productSold(811, 27);

        verify(alert).send(product);
    }

    @Test
    public void startDateIsSameDateAYearAgo() {
        SalesHistory salesHistory = mock(SalesHistory.class);
        when(salesHistory.getSalesTotal(any(),any(),any())).thenReturn(new SalesTotal());

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.YEAR, -1);
        Date startDate = calendar.getTime();


        Product product = new Product(811, 27, 14);
        Service<Product> productService = mock(Service.class);
        when(productService.getObject(anyString())).thenReturn(product);

        Today today = mock(Today.class);
        Calendar todayCalender = Calendar.getInstance();
        todayCalender.setTime(now);
        when(today.getCurrentCalendar()).thenReturn(todayCalender);

        StockMonitor stockMonitor = new StockMonitor(mock(Alert.class), productService, salesHistory, today);
        stockMonitor.productSold(811, 27);

        verify(salesHistory).getSalesTotal(any(),eq(startDate),any());
    }
}