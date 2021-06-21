package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.BonitovanyDil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class BonitovaneDilyParser implements TypeParser<List<BonitovanyDil>>{
    private final Element element;
    private final String prefix;

    public BonitovaneDilyParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
    }

    @Override
    public List<BonitovanyDil> parse(String attribute) {
        NodeList nList = element.getElementsByTagName(prefix + ":" + attribute); //BonitovaneDily

        if (nList.getLength() > 0) {
            List<BonitovanyDil> bonitovaneDily = new ArrayList<>();

            for (int i = 0; i < nList.getLength(); i++) {
                Element el = (Element) nList.item(i);

                Long vymeraBD = Long.parseLong(el.getElementsByTagName("com:Vymera")
                        .item(0).getTextContent());
                Integer bonitovaJednotkaKodBD = Integer.parseInt(el.getElementsByTagName("com:BonitovanaJednotkaKod")
                        .item(0).getTextContent());
                Long idTransakceBD = Long.parseLong(el.getElementsByTagName("com:IdTranskace")
                        .item(0).getTextContent());
                Long rizeniIdBD = Long.parseLong(el.getElementsByTagName("com:RizeniId")
                        .item(0).getTextContent());

                BonitovanyDil bonitovanyDil = new BonitovanyDil(vymeraBD, bonitovaJednotkaKodBD, idTransakceBD,
                        rizeniIdBD);

                bonitovaneDily.add(bonitovanyDil);
            }

            return bonitovaneDily;
        }

        return null;
    }
}
