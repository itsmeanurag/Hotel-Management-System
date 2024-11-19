package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.*;

public class ViewEmployee extends JFrame {

    public ViewEmployee() {
        super("View Employees");

        setLayout(new BorderLayout());
        setBounds(300, 100, 800, 600);

        // Title Label
        JLabel titleLabel = new JLabel("Employee Records");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(new Color(70, 130, 180));  // Light Steel Blue background
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Fetch Employee Data
        String[] columns = {"ID", "Name", "Age", "Salary", "Phone", "Email", "Job"};
        String[][] data = fetchEmployeeData();

        // Create Table Model
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Prevent editing cells
            }
        };

        JTable employeeTable = new JTable(model);
        employeeTable.setRowHeight(30);  // Set row height to make it cleaner

        // Customize table header
        JTableHeader header = employeeTable.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 14));  // Set header font
        header.setBackground(new Color(70, 130, 180));  // Light Steel Blue header
        header.setForeground(Color.WHITE);  // White text color
        header.setReorderingAllowed(false);  // Disable column reordering

        // Customize cell appearance
        employeeTable.setFont(new Font("Tahoma", Font.PLAIN, 14));  // Set font for table cells
        employeeTable.setSelectionBackground(new Color(70, 130, 180));  // Light Steel Blue selection color
        employeeTable.setSelectionForeground(Color.WHITE);  // White text on selected rows
        employeeTable.setBackground(Color.WHITE);  // White background for cells

        // Center text in all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < employeeTable.getColumnCount(); i++) {
            employeeTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(70, 130, 180));  // Light Steel Blue background
        closeButton.setForeground(Color.black);  // White text color
        closeButton.setFont(new Font("Tahoma", Font.BOLD, 14));  // Bold font
        closeButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180)));  // Button border color
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private String[][] fetchEmployeeData() {
        java.util.List<String[]> rowData = new java.util.ArrayList<>();
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String query = "SELECT * FROM employee";
            Statement stmt = dbConn.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int colIndex = 1; colIndex <= columnCount; colIndex++) {
                    row[colIndex - 1] = rs.getString(colIndex);
                }
                rowData.add(row);
            }

            rs.close();
            stmt.close();
            dbConn.getConnection().close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching employee data.");
        }

        return rowData.toArray(new String[0][0]);
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}
