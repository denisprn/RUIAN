package com.bp.RUIAN.parsers;

import com.bp.RUIAN.services.EsService;
import org.w3c.dom.Element;

public abstract class AbstractSaveParser implements Parser {
    final AttributeParser attributeParser;
    final EsService esService;

    protected AbstractSaveParser(EsService esService, Element element, String prefix) {
        this.attributeParser = new AttributeParser(element, prefix);
        this.esService = esService;
    }
}
