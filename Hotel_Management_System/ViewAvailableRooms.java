package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.*;

public class ViewAvailableRooms extends JFrame {

    private JTable table;

    public ViewAvailableRooms() {
        super("Available Rooms");

        setBounds(300, 150, 800, 400);
        setLayout(new BorderLayout());

        String[] columnNames = {"Room Number", "Price"};
        String[][] data = fetchRoomData();

        // Create a custom table model to allow better customization
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Prevent editing cells
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);  // Set row height to make the table look cleaner

        // Customizing the table header appearance
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));  // Set header font
        header.setBackground(new Color(0, 51, 102));  // Dark blue background
        header.setForeground(Color.WHITE);  // White text color
        header.setReorderingAllowed(false);  // Disable column reordering

        // Customizing table cell appearance
        table.setFont(new Font("Arial", Font.PLAIN, 14));  // Set font for table cells
        table.setSelectionBackground(new Color(0, 51, 102));  // Dark blue selection color
        table.setSelectionForeground(Color.WHITE);  // White text on selected rows
        table.setBackground(Color.WHITE);  // White background for cells

        // Set column alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Add table to scroll pane
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102)));  // Border color for the scroll pane
        add(sp, BorderLayout.CENTER);

        // Add a close button with proper styling
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(0, 51, 102));  // Dark blue background
        closeButton.setForeground(Color.WHITE);  // White text color
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));  // Bold font
        closeButton.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102)));  // Button border color
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private String[][] fetchRoomData() {
        java.util.List<String[]> roomData = new java.util.ArrayList<>();
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String query = "SELECT room_number, price FROM room WHERE is_booked = 0";  // Ensure only available rooms are fetched
            Statement stmt = dbConn.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String[] row = {
                        rs.getString("room_number"),
                        rs.getString("price")
                };
                roomData.add(row);
            }

            rs.close();
            stmt.close();
            dbConn.getConnection().close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching room data.");
        }

        return roomData.toArray(new String[0][0]);
    }

    public static void main(String[] args) {
        new ViewAvailableRooms();
    }
}

