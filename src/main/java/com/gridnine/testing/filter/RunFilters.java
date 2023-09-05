package com.gridnine.testing.filter;

import com.gridnine.testing.flight.Flight;

import java.util.List;

public class RunFilters {

    public RunFilters() {
    }

    public static boolean checkAll(Flight flight) {

        List<IFilter> allFilters = FilterProperties
                .getInstance().getFilters();

        for (IFilter filter : allFilters) {
            if (!filter.check(flight))
                return false;
        }
        return true;
    }
}
