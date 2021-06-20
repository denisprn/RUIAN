package com.bp.RUIAN.parsers;

import java.text.ParseException;

public interface RecordParser<T> {
    T parse() throws ParseException;
}
