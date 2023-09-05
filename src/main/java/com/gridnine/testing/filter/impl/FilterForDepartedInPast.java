package com.gridnine.testing.filter.impl;

import com.gridnine.testing.filter.IFilter;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.Segment;

import java.time.LocalDateTime;

public class FilterForDepartedInPast implements IFilter {

    @Override
    public boolean check(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            if (segment.getDepartureDate().isBefore(LocalDateTime.now())) {
                return false;
            }
        }
        return true;
    }
}
