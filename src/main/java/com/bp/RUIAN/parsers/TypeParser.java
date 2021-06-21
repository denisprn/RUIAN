package com.bp.RUIAN.parsers;

import java.text.ParseException;

public interface TypeParser<T> {
    T parse(String attribute) throws ParseException;
}
