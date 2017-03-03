/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Taula de Continguts:
 * *****************************************************************************
 * # Constructors
 * # Getters i Setters
 * *****************************************************************************
 * @author rogerforner
 */
@XmlAccessorType(XmlAccessType.NONE)

public class Artista {

    @XmlAttribute(name = "dni")
    private String _1_dni;
    
    @XmlElement(name = "nom")
    private String _2_nom;
    
    @XmlElement(name = "popular")
    private String _3_popular;
    
    @XmlElement(name = "spotify")
    private String _4_spotify;
    
    
    /***************************************************************************
     * # Constructors
     **************************************************************************/
    public Artista() {}

    public Artista(String _1_dni, String _2_nom, String _3_popular, String _4_spotify) {
        this._1_dni = _1_dni;
        this._2_nom = _2_nom;
        this._3_popular = _3_popular;
        this._4_spotify = _4_spotify;
    }
    
    
    /***************************************************************************
     * # Getters i Setters
     * @return 
     **************************************************************************/
    public String get1_dni() {
        return _1_dni;
    }

    public void set1_dni(String _1_dni) {
        this._1_dni = _1_dni;
    }

    public String get2_nom() {
        return _2_nom;
    }

    public void set2_nom(String _2_nom) {
        this._2_nom = _2_nom;
    }

    public String get3_popular() {
        return _3_popular;
    }

    public void set3_popular(String _3_popular) {
        this._3_popular = _3_popular;
    }

    public String get4_spotify() {
        return _4_spotify;
    }

    public void set4_spotify(String _4_spotify) {
        this._4_spotify = _4_spotify;
    }
    
    
    /**
     * toString.
     * @return
     */
    @Override
    public String toString() {
        return "Artista{" + "_1_dni=" + _1_dni + ", _2_nom=" + _2_nom + ", _3_popular=" + _3_popular + ", _4_spotify=" + _4_spotify + '}';
    }
}
