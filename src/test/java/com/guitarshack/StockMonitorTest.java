package com.guitarshack;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class StockMonitorTest {

    private SalesHistory salesHistory;
    private Date startDate;
    private Product product;
    private Alert alert;
    private Date endDate;

    @Test
    public void alertSentWhenProductReachesReorderLevel() {

        verify(alert).send(product);
    }

    @Test
    public void startDateIsSameDateAYearAgo() {

        verify(salesHistory).getSalesTotal(any(),eq(startDate),any());
    }

    @Test
    public void endDateIs30DaysFromTodayAYearAgo() {


        verify(salesHistory).getSalesTotal(any(), any(), eq(endDate));
    }

    @Before
    public void setUp() {
        alert = mock(Alert.class);

        salesHistory = mock(SalesHistory.class);
        when(salesHistory.getSalesTotal(any(),any(),any())).thenReturn(new SalesTotal());

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.YEAR, -1);
        startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 30);
        endDate = calendar.getTime();


        product = new Product(811, 27, 14);
        Service<Product> productService = mock(Service.class);
        when(productService.getObject(anyString())).thenReturn(product);

        Today today = mock(Today.class);
        Calendar todayCalender = Calendar.getInstance();
        todayCalender.setTime(now);
        when(today.getCurrentCalendar()).thenReturn(todayCalender);

        StockMonitor stockMonitor = new StockMonitor(alert, new Warehouse(productService), new ReorderLevelCalculator(salesHistory, today));
        stockMonitor.productSold(811, 27);
    }

}