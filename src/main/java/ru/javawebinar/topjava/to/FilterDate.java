package ru.javawebinar.topjava.to;

import java.time.LocalDate;
import java.time.LocalTime;

public class FilterDate
{
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalTime startTime;
    private final LocalTime endTime;


    public FilterDate()
    {
        this(null,null,null,null);
    }

    public FilterDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime)
    {
        this.startDate = (startDate == null ) ? LocalDate.MIN : startDate;
        this.endDate = (endDate == null) ? LocalDate.MAX : endDate;
        this.startTime = (startTime == null) ? LocalTime.MIN : startTime;
        this.endTime = (endTime == null) ? LocalTime.MAX : endTime;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public LocalTime getStartTime()
    {
        return startTime;
    }

    public LocalTime getEndTime()
    {
        return endTime;
    }

    @Override
    public String toString()
    {
        return "FilterDate{" + "startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime + ", endTime=" + endTime +
                '}';
    }
}
