package Hotel_Management_System;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame {

    public static void main(String[] args) {
        new Dashboard().setVisible(true);
    }

    public Dashboard() {
        super("HOTEL MANAGEMENT SYSTEM");

        setLayout(null);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon("/Users/anuragtiwari/Downloads/Dashboard4.png");
        Image img = backgroundImage.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(img));
        backgroundLabel.setBounds(0, 0, 1000, 700);
        setContentPane(backgroundLabel);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu hotelSystemMenu = new JMenu("HOTEL MANAGEMENT");
        hotelSystemMenu.setForeground(Color.BLUE);
        menuBar.add(hotelSystemMenu);

        JMenuItem hotelDetailsMenuItem = new JMenuItem("RECEPTION");
        hotelSystemMenu.add(hotelDetailsMenuItem);

        JMenu adminMenu = new JMenu("ADMIN");
        adminMenu.setForeground(Color.RED);
        menuBar.add(adminMenu);

        JMenuItem addEmployeeMenuItem = new JMenuItem("ADD EMPLOYEE");
        adminMenu.add(addEmployeeMenuItem);

        addEmployeeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    new Employee().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JMenuItem addRoomMenuItem = new JMenuItem("ADD ROOMS");
        adminMenu.add(addRoomMenuItem);

        addRoomMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    new AddRoom().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        hotelDetailsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                 new Reception().setVisible(true);
            }
        });

        JMenuItem addAdminMenuItem = new JMenuItem("ADD ADMIN");
        adminMenu.add(addAdminMenuItem);

        addAdminMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    new AddAdmin().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        hotelDetailsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Reception().setVisible(true);
            }
        });

        setVisible(true);
    }
}
