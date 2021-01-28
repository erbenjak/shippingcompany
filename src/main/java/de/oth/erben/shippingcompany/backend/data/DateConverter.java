package de.oth.erben.shippingcompany.backend.data;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
* Since the web-interface returns the date from the picker as a String we need to convert it to a date whenever needed.
* */
public class DateConverter  implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        Date pickUpTime;
        try {
            pickUpTime = new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (ParseException e) {
            System.out.println("Date could not be parsed");
            pickUpTime = null;
        }

        return pickUpTime;
    }

    @Override
    public <U> Converter<String, U> andThen(Converter<? super Date, ? extends U> after) {
        return null;
    }
}
