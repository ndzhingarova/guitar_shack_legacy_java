package com.guitarshack;

import java.util.Calendar;
import java.util.Date;

public class ReorderLevelCalculator {
    private final SalesHistory salesHistory;
    private final Today today;

    public ReorderLevelCalculator(SalesHistory salesHistory, Today today) {
        this.salesHistory = salesHistory;
        this.today = today;
    }

    int getReorderLevel(Product product) {
        Calendar calendar = today.getCurrentCalendar();

        calendar.add(Calendar.YEAR, -1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE,30);
        Date endDate = calendar.getTime();

        SalesTotal total = salesHistory.getSalesTotal(product, startDate, endDate);

        return (int) ((double) (total.getTotal() / 30) * product.getLeadTime());
    }
}
