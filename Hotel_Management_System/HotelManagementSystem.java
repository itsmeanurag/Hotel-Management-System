package Hotel_Management_System;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HotelManagementSystem extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1;

    public HotelManagementSystem() {

        setSize(900, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("/Users/anuragtiwari/Downloads/Image2.png");
        Image img = icon.getImage().getScaledInstance(900, 450, Image.SCALE_SMOOTH);
        l1 = new JLabel(new ImageIcon(img));
        l1.setBounds(0, 0, 900, 450);

        JLabel lid = new JLabel("HOTEL MANAGEMENT SYSTEM");
        lid.setFont(new Font("serif", Font.BOLD, 30));
        lid.setForeground(Color.black);
        lid.setBounds(200, 100, 700, 50);

        b1 = new JButton("Next");
        b1.setBounds(375, 300, 150, 50);
        b1.setBackground(Color.WHITE);
        b1.setForeground(Color.BLACK);
        b1.addActionListener(this);

        l1.add(lid);
        l1.add(b1);

        add(l1);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        new Login().setVisible(true);
        this.setVisible(false);
    }

    public static void main(String[] args) {
        HotelManagementSystem window = new HotelManagementSystem();
        window.setVisible(true);
    }
}
