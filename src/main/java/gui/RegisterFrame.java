package gui;

import data.UserRepository;
import domain.User;
import java.awt.*;
import javax.swing.*;
import services.SessionManager;

public class RegisterFrame extends JFrame {
    private final JTextField nameField, emailField;
    private final JPasswordField passwordField, confirmPasswordField;
    private final JComboBox<String> userTypeCombo;
    private final JPasswordField adminKeyField;
    private final JLabel adminKeyLabel;
    
    private static final String ADMIN_KEY = "200108"; // Clave predeterminada
    
    public RegisterFrame() {
        setTitle("Prestige Events - Register");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        // Panel superior
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("PRESTIGE EVENTS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Create your account");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitleLabel);
        
        // Panel del formulario con scroll
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        // Full Name
        addFormField(formPanel, "Full Name", nameField = new JTextField());
        
        // Email
        addFormField(formPanel, "Email Address", emailField = new JTextField());
        
        // Password
        addFormField(formPanel, "Password", passwordField = new JPasswordField());
        
        // Confirm Password
        addFormField(formPanel, "Confirm Password", confirmPasswordField = new JPasswordField());
        
        // User Type
        JLabel userTypeLabel = new JLabel("User Type");
        userTypeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userTypeLabel.setForeground(new Color(52, 73, 94));
        userTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        userTypeCombo = new JComboBox<>(new String[]{"Client", "Administrator"});
        userTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userTypeCombo.setMaximumSize(new Dimension(400, 40));
        userTypeCombo.setBackground(Color.WHITE);
        userTypeCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        userTypeCombo.addActionListener(e -> toggleAdminKey());
        
        formPanel.add(userTypeLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(userTypeCombo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Admin Key (solo visible para administradores)
        adminKeyLabel = new JLabel("Administrator Key (6 digits)");
        adminKeyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        adminKeyLabel.setForeground(new Color(231, 76, 60));
        adminKeyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminKeyLabel.setVisible(false);
        
        adminKeyField = new JPasswordField();
        adminKeyField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        adminKeyField.setMaximumSize(new Dimension(400, 40));
        adminKeyField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(231, 76, 60)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        adminKeyField.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminKeyField.setVisible(false);
        
        formPanel.add(adminKeyLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(adminKeyField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Botón de registro
        JButton registerButton = new JButton("Create Account");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(400, 45));
        registerButton.setMaximumSize(new Dimension(400, 45));
        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(e -> handleRegister());
        
        // Link de login
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.setBackground(Color.WHITE);
        
        JLabel loginLabel = new JLabel("Already have an account? ");
        loginLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel loginLink = new JLabel("Sign In");
        loginLink.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginLink.setForeground(new Color(41, 128, 185));
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        
        loginPanel.add(loginLabel);
        loginPanel.add(loginLink);
        
        formPanel.add(registerButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(loginPanel);
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void addFormField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(52, 73, 94));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(400, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }
    
    private void toggleAdminKey() {
        boolean isAdmin = userTypeCombo.getSelectedIndex() == 1;
        adminKeyLabel.setVisible(isAdmin);
        adminKeyField.setVisible(isAdmin);
        revalidate();
        repaint();
    }
    
    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        boolean isAdmin = userTypeCombo.getSelectedIndex() == 1;
        String adminKey = new String(adminKeyField.getPassword());
        
        // Validaciones
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Registration Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "Passwords do not match",
                "Registration Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "Password must be at least 6 characters",
                "Registration Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar clave de administrador
        if (isAdmin && !adminKey.equals(ADMIN_KEY)) {
            int option = JOptionPane.showConfirmDialog(this,
                "Invalid administrator key.\nWould you like to register as a client instead?",
                "Invalid Key",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (option == JOptionPane.YES_OPTION) {
                userTypeCombo.setSelectedIndex(0);
                isAdmin = false;
            } else {
                return;
            }
        }
        
        try {
            UserRepository userRepo = new UserRepository();
            
            // Verificar si el email ya existe
            if (userRepo.emailExists(email)) {
                JOptionPane.showMessageDialog(this,
                    "This email is already registered",
                    "Registration Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Crear usuario
            String userType = isAdmin ? "ADMIN" : "CLIENT";
            User newUser = new User(name, email, password, userType);
            userRepo.add(newUser);
            
            // Login automático
            SessionManager.getInstance().login(newUser);
            
            JOptionPane.showMessageDialog(this,
                "Account created successfully!\nWelcome, " + name + "!",
                "Registration Successful",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            new MainFrame().setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error during registration: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}