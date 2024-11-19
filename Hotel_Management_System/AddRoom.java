package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddRoom extends JFrame {
    private JTextField roomNumberField, priceField;

    public AddRoom() {
        super("Add Room");

        setLayout(null);
        setBounds(500, 200, 700, 400);

        ImageIcon icon = new ImageIcon("/Users/anuragtiwari/Downloads/rooms.png");
        Image img = icon.getImage().getScaledInstance(700, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(0, 0, 700, 150);
        add(imageLabel);

        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberLabel.setBounds(50, 170, 120, 30);
        add(roomNumberLabel);

        roomNumberField = new JTextField();
        roomNumberField.setBounds(180, 170, 150, 30);
        add(roomNumberField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 220, 100, 30);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(180, 220, 150, 30);
        add(priceField);

        JButton addButton = new JButton("Add Room");
        addButton.setBounds(180, 270, 150, 30);
        addButton.setBackground(Color.white);
        addButton.setForeground(Color.black);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    DatabaseConnection dbConn = new DatabaseConnection();
                    String query = "INSERT INTO room(room_number, price) VALUES(?, ?)";
                    PreparedStatement pst = dbConn.getConnection().prepareStatement(query);
                    pst.setString(1, roomNumberField.getText());
                    pst.setDouble(2, Double.parseDouble(priceField.getText()));

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Room Added Successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AddRoom();
    }
}
