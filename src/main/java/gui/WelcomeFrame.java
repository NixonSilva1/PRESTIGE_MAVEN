package gui;

import java.awt.*;
import javax.swing.*;

public class WelcomeFrame extends JFrame {
    
    public WelcomeFrame() {
        setTitle("Prestige Events - Event Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal con gradiente
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(new Color(245, 247, 250));
        
        // Panel superior con título
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("PRESTIGE EVENTS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Premium Event Management");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitleLabel);
        
        // Panel central con mensaje de bienvenida
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        JLabel welcomeLabel = new JLabel("Welcome to Prestige Events!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(52, 73, 94));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea welcomeText = new JTextArea(
            "We are your perfect partner for organizing unforgettable events.\n\n" +
            "Our system allows you to:\n" +
            "  • Select the ideal venue for your event\n" +
            "  • Hire additional services (catering, decoration, etc.)\n" +
            "  • Manage all logistics in a simple way\n\n" +
            "Click 'Get Started' to begin your reservation."
        );
        welcomeText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeText.setEditable(false);
        welcomeText.setBackground(Color.WHITE);
        welcomeText.setLineWrap(true);
        welcomeText.setWrapStyleWord(true);
        welcomeText.setForeground(new Color(52, 73, 94));
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(welcomeText);
        
        // Botón de inicio mejorado
        JButton startButton = new JButton("Get Started");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        startButton.setPreferredSize(new Dimension(220, 55));
        startButton.setMaximumSize(new Dimension(220, 55));
        startButton.setBackground(new Color(46, 204, 113));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
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
        
        startButton.addActionListener(e -> {
            new MainFrame().setVisible(true);
            dispose();
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.add(startButton);
        
        // Agregar componentes
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
}