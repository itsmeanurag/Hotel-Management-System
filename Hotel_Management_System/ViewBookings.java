package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.*;

public class ViewBookings extends JFrame {

    private JTable bookingsTable;

    public ViewBookings() {
        super("View Bookings");

        setBounds(300, 200, 900, 400);
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Customer Name", "Phone", "Email", "Room Number", "Booking Date"};
        String[][] data = fetchBookingData();

        // Create a custom table model to allow better customization
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Prevent editing cells
            }
        };

        bookingsTable = new JTable(model);
        bookingsTable.setRowHeight(30);  // Set row height to make the table look cleaner

        // Customizing the table header appearance
        JTableHeader header = bookingsTable.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 14));  // Set header font (neutral style)
        header.setBackground(new Color(0, 128, 128));  // Teal background color
        header.setForeground(Color.WHITE);  // White text color
        header.setReorderingAllowed(false);  // Disable column reordering

        // Customizing table cell appearance
        bookingsTable.setFont(new Font("Tahoma", Font.PLAIN, 14));  // Set font for table cells
        bookingsTable.setSelectionBackground(new Color(0, 128, 128));  // Teal selection color
        bookingsTable.setSelectionForeground(Color.WHITE);  // White text on selected rows
        bookingsTable.setBackground(Color.WHITE);  // White background for cells

        // Set column alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < bookingsTable.getColumnCount(); i++) {
            bookingsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Add table to scroll pane
        JScrollPane sp = new JScrollPane(bookingsTable);
        sp.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128)));  // Border color for the scroll pane
        add(sp, BorderLayout.CENTER);

        // Add a close button with proper styling
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(0, 128, 128));  // Teal background
        closeButton.setForeground(Color.WHITE);  // White text color
        closeButton.setFont(new Font("Tahoma", Font.BOLD, 14));  // Bold font
        closeButton.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128)));  // Button border color
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private String[][] fetchBookingData() {
        java.util.List<String[]> bookingList = new java.util.ArrayList<>();
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            Connection conn = dbConn.getConnection();  // Get connection from DatabaseConnection class
            if (conn == null) {
                throw new SQLException("Database connection is null.");
            }

            String query = "SELECT id, customer_name, phone, email, room_number, booking_date FROM booking";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String[] row = new String[6];
                row[0] = String.valueOf(rs.getInt("id"));
                row[1] = rs.getString("customer_name");
                row[2] = rs.getString("phone");
                row[3] = rs.getString("email");
                row[4] = rs.getString("room_number");
                row[5] = rs.getString("booking_date");
                bookingList.add(row);
            }

            rs.close();
            stmt.close();
            conn.close();  // Always remember to close the connection after use

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching booking data.");
        }

        return bookingList.toArray(new String[0][0]);
    }

    public static void main(String[] args) {
        new ViewBookings();
    }
}
