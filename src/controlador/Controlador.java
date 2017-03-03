/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.Artista;
import model.Model;
import vista.Vista;

/**
 * Taula de Continguts:
 * *****************************************************************************
 * # Control
 *   ## ActionListener
 *      ### Afegir
 *      ### Modificar
 *      ### Eliminar
 *   ## MouseAdapter
 * # Guardat de dades
 * # Estat inicial
 * # Botons
 * *****************************************************************************
 * @author rogerforner
 */
public class Controlador {

    Model model;
    Vista vista;
    int filasel = 0;

    public Controlador(Model model, Vista vista) {
        this.model = model;
        this.vista = vista;
        
        vista.setVisible(true);
        
        estatInicial();
        CarregaTaula.carregaTaula(
            model.getArtistesLlista(),
            vista.getjTable1(),
            Artista.class
        );
        control();
    }
    
    /***************************************************************************
     * # Control
     **************************************************************************/
    private void control() {
        /***********************************************************************
         * ## ActionListener
         **********************************************************************/
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /***************************************************************
                 * ### Afegir
                 **************************************************************/
                if (e.getSource().equals(vista.getjButton1())) {
                    boolean existeix = false;
                    String dniEscollit = "";
                    String dniExistent = "";
                    
                    if (vista.getjTextField1().getText().length() != 0 &&
                        vista.getjTextField2().getText().length() != 0
                    ) {
                        //Comrovem que no hi hagi cap DNI igual al que afegim
                        for (int i = 0; i < vista.getjTable1().getRowCount(); i++) {
                            if(vista.getjTextField1().getText().toString().equals(
                                vista.getjTable1().getValueAt(i, 0).toString()
                            )) {
                                existeix = true;
                                dniEscollit = vista.getjTextField1().getText().toString();
                                dniExistent = vista.getjTable1().getValueAt(i, 1).toString();
                            }
                        }
                        
                        if (existeix) {
                            vista.getjLabel7().setText("Error! El DNI/NIE "
                                +dniEscollit+" ja existeix ("+dniExistent+")");
                            
                            vista.getjLabel2().setForeground(Color.red);
                            vista.getjLabel3().setForeground(Color.black);
                            
                        } else {
                            Afegir();
                            vista.getjLabel2().setForeground(Color.black);
                            vista.getjLabel3().setForeground(Color.black);
                            vista.getjLabel6().setForeground(Color.black);
                            vista.getjLabel7().setText("");

                            CarregaTaula.carregaTaula(
                                model.getArtistesLlista(),
                                vista.getjTable1(),
                                Artista.class
                            );

                            Guardar();
                        }
                        
                    } else {
                        vista.getjLabel2().setForeground(Color.red);
                        vista.getjLabel3().setForeground(Color.red);
                        vista.getjLabel6().setForeground(Color.red);
                    }
                }
                /***************************************************************
                 * ### Modificar
                 **************************************************************/
                if (e.getSource().equals(vista.getjButton2())) {

                    Modificar();
                    
                    CarregaTaula.carregaTaula(
                        model.getArtistesLlista(),
                        vista.getjTable1(),
                        Artista.class
                    );
                    
                    Guardar();
                }
                /***************************************************************
                 * ### Eliminar
                 **************************************************************/
                if (e.getSource().equals(vista.getjButton3())) {

                    Eliminar();

                    CarregaTaula.carregaTaula(
                        model.getArtistesLlista(),
                        vista.getjTable1(),
                        Artista.class
                    );
                    
                    Guardar();
                }
            }
        };
        //Afegir al ActionListener
        vista.getjButton1().addActionListener(actionListener);
        vista.getjButton2().addActionListener(actionListener);
        vista.getjButton3().addActionListener(actionListener);
        
        /***********************************************************************
         * ## MouseAdapter
         **********************************************************************/
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getSource().equals(vista.getjTable1())) {

                    filasel = vista.getjTable1().getSelectedRow();
                    
                    //DNI
                    vista.getjTextField1().setText(
                        vista.getjTable1().getValueAt(filasel, 0).toString()
                    );
                    //Nom sencer
                    vista.getjTextField2().setText(
                        vista.getjTable1().getValueAt(filasel, 1).toString()
                    );
                    //Popular
                    vista.getjTextField3().setText(
                        vista.getjTable1().getValueAt(filasel, 2).toString()
                    );
                    //Spotify
                    vista.getjTextField4().setText(
                        vista.getjTable1().getValueAt(filasel, 3).toString()
                    );
                    
                    //Si seleccionem una fila ocultem l'error de selecció de
                    //fila
                    vista.getjLabel7().setText("");
                }
            }
        };
        //Afegir al MouseListener
        vista.getjTable1().addMouseListener(mouseAdapter);
    }

    /***************************************************************************
     * # Guardat de dades
     **************************************************************************/
    public void Guardar() {
        model.marshal();
    }
    
    /***************************************************************************
     * # Estat inicial
     **************************************************************************/
    public void estatInicial() {
        //Error
        vista.getjLabel7().setText("");
    }
    
    /***************************************************************************
     * # Botons
     **************************************************************************/
    public void Afegir() {
        model.getArtistesLlista().add(new Artista(
            vista.getjTextField1().getText(),
            vista.getjTextField2().getText(),
            vista.getjTextField3().getText(),
            vista.getjTextField4().getText()
        ));
        
        vista.getjButton1().setForeground(Color.black);
    }
    
    public void Modificar() {
        if (vista.getjTable1().getRowCount() != 0) {
            filasel = vista.getjTable1().getSelectedRow();
            
            if (filasel != -1) {
                //DNI
                model.getArtistesLlista().get(filasel).set1_dni(
                    vista.getjTextField1().getText()
                );
                //Nom sencer
                model.getArtistesLlista().get(filasel).set2_nom(
                    vista.getjTextField2().getText()
                );
                //Popular
                model.getArtistesLlista().get(filasel).set3_popular(
                    vista.getjTextField3().getText()
                );
                //Spotify
                model.getArtistesLlista().get(filasel).set4_spotify(
                    vista.getjTextField4().getText()
                );
                
            } else {
                vista.getjLabel7().setText("Error! Selecciona una fila.");
            }
            
        } else {
            vista.getjLabel7().setText("Error! No hi ha dades en la taula.");
            vista.getjButton1().setForeground(Color.red);
        }
    }
    
    public void Eliminar() {
        if (vista.getjTable1().getRowCount() != 0) {
            filasel = vista.getjTable1().getSelectedRow();
            
            if (filasel != -1) {
                model.getArtistesLlista().remove(filasel);
                
            } else {
                vista.getjLabel7().setText("Error! Selecciona una fila.");
            }
            
        } else {
            vista.getjLabel7().setText("Error! No hi ha dades en la taula.");
            vista.getjButton1().setForeground(Color.red);
        }
    }
}
