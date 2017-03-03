/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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

/**
 *
 * @author rogerforner
 */
public class CarregaTaula {
    
    public static void carregaTaula(ArrayList resultSet, JTable taula, Class<?> classe) {
        // TODO add your handling code here:
        //Quan tornem a carregar la taula perdem la selecció que haviem fet i posem filasel a -1
        
        Vector columnNames = new Vector();
        Vector data = new Vector();
        DefaultTableModel model;

        //Anotem el nº de camps de la classe
        Field[] camps = classe.getDeclaredFields();

        //Ordenem els camps alfabèticament
        Arrays.sort(camps, new OrdenarCampClasseAlfabeticament());
        int ncamps = camps.length;

        //Recorrem els camps de la classe i posem els seus noms com a columnes de la taula
        //Com hem hagut de posar numero davant el nom dels camps, mostrem el nom a partir de la 4ª lletra 
        for (Field f : camps) {
            columnNames.addElement(f.getName().substring(3));
        }

        //Si hi ha algun element a l'arraylist omplim la taula
        if (resultSet.size() != 0) {
            //Guardem els descriptors de mètode que ens interessen (els getters)
            Vector<Method> methods = new Vector(resultSet.size());

            try {
                PropertyDescriptor[] descriptors = Introspector.getBeanInfo(classe).getPropertyDescriptors();
                Arrays.sort(descriptors, new OrdenarMetodeClasseAlfabeticament());

                for (PropertyDescriptor pD : descriptors) {
                    Method m = pD.getReadMethod();

                    if (m != null & !m.getName().equals("getClass")) {
                        methods.addElement(m);
                    }
                }

            } catch (Exception ex) {
                //Logger.getLogger(Equips.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (Object m : resultSet) {
                Vector row = new Vector(ncamps);

                for (Method mD : methods) {
                    try {
                        row.addElement(mD.invoke(m));
                    } catch (IllegalAccessException ex) {
//                        Logger.getLogger(Equips.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
//                        Logger.getLogger(Equips.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
//                        Logger.getLogger(Equips.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

//               for(Field f:classe.getDeclaredFields())
//                    try {
//                        row.addElement(f.get(m));
//                   } catch (IllegalArgumentException ex) {
//                       Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
//                   } catch (IllegalAccessException ex) {
//                       Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
//                   }
                data.addElement(row);
            }
        }

        model = new DefaultTableModel(data, columnNames);
        taula.setModel(model);

        TableColumn column;
        for (int i = 0; i < taula.getColumnCount(); i++) {
            column = taula.getColumnModel().getColumn(i);
            column.setMaxWidth(250);
        }
    }

    public static class OrdenarMetodeClasseAlfabeticament implements Comparator {

        public int compare(Object o1, Object o2) {

            Method mo1 = ((PropertyDescriptor) o1).getReadMethod();
            Method mo2 = ((PropertyDescriptor) o2).getReadMethod();

            if (mo1 != null && mo2 != null) {
                return (int) mo1.getName().compareToIgnoreCase(mo2.getName());
            }

            if (mo1 == null) {
                return -1;

            } else {
                return 1;
            }
        }
    }

    public static class OrdenarCampClasseAlfabeticament implements Comparator {

        public int compare(Object o1, Object o2) {
            return (int) (((Field) o1).getName().compareToIgnoreCase(((Field) o2).getName()));
        }
    }
}
