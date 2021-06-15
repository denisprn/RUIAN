package com.bp.RUIAN;

import com.bp.RUIAN.parsers.*;
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
public class FilesParser {
    private final EsService esService;

    public FilesParser(EsService esService) {
        this.esService = esService;
    }

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
                                case "vf:Stat" ->
                                    new StatParser(esService,element).parse();
                                case "vf:RegionSoudrznosti" ->
                                    new RegionSoudrznostiParser(esService,element).parse();
                                case "vf:Vusc" ->
                                    new VuscParser(esService,element).parse();
                                case "vf:Okres" ->
                                    new OkresParser(esService,element).parse();
                                case "vf:Orp" ->
                                    new OrpParser(esService,element).parse();
                                case "vf:Pou" ->
                                    new PouParser(esService,element).parse();
                                case "vf:Obec" ->
                                    new ObecParser(esService,element).parse();
                                case "vf:SpravniObvod" ->
                                    new SpravniObvodParser(esService,element).parse();
                                case "vf:Mop" ->
                                    new MopParser(esService,element).parse();
                                case "vf:Momc" ->
                                    new MomcParser(esService,element).parse();
                                case "vf:CastObce" ->
                                    new CastObceParser(esService,element).parse();
                                case "vf:KatastralniUzemi" ->
                                    new KatastralniUzemiParser(esService,element).parse();
                                case "vf:Zsj" ->
                                    new ZsjParser(esService,element).parse();
                                case "vf:Ulice" ->
                                    new UliceParser(esService,element).parse();
                                case "vf:Parcela" ->
                                    new ParcelaParser(esService,element).parse();
                                case "vf:StavebniObjekt" ->
                                    new StavebniObjektParser(esService,element).parse();
                                case "vf:AdresniMisto" ->
                                    new AdresniMistoParser(esService,element).parse();
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
