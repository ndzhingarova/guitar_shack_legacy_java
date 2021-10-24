package com.guitarshack;

import java.util.Calendar;
import java.util.Date;

public class DateRange {
    private final Date startDate;
    private final Date endDate;

    public DateRange(Today today) {
        Calendar calendar = today.getCurrentCalendar();

        calendar.add(Calendar.YEAR, -1);
        startDate = calendar.getTime();
        calendar.add(Calendar.DATE,30);
        endDate = calendar.getTime();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
