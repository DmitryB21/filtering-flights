package com.gridnine.testing.filter.impl;

import com.gridnine.testing.filter.IFilter;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.Segment;

import java.time.LocalDateTime;
import java.util.List;

public class FilterForDepartsBeforeArrives implements IFilter {


    @Override
    public boolean check(Flight flight) {

        if (flight.getSegments().size() == 1) {
            // One segment flight
            LocalDateTime departureDate = flight.getSegments().get(0).getDepartureDate();
            LocalDateTime arrivalDate = flight.getSegments().get(0).getArrivalDate();

            return !departureDate.isAfter(arrivalDate);
        } else {
            List<Segment> segmentList = flight.getSegments();
            for (int i = 0; i < segmentList.size() - 1; i++) {
                LocalDateTime departureDate = segmentList.get(i).getDepartureDate();
                LocalDateTime arrivalDate = segmentList.get(i).getArrivalDate();
                LocalDateTime departureDateNext = segmentList.get(i + 1).getDepartureDate();

                if (departureDate.isAfter(arrivalDate) || arrivalDate.isAfter(departureDateNext)) {
                    return false;
                }
            }
        }
        return true;
    }

}
