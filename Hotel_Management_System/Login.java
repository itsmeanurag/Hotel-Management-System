package Hotel_Management_System;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JLabel l1, l2, imageLabel;
    JTextField t1;
    JPasswordField t2;
    JButton b1, b2;

    Login() {
        super("Login");
        setLayout(null);

        ImageIcon icon = new ImageIcon("/Users/anuragtiwari/Downloads/loginphoto.jpg");
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(140, 10, 100, 100);
        add(imageLabel);

        l1 = new JLabel("Username");
        l1.setBounds(40, 120, 100, 30);
        add(l1);

        l2 = new JLabel("Password");
        l2.setBounds(40, 170, 100, 30);
        add(l2);

        t1 = new JTextField();
        t1.setBounds(150, 120, 150, 30);
        add(t1);

        t2 = new JPasswordField();
        t2.setBounds(150, 170, 150, 30);
        add(t2);

        b1 = new JButton("Login");
        b1.setBounds(40, 230, 120, 30);
        b1.setFont(new Font("serif", Font.BOLD, 15));
        b1.addActionListener(this);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.BLACK);
        add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(180, 230, 120, 30);
        b2.setFont(new Font("serif", Font.BOLD, 15));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.BLACK);
        add(b2);

        b2.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);

        setVisible(true);
        setSize(400, 350);
        setLocation(200, 200);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            try {
                DatabaseConnection dbConn = new DatabaseConnection();
                String u = t1.getText();
                String v = new String(t2.getPassword());

                String query = "SELECT * FROM login WHERE username=? AND password=?";

                try (PreparedStatement stmt = dbConn.getConnection().prepareStatement(query)) {
                    stmt.setString(1, u);
                    stmt.setString(2, v);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        new Dashboard().setVisible(true);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid login");
                    }
                } finally {
                    dbConn.getConnection().close();
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error connecting to the database.");
            }
        } else if (ae.getSource() == b2) {
            System.exit(0);
        }
    }

    public static void main(String[] arg) {
        new Login();
    }
}
