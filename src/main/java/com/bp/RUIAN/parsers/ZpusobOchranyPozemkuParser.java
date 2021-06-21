package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.ZpusobOchranyPozemku;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ZpusobOchranyPozemkuParser implements TypeParser<ZpusobOchranyPozemku>{
    private final Element element;
    private final String prefix;

    public ZpusobOchranyPozemkuParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
    }

    @Override
    public ZpusobOchranyPozemku parse(String attribute) {
        NodeList nList = element.getElementsByTagName(prefix + ":" + attribute);

        if (nList.getLength() > 0) {
            Integer kodZOP = Integer.parseInt(element.getElementsByTagName("com:Kod")
                    .item(0).getTextContent());
            Integer typOchranyKodZOP = Integer.parseInt(element.getElementsByTagName("com:TypOchranyKod")
                    .item(0).getTextContent());
            Long idTransakceZOP = Long.parseLong(element.getElementsByTagName("com:IdTransakce")
                    .item(0).getTextContent());
            Long rizeniIdZOP = Long.parseLong(element.getElementsByTagName("com:RizeniId")
                    .item(0).getTextContent());

            return new ZpusobOchranyPozemku(kodZOP, typOchranyKodZOP,
                    idTransakceZOP, rizeniIdZOP);
        }

        return null;
    }
}
