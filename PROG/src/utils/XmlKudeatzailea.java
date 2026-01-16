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

import model.*; // Importar tus modelos

public class XmlKudeatzailea {

    /**
     * Federazioa objektua XML fitxategi batera esportatzen du.
     * @param federazioa Datuak dituen objektu nagusia.
     * @param rutaFitxategia Fitxategia gordetzeko ruta (adibidez: "datuak.xml")
     * @return true ondo joan bada, false bestela.
     */
    public boolean esportatuXML(Federazioa federazioa, String rutaFitxategia) {
        try {
            // 1. Dokumentua sortu
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // 2. Elementu erroa (Raíz) -> <Federazioa>
            Element rootElement = doc.createElement("Federazioa");
            doc.appendChild(rootElement);

            // -------------------------------------------------
            // 3. TALDEAK ESPORTATU
            // -------------------------------------------------
            Element taldeakElement = doc.createElement("Taldeak");
            rootElement.appendChild(taldeakElement);

            for (Talde t : federazioa.getTaldeGuztiak()) {
                Element taldeElement = doc.createElement("Talde");
                taldeakElement.appendChild(taldeElement);

                //Taldearen izena (Asumiendo que Talde tiene getIzena)
                Element izena = doc.createElement("Izena");
                izena.appendChild(doc.createTextNode(t.getIzena()));
                taldeElement.appendChild(izena);

                // Jokalariak
                Element jokalariakElement = doc.createElement("Jokalariak");
                taldeElement.appendChild(jokalariakElement);

                // Asumiendo que Talde tiene getJokalariak()
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

            // -------------------------------------------------
            // 4. DENBORALDIAK ESPORTATU
            // -------------------------------------------------
            Element denboraldiakElement = doc.createElement("Denboraldiak");
            rootElement.appendChild(denboraldiakElement);

            for (Denboraldia d : federazioa.getDenboraldiak()) {
                Element denbElement = doc.createElement("Denboraldia");
                // Urtea atributu gisa jarriko dugu
                denbElement.setAttribute("urtea", String.valueOf(d.getUrtea()));
                denboraldiakElement.appendChild(denbElement);

                // Jardunaldiak
                Element jardunaldiakElement = doc.createElement("Jardunaldiak");
                denbElement.appendChild(jardunaldiakElement);

                for (Jardunaldi j : d.getLigakoJardunaldi()) {
                    Element jardElement = doc.createElement("Jardunaldi");
                    jardElement.setAttribute("zenbakia", String.valueOf(j.getJardunaldiZbk()));
                    jardunaldiakElement.appendChild(jardElement);

                    // Partiduak
                    for (Partidua p : j.getPartiduak()) {
                        Element partElement = doc.createElement("Partidua");
                        jardElement.appendChild(partElement);

                        // Etxeko taldea (Izena bakarrik gordetzen dugu erreferentzia ziklikoak ekiditeko)
                        Element etxeko = doc.createElement("EtxekoTaldea");
                        // Asumiendo getIzena:
                        etxeko.appendChild(doc.createTextNode(p.getEtxekoTaldea().getIzena())); 
                        // Si no tienes getIzena accesible directo, usa toString o similar
                        partElement.appendChild(etxeko);

                        // Kanpoko taldea
                        Element kanpoko = doc.createElement("KanpokoTaldea");
                        kanpoko.appendChild(doc.createTextNode(p.getKanpokoTaldea().getIzena()));
                        partElement.appendChild(kanpoko);

                        // Emaitza (soilik jokatuta badago)
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

            // 5. Fitxategia idatzi (Guardar el archivo)
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Formateo para que el XML se vea bonito (saltos de línea y sangría)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(rutaFitxategia));

            transformer.transform(source, result);

            System.out.println("XML fitxategia ondo gorde da: " + rutaFitxategia);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}