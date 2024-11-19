package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BookRoom extends JFrame {
    private JComboBox<String> roomComboBox;
    private JTextField customerNameField, phoneField, emailField;

    public BookRoom() {
        super("Book Room");

        setBounds(400, 200, 600, 400);
        setLayout(null);

        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setBounds(50, 50, 150, 30);
        add(customerNameLabel);

        customerNameField = new JTextField();
        customerNameField.setBounds(200, 50, 200, 30);
        add(customerNameField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 100, 150, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(200, 100, 200, 30);
        add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 150, 150, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(200, 150, 200, 30);
        add(emailField);

        JLabel roomLabel = new JLabel("Select Room:");
        roomLabel.setBounds(50, 200, 150, 30);
        add(roomLabel);

        roomComboBox = new JComboBox<>(fetchAvailableRooms());
        roomComboBox.setBounds(200, 200, 200, 30);
        add(roomComboBox);

        JButton bookButton = new JButton("Book Room");
        bookButton.setBounds(200, 250, 150, 30);
        bookButton.setBackground(Color.BLACK);
        bookButton.setForeground(Color.black);
        add(bookButton);

        bookButton.addActionListener(e -> bookRoom());

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private String[] fetchAvailableRooms() {
        java.util.List<String> availableRooms = new java.util.ArrayList<>();
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String query = "SELECT room_number FROM room WHERE room_number NOT IN (SELECT room_number FROM booking)";
            Statement stmt = dbConn.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                availableRooms.add(rs.getString("room_number"));
            }

            rs.close();
            stmt.close();
            dbConn.getConnection().close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching available rooms.");
        }

        return availableRooms.toArray(new String[0]);
    }

    private void bookRoom() {
        String customerName = customerNameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String roomNumber = (String) roomComboBox.getSelectedItem();

        if (customerName.isEmpty() || phone.isEmpty() || email.isEmpty() || roomNumber == null) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String query = "INSERT INTO booking(customer_name, phone, email, room_number) VALUES(?, ?, ?, ?)";
            PreparedStatement pst = dbConn.getConnection().prepareStatement(query);

            pst.setString(1, customerName);
            pst.setString(2, phone);
            pst.setString(3, email);
            pst.setString(4, roomNumber);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Room booked successfully!");

            pst.close();
            dbConn.getConnection().close();
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error booking room.");
        }
    }

    public static void main(String[] args) {
        new BookRoom();
    }
}
