package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.*;
import org.springframework.data.elasticsearch.core.geo.GeoJsonLineString;
import org.springframework.data.elasticsearch.core.geo.GeoJsonMultiLineString;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.springframework.data.geo.Point;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AttributeParser {

    private final Element element;
    private final String prefix;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public AttributeParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
        this.sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    Integer getKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Long getId() {
        NodeList nList = element.getElementsByTagName(prefix + ":Id");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    String getNazev() {
        NodeList nList = element.getElementsByTagName(prefix + ":Nazev");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    Integer getKodStatu() {
        NodeList nList = element.getElementsByTagName("sti:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodRS() {
        NodeList nList = element.getElementsByTagName("rsi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodVusc() {
        NodeList nList = element.getElementsByTagName("vci:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodSpravniObec() {
        NodeList nList = element.getElementsByTagName(prefix + ":SpravniObecKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodOkresu() {
        NodeList nList = element.getElementsByTagName("oki:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodOrp() {
        NodeList nList = element.getElementsByTagName("opi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getStatusKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":StatusKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodPou() {
        NodeList nList = element.getElementsByTagName("pui:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getSpravniMomcKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":SpravniMomcKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodObce() {
        NodeList nList = element.getElementsByTagName("obi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodMop() {
        NodeList nList = element.getElementsByTagName("mpi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getAdresniMistoKod() {
        NodeList nList = element.getElementsByTagName("base:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodSpravniObvod() {
        NodeList nList = element.getElementsByTagName("spi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodKatastralniUzemi() {
        NodeList nList = element.getElementsByTagName("kui:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodCastObce() {
        NodeList nList = element.getElementsByTagName("coi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodStavebniObjekt() {
        NodeList nList = element.getElementsByTagName("soi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKodUlice() {
        NodeList nList = element.getElementsByTagName("uli:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getVOKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":VOKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getCleneniSMRozsahKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":CleneniSMRozsahKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getCleneniSMTypKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":CleneniSMTypKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    String getCisloDomovni() {
        NodeList nList = element.getElementsByTagName(prefix + ":CisloDomovni");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    String getCisloOrientacni() {
        NodeList nList = element.getElementsByTagName(prefix + ":CisloOrientacni");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    String getCisloOrientacniPismeno() {
        NodeList nList = element.getElementsByTagName(prefix + ":CisloOrientacniPismeno");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    String getPSC() {
        NodeList nList = element.getElementsByTagName(prefix + ":Psc");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    Date getPlatiOd() throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":PlatiOd");

        if (nList.getLength() > 0) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(nList.item(0).getTextContent());
        }

        return null;
    }

    Date getPlatiDo() throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":PlatiDo");

        if (nList.getLength() > 0) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(nList.item(0).getTextContent());
        }

        return null;
    }

    Long getIdTransakce() {
        NodeList nList = element.getElementsByTagName(prefix + ":IdTransakce");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    Long getGlobalniIdNavrhuZmeny() {
        NodeList nList = element.getElementsByTagName(prefix + ":GlobalniIdNavrhuZmeny");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    Boolean getExistujeDigitalniMapa() {
        NodeList nList = element.getElementsByTagName(prefix + ":ExistujeDigitalniMapa");

        if (nList.getLength() > 0) {
            return Boolean.parseBoolean(nList.item(0).getTextContent());
        }

        return null;
    }

    Long getIdRizeni() {
        NodeList nList = element.getElementsByTagName(prefix + ":RizeniId");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    String getNutsLau() {
        NodeList nList = element.getElementsByTagName(prefix + ":NutsLau");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    Long getVymera() {
        NodeList nList = element.getElementsByTagName(prefix + ":Vymera");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getCharakterZsjKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":CharakterZsjKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getKmenoveCislo() {
        NodeList nList = element.getElementsByTagName(prefix + ":KmenoveCislo");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getPoddodeleniCisla() {
        NodeList nList = element.getElementsByTagName(prefix + ":PododdeleniCisla");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Long getVymeraParcely() {
        NodeList nList = element.getElementsByTagName(prefix + ":VymeraParcely");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getDruhCislovaniKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":DruhCislovaniKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getDruhPozemkuKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":DruhPozemkuKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    List<Integer> getCislaDomovni() {
        NodeList nListCislaDomovni = element.getElementsByTagName("com:CisloDomovni");

        if (nListCislaDomovni.getLength() > 0) {
            List<Integer> cislaDomovni = new ArrayList<>();

            for (int i = 0; i < nListCislaDomovni.getLength(); i++) {
                cislaDomovni.add(Integer.parseInt(nListCislaDomovni.item(i).getTextContent()));
            }

            return cislaDomovni;
        }

        return null;
    }

    Long getIdentifikacniParcelaId() {
        NodeList nList = element.getElementsByTagName("pai:Id");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getTypStavebnihoObjektuKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":TypStavebnihoObjektuKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getZpusobVyuzitiKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":ZpusobVyuzitiKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Long getIsknBudovaId() {
        NodeList nList = element.getElementsByTagName(prefix + ":IsknBudovaId");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    Date getDokonceni() throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":Dokonceni");

        if (nList.getLength() > 0) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getDruhKonstrukceKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":DruhKonstrukceKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getObestavenyProstor() {
        NodeList nList = element.getElementsByTagName(prefix + ":ObestavenyProstor");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getPocetBytu() {
        NodeList nList = element.getElementsByTagName(prefix + ":PocetBytu");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getPocetPodlazi() {
        NodeList nList = element.getElementsByTagName(prefix + ":PocetPodlazi");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getPodlahovaPlocha() {
        NodeList nList = element.getElementsByTagName(prefix + ":PodlahovaPlocha");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getPripojeniKanalizaceKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":PripojeniKanalizaceKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getPripojeniPlynKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":PripojeniPlynKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getPripojeniVodovodKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":PripojeniVodovodKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getVybaveniVytahemKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":VybaveniVytahemKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getZastavenaPlocha() {
        NodeList nList = element.getElementsByTagName(prefix + ":ZastavenaPlocha");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    Integer getZpusobVytapeniKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":ZpusobVytapeniKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    List<Integer> getZpusobyOchranyKod() {
        NodeList nList = element.getElementsByTagName(prefix + ":ZpusobOchranyKod");

        if (nList.getLength() > 0) {
            List<Integer> zpusobyOchranyKod = new ArrayList<>();

            for (int i = 0; i < nList.getLength(); i++) {
                zpusobyOchranyKod.add(Integer.parseInt(nList.item(i).getTextContent()));
            }

            return zpusobyOchranyKod;
        }

        return null;
    }

    List<DetailniTEA> getDetailniTea() throws ParseException {
        NodeList nListDetailniTea = element.getElementsByTagName(prefix + ":DetailniTEA");

        if (nListDetailniTea.getLength() > 0) {
            NodeList nodeListDTChildren = nListDetailniTea.item(0).getChildNodes();
            List <DetailniTEA> detailniTEAList = new ArrayList<>();

            for (int i = 0; i < nodeListDTChildren.getLength(); i++) {
                if (nodeListDTChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    boolean nespravnyDT = false;
                    Element el = (Element) nodeListDTChildren.item(i);

                    AttributeParser attributeParser = new AttributeParser(el, prefix);

                    Integer kodDT = attributeParser.getKod();
                    Date platiOdDT = attributeParser.getPlatiOd();
                    Long globalniIdNavrhuZmenyDT = attributeParser.getGlobalniIdNavrhuZmeny();
                    Integer druhKonstrukceKodDT = attributeParser.getDruhKonstrukceKod();
                    Integer pocetBytuDT = attributeParser.getPocetBytu();
                    Integer pocetPodlaziDT = attributeParser.getPocetPodlazi();
                    Integer pripojeniKanalizaceKodDT = attributeParser.getPripojeniKanalizaceKod();
                    Integer pripojeniPlynKodDT = attributeParser.getPripojeniPlynKod();
                    Integer pripojeniVodovodKodDT = attributeParser.getPripojeniVodovodKod();
                    Integer zpusobVytapeniKodDT = attributeParser.getZpusobVytapeniKod();
                    Integer adresniMistoKodDT = attributeParser.getAdresniMistoKod();

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

    GeoJsonPoint getDefinicniBod() {
        NodeList nList = element.getElementsByTagName("gml:pos");

        if (nList.getLength() > 0) {
            String definicniBod = nList.item(0).getTextContent();
            double x = Double.parseDouble(definicniBod.split(" ")[0]);
            double y = Double.parseDouble(definicniBod.split(" ")[1]);

            return GeoJsonPoint.of(x,y);
        }

        return null;
    }

    GeoJsonPolygon getHranice() {
        NodeList nList = element.getElementsByTagName("gml:posList");

        if (nList.getLength() > 0) {
            List<Point> points = new ArrayList<>();

            String[] posList = nList.item(0).getTextContent().split(" ");

            for (int i = 0; i < posList.length; i++) {
                double x = Double.parseDouble(posList[i]);
                double y = Double.parseDouble(posList[++i]);
                Point point = new Point(x, y);
                points.add(point);
            }

            return GeoJsonPolygon.of(points);
        }

        return null;
    }

    GeoJsonMultiLineString getDefinicniCara() {
        NodeList nList = element.getElementsByTagName("gml:posList");

        if (nList.getLength() > 0) {
            List<GeoJsonLineString> definicniCara = new ArrayList<>();

            for (int i = 0; i < nList.getLength(); i++) {
                List<Point> points = new ArrayList<>();

                String[] posList = nList.item(i).getTextContent().split(" ");

                for (int j = 0; j < posList.length; j++) {
                    double x = Double.parseDouble(posList[j]);
                    double y = Double.parseDouble(posList[++j]);
                    Point point = new Point(x, y);
                    points.add(point);
                }

                definicniCara.add(GeoJsonLineString.of(points));
            }

            return GeoJsonMultiLineString.of(definicniCara);
        }

        return null;
    }

    NespravnyUdaj getNespravneUdaje() throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":NespravneUdaje");

        if (nList.getLength() > 0) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String nazevUdaje = element.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(element.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = element.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();

            return new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        return null;
    }

    Date getDatumVzniku() throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":DatumVzniku");

        if (nList.getLength() > 0) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(nList.item(0).getTextContent());
        }

        return null;
    }

    MluvnickeCharakteristiky getMluvChar() {
        NodeList nList = element.getElementsByTagName(prefix + ":MluvnickeCharakteristiky");

        if (nList.getLength() > 0) {
            String pad2 = element.getElementsByTagName("com:Pad2").item(0).getTextContent();
            String pad3 = element.getElementsByTagName("com:Pad3").item(0).getTextContent();
            String pad4 = element.getElementsByTagName("com:Pad4").item(0).getTextContent();
            String pad6 = element.getElementsByTagName("com:Pad6").item(0).getTextContent();
            String pad7 = element.getElementsByTagName("com:Pad7").item(0).getTextContent();
            return new MluvnickeCharakteristiky(pad2, pad3, pad4, pad6, pad7);
        }

        return null;
    }

    List<BonitovanyDil> getBonitovaneDily() {
        NodeList nList = element.getElementsByTagName(prefix + ":BonitovaneDily");

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

    ZpusobOchranyPozemku getZpusobOchranyPozemku() {
        NodeList nList = element.getElementsByTagName(prefix + ":ZpusobyOchranyPozemku");

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

    String getVlajkaText() {
        NodeList nList = element.getElementsByTagName(prefix + ":VlajkaText");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    String getVlajkaObrazek() {
        NodeList nList = element.getElementsByTagName(prefix + ":VlajkaObrazek");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    String getZnakObrazek() {
        NodeList nList = element.getElementsByTagName(prefix + ":ZnakObrazek");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    String getZnakText() {
        NodeList nList = element.getElementsByTagName(prefix + ":ZnakText");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }
}
