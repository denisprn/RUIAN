package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.DetailniTEA;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailniTeaParser implements TypeParser<List<DetailniTEA>>{
    private final Element element;
    private final String prefix;

    public DetailniTeaParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
    }

    @Override
    public List<DetailniTEA> parse(String attribute) throws ParseException {
        NodeList nListDetailniTea = element.getElementsByTagName(prefix + ":" + attribute); //DetailniTEA

        if (nListDetailniTea.getLength() > 0) {
            NodeList nodeListDTChildren = nListDetailniTea.item(0).getChildNodes();
            List <DetailniTEA> detailniTEAList = new ArrayList<>();

            for (int i = 0; i < nodeListDTChildren.getLength(); i++) {
                if (nodeListDTChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    boolean nespravnyDT = false;
                    Element el = (Element) nodeListDTChildren.item(i);

                    //AttributeParser attributeParser = new AttributeParser(el, prefix);
                    IntegerParser integerParser = new IntegerParser(el, prefix);
                    LongParser longParser = new LongParser(el, prefix);
                    DateParser dateParser = new DateParser(el, prefix);

                    Integer kodDT = integerParser.parse("Kod");
                    Date platiOdDT = dateParser.parse("PlatiOd");
                    Long globalniIdNavrhuZmenyDT = longParser.parse("GlobalniIdNavrhuZmeny");
                    Integer druhKonstrukceKodDT = integerParser.parse("DruhKonstrukceKod");
                    Integer pocetBytuDT = integerParser.parse("PocetBytu");
                    Integer pocetPodlaziDT = integerParser.parse("PocetPodlazi");
                    Integer pripojeniKanalizaceKodDT = integerParser.parse("PripojeniKanalizaceKod");
                    Integer pripojeniPlynKodDT = integerParser.parse("PripojeniPlynKod");
                    Integer pripojeniVodovodKodDT = integerParser.parse("PripojeniVodovodKod");
                    Integer zpusobVytapeniKodDT = integerParser.parse("ZpusobVytapeniKod");
                    Integer adresniMistoKodDT = integerParser.parse("base:Kod");

                    DetailniTEA detailniTEA = new DetailniTEA(kodDT, platiOdDT, nespravnyDT, globalniIdNavrhuZmenyDT,
                            druhKonstrukceKodDT, pocetBytuDT, pocetPodlaziDT, pripojeniKanalizaceKodDT, pripojeniPlynKodDT,
                            pripojeniVodovodKodDT, zpusobVytapeniKodDT, adresniMistoKodDT);

                    detailniTEAList.add(detailniTEA);
                }
            }

            return detailniTEAList;
        }

        return null;
    }
}
