package com.pid.proyecto.auxiliares;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class Convertidor {

    public LocalDate SqlDateToLocalDate(Date date) {

        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    public Date LocalDateToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }
}
