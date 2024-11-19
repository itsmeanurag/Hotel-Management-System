package Hotel_Management_System;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class Billing extends JFrame {

    private JTextField bookingIdField;
    private String customerName;
    private String contactNo;
    private int roomNo;
    private int billAmount;

    public Billing() {
        setTitle("Generate Bill");
        setLayout(new BorderLayout(10, 10));
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180)); // Light Steel Blue color
        JLabel titleLabel = new JLabel("Generate Bill");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Input Fields Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel bookingIdLabel = new JLabel("Booking ID:");
        bookingIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        bookingIdField = new JTextField();
        bookingIdField.setFont(new Font("Tahoma", Font.PLAIN, 14));

        inputPanel.add(bookingIdLabel);
        inputPanel.add(bookingIdField);

        add(inputPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton generateButton = new JButton("Generate Bill");
        JButton savePdfButton = new JButton("Save as PDF");
        JButton cancelButton = new JButton("Cancel");

        // Style buttons
        styleButton(generateButton);
        styleButton(savePdfButton);
        styleButton(cancelButton);

        generateButton.addActionListener(e -> {
            String bookingId = bookingIdField.getText();
            if (bookingId == null || bookingId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String daysStr = JOptionPane.showInputDialog(this, "Enter the number of days:");
                if (daysStr == null || daysStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Number of days is required to generate the bill.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int days;
                try {
                    days = Integer.parseInt(daysStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input for number of days.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                generateBill(bookingId, days);
            }
        });

        savePdfButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsPdf();
            }
        });

        cancelButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        buttonPanel.add(generateButton);
        buttonPanel.add(savePdfButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180)); // Light Steel Blue background
        button.setForeground(Color.black);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(140, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
    }

    public void generateBill(String bookingId, int days) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmanagementsystem", "root", "password");

            String checkBookingQuery = "SELECT * FROM booking WHERE id = ?";
            ps = conn.prepareStatement(checkBookingQuery);
            ps.setString(1, bookingId);
            rs = ps.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "No booking found with the given Booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            customerName = rs.getString("customer_name");
            contactNo = rs.getString("phone");
            roomNo = rs.getInt("room_number");

            billAmount = days * 3000;

            String insertBillQuery = "INSERT INTO billing (booking_id, amount) VALUES (?, ?)";
            ps = conn.prepareStatement(insertBillQuery);
            ps.setString(1, bookingId);
            ps.setInt(2, billAmount);
            ps.executeUpdate();

            String updateRoomQuery = "UPDATE room SET is_booked = 0 WHERE room_number = ?";
            ps = conn.prepareStatement(updateRoomQuery);
            ps.setInt(1, roomNo);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Bill Generated Successfully!\n" +
                            "Booking ID: " + bookingId + "\n" +
                            "Customer Name: " + customerName + "\n" +
                            "Room No: " + roomNo + "\n" +
                            "Contact No: " + contactNo + "\n" +
                            "Bill Amount: Rs" + billAmount,
                    "Bill Details", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveAsPdf() {
        String bookingId = bookingIdField.getText();
        if (bookingId == null || bookingId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile() + ".pdf"));
                document.open();

                document.add(new Paragraph("Hotel Management System"));
                document.add(new Paragraph("Bill Details"));
                document.add(new Paragraph("Booking ID: " + bookingId));
                document.add(new Paragraph("Customer Name: " + customerName));
                document.add(new Paragraph("Room No: " + roomNo));
                document.add(new Paragraph("Contact No: " + contactNo));
                document.add(new Paragraph("Bill Amount: Rs " + billAmount));

                document.close();
                JOptionPane.showMessageDialog(this, "PDF saved successfully.");
            } catch (DocumentException | IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new Billing();
    }
}
