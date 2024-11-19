package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;

public class Reception extends JFrame {

    public Reception() {
        super("Reception");
        setBounds(500, 200, 700, 500);
        setLayout(null);

        ImageIcon icon = new ImageIcon("/Users/anuragtiwari/Downloads/reception.png");
        Image img = icon.getImage().getScaledInstance(700, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(0, 0, 700, 200);
        add(imageLabel);

        JButton bookRoomButton = new JButton("Book Room");
        bookRoomButton.setBounds(50, 210, 300, 30);
        add(bookRoomButton);

        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.setBounds(50, 260, 300, 30);
        add(viewBookingsButton);

        JButton availableRoomsButton = new JButton("View Available Rooms");
        availableRoomsButton.setBounds(50, 310, 300, 30);
        add(availableRoomsButton);

        JButton viewEmployeeButton = new JButton("View Employees");
        viewEmployeeButton.setBounds(50, 360, 300, 30);
        add(viewEmployeeButton);

        JButton billingButton = new JButton("Billing");
        billingButton.setBounds(50, 410, 300, 30);
        add(billingButton);

        bookRoomButton.addActionListener(e -> new BookRoom());

        availableRoomsButton.addActionListener(e -> new ViewAvailableRooms());

        viewEmployeeButton.addActionListener(e -> new ViewEmployee());

        viewBookingsButton.addActionListener(e -> new ViewBookings());

        billingButton.addActionListener(e -> new Billing());

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Reception();
    }
}
