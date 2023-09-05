package com.gridnine.testing.filter.impl;

import com.gridnine.testing.filter.IFilter;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.Segment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FilterForMoreTwoHoursGroundTime implements IFilter {


    @Override
    public boolean check(Flight flight) {
        if (flight.getSegments().size() > 1) {
            List<Segment> segmentList = flight.getSegments();
            long groundTime = 0;
            int minutesInHour = 60;
            for (int i = 0; i < segmentList.size() - 1; i++) {
                LocalDateTime arrivalDate = segmentList.get(i).getArrivalDate();
                LocalDateTime departureDateNext = segmentList.get(i + 1).getDepartureDate();
                groundTime += arrivalDate.until(departureDateNext, ChronoUnit.MINUTES);
            }
            return groundTime / minutesInHour <= 2;
        }
        return true;


    }
}
