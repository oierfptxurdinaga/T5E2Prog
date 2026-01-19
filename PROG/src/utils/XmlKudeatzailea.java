package utils;

import java.io.File;
import java.util.ArrayList;
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

    public boolean esportatuXML(Federazioa federazioa, String rutaFitxategia) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            int idKontagailua = 1;
            Element rootElement = doc.createElement("Federazioa");
            doc.appendChild(rootElement);

            // -------------------------------------------------
            // A. TALDE GUZTIAK
            // -------------------------------------------------
            Element taldeakElement = doc.createElement("TaldeGuztiak");
            rootElement.appendChild(taldeakElement);

            for (Talde t : federazioa.getTaldeGuztiak()) {
                taldeakElement.appendChild(sortuTaldeNodoa(doc, t));
            }

            // -------------------------------------------------
            // B. DENBORALDIAK
            // -------------------------------------------------
            Element denboraldiakElement = doc.createElement("Denboraldiak");
            rootElement.appendChild(denboraldiakElement);

            for (Denboraldia d : federazioa.getDenboraldiak()) {
                Element denbElement = doc.createElement("Denboraldia");
                denbElement.setAttribute("urtea", String.valueOf(d.getUrtea()));
                denboraldiakElement.appendChild(denbElement);

                Element denbTaldeak = doc.createElement("DenboraldikoTaldeak");
                denbElement.appendChild(denbTaldeak);

                if (d.getLigakoTaldeak() != null) {
                    for (Talde t : d.getLigakoTaldeak()) {
                        denbTaldeak.appendChild(sortuTaldeNodoa(doc, t));
                    }
                }

                // ---------------------------------------------------------
                // 2. SAILKAPENA
                // ---------------------------------------------------------
                ArrayList<DenboraldiTalde> stats = d.getSailkapena();
                stats.sort((s1, s2) -> {
                    if (s1.getPts() != s2.getPts()) return s2.getPts() - s1.getPts();
                    return s2.getDG() - s1.getDG();
                });

                Element sailkapenaElem = doc.createElement("Sailkapena");
                for (int i = 0; i < stats.size(); i++) {
                    DenboraldiTalde s = stats.get(i);
                    Element lerroa = doc.createElement("Lerroa");

                    elementuaSortu(doc, lerroa, "Posizioa", String.valueOf(i + 1));
                    elementuaSortu(doc, lerroa, "Taldea", s.getTalde().getIzena());
                    
                    // --- ALDAKETA: Ruta garbitu ---
                    String izenGarbia = lortuIzenaGarbia(s.getTalde().getEzkutua());
                    elementuaSortu(doc, lerroa, "Ezkutua", izenGarbia); 
                    
                    elementuaSortu(doc, lerroa, "Puntuak", String.valueOf(s.getPts()));
                    elementuaSortu(doc, lerroa, "Jokatuak", String.valueOf(s.getPJ()));
                    elementuaSortu(doc, lerroa, "Irabaziak", String.valueOf(s.getG()));
                    elementuaSortu(doc, lerroa, "Berdinduak", String.valueOf(s.getE()));
                    elementuaSortu(doc, lerroa, "Galduak", String.valueOf(s.getP()));
                    elementuaSortu(doc, lerroa, "AldekoGolak", String.valueOf(s.getGF()));
                    elementuaSortu(doc, lerroa, "AurkakoGolak", String.valueOf(s.getGC()));

                    sailkapenaElem.appendChild(lerroa);
                }
                denbElement.appendChild(sailkapenaElem);
                
                // ---------------------------------------------------------
                // 3. JARDUNALDIAK
                // ---------------------------------------------------------
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
                                partElement.setAttribute("id", String.valueOf(idKontagailua++));
                                jardElement.appendChild(partElement);

                                // Etxekoa
                                Element etxeko = doc.createElement("EtxekoTaldea");
                                etxeko.appendChild(doc.createTextNode(p.getEtxekoTaldea().getIzena()));
                                partElement.appendChild(etxeko);

                                // --- ALDAKETA: Ruta garbitu ---
                                String etxekoImg = lortuIzenaGarbia(p.getEtxekoTaldea().getEzkutua());
                                Element etxekoIrudia = doc.createElement("EtxekoEzkutua");
                                etxekoIrudia.appendChild(doc.createTextNode(etxekoImg));
                                partElement.appendChild(etxekoIrudia);

                                // Kanpokoa
                                Element kanpoko = doc.createElement("KanpokoTaldea");
                                kanpoko.appendChild(doc.createTextNode(p.getKanpokoTaldea().getIzena()));
                                partElement.appendChild(kanpoko);

                                // --- ALDAKETA: Ruta garbitu ---
                                String kanpokoImg = lortuIzenaGarbia(p.getKanpokoTaldea().getEzkutua());
                                Element kanpokoIrudia = doc.createElement("KanpokoEzkutua");
                                kanpokoIrudia.appendChild(doc.createTextNode(kanpokoImg));
                                partElement.appendChild(kanpokoIrudia);

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

    /**
     * Metodo honek ruta oso bat hartzen du eta bakarrik fitxategiaren izena itzultzen du,
     * estentsioa kenduta.
     * Adibidez: "/resources/images/inter.png" -> "inter"
     */
    private String lortuIzenaGarbia(String rutaOsoa) {
        if (rutaOsoa == null || rutaOsoa.isEmpty()) {
            return "defecto";
        }
        
        // 1. File klasea erabili izena lortzeko (ruta kendu)
        File f = new File(rutaOsoa);
        String izena = f.getName(); // "inter.png"
        
        // 2. Estentsioa kendu (.png)
        int pos = izena.lastIndexOf(".");
        if (pos > 0) {
            izena = izena.substring(0, pos); // "inter"
        }
        
        return izena;
    }

    private Element sortuTaldeNodoa(Document doc, Talde t) {
        Element taldeElement = doc.createElement("Talde");
        elementuaSortu(doc, taldeElement, "Izena", t.getIzena());
        elementuaSortu(doc, taldeElement, "Hiria", t.getHiria());
        
        // --- ALDAKETA: Ruta garbitu hemen ere ---
        String imgGarbia = lortuIzenaGarbia(t.getEzkutua());
        elementuaSortu(doc, taldeElement, "Ezkutua", imgGarbia);

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

    private void elementuaSortu(Document doc, Element gurasoa, String etiketa, String balioa) {
        Element e = doc.createElement(etiketa);
        e.appendChild(doc.createTextNode(balioa));
        gurasoa.appendChild(e);
    }
}