package ch.css.m3000.banking.adapter;

import java.time.LocalDate;

public class SystemDateTimeAdapter implements DateTimeAdapter {
    @Override
    public LocalDate currentDate() {
        return LocalDate.now();
    }
}
