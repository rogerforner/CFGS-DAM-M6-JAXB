/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Taula de Continguts:
 * *****************************************************************************
 * # Constructors
 * # Getters i Setters
 * *****************************************************************************
 * @author rogerforner
 */
@XmlRootElement(name = "Artistes")
@XmlAccessorType(XmlAccessType.FIELD)

public class Artistes {

    @XmlElement(name = "Artista")
    ArrayList<Artista> artistes = new ArrayList();
    
    /***************************************************************************
     * Constructors
     **************************************************************************/
    public Artistes() {}

    public Artistes(ArrayList<Artista> artistes) {
        this.artistes = artistes;
    }
    
    
    /***************************************************************************
     * # Getters i Setters
     * @return 
     **************************************************************************/
    public ArrayList<Artista> getArtistes() {
        return artistes;
    }

    public void setArtistes(ArrayList<Artista> artistes) {
        this.artistes = artistes;
    }
}
