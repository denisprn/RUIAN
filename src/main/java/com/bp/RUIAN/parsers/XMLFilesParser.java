package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.services.EsService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

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
public class XMLFilesParser extends FilesParser{
    public XMLFilesParser(EsService esService) {
        super(esService, "xml");
        //this.fileExtension = ".xml";
    }

    @Override
    public void parseFile(String filePath) {
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
                                    Stat stat = new StatParser(element).parse();
                                    esService.saveStat(stat);
                                }
                                case "vf:RegionSoudrznosti" -> {
                                    RegionSoudrznosti regionSoudrznosti =
                                            new RegionSoudrznostiParser(element).parse();
                                    esService.saveRs(regionSoudrznosti);
                                }
                                case "vf:Vusc" -> {
                                    Vusc vusc = new VuscParser(element).parse();
                                    esService.saveVusc(vusc);
                                }
                                case "vf:Okres" -> {
                                    Okres okres = new OkresParser(element).parse();
                                    esService.saveOkres(okres);
                                }
                                case "vf:Orp" -> {
                                    Orp orp = new OrpParser(element).parse();
                                    esService.saveOrp(orp);
                                }
                                case "vf:Pou" -> {
                                    Pou pou = new PouParser(element).parse();
                                    esService.savePou(pou);
                                }
                                case "vf:Obec" -> {
                                    Obec obec = new ObecParser(element).parse();
                                    esService.saveObec(obec);
                                }
                                case "vf:SpravniObvod" -> {
                                    SpravniObvod spravniObvod =
                                            new SpravniObvodParser(element).parse();
                                    esService.saveSO(spravniObvod);
                                }
                                case "vf:Mop" -> {
                                    Mop mop = new MopParser(element).parse();
                                    esService.saveMop(mop);
                                }
                                case "vf:Momc" -> {
                                    Momc momc = new MomcParser(element).parse();
                                    esService.saveMomc(momc);
                                }
                                case "vf:CastObce" -> {
                                    CastObce castObce = new CastObceParser(element).parse();
                                    esService.saveCastObce(castObce);
                                }
                                case "vf:KatastralniUzemi" -> {
                                    KatastralniUzemi katastralniUzemi =
                                            new KatastralniUzemiParser(element).parse();
                                    esService.saveKatastralniUzemi(katastralniUzemi);
                                }
                                case "vf:Zsj" -> {
                                    Zsj zsj = new ZsjParser(element).parse();
                                    esService.saveZsj(zsj);
                                }
                                case "vf:Ulice" -> {
                                    Ulice ulice = new UliceParser(element).parse();
                                    esService.saveUlice(ulice);
                                }
                                case "vf:Parcela" -> {
                                    Parcela parcela = new ParcelaParser(element).parse();
                                    esService.saveParcela(parcela);
                                }
                                case "vf:StavebniObjekt" -> {
                                    StavebniObjekt stavebniObjekt =
                                            new StavebniObjektParser(element).parse();
                                    esService.saveStavebniObjekt(stavebniObjekt);
                                }
                                case "vf:AdresniMisto" -> {
                                    AdresniMisto adresniMisto = new AdresniMistoParser(element).parse();
                                    esService.saveAdresniMisto(adresniMisto);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
