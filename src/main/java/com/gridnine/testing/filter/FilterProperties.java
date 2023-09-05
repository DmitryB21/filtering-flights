package com.gridnine.testing.filter;

import com.gridnine.testing.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FilterProperties {

    Logger logger = LoggerFactory.getLogger(FilterProperties.class);

    private static final FilterProperties INSTANCE;
    private static final String PACKAGE = "com.gridnine.testing.filter.impl.";
    private static final File PROPERTY_FILE = new File("application.yaml");
    private static final String FILTERS = "filters";

    private final List<IFilter> filterList = new ArrayList<>();

    private FilterProperties() {

        Properties property = new Properties();

        try (FileReader fileReader = new FileReader(PROPERTY_FILE)) {
            property.load(fileReader);

             logger.info("Получение фильтров из application.yaml");
            String filtersProperty = property.getProperty(FILTERS);

            if (filtersProperty == null || filtersProperty.equals("")) {
                throw new IllegalArgumentException("В application.yaml фильтры не указаны !!!");
            }

            for (String flightFilter : filtersProperty.split(" ")) {
                addFilter(flightFilter);
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error("Файл application.yaml не найден", e);
        }
    }

    private void addFilter(String flightFilter) {
        try {
            filterList.add((IFilter)Class.forName(PACKAGE + flightFilter).getDeclaredConstructor().newInstance());
            logger.info("Добавлен фильтр: " + flightFilter);

        } catch (InstantiationException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException |
                 NoSuchMethodException | SecurityException |
                 ClassNotFoundException e) {
            logger.error("Filter: " + flightFilter +
                    " - Не верное имя фильтра", e);
        }
    }

    static {
        INSTANCE = new FilterProperties();
    }

    public static FilterProperties getInstance() {
        return INSTANCE;
    }

    public List<IFilter> getFilters() {
        return filterList;
    }
}
