/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Taula de Continguts:
 * *****************************************************************************
 * # Unmarshal
 * # Marshal
 * # Getters i Setters
 * *****************************************************************************
 * @author rogerforner
 */
public class Model {

    ArrayList<Artista> artistesLlista;

    public Model() {
        unmarshal();
    }

    /***************************************************************************
     * # Unmarshal
     **************************************************************************/
    private void unmarshal() {
        try {

            JAXBContext jaxbCtx;

            jaxbCtx = JAXBContext.newInstance(Artistes.class);

            File xmlWriter = new File("artistes.xml");

            Artistes recullDadesXML = (Artistes) jaxbCtx.createUnmarshaller().unmarshal(xmlWriter);
            
            artistesLlista = recullDadesXML.getArtistes();
            
        } catch (JAXBException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /***************************************************************************
     * # Marshal
     **************************************************************************/
    public void marshal() {
        try {
            Artistes artistes = new Artistes(artistesLlista);

            JAXBContext jaxbCtx;

            jaxbCtx = JAXBContext.newInstance(Artistes.class);

            File xmlWriter = new File("artistes.xml");
            Marshaller jaxbMarshaller = jaxbCtx.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(artistes, xmlWriter);
            System.out.println("MARSHAL - Creacio fitxer");

        } catch (JAXBException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /***************************************************************************
     * # Getters i Setters
     * @return
     **************************************************************************/
    public ArrayList<Artista> getArtistesLlista() {
        return artistesLlista;
    }

    public void setArtistesLlista(ArrayList<Artista> artistesLlista) {
        this.artistesLlista = artistesLlista;
    }

}
