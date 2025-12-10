package gui;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {
    
    public WelcomeFrame() {
        setTitle("Prestige Events - Event Management System");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Panel superior con título
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("PRESTIGE EVENTS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Premium Event Management System");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(subtitleLabel);
        
        // Panel central con mensaje de bienvenida (con scroll)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        
        JLabel welcomeLabel = new JLabel("Welcome to Prestige Events!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(new Color(52, 73, 94));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Texto de bienvenida más compacto
        String[] features = {
            "We are your perfect partner for organizing unforgettable events.",
            "",
            "Our system allows you to:",
            "  • Select the ideal venue for your event",
            "  • Hire additional services (catering, decoration, etc.)",
            "  • Manage all logistics in a simple way",
            "  • Make secure online payments",
            "",
            "Click 'Get Started' to sign in or create an account."
        };
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        
        for (String line : features) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lineLabel.setForeground(new Color(52, 73, 94));
            lineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            textPanel.add(lineLabel);
            textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        // Agregar scroll al contenido
        JScrollPane scrollPane = new JScrollPane(textPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(scrollPane);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 247, 250));
        
        // Botón de inicio
        JButton startButton = new JButton("Get Started");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setBackground(new Color(46, 204, 113));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover en startButton
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(39, 174, 96));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(46, 204, 113));
            }
        });
        
        // Acciones de botones - MODIFICADO PARA IR A LOGIN
        startButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        
        buttonPanel.add(startButton);
        
        // Agregar componentes al panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
}