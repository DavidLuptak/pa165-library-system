package cz.muni.fi.pa165.library.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author Martin Mlynarik
 * @version 17.12.2016
 */
public class DateConverter implements Converter<String, LocalDateTime> {

    private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);

    //DD.MM.YYYY HH:mm
    @Override
    public LocalDateTime convert(String input) {
        try {
            logger.debug(input);
            String[] parsed = input.split(" ");
            String date = parsed[0];
            String time = parsed[1];

            String[] dateParsed = date.split("\\.");
            String[] timeParsed = time.split(":");

            return LocalDateTime.of(Integer.valueOf(dateParsed[2]),
                    Integer.valueOf(dateParsed[1]),
                    Integer.valueOf(dateParsed[0]),
                    Integer.valueOf(timeParsed[0]),
                    Integer.valueOf(timeParsed[1]));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}