package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Employee extends JFrame {
    private JTextField nameField, ageField, salaryField, phoneField, emailField, jobField;

    public Employee() {
        super("Add Employee");

        setLayout(null);
        setBounds(500, 200, 700, 600);

        ImageIcon icon = new ImageIcon("/Users/anuragtiwari/Downloads/employee.png");
        Image img = icon.getImage().getScaledInstance(700, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(0, 0, 700, 150);
        add(imageLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 170, 400, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 170, 400, 30);
        add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 220, 400, 30);
        add(ageLabel);

        ageField = new JTextField();
        ageField.setBounds(150, 220, 400, 30);
        add(ageField);

        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(50, 270, 400, 30);
        add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setBounds(150, 270, 400, 30);
        add(salaryField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 320, 400, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(150, 320, 400, 30);
        add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 370, 400, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 370, 400, 30);
        add(emailField);

        JLabel jobLabel = new JLabel("Job:");
        jobLabel.setBounds(50, 420, 400, 30);
        add(jobLabel);

        jobField = new JTextField();
        jobField.setBounds(150, 420, 400, 30);
        add(jobField);

        JButton submitButton = new JButton("Add Employee");
        submitButton.setBounds(150, 470, 150, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.black);
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    DatabaseConnection dbConn = new DatabaseConnection();
                    String query = "INSERT INTO employee(name, age, salary, phone, email, job) VALUES(?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = dbConn.getConnection().prepareStatement(query);
                    pst.setString(1, nameField.getText());
                    pst.setInt(2, Integer.parseInt(ageField.getText()));
                    pst.setDouble(3, Double.parseDouble(salaryField.getText()));
                    pst.setString(4, phoneField.getText());
                    pst.setString(5, emailField.getText());
                    pst.setString(6, jobField.getText());

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Employee Added Successfully!");
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
        new Employee();
    }
}
