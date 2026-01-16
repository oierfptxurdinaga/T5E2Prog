package utils;

import java.io.File;
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

            Element rootElement = doc.createElement("Federazioa");
            doc.appendChild(rootElement);

            // -------------------------------------------------
            // A. TALDEAK (MAISUA / MASTER) - Egoera Orokorra
            // -------------------------------------------------
            Element taldeakElement = doc.createElement("TaldeGuztiak"); // Izen argiagoa
            rootElement.appendChild(taldeakElement);

            for (Talde t : federazioa.getTaldeGuztiak()) {
                // Erabiltzen dugu metodo laguntzailea kodea ez errepikatzeko
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

                // --- ALDAKETA GARRANTZITSUA HEMEN ---
                // Denboraldi bakoitzaren barruan, taldeen "FOTOA" gordetzen dugu.
                // Horrela, urte horretako jokalariak gordetzen dira.
                Element denbTaldeak = doc.createElement("DenboraldikoTaldeak");
                denbElement.appendChild(denbTaldeak);

                if (d.getLigakoTaldeak() != null) {
                    for (Talde t : d.getLigakoTaldeak()) {
                        // Hemen taldearen KOPIA gordetzen da (jokalari zaharrekin)
                        denbTaldeak.appendChild(sortuTaldeNodoa(doc, t));
                    }
                }

                // --- JARDUNALDIAK ---
                Element jardunaldiakElement = doc.createElement("Jardunaldiak");
                denbElement.appendChild(jardunaldiakElement);

                if (d.getLigakoJardunaldi() != null) {
                    for (Jardunaldi j : d.getLigakoJardunaldi()) {
                        Element jardElement = doc.createElement("Jardunaldi");
                        jardElement.setAttribute("zenbakia", String.valueOf(j.getJardunaldiZbk()));
                        jardunaldiakElement.appendChild(jardElement);

                        // Partiduak
                        if (j.getPartiduak() != null) {
                            for (Partidua p : j.getPartiduak()) {
                                Element partElement = doc.createElement("Partidua");
                                jardElement.appendChild(partElement);

                                // Izenak bakarrik (datu osoak 'DenboraldikoTaldeak' atalean daude jada)
                                Element etxeko = doc.createElement("EtxekoTaldea");
                                etxeko.appendChild(doc.createTextNode(p.getEtxekoTaldea().getIzena()));
                                partElement.appendChild(etxeko);

                                Element kanpoko = doc.createElement("KanpokoTaldea");
                                kanpoko.appendChild(doc.createTextNode(p.getKanpokoTaldea().getIzena()));
                                partElement.appendChild(kanpoko);

                                // Emaitza
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
            System.out.println("XML gordeta: " + rutaFitxategia);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo laguntzailea Talde baten XML nodoa sortzeko.
     * Kodea bitan ez idazteko balio du (behin orokorrean eta behin denboraldian).
     */
    private Element sortuTaldeNodoa(Document doc, Talde t) {
        Element taldeElement = doc.createElement("Talde");
        
        // Atributu bezala ID bat baduzu, hemen jarri dezakezu
        // taldeElement.setAttribute("id", ...);

        Element izena = doc.createElement("Izena");
        izena.appendChild(doc.createTextNode(t.getIzena()));
        taldeElement.appendChild(izena);
        
        Element hiria = doc.createElement("Hiria");
        hiria.appendChild(doc.createTextNode(t.getHiria()));
        taldeElement.appendChild(hiria);

        // Jokalariak
        Element jokalariakElement = doc.createElement("Jokalariak");
        taldeElement.appendChild(jokalariakElement);

        if (t.getJokalariak() != null) {
            for (Jokalari j : t.getJokalariak()) {
                Element jokElement = doc.createElement("Jokalari");
                jokElement.setAttribute("dortsala", String.valueOf(j.getDortsala()));

                Element izenaJok = doc.createElement("Izena");
                izenaJok.appendChild(doc.createTextNode(j.getIzena()));
                jokElement.appendChild(izenaJok);

                Element abizenaJok = doc.createElement("Abizena");
                abizenaJok.appendChild(doc.createTextNode(j.getAbizena()));
                jokElement.appendChild(abizenaJok);

                Element posizioa = doc.createElement("Posizioa");
                posizioa.appendChild(doc.createTextNode(j.getPosizio()));
                jokElement.appendChild(posizioa);

                jokalariakElement.appendChild(jokElement);
            }
        }
        return taldeElement;
    }
}