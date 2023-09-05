package com.gridnine.testing;

import com.gridnine.testing.filter.RunFilters;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        try {
            List<Flight> flights = FlightBuilder.createFlights();

            flights.stream()
                    .filter(RunFilters::checkAll)
                    .forEach(flight -> logger.info(flight.toString()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
