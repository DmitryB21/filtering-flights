package com.gridnine.testing.filter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FilterProperties {

    private static final FilterProperties INSTANCE;
    private static final String PACKAGE = "com.gridnine.testing.filter.impl.";
    private static final File PROPERTY_FILE = new File("application.yaml");
    private static final String FILTERS = "filters";

    private final List<IFilter> filterList = new ArrayList<>();

    private FilterProperties() {

        Properties property = new Properties();

        try (FileReader fileReader = new FileReader(PROPERTY_FILE)) {
            property.load(fileReader);

//            Log.info(property.getProperty(CHECK_CONNECTION));
//            Log.info("Get filters from Application.yaml");
            String filtersProperty = property.getProperty(FILTERS);

            if (filtersProperty == null || filtersProperty.equals("")) {
                throw new IllegalArgumentException("В application.yaml фильтры не указаны !!!");
            }

            for (String flightFilter : filtersProperty.split(" ")) {
                addFilter(flightFilter);
            }
        } catch (IllegalArgumentException e) {
//            Log.error(e.getMessage(), e);
        } catch (IOException e) {
//            Log.error("Properties file not found! Put Application.yaml " +
//                    "in the same folder as FlightFilter.jar.", e);
//            Log.error("Flights are shown without filters: !!!\"", e);
        }
    }

    private void addFilter(String flightFilter) {
        try {
            filterList.add((IFilter)
                    Class.forName(PACKAGE + flightFilter)
                            .getDeclaredConstructor().newInstance());
//            Log.info("Filter: " + flightFilter);

        } catch (InstantiationException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException |
                 NoSuchMethodException | SecurityException |
                 ClassNotFoundException e) {
//            Log.error("Filter: " + flightFilter +
//                    " - Invalid filter name. Filter not applied. " +
//                    "Check Application.yaml !!!", e);
        }
    }

    static {
        INSTANCE = new FilterProperties();
    }

    public static FilterProperties getInstance() {
        return INSTANCE;
    }

    public List<IFilter> getFlightFilters() {
        return filterList;
    }
}
