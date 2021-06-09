package com.bp.RUIAN;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.services.EsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.geo.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class RuianApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuianApplication.class, args);
	}

	private MluvnickeCharakteristiky getMluvChar(Element eElement) {
		String pad2 = eElement.getElementsByTagName("com:Pad2").item(0).getTextContent();
		String pad3 = eElement.getElementsByTagName("com:Pad3").item(0).getTextContent();
		String pad4 = eElement.getElementsByTagName("com:Pad4").item(0).getTextContent();
		String pad6 = eElement.getElementsByTagName("com:Pad6").item(0).getTextContent();
		String pad7 = eElement.getElementsByTagName("com:Pad7").item(0).getTextContent();
		return new MluvnickeCharakteristiky(pad2, pad3, pad4, pad6, pad7);
	}

	private String getVlajkaText(Element eElement, String prefix) {
		NodeList nListVlajkaText = eElement.getElementsByTagName(prefix + ":VlajkaText");

		if (nListVlajkaText.getLength() > 0) {
			return nListVlajkaText.item(0).getTextContent();
		}

		return null;
	}

	private String getVlajkaObrazek(Element eElement, String prefix) {
		NodeList nListVlajkaObrazek = eElement.getElementsByTagName(prefix + ":VlajkaObrazek");

		if (nListVlajkaObrazek.getLength() > 0) {
			return nListVlajkaObrazek.item(0).getTextContent();
		}

		return null;
	}

	private String getZnakObrazek(Element eElement, String prefix) {
		NodeList nListZnakObrazek = eElement.getElementsByTagName(prefix + ":ZnakObrazek");

		if (nListZnakObrazek.getLength() > 0) {
			return nListZnakObrazek.item(0).getTextContent();
		}

		return null;
	}

	private String getZnakText(Element eElement, String prefix) {
		NodeList nListZnakText = eElement.getElementsByTagName(prefix + ":ZnakText");

		if (nListZnakText.getLength() > 0) {
			return nListZnakText.item(0).getTextContent();
		}

		return null;
	}

	private void parse(Node node, String prefix, EsService esService) throws Exception {
		Integer kod = null;
		Long globalniIdNavrhuZmeny = null;
		String nazev = null;
		Boolean nespravny = false;
		Date platiDo = null, datumVzniku = null;
		NespravnyUdaj nespravnyUdaj = null;
		Point pos = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		Element eElement = (Element) node;

		NodeList nListKod = eElement.getElementsByTagName(prefix + ":Kod");

		if (nListKod.getLength() > 0) {
			kod = Integer.parseInt(nListKod.item(0).getTextContent());
		}

		NodeList nListNazev = eElement.getElementsByTagName(prefix + ":Nazev");

		if (nListNazev.getLength() > 0) {
			nazev = nListNazev.item(0).getTextContent();
		}

		Date platiOd = sdf.parse(eElement.getElementsByTagName(prefix + ":PlatiOd").item(0).getTextContent());

		NodeList nListPlatiDo = eElement.getElementsByTagName(prefix + ":PlatiDo");

		if (nListPlatiDo.getLength() > 0) {
			platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
		}

		Long idTransakce = Long.parseLong(eElement.getElementsByTagName(prefix + ":IdTransakce")
				.item(0).getTextContent());

		NodeList nListGlobIDNavrhuZmeny = eElement.getElementsByTagName(prefix + ":GlobalniIdNavrhuZmeny");

		if (nListGlobIDNavrhuZmeny.getLength() > 0) {
			globalniIdNavrhuZmeny = Long.parseLong(nListGlobIDNavrhuZmeny.item(0).getTextContent());
		}

		NodeList nListPos = eElement.getElementsByTagName("gml:pos");

		if (nListPos.getLength() > 0) {
			String position = nListPos.item(0).getTextContent();
			double x = Double.parseDouble(position.split(" ")[0]);
			double y = Double.parseDouble(position.split(" ")[1]);
			pos = new Point(x,y);
		}

		NodeList nListNU = eElement.getElementsByTagName(prefix + ":NespravneUdaje");

		if (nListNU.getLength() > 0) {
			nespravny = true;
			String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
			Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
			String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
			nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
		}

		NodeList nListDatumVzniku = eElement.getElementsByTagName(prefix + ":DatumVzniku");

		if (nListDatumVzniku.getLength() > 0) {
			datumVzniku = sdf.parse(nListDatumVzniku.item(0).getTextContent());
		}

		switch (node.getNodeName()) {
			case "vf:Stat" -> {
				String nutsLau = eElement.getElementsByTagName(prefix + ":NutsLau").item(0).getTextContent();

				Stat stat = new Stat(kod, nazev, nespravny, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
						nutsLau, pos, nespravnyUdaj, datumVzniku);
				esService.saveStat(stat);
			}
			case "vf:RegionSoudrznosti" -> {
				Integer kodStatu = Integer.parseInt(eElement.getElementsByTagName("sti:Kod").item(0).getTextContent());
				String nutsLau = eElement.getElementsByTagName(prefix + ":NutsLau").item(0).getTextContent();

				RegionSoudrznosti regionSoudrznosti = new RegionSoudrznosti(kod, nazev, nespravny, kodStatu,
						platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nutsLau, pos,
						nespravnyUdaj, datumVzniku);

				esService.saveRs(regionSoudrznosti);
			}
			case "vf:Vusc" -> {
				Integer kodRs = Integer.parseInt(eElement.getElementsByTagName("rsi:Kod").item(0).getTextContent());
				String nutsLau = eElement.getElementsByTagName(prefix + ":NutsLau").item(0).getTextContent();

				Vusc vusc = new Vusc(kod, nazev, nespravny, kodRs, platiOd, platiDo,
						idTransakce, globalniIdNavrhuZmeny, nutsLau, pos, nespravnyUdaj, datumVzniku);
				esService.saveVusc(vusc);
			}
			case "vf:Okres" -> {
				Integer kodVusc = Integer.parseInt(eElement.getElementsByTagName("vci:Kod").item(0).getTextContent());
				String nutsLau = eElement.getElementsByTagName(prefix + ":NutsLau").item(0).getTextContent();

				Okres okres = new Okres(kod, nazev, nespravny, kodVusc, platiOd, platiDo, idTransakce,
						globalniIdNavrhuZmeny, nutsLau, pos, nespravnyUdaj, datumVzniku);

				esService.saveOkres(okres);
			}
			case "vf:Orp" -> {
				Integer spravniObecKod = Integer.parseInt(eElement.getElementsByTagName("opi:SpravniObecKod")
						.item(0).getTextContent());
				Integer kodOkresu = Integer.parseInt(eElement.getElementsByTagName("oki:Kod")
						.item(0).getTextContent());

				Orp orp = new Orp(kod, nazev, nespravny, spravniObecKod, kodOkresu, platiOd, platiDo, idTransakce,
						globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);
				esService.saveOrp(orp);
			}
			case "vf:Pou" -> {
				Integer spravniObecKod = Integer.parseInt(eElement.getElementsByTagName("pui:SpravniObecKod").item(0).getTextContent());
				Integer kodOrp = Integer.parseInt(eElement.getElementsByTagName("opi:Kod").item(0).getTextContent());

				Pou pou = new Pou(kod, nazev, nespravny, spravniObecKod, kodOrp, platiOd, platiDo, idTransakce,
						globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);
				esService.savePou(pou);
			}
			case "vf:Obec" -> {
				Integer cleneniSMRozsahKod = null, cleneniSMTypKod = null;
				Integer kodOkresu = Integer.parseInt(eElement.getElementsByTagName("oki:Kod").item(0).getTextContent());
				Integer kodPou = Integer.parseInt(eElement.getElementsByTagName("pui:Kod").item(0).getTextContent());
				Integer statusKod = Integer.parseInt(eElement.getElementsByTagName("obi:Kod").item(0).getTextContent());
				String nutsLau = eElement.getElementsByTagName(prefix + ":NutsLau").item(0).getTextContent();
				MluvnickeCharakteristiky mluvChar = getMluvChar(eElement);
				String vlajkaText = getVlajkaText(eElement, prefix);
				String vlajkaObrazek = getVlajkaObrazek(eElement, prefix);
				String znakText = getZnakText(eElement, prefix);
				String znakObrazek = getZnakObrazek(eElement, prefix);

				NodeList nListCleneniSMRozsahKod = eElement.getElementsByTagName(prefix + ":cleneniSMRozsahKod");

				if (nListCleneniSMRozsahKod.getLength() > 0) {
					cleneniSMRozsahKod = Integer.parseInt(nListCleneniSMRozsahKod.item(0).getTextContent());
				}

				NodeList nListCleneniSMTypKod = eElement.getElementsByTagName(prefix + ":cleneniSMTypKod");

				if (nListCleneniSMTypKod.getLength() > 0) {
					cleneniSMTypKod = Integer.parseInt(nListCleneniSMTypKod.item(0).getTextContent());
				}

				Obec obec = new Obec(kod, nazev, nespravny, statusKod, kodOkresu, kodPou, platiOd, platiDo, idTransakce,
						globalniIdNavrhuZmeny, mluvChar, vlajkaText, vlajkaObrazek, znakText, znakObrazek,
						cleneniSMRozsahKod, cleneniSMTypKod, nutsLau, pos, nespravnyUdaj, datumVzniku);
				esService.saveObec(obec);
			}
			case "vf:SpravniObvod" -> {
				Integer spravniMomcKod = Integer.parseInt(eElement.getElementsByTagName("spi:SpravniMomcKod")
						.item(0).getTextContent());
				Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
						.item(0).getTextContent());

				SpravniObvod spravniObvod = new SpravniObvod(kod, nazev, nespravny, spravniMomcKod, kodObce,
						platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);
				esService.saveSO(spravniObvod);
			}
			case "vf:Mop" -> {
				Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
						.item(0).getTextContent());

				Mop mop = new Mop(kod, nazev, nespravny, kodObce, platiOd, platiDo, idTransakce,
						globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);
				esService.saveMop(mop);
			}
			case "vf:Momc" -> {
				Integer kodMop = null, kodSpravniObvod = null;
				String vlajkaText = null, vlajkaObrazek = null,
						znakText = null, znakObrazek = null;

				NodeList nListKodMop = eElement.getElementsByTagName("mpi:Kod");

				if (nListKodMop.getLength() > 0) {
					kodMop = Integer.parseInt(nListKodMop.item(0).getTextContent());
				}

				Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
						.item(0).getTextContent());

				NodeList nListKodSpravniObvod = eElement.getElementsByTagName("spi:Kod");

				if (nListKodSpravniObvod.getLength() > 0) {
					kodSpravniObvod = Integer.parseInt(nListKodSpravniObvod.item(0).getTextContent());
				}

				NodeList nListVlajkaText = eElement.getElementsByTagName(prefix + ":VlajkaText");

				if (nListVlajkaText.getLength() > 0) {
					vlajkaText = nListVlajkaText.item(0).getTextContent();
				}

				NodeList nListVlajkaObrazek = eElement.getElementsByTagName(prefix + ":VlajkaObrazek");

				if (nListVlajkaObrazek.getLength() > 0) {
					vlajkaObrazek = nListVlajkaObrazek.item(0).getTextContent();
				}

				NodeList nListZnakText = eElement.getElementsByTagName(prefix + ":ZnakText");

				if (nListZnakText.getLength() > 0) {
					znakText = nListZnakText.item(0).getTextContent();
				}

				NodeList nListZnakObrazek = eElement.getElementsByTagName(prefix + ":ZnakObrazek");

				if (nListZnakObrazek.getLength() > 0) {
					znakObrazek = nListZnakObrazek.item(0).getTextContent();
				}

				MluvnickeCharakteristiky mluvChar = getMluvChar(eElement);

				Momc momc = new Momc(kod, nazev, nespravny, kodMop, kodObce, kodSpravniObvod, platiOd, platiDo,
						idTransakce, globalniIdNavrhuZmeny, vlajkaText, vlajkaObrazek,
						mluvChar, znakText, znakObrazek, pos, nespravnyUdaj, datumVzniku);
				esService.saveMomc(momc);
			}
			case "vf:CastObce" -> {
				Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
						.item(0).getTextContent());
				MluvnickeCharakteristiky mluvChar = getMluvChar(eElement);

				CastObce castObce = new CastObce(kod, nazev, nespravny, kodObce, platiOd, platiDo,
						idTransakce, globalniIdNavrhuZmeny, mluvChar, pos, nespravnyUdaj, datumVzniku);
				esService.saveCastObce(castObce);
			}
			case "vf:KatastralniUzemi" -> {
				Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
						.item(0).getTextContent());
				Long rizeniId = Long.parseLong(eElement.getElementsByTagName(prefix + ":RizeniId")
						.item(0).getTextContent());
				Boolean existujeDigitalniMapa = Boolean.parseBoolean(eElement
						.getElementsByTagName(prefix + ":ExistujeDigitalniMapa").item(0).getTextContent());
				MluvnickeCharakteristiky mluvChar = getMluvChar(eElement);

				KatastralniUzemi katastralniUzemi = new KatastralniUzemi(kod, nazev, nespravny,
						existujeDigitalniMapa, kodObce, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
						rizeniId, mluvChar, pos, nespravnyUdaj, datumVzniku);
				esService.saveKatastralniUzemi(katastralniUzemi);
			}
			case "vf:Zsj" -> {
				MluvnickeCharakteristiky mluvChar = null;
				Integer kodKatastralniUzemi = Integer.parseInt(eElement.getElementsByTagName("kui:Kod")
						.item(0).getTextContent());
				Long vymera = Long.parseLong(eElement.getElementsByTagName(prefix + ":Vymera")
						.item(0).getTextContent());
				Integer charakterZsjKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":CharakterZsjKod")
						.item(0).getTextContent());

				NodeList nListMluvChar = eElement.getElementsByTagName(prefix + ":MluvnickeCharakteristiky");

				if (nListMluvChar.getLength() > 0) {
					mluvChar = getMluvChar(eElement);
				}

				Zsj zsj = new Zsj(kod, nazev, nespravny, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
						globalniIdNavrhuZmeny, mluvChar, vymera, charakterZsjKod, pos, nespravnyUdaj, datumVzniku);

				esService.saveZsj(zsj);
			}
			case "vf:Ulice" -> {
				Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
						.item(0).getTextContent());

				Ulice ulice = new Ulice(kod, nazev, nespravny, kodObce,
						platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nespravnyUdaj);

				esService.saveUlice(ulice);
			}
			case "vf:Parcela" -> {
				ZpusobOchranyPozemku zpusobOchranyPozemku = null;
				List<BonitovanyDil> bonitovaneDily = null;

				Long id = Long.parseLong(eElement.getElementsByTagName(prefix + ":Id")
						.item(0).getTextContent());

				Integer kmenoveCislo = Integer.parseInt(eElement.getElementsByTagName(prefix + ":KmenoveCislo")
						.item(0).getTextContent());

				Integer pododdeleniCisla = Integer.parseInt(eElement.getElementsByTagName(prefix + ":PododdeleniCisla")
						.item(0).getTextContent());

				Long vymeraParcely = Long.parseLong(eElement.getElementsByTagName(prefix + ":VymeraParcely")
						.item(0).getTextContent());

				Integer druhCislovaniKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":DruhCislovaniKod")
						.item(0).getTextContent());

				Integer druhPozemkuKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":DruhPozemkuKod")
						.item(0).getTextContent());

				Integer kodKatastralniUzemi = Integer.parseInt(eElement.getElementsByTagName("kui:Kod")
						.item(0).getTextContent());

				Long rizeniId = Long.parseLong(eElement.getElementsByTagName(prefix + ":RizeniId")
						.item(0).getTextContent());

				NodeList nListBonitovaneDily = eElement.getElementsByTagName(prefix + ":BonitovaneDily");

				if (nListBonitovaneDily.getLength() > 0) {
					bonitovaneDily = new ArrayList<>();

					for (int i = 0; i < nListBonitovaneDily.getLength(); i++) {
						Element element = (Element) nListBonitovaneDily.item(i);

						Long vymeraBD = Long.parseLong(element.getElementsByTagName("com:Vymera")
								.item(0).getTextContent());
						Integer bonitovaJednotkaKodBD = Integer.parseInt(element.getElementsByTagName("com:BonitovanaJednotkaKod")
								.item(0).getTextContent());
						Long idTransakceBD = Long.parseLong(element.getElementsByTagName("com:IdTranskace")
								.item(0).getTextContent());
						Long rizeniIdBD = Long.parseLong(element.getElementsByTagName("com:RizeniId")
								.item(0).getTextContent());

						BonitovanyDil bonitovanyDil = new BonitovanyDil(vymeraBD, bonitovaJednotkaKodBD, idTransakceBD,
								rizeniIdBD);

						bonitovaneDily.add(bonitovanyDil);
					}
				}

				NodeList nListZpOchranyPozemku = eElement.getElementsByTagName(prefix + ":ZpusobyOchranyPozemku");

				if (nListZpOchranyPozemku.getLength() > 0) {
					Integer kodZOP = Integer.parseInt(eElement.getElementsByTagName("com:Kod")
							.item(0).getTextContent());
					Integer typOchranyKodZOP = Integer.parseInt(eElement.getElementsByTagName("com:TypOchranyKod")
							.item(0).getTextContent());
					Long idTransakceZOP = Long.parseLong(eElement.getElementsByTagName("com:IdTransakce")
							.item(0).getTextContent());
					Long rizeniIdZOP = Long.parseLong(eElement.getElementsByTagName("com:RizeniId")
							.item(0).getTextContent());

					zpusobOchranyPozemku = new ZpusobOchranyPozemku(kodZOP, typOchranyKodZOP,
							idTransakceZOP, rizeniIdZOP);
				}

				Parcela parcela = new Parcela(id, nespravny, kmenoveCislo, pododdeleniCisla, vymeraParcely,
						druhCislovaniKod, druhPozemkuKod, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
						rizeniId, bonitovaneDily, zpusobOchranyPozemku, pos, nespravnyUdaj);

				esService.saveParcela(parcela);
			}
			case "vf:StavebniObjekt" -> {
				Date dokonceni = null;
				Integer podlahovaPlocha = null, obestavenyProstor = null, zastavenaPlocha = null;
				List<Integer> cislaDomovni = null,
						zpusobyOchranyKod = null;
				List<DetailniTEA> detailniTEAList = null;

				NodeList nListCislaDomovni = eElement.getElementsByTagName("com:CisloDomovni");

				if (nListCislaDomovni.getLength() > 0) {
					cislaDomovni = new ArrayList<>();

					for (int i = 0; i < nListCislaDomovni.getLength(); i++) {
						cislaDomovni.add(Integer.parseInt(nListCislaDomovni.item(i).getTextContent()));
					}
				}

				Long identifikacniParcelaId = Long.parseLong(eElement.getElementsByTagName("pai:Id")
						.item(0).getTextContent());

				Integer typStavebnihoObjektuKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":TypStavebnihoObjektuKod")
						.item(0).getTextContent());

				Integer zpusobVyuzitiKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":ZpusobVyuzitiKod")
						.item(0).getTextContent());

				Integer castObceKod = Integer.parseInt(eElement.getElementsByTagName("coi:Kod")
						.item(0).getTextContent());

				Long isknBudovaId = Long.parseLong(eElement.getElementsByTagName(prefix + ":IsknBudovaId")
						.item(0).getTextContent());

				NodeList nListDokonceni = eElement.getElementsByTagName(prefix + ":Dokonceni");

				if (nListDokonceni.getLength() > 0) {
					dokonceni = sdf.parse(nListDokonceni.item(0).getTextContent());
				}

				Integer druhKonstrukceKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":DruhKonstrukceKod")
						.item(0).getTextContent());

				NodeList nListObestavenyProstor = eElement.getElementsByTagName(prefix + ":ObestavenyProstor");

				if (nListDokonceni.getLength() > 0) {
					obestavenyProstor = Integer.parseInt(nListObestavenyProstor.item(0).getTextContent());
				}

				Integer pocetBytu = Integer.parseInt(eElement.getElementsByTagName(prefix + ":PocetBytu")
						.item(0).getTextContent());

				Integer pocetPodlazi = Integer.parseInt(eElement.getElementsByTagName(prefix + ":PocetPodlazi")
						.item(0).getTextContent());

				NodeList nListPodlahovaPlocha = eElement.getElementsByTagName(prefix + ":PodlahovaPlocha");

				if (nListPodlahovaPlocha.getLength() > 0) {
					podlahovaPlocha = Integer.parseInt(nListPodlahovaPlocha.item(0).getTextContent());
				}

				Integer pripojeniKanalizaceKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":PripojeniKanalizaceKod")
						.item(0).getTextContent());

				Integer pripojeniPlynKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":PripojeniPlynKod")
						.item(0).getTextContent());

				Integer pripojeniVodovodKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":PripojeniVodovodKod")
						.item(0).getTextContent());

				Integer vybaveniVytahemKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":VybaveniVytahemKod")
						.item(0).getTextContent());

				NodeList nListZastavenaPlocha = eElement.getElementsByTagName(prefix + ":ZastavenaPlocha");

				if (nListZastavenaPlocha.getLength() > 0) {
					zastavenaPlocha = Integer.parseInt(nListZastavenaPlocha.item(0).getTextContent());
				}

				Integer zpusobVytapeniKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":ZpusobVytapeniKod")
						.item(0).getTextContent());

				NodeList nListZpusobyOchranyKod = eElement.getElementsByTagName(prefix + ":ZpusobyOchranyKod");

				if (nListZpusobyOchranyKod.getLength() > 0) {
					zpusobyOchranyKod = new ArrayList<>();

					for (int i = 0; i < nListZpusobyOchranyKod.getLength(); i++) {
						zpusobyOchranyKod.add(Integer.parseInt(eElement.getElementsByTagName("com:ZpusobOchranyKod")
								.item(i).getTextContent()));
					}
				}

				NodeList nListDetailniTea = eElement.getElementsByTagName(prefix + ":DetailniTEA");

				if (nListDetailniTea.getLength() > 0) {
					NodeList nodeListDTChildren = nListDetailniTea.item(0).getChildNodes();
					detailniTEAList = new ArrayList<>();

					for (int i = 0; i < nodeListDTChildren.getLength(); i++) {
						if (nodeListDTChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
							Boolean nespravnyDT = false;
							Element element = (Element) nodeListDTChildren.item(i);

							Integer kodDT = Integer.parseInt(element.getElementsByTagName(prefix + ":Kod")
									.item(0).getTextContent());
							Date platiOdDT = sdf.parse(element.getElementsByTagName(prefix + ":PlatiOd")
									.item(0).getTextContent());

							NodeList nListNespravnyDT = element.getElementsByTagName(prefix + ":Nespravny");

							if (nListNespravnyDT.getLength() > 0) {
								nespravnyDT = true;
							}

							Long globalniIdNavrhuZmenyDT = Long.parseLong(element.
									getElementsByTagName(prefix + ":GlobalniIdNavrhuZmeny").item(0).getTextContent());

							Integer druhKonstrukceKodDT = Integer.parseInt(element.
									getElementsByTagName(prefix + ":DruhKonstrukceKod").item(0).getTextContent());

							Integer pocetBytuDT = Integer.parseInt(element.
									getElementsByTagName(prefix + ":PocetBytu").item(0).getTextContent());

							Integer pocetPodlaziDT = Integer.parseInt(element.getElementsByTagName(prefix + ":PocetPodlazi")
									.item(0).getTextContent());

							Integer pripojeniKanalizaceKodDT = Integer.parseInt(element.getElementsByTagName(prefix + ":PripojeniKanalizaceKod")
									.item(0).getTextContent());

							Integer pripojeniPlynKodDT = Integer.parseInt(element.getElementsByTagName(prefix + ":PripojeniPlynKod")
									.item(0).getTextContent());

							Integer pripojeniVodovodKodDT = Integer.parseInt(element.getElementsByTagName(prefix + ":PripojeniVodovodKod")
									.item(0).getTextContent());

							Integer zpusobVytapeniKodDT = Integer.parseInt(element.getElementsByTagName(prefix + ":ZpusobVytapeniKod")
									.item(0).getTextContent());


							Integer adresniMistoKodDT = Integer.parseInt(element.getElementsByTagName("base:Kod")
									.item(0).getTextContent());

							DetailniTEA detailniTEA = new DetailniTEA(kodDT, platiOdDT, nespravnyDT, globalniIdNavrhuZmenyDT,
									druhKonstrukceKodDT, pocetBytuDT, pocetPodlaziDT, pripojeniKanalizaceKodDT, pripojeniPlynKodDT,
									pripojeniVodovodKodDT, zpusobVytapeniKodDT, adresniMistoKodDT);

							detailniTEAList.add(detailniTEA);
						}
					}
				}

				StavebniObjekt stavebniObjekt = new StavebniObjekt(kod, nespravny, cislaDomovni, identifikacniParcelaId,
						typStavebnihoObjektuKod, zpusobVyuzitiKod, castObceKod, platiOd, platiDo, idTransakce,
						globalniIdNavrhuZmeny, isknBudovaId, dokonceni, druhKonstrukceKod, obestavenyProstor, pocetBytu,
						pocetPodlazi, podlahovaPlocha, pripojeniKanalizaceKod, pripojeniPlynKod, pripojeniVodovodKod,
						vybaveniVytahemKod, zastavenaPlocha, zpusobVytapeniKod, zpusobyOchranyKod, detailniTEAList,
						pos, nespravnyUdaj);

				esService.saveStavebniObjekt(stavebniObjekt);
			}
			case "vf:AdresniMisto" -> {
				String cisloOrientacni = null, cisloOrientacniPismeno = null;
				Integer uliceKod = null;
				String cisloDomovni = eElement.getElementsByTagName(prefix + ":CisloDomovni")
						.item(0).getTextContent();

				NodeList nListCisloOrientacni = eElement.getElementsByTagName(prefix + ":CisloOrientacni");

				if (nListCisloOrientacni.getLength() > 0) {
					cisloOrientacni = nListCisloOrientacni.item(0).getTextContent();
				}

				NodeList nListCisloOrientacniPismeno = eElement.getElementsByTagName(prefix + ":CisloOrientacniPismeno");

				if (nListCisloOrientacniPismeno.getLength() > 0) {
					cisloOrientacniPismeno = nListCisloOrientacniPismeno.item(0).getTextContent();
				}

				String psc = eElement.getElementsByTagName(prefix + ":Psc").item(0).getTextContent();
				Integer stavebniObjektKod = Integer.parseInt(eElement.getElementsByTagName("soi:Kod")
						.item(0).getTextContent());

				NodeList nListUliceKod = eElement.getElementsByTagName("uli:Kod");

				if (nListUliceKod.getLength() > 0) {
					uliceKod = Integer.parseInt(nListUliceKod.item(0).getTextContent());
				}

				Integer voKod = Integer.parseInt(eElement.getElementsByTagName(prefix + ":VOKod")
						.item(0).getTextContent());

				AdresniMisto adresniMisto = new AdresniMisto(kod, nespravny, cisloDomovni, cisloOrientacni,
						cisloOrientacniPismeno, psc, stavebniObjektKod, uliceKod, voKod, platiOd, platiDo,
						idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj);

				esService.saveAdresniMisto(adresniMisto);
			}
		}
	}

	@Bean
	CommandLineRunner runner(EsService esService) {
		return args -> {
			//read xml file and import to Elasticsearch

			try {
                //Get Document Builder
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                File file = new ClassPathResource("ruian_test.xml").getFile();

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
							if (nodeL.item(j).getNodeType() == Node.ELEMENT_NODE) {
								switch (nodeL.item(j).getNodeName()) {
									case "vf:Stat" -> parse(nodeL.item(j), "sti",
											esService);
									case "vf:RegionSoudrznosti" -> parse(nodeL.item(j),
											"rsi", esService);
									case "vf:Vusc" -> parse(nodeL.item(j),
											"vci", esService);
									case "vf:Okres" -> parse(nodeL.item(j),
											"oki", esService);
									case "vf:Orp" -> parse(nodeL.item(j),
											"opi", esService);
									case "vf:Pou" -> parse(nodeL.item(j),
											"pui", esService);
									case "vf:Obec" -> parse(nodeL.item(j),
											"obi", esService);
									case "vf:SpravniObvod" -> parse(nodeL.item(j),
											"spi", esService);
									case "vf:Mop" -> parse(nodeL.item(j),
											"mpi", esService);
									case "vf:Momc" -> parse(nodeL.item(j),
											"mci", esService);
									case "vf:CastObce" -> parse(nodeL.item(j),
											"coi", esService);
									case "vf:KatastralniUzemi" -> parse(nodeL.item(j),
											"kui", esService);
									case "vf:Zsj" -> parse(nodeL.item(j),
											"zji", esService);
									case "vf:Ulice" -> parse(nodeL.item(j),
											"uli", esService);
									case "vf:Parcela" -> parse(nodeL.item(j),
											"pai", esService);
									case "vf:StavebniObjekt" -> parse(nodeL.item(j),
											"soi", esService);
									case "vf:AdresniMisto" -> parse(nodeL.item(j),
											"ami", esService);
								}
							}
						}
					}
				}
            } catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}
		};
	}
}
