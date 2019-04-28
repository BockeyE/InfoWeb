package com.InfoWeb.demo.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author bockey
 */
@Component
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        SimpleDateFormat formet = new SimpleDateFormat(
                "yyyy-MM-dd"
        );
        Date parse = null;
        try {
            parse = formet.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
}


