package com.bp.RUIAN;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.services.EsService;
import org.springframework.data.geo.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Source directory parser class
 * <p>
 *     This class recursively parse all XML files in the given directory
 * </p>
 * <p>
 *     This class also save parsed records to repositories
 * </p>
 * @author Denys Peresychanskyi
 */
public class FilesParser {
    private final EsService esService;

    public FilesParser(EsService esService) {
        this.esService = esService;
    }

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Method for recursively walking through all directories
     * @param directoryPath path of the root directory, that contains files or other directories
     */
    public void walk(String directoryPath) {
        File root = new File(directoryPath);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
            }
            else if (f.getName().endsWith(".xml")) {
                parseFile(f.getAbsolutePath());
            }
        }
    }

    /**
     * Main method for parsing given files
     * @param filePath path of the file to parse
     */
    private void parseFile(String filePath) {
        try {
            File file = new File(filePath);

            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            //get list of "Data" elements
            NodeList nListDataNode = document.getElementsByTagName("vf:Data");

            //get child nodes of "Data"
            NodeList nodeListData = nListDataNode.item(0).getChildNodes();

            for (int i = 0; i < nodeListData.getLength(); i++) {
                if (nodeListData.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    NodeList nodeL = nodeListData.item(i).getChildNodes();

                    for (int j = 0; j < nodeL.getLength(); j++) {
                        Node node = nodeL.item(j);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;

                            switch (node.getNodeName()) {
                                case "vf:Stat" -> {
                                    parseStatElement(element);
                                }
                                case "vf:RegionSoudrznosti" -> {
                                    parseRegionSoudrznostiElement(element);
                                }
                                case "vf:Vusc" -> {
                                    parseVuscElement(element);
                                }
                                case "vf:Okres" -> {
                                    parseOkresElement(element);
                                }
                                case "vf:Orp" -> {
                                    parseOrpElement(element);
                                }
                                case "vf:Pou" -> {
                                    parsePouElement(element);
                                }
                                case "vf:Obec" -> {
                                    parseObecElement(element);
                                }
                                case "vf:SpravniObvod" -> {
                                    parseSpravniObvodElement(element);
                                }
                                case "vf:Mop" -> {
                                    parseMopElement(element);
                                }
                                case "vf:Momc" -> {
                                    parseMomcElement(element);
                                }
                                case "vf:CastObce" -> {
                                    parseCastObceElement(element);
                                }
                                case "vf:KatastralniUzemi" -> {
                                    parseKatastralniUzemiElement(element);
                                }
                                case "vf:Zsj" -> {
                                    parseZsjElement(element);
                                }
                                case "vf:Ulice" -> {
                                    parseUliceElement(element);
                                }
                                case "vf:Parcela" -> {
                                    parseParcelaElement(element);
                                }
                                case "vf:StavebniObjekt" -> {
                                    parseStavebniObjektElement(element);
                                }
                                case "vf:AdresniMisto" -> {
                                    parseAdresniMistoElement(element);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Parse record's 'kod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer kod or null
     */
    private Integer getKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'id' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Long id or null
     */
    private Long getId(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":Id");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'nazev' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String nazev or null
     */
    private String getNazev(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":Nazev");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse record's 'kodStatu' attribute
     * @param element xml element
     * @return Integer kodStatu or null
     */
    private Integer getKodStatu(Element element) {
        NodeList nList = element.getElementsByTagName("sti:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodRegionuSoudrznosti' attribute
     * @param element xml element
     * @return Integer kodRegionuSoudrznosti or null
     */
    private Integer getKodRS(Element element) {
        NodeList nList = element.getElementsByTagName("rsi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodVusc' attribute
     * @param element xml element
     * @return Integer kodVusc or null
     */
    private Integer getKodVusc(Element element) {
        NodeList nList = element.getElementsByTagName("vci:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodVusc' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer kodVusc or null
     */
    private Integer getKodSpravniObec(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":SpravniObecKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodOkresu' attribute
     * @param element xml element
     * @return Integer kodOkresu or null
     */
    private Integer getKodOkresu(Element element) {
        NodeList nList = element.getElementsByTagName("oki:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodOrp' attribute
     * @param element xml element
     * @return Integer kodOrp or null
     */
    private Integer getKodOrp(Element element) {
        NodeList nList = element.getElementsByTagName("opi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'statusKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer statusKod or null
     */
    private Integer getStatusKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":StatusKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodPou' attribute
     * @param element xml element
     * @return Integer kodPou or null
     */
    private Integer getKodPou(Element element) {
        NodeList nList = element.getElementsByTagName("pui:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodSpravniMomc' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer kodSpravniMomc or null
     */
    private Integer getSpravniMomcKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + "SpravniMomcKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodObce' attribute
     * @param element xml element
     * @return Integer kodObce or null
     */
    private Integer getKodObce(Element element) {
        NodeList nList = element.getElementsByTagName("obi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodMop' attribute
     * @param element xml element
     * @return Integer kodmop or null
     */
    private Integer getKodMop(Element element) {
        NodeList nList = element.getElementsByTagName("mpi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'adresniMistoKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer adresniMistoKod or null
     */
    private Integer getAdresniMistoKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodSpravniObvod' attribute
     * @param element xml element
     * @return Integer kodSpravniObvod or null
     */
    private Integer getKodSpravniObvod(Element element) {
        NodeList nList = element.getElementsByTagName("spi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodKatastralniUzemi' attribute
     * @param element xml element
     * @return Integer kodKatastralniUzemi or null
     */
    private Integer getKodKatastralniUzemi(Element element) {
        NodeList nList = element.getElementsByTagName("kui:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodCastObce' attribute
     * @param element xml element
     * @return Integer kodCastObce or null
     */
    private Integer getKodCastObce(Element element) {
        NodeList nList = element.getElementsByTagName("coi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodStavebniObjekt' attribute
     * @param element xml element
     * @return Integer kodStavebniObjekt or null
     */
    private Integer getKodStavebniObjekt(Element element) {
        NodeList nList = element.getElementsByTagName("soi:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kodUlice' attribute
     * @param element xml element
     * @return Integer kodUlice or null
     */
    private Integer getKodUlice(Element element) {
        NodeList nList = element.getElementsByTagName("uli:Kod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'voKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer voKod or null
     */
    private Integer getVOKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":VOKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'cleneniSMRozsahKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer cleneniSMRozsahKod or null
     */
    private Integer getCleneniSMRozsahKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":CleneniSMRozsahKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'cleneniSMTypKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer cleneniSMTypKod or null
     */
    private Integer getCleneniSMTypKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":CleneniSMTypKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'cleneniSMTypKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String cleneniSMTypKod or null
     */
    private String getCisloDomovni(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":CisloDomovni");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse record's 'cisloOrientacni' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String cisloOrientacni or null
     */
    private String getCisloOrientacni(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":CisloOrientacni");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse record's 'cisloOrientacniPismeno' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String cisloOrientacniPismeno or null
     */
    private String getCisloOrientacniPismeno(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":CisloOrientacniPismeno");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse record's 'psc' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String psc or null
     */
    private String getPSC(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":Psc");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse record's 'platiOd' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Date platiOd or null
     * @throws ParseException if an error occurs during parsing
     */
    private Date getPlatiOd(Element element, String prefix) throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":PlatiOd");

        if (nList.getLength() > 0) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'platiDo' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Date platiDo or null
     * @throws ParseException if an error occurs during parsing
     */
    private Date getPlatiDo(Element element, String prefix) throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":PlatiDo");

        if (nList.getLength() > 0) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'idTransakce' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Long idTransakce or null
     */
    private Long getIdTransakce(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":IdTransakce");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'globalniIdNavrhuZmeny' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Long globalniIdNavrhuZmeny or null
     */
    private Long getGlobalniIdNavrhuZmeny(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":GlobalniIdNavrhuZmeny");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'existujeDigitalniMapa' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Boolean existujeDigitalniMapa or null
     */
    private Boolean getExistujeDigitalniMapa(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":ExistujeDigitalniMapa");

        if (nList.getLength() > 0) {
            return Boolean.parseBoolean(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'idRizeni' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Long idRizeni or null
     */
    private Long getIdRizeni(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":RizeniId");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'nutsLau' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String nutsLau or null
     */
    private String getNutsLau(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":NutsLau");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse record's 'vymera' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Long vymera or null
     */
    private Long getVymera(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":Vymera");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'charakterZsjKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer charakterZsjKod or null
     */
    private Integer getCharakterZsjKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":CharakterZsjKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'kmenoveCislo' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer kmenoveCislo or null
     */
    private Integer getKmenoveCislo(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":KmenoveCislo");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'poddodeleniCisla' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer poddodeleniCisla or null
     */
    private Integer getPoddodeleniCisla(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":PododdeleniCisla");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'vymeraParcely' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Long vymeraParcely or null
     */
    private Long getVymeraParcely(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":VymeraParcely");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'druhCislovaniKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer druhCislovaniKod or null
     */
    private Integer getDruhCislovaniKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":DruhCislovaniKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'druhPozemkuKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer druhPozemkuKod or null
     */
    private Integer getDruhPozemkuKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":DruhPozemkuKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'cislaDomovni' attribute
     * @param element xml element
     * @return List of Integer cislaDomovni or null
     */
    private List<Integer> getCislaDomovni(Element element) {
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

    /**
     * Parse record's 'identifikacniParcelaId' attribute
     * @param element xml element
     * @return Long dentifikacniParcelaId or null
     */
    private Long getIdentifikacniParcelaId(Element element) {
        NodeList nList = element.getElementsByTagName("pai:Id");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'typStavebnihoObjektuKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer typStavebnihoObjektuKod or null
     */
    private Integer getTypStavebnihoObjektuKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":TypStavebnihoObjektuKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'zpusobVyuzitiKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer zpusobVyuzitiKod or null
     */
    private Integer getZpusobVyuzitiKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":ZpusobVyuzitiKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'isknBudovaId' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Long isknBudovaId or null
     */
    private Long getIsknBudovaId(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + "IsknBudovaId");

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'dokonceni' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Date dokonceni or null
     * @throws ParseException if an error occurs during parsing
     */
    private Date getDokonceni(Element element, String prefix) throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":Dokonceni");

        if (nList.getLength() > 0) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'dokonceni' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Date dokonceni or null
     */
    private Integer getDruhKonstrukceKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":DruhKonstrukceKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'obestavenyProstor' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer obestavenyProstor or null
     */
    private Integer getObestavenyProstor(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":ObestavenyProstor");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'pocetBytu' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer pocetBytu or null
     */
    private Integer getPocetBytu(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":PocetBytu");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'pocetPodlazi' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer pocetPodlazi or null
     */
    private Integer getPocetPodlazi(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":PocetPodlazi");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'podlahovaPlocha' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer podlahovaPlocha or null
     */
    private Integer getPodlahovaPlocha(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":PodlahovaPlocha");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'pripojeniKanalizaceKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer pripojeniKanalizaceKod or null
     */
    private Integer getPripojeniKanalizaceKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":PripojeniKanalizaceKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'pripojeniPlynKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer pripojeniPlynKod or null
     */
    private Integer getPripojeniPlynKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":PripojeniPlynKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'pripojeniVodovodKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer pripojeniVodovodKod or null
     */
    private Integer getPripojeniVodovodKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":PripojeniVodovodKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'vybaveniVytahemKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer vybaveniVytahemKod or null
     */
    private Integer getVybaveniVytahemKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":VybaveniVytahemKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'zastavenaPlocha' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer zastavenaPlocha or null
     */
    private Integer getZastavenaPlocha(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":ZastavenaPlocha");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'zpusobVytapeniKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Integer zpusobVytapeniKod or null
     */
    private Integer getZpusobVytapeniKod(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":ZpusobVytapeniKod");

        if (nList.getLength() > 0) {
            return Integer.parseInt(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'zpusobOchranyKod' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return List of Integer zpusobOchranyKod or null
     */
    private List<Integer> getZpusobyOchranyKod(Element element, String prefix) {
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

    /**
     * Parse record's 'detailniTea' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return List of DetailniTEA detailniTea or null
     * @throws ParseException if an error occurs during parsing
     */
    private List<DetailniTEA> getDetailniTea(Element element, String prefix) throws ParseException {
        NodeList nListDetailniTea = element.getElementsByTagName(prefix + ":DetailniTEA");

        if (nListDetailniTea.getLength() > 0) {
            NodeList nodeListDTChildren = nListDetailniTea.item(0).getChildNodes();
            List <DetailniTEA> detailniTEAList = new ArrayList<>();

            for (int i = 0; i < nodeListDTChildren.getLength(); i++) {
                if (nodeListDTChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    boolean nespravnyDT = false;
                    Element el = (Element) nodeListDTChildren.item(i);

                    Integer kodDT = getKod(el, prefix);
                    Date platiOdDT = getPlatiOd(el, prefix);
                    Long globalniIdNavrhuZmenyDT = getGlobalniIdNavrhuZmeny(el, prefix);
                    Integer druhKonstrukceKodDT = getDruhKonstrukceKod(el, prefix);
                    Integer pocetBytuDT = getPocetBytu(el, prefix);
                    Integer pocetPodlaziDT = getPocetPodlazi(el, prefix);
                    Integer pripojeniKanalizaceKodDT = getPripojeniKanalizaceKod(el, prefix);
                    Integer pripojeniPlynKodDT = getPripojeniPlynKod(el, prefix);
                    Integer pripojeniVodovodKodDT = getPripojeniVodovodKod(el, prefix);
                    Integer zpusobVytapeniKodDT = getZpusobVytapeniKod(el, prefix);
                    Integer adresniMistoKodDT = getAdresniMistoKod(el, "base");

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

    /**
     * Parse record's 'pos' attribute
     * @param element xml element
     * @return Point pos or null
     */
    private Point getPos(Element element) {
        NodeList nList = element.getElementsByTagName("gml:pos");

        if (nList.getLength() > 0) {
            String position = nList.item(0).getTextContent();
            double x = Double.parseDouble(position.split(" ")[0]);
            double y = Double.parseDouble(position.split(" ")[1]);
            return new Point(x,y);
        }

        return null;
    }

    /**
     * Parse record's 'nespravnyUdaj' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return NespravnyUdaj nespravnyUdaj or null
     * @throws ParseException if an error occurs during parsing
     */
    private NespravnyUdaj getNespravneUdaje(Element element, String prefix) throws ParseException {
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

    /**
     * Parse record's 'datumVzniku' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return Date datumVzniku or null
     * @throws ParseException if an error occurs during parsing
     */
    private Date getDatumVzniku(Element element, String prefix) throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":DatumVzniku");

        if (nList.getLength() > 0) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(nList.item(0).getTextContent());
        }

        return null;
    }

    /**
     * Parse record's 'mluvChar' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return MluvnickeCharakteristiky mluvChar or null
     */
    private MluvnickeCharakteristiky getMluvChar(Element element, String prefix) {
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

    /**
     * Parse record's 'bonitovaneDily' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return List of BonitovanyDil bonitovaneDily or null
     */
    private List<BonitovanyDil> getBonitovaneDily(Element element, String prefix) {
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

    /**
     * Parse record's 'zpusobOchranyPozemku' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return ZpusobOchranyPozemku zpusobOchranyPozemku or null
     */
    private ZpusobOchranyPozemku getZpusobOchranyPozemku(Element element, String prefix) {
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

    /**
     * Parse record's 'vlajkaText' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String vlajkaText or null
     */
    private String getVlajkaText(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":VlajkaText");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse record's 'vlajkaObrazek' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String vlajkaObrazek or null
     */
    private String getVlajkaObrazek(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":VlajkaObrazek");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse record's 'znakObrazek' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String znakObrazek or null
     */
    private String getZnakObrazek(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":ZnakObrazek");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse record's 'znakText' attribute
     * @param element xml element
     * @param prefix xml prefix
     * @return String znakText or null
     */
    private String getZnakText(Element element, String prefix) {
        NodeList nList = element.getElementsByTagName(prefix + ":ZnakText");

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Parse and save Stat record to StatRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseStatElement(Element element) throws ParseException {
        String prefix = "sti";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        String nutsLau = getNutsLau(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        Stat stat = new Stat(kod, nazev, nespravny, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
                nutsLau, pos, nespravnyUdaj, datumVzniku);

        esService.saveStat(stat);
    }

    /**
     * Parse and save RegionSoudrznosti record to RegionSoudrznostiRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseRegionSoudrznostiElement(Element element) throws ParseException {
        String prefix = "rsi";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer kodStatu = getKodStatu(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        String nutsLau = getNutsLau(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        RegionSoudrznosti regionSoudrznosti = new RegionSoudrznosti(kod, nazev, nespravny, kodStatu,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nutsLau, pos,
                nespravnyUdaj, datumVzniku);

        esService.saveRs(regionSoudrznosti);
    }

    /**
     * Parse and save Vusc record to VuscRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseVuscElement(Element element) throws ParseException {
        String prefix = "vci";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer kodRs = getKodRS(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        String nutsLau = getNutsLau(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        Vusc vusc = new Vusc(kod, nazev, nespravny, kodRs, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, nutsLau, pos, nespravnyUdaj, datumVzniku);
        esService.saveVusc(vusc);
    }

    /**
     * Parse and save Okres record to OkresRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseOkresElement(Element element) throws ParseException {
        String prefix = "oki";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer kodVusc = getKodVusc(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        String nutsLau = getNutsLau(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        Okres okres = new Okres(kod, nazev, nespravny, kodVusc, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, nutsLau, pos, nespravnyUdaj, datumVzniku);

        esService.saveOkres(okres);
    }

    /**
     * Parse and save Orp record to OrpRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseOrpElement(Element element) throws ParseException {
        String prefix = "oki";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer spravniObecKod = getKodSpravniObec(element, prefix);
        Integer kodOkresu = getKodOkresu(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        Orp orp = new Orp(kod, nazev, nespravny, spravniObecKod, kodOkresu, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);
        esService.saveOrp(orp);
    }

    /**
     * Parse and save Pou record to PouRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parsePouElement(Element element) throws ParseException {
        String prefix = "pui";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer spravniObecKod = getKodSpravniObec(element, prefix);
        Integer kodOrp = getKodOrp(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        Pou pou = new Pou(kod, nazev, nespravny, spravniObecKod, kodOrp, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);
        esService.savePou(pou);
    }

    /**
     * Parse and save Obec record to ObecRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseObecElement(Element element) throws ParseException {
        String prefix = "obi";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer statusKod = getStatusKod(element, prefix);
        Integer kodOkresu = getKodOkresu(element);
        Integer kodPou = getKodPou(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        MluvnickeCharakteristiky mluvChar = getMluvChar(element, prefix);
        String vlajkaText = getVlajkaText(element, prefix);
        String vlajkaObrazek = getVlajkaObrazek(element, prefix);
        String znakText = getZnakText(element, prefix);
        String znakObrazek = getZnakObrazek(element, prefix);
        Integer cleneniSMRozsahKod = getCleneniSMRozsahKod(element, prefix);
        Integer cleneniSMTypKod = getCleneniSMTypKod(element, prefix);
        String nutsLau = getNutsLau(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        Obec obec = new Obec(kod, nazev, nespravny, statusKod, kodOkresu, kodPou, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, mluvChar, vlajkaText, vlajkaObrazek, znakText, znakObrazek,
                cleneniSMRozsahKod, cleneniSMTypKod, nutsLau, pos, nespravnyUdaj, datumVzniku);
        esService.saveObec(obec);
    }

    /**
     * Parse and save SpravniObvod record to SpravniObvodRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseSpravniObvodElement(Element element) throws ParseException {
        String prefix = "spi";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer spravniMomcKod = getSpravniMomcKod(element, prefix);
        Integer kodObce = getKodObce(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        SpravniObvod spravniObvod = new SpravniObvod(kod, nazev, nespravny, spravniMomcKod, kodObce,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);
        esService.saveSO(spravniObvod);
    }

    /**
     * Parse and save Mop record to MopRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseMopElement(Element element) throws ParseException {
        String prefix = "mpi";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer kodObce = getKodObce(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        Mop mop = new Mop(kod, nazev, nespravny, kodObce, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);
        esService.saveMop(mop);
    }

    /**
     * Parse and save Momc record to MomcRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseMomcElement(Element element) throws ParseException {
        String prefix = "mci";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer kodMop = getKodMop(element);
        Integer kodObce = getKodObce(element);
        Integer kodSpravniObvod = getKodSpravniObvod(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        MluvnickeCharakteristiky mluvChar = getMluvChar(element, prefix);
        String vlajkaText = getVlajkaText(element, prefix);
        String vlajkaObrazek = getVlajkaObrazek(element, prefix);
        String znakText = getZnakText(element, prefix);
        String znakObrazek = getZnakObrazek(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        Momc momc = new Momc(kod, nazev, nespravny, kodMop, kodObce, kodSpravniObvod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, vlajkaText, vlajkaObrazek,
                mluvChar, znakText, znakObrazek, pos, nespravnyUdaj, datumVzniku);
        esService.saveMomc(momc);
    }

    /**
     * Parse and save CastObce record to CastObceRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseCastObceElement(Element element) throws ParseException {
        String prefix = "coi";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer kodObce = getKodObce(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        MluvnickeCharakteristiky mluvChar = getMluvChar(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        CastObce castObce = new CastObce(kod, nazev, nespravny, kodObce, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, mluvChar, pos, nespravnyUdaj, datumVzniku);
        esService.saveCastObce(castObce);
    }

    /**
     * Parse and save KatastralniUzemi record to KatastralniUzemiRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseKatastralniUzemiElement(Element element) throws ParseException {
        String prefix = "kui";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Boolean existujeDigitalniMapa = getExistujeDigitalniMapa(element, prefix);
        Integer kodObce = getKodObce(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        Long rizeniId = getIdRizeni(element, prefix);
        MluvnickeCharakteristiky mluvChar = getMluvChar(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        KatastralniUzemi katastralniUzemi = new KatastralniUzemi(kod, nazev, nespravny,
                existujeDigitalniMapa, kodObce, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
                rizeniId, mluvChar, pos, nespravnyUdaj, datumVzniku);
        esService.saveKatastralniUzemi(katastralniUzemi);
    }

    /**
     * Parse and save Zsj record to ZsjRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseZsjElement(Element element) throws ParseException {
        String prefix = "zji";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer kodKatastralniUzemi = getKodKatastralniUzemi(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        MluvnickeCharakteristiky mluvChar = getMluvChar(element, prefix);
        Long vymera = getVymera(element, prefix);
        Integer charakterZsjKod = getCharakterZsjKod(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = getDatumVzniku(element, prefix);

        Zsj zsj = new Zsj(kod, nazev, nespravny, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, mluvChar, vymera, charakterZsjKod, pos, nespravnyUdaj, datumVzniku);

        esService.saveZsj(zsj);
    }

    /**
     * Parse and save Ulice record to UliceRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseUliceElement(Element element) throws ParseException {
        String prefix = "uli";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String nazev = getNazev(element, prefix);
        Integer kodObce = getKodObce(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Ulice ulice = new Ulice(kod, nazev, nespravny, kodObce,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nespravnyUdaj);

        esService.saveUlice(ulice);
    }

    /**
     * Parse and save Parcela record to ParcelaRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseParcelaElement(Element element) throws ParseException {
        String prefix = "pai";
        boolean nespravny = false;
        Long id = getId(element, prefix);
        Integer kmenoveCislo = getKmenoveCislo(element, prefix);
        Integer pododdeleniCisla = getPoddodeleniCisla(element, prefix);
        Long vymeraParcely = getVymeraParcely(element, prefix);
        Integer druhCislovaniKod = getDruhCislovaniKod(element, prefix);
        Integer druhPozemkuKod = getDruhPozemkuKod(element, prefix);
        Integer kodKatastralniUzemi = getKodKatastralniUzemi(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long rizeniId = getIdRizeni(element, prefix);
        List<BonitovanyDil> bonitovaneDily = getBonitovaneDily(element, prefix);
        ZpusobOchranyPozemku zpusobOchranyPozemku = getZpusobOchranyPozemku(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Parcela parcela = new Parcela(id, nespravny, kmenoveCislo, pododdeleniCisla, vymeraParcely,
                druhCislovaniKod, druhPozemkuKod, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
                rizeniId, bonitovaneDily, zpusobOchranyPozemku, pos, nespravnyUdaj);

        esService.saveParcela(parcela);
    }

    /**
     * Parse and save StavebniObjekt record to StavebniObjektRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseStavebniObjektElement(Element element) throws ParseException {
        String prefix = "soi";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        List<Integer> cislaDomovni = getCislaDomovni(element);
        Long identifikacniParcelaId = getIdentifikacniParcelaId(element);
        Integer typStavebnihoObjektuKod = getTypStavebnihoObjektuKod(element, prefix);
        Integer zpusobVyuzitiKod = getZpusobVyuzitiKod(element, prefix);
        Integer castObceKod = getKodCastObce(element);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        Long isknBudovaId = getIsknBudovaId(element, prefix);
        Date dokonceni = getDokonceni(element, prefix);
        Integer druhKonstrukceKod = getDruhKonstrukceKod(element, prefix);
        Integer obestavenyProstor = getObestavenyProstor(element, prefix);
        Integer pocetBytu = getPocetBytu(element, prefix);
        Integer pocetPodlazi = getPocetPodlazi(element, prefix);
        Integer podlahovaPlocha = getPodlahovaPlocha(element, prefix);
        Integer pripojeniKanalizaceKod = getPripojeniKanalizaceKod(element, prefix);
        Integer pripojeniPlynKod = getPripojeniPlynKod(element, prefix);
        Integer pripojeniVodovodKod = getPripojeniVodovodKod(element, prefix);
        Integer vybaveniVytahemKod = getVybaveniVytahemKod(element, prefix);
        Integer zastavenaPlocha = getZastavenaPlocha(element, prefix);
        Integer zpusobVytapeniKod = getZpusobVytapeniKod(element, prefix);
        List<Integer> zpusobyOchranyKod = getZpusobyOchranyKod(element, prefix);
        List<DetailniTEA> detailniTEAList = getDetailniTea(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        StavebniObjekt stavebniObjekt = new StavebniObjekt(kod, nespravny, cislaDomovni, identifikacniParcelaId,
                typStavebnihoObjektuKod, zpusobVyuzitiKod, castObceKod, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, isknBudovaId, dokonceni, druhKonstrukceKod, obestavenyProstor, pocetBytu,
                pocetPodlazi, podlahovaPlocha, pripojeniKanalizaceKod, pripojeniPlynKod, pripojeniVodovodKod,
                vybaveniVytahemKod, zastavenaPlocha, zpusobVytapeniKod, zpusobyOchranyKod, detailniTEAList,
                pos, nespravnyUdaj);

        esService.saveStavebniObjekt(stavebniObjekt);
    }

    /**
     * Parse and save AdresniMisto record to AdresniMistoRepository
     * @param element xml element
     * @throws ParseException if an error occurs during parsing
     */
    private void parseAdresniMistoElement(Element element) throws ParseException {
        String prefix = "ami";
        boolean nespravny = false;
        Integer kod = getKod(element, prefix);
        String cisloDomovni = getCisloDomovni(element, prefix);
        String cisloOrientacni = getCisloOrientacni(element, prefix);
        String cisloOrientacniPismeno = getCisloOrientacniPismeno(element, prefix);
        String psc = getPSC(element, prefix);
        Integer stavebniObjektKod = getKodStavebniObjekt(element);
        Integer uliceKod = getKodUlice(element);
        Integer voKod = getVOKod(element, prefix);
        Date platiOd = getPlatiOd(element, prefix);
        Date platiDo = getPlatiDo(element, prefix);
        Long idTransakce = getIdTransakce(element, prefix);
        Long globalniIdNavrhuZmeny = getGlobalniIdNavrhuZmeny(element, prefix);
        Point pos = getPos(element);
        NespravnyUdaj nespravnyUdaj = getNespravneUdaje(element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        AdresniMisto adresniMisto = new AdresniMisto(kod, nespravny, cisloDomovni, cisloOrientacni,
                cisloOrientacniPismeno, psc, stavebniObjektKod, uliceKod, voKod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj);

        esService.saveAdresniMisto(adresniMisto);
    }
}
