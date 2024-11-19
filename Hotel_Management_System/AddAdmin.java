package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddAdmin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AddAdmin() {
        super("Add New Admin");

        setLayout(null);
        setBounds(500, 200, 500, 350);

        JLabel titleLabel = new JLabel("Add New Admin");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setBounds(150, 20, 200, 30);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 80, 100, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 80, 200, 30);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 130, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 200, 30);
        add(passwordField);

        JButton addButton = new JButton("Add Admin");
        addButton.setBounds(150, 200, 120, 30);
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.black);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    DatabaseConnection dbConn = new DatabaseConnection();
                    String query = "INSERT INTO login(username, password) VALUES(?, ?)";
                    PreparedStatement pst = dbConn.getConnection().prepareStatement(query);
                    pst.setString(1, usernameField.getText());
                    pst.setString(2, new String(passwordField.getPassword()));

                    int rowsInserted = pst.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Admin Added Successfully!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add admin.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database Error: Unable to add admin.");
                }
            }
        });

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AddAdmin();
    }
}

