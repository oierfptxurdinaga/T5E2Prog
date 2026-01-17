package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.*;

public class XmlKudeatzailea {

    // Barruko klasea estatistikak kalkulatzeko (Model-en ez baduzu, hemen erabil dezakezu)
    // Zure TaldeStats klasea model paketean badago, hau ezabatu dezakezu.
    private class TaldeStats {
        String izena;
        int puntuak = 0, jokatuak = 0, irabaziak = 0, berdinduak = 0, galduak = 0, gAlde = 0, gAurka = 0;
        public TaldeStats(String izena) { this.izena = izena; }
    }

    public boolean esportatuXML(Federazioa federazioa, String rutaFitxategia) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Federazioa");
            doc.appendChild(rootElement);

            // -------------------------------------------------
            // A. TALDEAK (MAISUA / MASTER)
            // -------------------------------------------------
            Element taldeakElement = doc.createElement("TaldeGuztiak");
            rootElement.appendChild(taldeakElement);

            for (Talde t : federazioa.getTaldeGuztiak()) {
                taldeakElement.appendChild(sortuTaldeNodoa(doc, t));
            }

            // -------------------------------------------------
            // B. DENBORALDIAK (HISTORIKOA)
            // -------------------------------------------------
            Element denboraldiakElement = doc.createElement("Denboraldiak");
            rootElement.appendChild(denboraldiakElement);

            for (Denboraldia d : federazioa.getDenboraldiak()) {
                Element denbElement = doc.createElement("Denboraldia");
                denbElement.setAttribute("urtea", String.valueOf(d.getUrtea()));
                denboraldiakElement.appendChild(denbElement);

                // 1. TALDEEN FOTOA (PLANTILLAK)
                Element denbTaldeak = doc.createElement("DenboraldikoTaldeak");
                denbElement.appendChild(denbTaldeak);

                if (d.getLigakoTaldeak() != null) {
                    for (Talde t : d.getLigakoTaldeak()) {
                        denbTaldeak.appendChild(sortuTaldeNodoa(doc, t));
                    }
                }

                // ---------------------------------------------------------
                // 2. SAILKAPENA (HEMEN GEHITU DUGU ZATI BERRIA)
                // ---------------------------------------------------------
                
                // A) Estatistikak kalkulatu
                ArrayList<TaldeStats> stats = kalkulatuEstatistikak(d.getLigakoTaldeak(), d.getLigakoJardunaldi());
                
                // B) Ordenatu (Puntuak > Gol Aldea)
                stats.sort((s1, s2) -> {
                    if (s1.puntuak != s2.puntuak) return s2.puntuak - s1.puntuak;
                    return (s2.gAlde - s2.gAurka) - (s1.gAlde - s1.gAurka);
                });

                // C) XML Nodoak sortu
                Element sailkapenaElem = doc.createElement("Sailkapena");
                for (int i = 0; i < stats.size(); i++) {
                    TaldeStats s = stats.get(i);
                    Element lerroa = doc.createElement("Lerroa");

                    elementuaSortu(doc, lerroa, "Posizioa", String.valueOf(i + 1));
                    elementuaSortu(doc, lerroa, "Taldea", s.izena);
                    elementuaSortu(doc, lerroa, "Puntuak", String.valueOf(s.puntuak));
                    elementuaSortu(doc, lerroa, "Jokatuak", String.valueOf(s.jokatuak));
                    elementuaSortu(doc, lerroa, "Irabaziak", String.valueOf(s.irabaziak));
                    elementuaSortu(doc, lerroa, "Berdinduak", String.valueOf(s.berdinduak));
                    elementuaSortu(doc, lerroa, "Galduak", String.valueOf(s.galduak));
                    elementuaSortu(doc, lerroa, "AldekoGolak", String.valueOf(s.gAlde));
                    elementuaSortu(doc, lerroa, "AurkakoGolak", String.valueOf(s.gAurka));

                    sailkapenaElem.appendChild(lerroa);
                }
                denbElement.appendChild(sailkapenaElem);
                // ---------------------------------------------------------

                // 3. JARDUNALDIAK
                Element jardunaldiakElement = doc.createElement("Jardunaldiak");
                denbElement.appendChild(jardunaldiakElement);

                if (d.getLigakoJardunaldi() != null) {
                    for (Jardunaldi j : d.getLigakoJardunaldi()) {
                        Element jardElement = doc.createElement("Jardunaldi");
                        jardElement.setAttribute("zenbakia", String.valueOf(j.getJardunaldiZbk()));
                        jardunaldiakElement.appendChild(jardElement);

                        if (j.getPartiduak() != null) {
                            for (Partidua p : j.getPartiduak()) {
                                Element partElement = doc.createElement("Partidua");
                                jardElement.appendChild(partElement);

                                Element etxeko = doc.createElement("EtxekoTaldea");
                                etxeko.appendChild(doc.createTextNode(p.getEtxekoTaldea().getIzena()));
                                partElement.appendChild(etxeko);

                                Element kanpoko = doc.createElement("KanpokoTaldea");
                                kanpoko.appendChild(doc.createTextNode(p.getKanpokoTaldea().getIzena()));
                                partElement.appendChild(kanpoko);

                                if (p.jokatutaDago()) {
                                    Element emaitza = doc.createElement("Emaitza");
                                    emaitza.setAttribute("etxekoGolak", String.valueOf(p.getEtxekoGolak()));
                                    emaitza.setAttribute("kanpokoGolak", String.valueOf(p.getKanpokoGolak()));
                                    partElement.appendChild(emaitza);
                                } else {
                                    partElement.setAttribute("egoera", "JokatuGabe");
                                }
                            }
                        }
                    }
                }
            }

            // Fitxategia idatzi
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(rutaFitxategia));

            transformer.transform(source, result);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- METODO LAGUNTZAILEAK ---

    private Element sortuTaldeNodoa(Document doc, Talde t) {
        Element taldeElement = doc.createElement("Talde");
        elementuaSortu(doc, taldeElement, "Izena", t.getIzena());
        elementuaSortu(doc, taldeElement, "Hiria", t.getHiria());

        Element jokalariakElement = doc.createElement("Jokalariak");
        taldeElement.appendChild(jokalariakElement);

        if (t.getJokalariak() != null) {
            for (Jokalari j : t.getJokalariak()) {
                Element jokElement = doc.createElement("Jokalari");
                jokElement.setAttribute("dortsala", String.valueOf(j.getDortsala()));
                elementuaSortu(doc, jokElement, "Izena", j.getIzena());
                elementuaSortu(doc, jokElement, "Abizena", j.getAbizena());
                elementuaSortu(doc, jokElement, "Posizioa", j.getPosizio());
                jokalariakElement.appendChild(jokElement);
            }
        }
        return taldeElement;
    }

    // Kodea garbitzeko metodo berria (Elementu sinpleak sortzeko)
    private void elementuaSortu(Document doc, Element gurasoa, String etiketa, String balioa) {
        Element e = doc.createElement(etiketa);
        e.appendChild(doc.createTextNode(balioa));
        gurasoa.appendChild(e);
    }

    // Estatistikak kalkulatzeko logika
    private ArrayList<TaldeStats> kalkulatuEstatistikak(ArrayList<Talde> taldeak, ArrayList<Jardunaldi> jardunaldi) {
        Map<String, TaldeStats> mapaStats = new HashMap<>();

        if (taldeak != null) {
            for (Talde t : taldeak) {
                mapaStats.put(t.getIzena(), new TaldeStats(t.getIzena()));
            }
        }

        if (jardunaldi != null) {
            for (Jardunaldi j : jardunaldi) {
                if (j.getPartiduak() != null) {
                    for (Partidua p : j.getPartiduak()) {
                        if (!p.jokatutaDago()) continue;

                        TaldeStats local = mapaStats.get(p.getEtxekoTaldea().getIzena());
                        TaldeStats visit = mapaStats.get(p.getKanpokoTaldea().getIzena());

                        if (local == null || visit == null) continue;

                        int gL = p.getEtxekoGolak();
                        int gK = p.getKanpokoGolak();

                        local.jokatuak++; visit.jokatuak++;
                        local.gAlde += gL; local.gAurka += gK;
                        visit.gAlde += gK; visit.gAurka += gL;

                        if (gL > gK) {
                            local.puntuak += 3; local.irabaziak++; visit.galduak++;
                        } else if (gK > gL) {
                            visit.puntuak += 3; visit.irabaziak++; local.galduak++;
                        } else {
                            local.puntuak += 1; local.berdinduak++;
                            visit.puntuak += 1; visit.berdinduak++;
                        }
                    }
                }
            }
        }
        return new ArrayList<>(mapaStats.values());
    }
}