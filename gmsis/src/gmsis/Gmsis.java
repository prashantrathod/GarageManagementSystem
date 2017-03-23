/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmsis;

import java.awt.*;
import java.awt.Desktop.Action;
import java.awt.event.*;
import javafx.event.ActionEvent;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.io.IOException;


public class Gmsis implements ItemListener {
    
    CardLayout c = new CardLayout();
    JPanel cards; //a panel that uses CardLayout
    
    final static String DIAGNOSIS = "Card_diagnosis";
    final static String MENU = "Card_menu";
    final static String VEHICLE = "Card_Vehicle";
    final static String PARTS = "Card_Parts";
    final static String CUSTOMER = "Card_Customer";
    final static String MAINTAINCE = "Card_Maintaince";
    final static String BOOKINGS = "Card_Booking";
    
    
    public void addComponentToPane(Container pane) {

        //Create the panel that contains the "cards".
        cards = new JPanel(c);
        
        menu m = new menu(cards, c);
        cards.add(m, MENU);
        
        diagnosis d = new diagnosis(cards, c);
        cards.add(d, DIAGNOSIS);
        
        bookings b = new bookings(cards, c);
        cards.add(b, BOOKINGS);
        
        /*PartsPanel p = new PartsPanel(cards,c);
        cards.add(p, PARTS);*/
        
        //VehicleRecord vr = new VehicleRecord(cards, c);
        //cards.add(vr, VEHICLE);
        
      //  cards.add(card2, DIAGNOSIS);
        /*cards.add(card3, VEHICLE);
        cards.add(card4, PARTS);
        cards.add(card5, CUSTOMER);
        cards.add(card6, MAINTAINCE);*/

        pane.add(cards, BorderLayout.CENTER);
    
        JLabel label = new JLabel("Welcome to the Garage Management System Menu", SwingConstants.CENTER);
        pane.add(label, BorderLayout.PAGE_START);
       

    
    
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    private static void GUI(){
        //Create and set up the window.
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Gmsis demo = new Gmsis();
        demo.addComponentToPane(frame.getContentPane());
        //frame.repaint();
        //Display the window.
        
        frame.pack();
        frame.setSize(1300, 900);
        frame.setResizable(false);
        frame.setVisible(true);
    }
       

    public static void main(String[] args){
        /* Use an appropriate Look and Feel */
        
         
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            UIManager.put("nimbusBase", new Color(0,188,255));
            break;
        }
    }
    GUI(); 
} catch (Exception e)  {
    // If Nimbus is not available, you can set the GUI to another look and feel.
}
              
            }
        });
    }
}
