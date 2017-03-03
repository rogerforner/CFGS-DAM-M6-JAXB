/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import controlador.Controlador;
import model.Model;
import vista.Vista;

/**
 *
 * @author rogerforner
 */
public class Main {
    static Model model = new Model();
    static Vista vista = new Vista();

    public static void main(String[] args) {
        new Controlador(model, vista);
    }
}