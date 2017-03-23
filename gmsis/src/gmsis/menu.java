package gmsis;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Prashant
 */
public class menu extends JPanel {

    public menu(JPanel cards, CardLayout c) {

        setLayout(new GridLayout(0, 3));

        JButton Customer = new JButton("customer");
        //Customer.setPreferredSize(new Dimension(10, 10));
        add(Customer);
        Customer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("customer button clicked");
            }
        });

        JButton vehicle = new JButton("Vehicle");
        //vehicle.setPreferredSize(new Dimension(10, 10));
        add(vehicle);
        vehicle.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("vehicle button clicked");
                //c.show(cards, "Card_Vehicle");
            }
        });
        JButton diagnosis = new JButton("Diagnosis");
        //diagnosis.setPreferredSize(new Dimension(10, 10));

        add(diagnosis);
        diagnosis.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("diagnosis button clicked");
                c.show(cards,  "Card_diagnosis");
                
                
            }
        });
        JButton parts = new JButton("Parts");
        //parts.setPreferredSize(new Dimension(10, 10));
        add(parts);
        parts.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("parts button clicked");
                c.show(cards, "Card_Parts");
            }
        });
        JButton maintaince = new JButton("Maintaince");
        //maintaince.setPreferredSize(new Dimension(100, 100));
        add(maintaince);
        maintaince.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("maintaince button clicked");
            }
        });
        JButton test = new JButton("Bookings");
        //test.setPreferredSize(new Dimension(100, 100));
        add(test);
        test.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("Bookings button clicked");
                c.show(cards,  "Card_Booking");
            }
        });

    }

}
